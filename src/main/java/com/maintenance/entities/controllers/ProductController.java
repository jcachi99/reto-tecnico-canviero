package com.maintenance.entities.controllers;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.entities.Product;
import com.maintenance.entities.services.impl.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.maintenance.entities.util.Constants.*;

@RestController
@RequestMapping(path = "/api/entities/v1")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(path = "/products")
    public ResponseEntity<?> listAll(){
        List<Product> list = productService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/products/active/paged")
    public ResponseEntity<?> listAllActivePaged(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "3") Integer size){
        Page<Product> list = productService.getAllActivePaged(page,size);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/products/paged")
    public ResponseEntity<?> listAllPaged(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "3") Integer size){
        Page<Product> list = productService.getAllPaged(page,size);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/product/{productId}")
    public ResponseEntity<?> getById(@PathVariable Long productId){
        Product responseP = productService.getById(productId);
        return responseP!=null ?
                new ResponseEntity<>(responseP, HttpStatus.OK)
                :new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,PRODUCT_NOT_FOUND.replace(REPLACE,productId.toString())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/product")
    public ResponseEntity<?> create(@RequestBody Product product){
        Product responseP = productService.save(product);
        return responseP!=null ?
                new ResponseEntity<>(responseP, HttpStatus.OK)
                :new ResponseEntity<>(new ResponseDTO(Boolean.FALSE, PRODUCT_SAVE_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/product")
    public ResponseEntity<?> update(@RequestBody Product product){
        Product responseP = productService.update(product);
        return responseP!=null ?
                new ResponseEntity<>(responseP, HttpStatus.OK)
                :new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,PRODUCT_UPDATE_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/product/delete/{productId}")
    public ResponseEntity<?> delete(@PathVariable Long productId){
        ResponseDTO responseDTO = productService.delete(productId);
        return responseDTO.getProcessed() ?
                new ResponseEntity<>(responseDTO, HttpStatus.OK)
                :new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
