package com.maintenance.entities.repository;

import com.maintenance.entities.domain.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Page<Client> findAllByActiveIsTrue(Pageable pageable);
    Page<Client> findAll(Pageable pageable);
}
