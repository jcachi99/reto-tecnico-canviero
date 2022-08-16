package com.maintenance.entities.services;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    List<Product> getAll();
    Product getById(Long id);

    Product save(Product product);

    Product update(Product product);

    ResponseDTO delete(Long productId);

    Page<Product> getAllActivePaged(Integer page, Integer size);
}
