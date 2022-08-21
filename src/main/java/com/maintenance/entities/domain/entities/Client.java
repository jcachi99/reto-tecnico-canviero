package com.maintenance.entities.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotNull(message = "el campo last name es obligatorio")
    @NotEmpty(message = "el campo last name no puede ser vacio")
    @Column(name="last_name")
    private String last_name;

    @NotNull(message = "el campo document es obligatorio")
    @NotEmpty(message = "el campo document no puede ser vacio")
    @Column(name="document")
    private String document;

    @NotNull(message = "el campo phone es obligatorio")
    @NotEmpty(message = "el campo phone no puede ser vacio")
    @Column(name="phone")
    private String phone;

    @NotNull(message = "el campo email es obligatorio")
    @NotEmpty(message = "el campo email no puede ser vacio")
    @Email(message = "debe ser un email", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.com", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name="email")
    private String email;

    @NotNull(message = "el campo sex es obligatorio")
//    @NotEmpty(message = "el campo sex no puede ser vacio")
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
