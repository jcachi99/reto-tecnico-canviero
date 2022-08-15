package com.maintenance.entities.services.impl;

import com.maintenance.entities.domain.entities.Product;
import com.maintenance.entities.repository.ProductRepository;
import com.maintenance.entities.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }
}
