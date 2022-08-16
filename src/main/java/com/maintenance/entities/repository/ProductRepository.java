package com.maintenance.entities.repository;

import com.maintenance.entities.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByActiveIsTrue(Pageable pageable);
    Page<Product> findAll(Pageable pageable);

}
