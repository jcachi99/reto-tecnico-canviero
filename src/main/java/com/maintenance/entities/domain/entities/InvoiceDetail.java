package com.maintenance.entities.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_detail", schema = "db_maintenance")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="invoice_header_id")
    private Long invoice_header_id;

    @JoinColumn(name = "product_id")
    private Long product_id;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="ammount")
    private BigDecimal ammount;

}
