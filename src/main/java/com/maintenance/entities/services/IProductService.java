package com.maintenance.entities.services;

import com.maintenance.entities.domain.entities.Product;

public interface IProductService {

    Product getById(Long id);
}
