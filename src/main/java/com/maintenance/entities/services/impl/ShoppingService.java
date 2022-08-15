package com.maintenance.entities.services.impl;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.dtos.ShoppingDTO;
import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.domain.entities.InvoiceDetail;
import com.maintenance.entities.domain.entities.Product;
import com.maintenance.entities.repository.InvoiceDetailRepository;
import com.maintenance.entities.services.IShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maintenance.entities.util.Constants.*;

@Service
@Slf4j
public class ShoppingService implements IShoppingService {

    @Autowired
    ProductService productService;

    @Autowired
    ClientService clientService;

    @Autowired
    InvoiceDetailRepository invoiceDetailRepository;

    @Transactional
    @Override
    public ResponseDTO shop(ShoppingDTO shoppingDTO) {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setProcessed(Boolean.FALSE);

        try{
            Client client = clientService.getById(shoppingDTO.getClient_id());
            if(client!=null){
                List<Product> products = shoppingDTO.getDetail().stream().map(d -> productService.getById(d.getProduct_id())).collect(Collectors.toList());

                shoppingDTO.getDetail().stream().forEach( shoppingDetailDTO -> {
                    Product p = products.stream().filter(product -> shoppingDetailDTO.getProduct_id()==product.getId()).findFirst().get();
                    shoppingDetailDTO.setAmmount(p.getPrice().multiply(BigDecimal.valueOf(shoppingDetailDTO.getQuantity())));
                });

                BigDecimal total = shoppingDTO.getDetail().stream().map(sd -> sd.getAmmount().add(sd.getAmmount())).reduce(BigDecimal.ZERO, BigDecimal::add);
                Long invoiceHeader = invoiceDetailRepository.executeInvoce(client.getId(),total);

                List<InvoiceDetail> invoiceDetails = new ArrayList<>();

                shoppingDTO.getDetail().stream().forEach(shoppingDetailDTO -> {
                    InvoiceDetail invoiceDetail = new InvoiceDetail();
                    invoiceDetail.setInvoice_header_id(invoiceHeader);
                    invoiceDetail.setProduct_id(shoppingDetailDTO.getProduct_id());
                    invoiceDetail.setQuantity(shoppingDetailDTO.getQuantity());
                    invoiceDetail.setAmmount(shoppingDetailDTO.getAmmount());

                    invoiceDetails.add(invoiceDetail);
                });

                invoiceDetailRepository.saveAll(invoiceDetails);

                responseDTO.setProcessed(Boolean.TRUE);
                responseDTO.setMessage(INVOICE_CREATION_SUCCESS.replace(REPLACE,invoiceHeader.toString()));

                return responseDTO;
            }else{
                responseDTO.setMessage(CLIENT_NOT_FOUND.replace(REPLACE,shoppingDTO.getClient_id().toString()));
                return responseDTO;
            }

        }catch (Exception e){
            log.info("Error al crear la factura, error: "+e.getMessage());
            responseDTO.setMessage(INVOICE_CREATION_FAIL);
            return responseDTO;
        }

    }
}
