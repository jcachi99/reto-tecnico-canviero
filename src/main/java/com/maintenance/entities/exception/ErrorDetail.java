package com.maintenance.entities.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

    private Date timestamp;
    private String message;
    private String detail;

}
