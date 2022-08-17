package com.maintenance.entities.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "client", schema = "db_maintenance")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull(message = "el campo name es obligatorio")
    @NotEmpty(message = "el campo name no puede ser vacio")
    @Column(name="name")
    private String name;

    @Column(name="sex")
    private Character sex;

    @Column(name="active")
    private Boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name="created_at")
    private String created_at;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name="updated_at")
    private String updated_at;
}
