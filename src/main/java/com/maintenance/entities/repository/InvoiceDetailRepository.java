package com.maintenance.entities.repository;

import com.maintenance.entities.domain.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

    @Query(value = "execute db_maintenance.createinvoce @clientId = :clientId,@total = :total", nativeQuery = true)
    Long executeInvoce(@Param("clientId") Long clientId, @Param("total") BigDecimal total);

}
