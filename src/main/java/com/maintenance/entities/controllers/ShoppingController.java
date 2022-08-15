package com.maintenance.entities.controllers;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.dtos.ShoppingDTO;
import com.maintenance.entities.services.impl.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/entities/v1")
@Slf4j
public class ShoppingController {

    @Autowired
    ShoppingService shoppingService;

    @PostMapping(path = "/shopping")
    public ResponseEntity<?> create(@RequestBody ShoppingDTO shoppingDTO){
        ResponseDTO responseDTO = shoppingService.shop(shoppingDTO);
        return responseDTO.getProcessed() ?
                new ResponseEntity<>(responseDTO, HttpStatus.OK)
                :new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
