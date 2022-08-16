package com.maintenance.entities.services.impl;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.domain.entities.Product;
import com.maintenance.entities.repository.ProductRepository;
import com.maintenance.entities.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.maintenance.entities.util.Constants.*;

@Service
@Slf4j
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        try {
            return productRepository.findById(id).get();
        }catch (Exception e){
            log.info(PRODUCT_NOT_FOUND+e.getMessage());
            return null;
        }
    }

    @Override
    public Product save(Product product) {
        try {
            return productRepository.save(product);
        }catch (Exception e){
            log.info(PRODUCT_SAVE_FAIL +e.getMessage());
            return null;
        }
    }

    @Override
    public Product update(Product product) {
        try{
            Product c = this.getById(product.getId());
            if(c!=null){
                return productRepository.save(product);
            }else{
                return null;
            }
        }catch (Exception e){
            log.info(PRODUCT_UPDATE_FAIL+e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseDTO delete(Long productId) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setProcessed(Boolean.FALSE);
        Product product = this.getById(productId);
        if(product!=null){
            try {
                productRepository.deleteById(productId);
                responseDTO.setProcessed(Boolean.TRUE);
                responseDTO.setMessage(PRODUCT_DELETE_SUCCESS.replace(REPLACE,productId.toString()));
            }catch (Exception e){
                responseDTO.setMessage(PRODUCT_DELETE_FAIL.replace(REPLACE,productId.toString()));
                log.info(PRODUCT_DELETE_FAIL+e.getMessage());
            }
        }else{
            responseDTO.setMessage(PRODUCT_NOT_FOUND.replace(REPLACE,productId.toString()));
        }
        return responseDTO;
    }

    @Override
    public Page<Product> getAllActivePaged(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findAllByActiveIsTrue(paging);
    }

}
