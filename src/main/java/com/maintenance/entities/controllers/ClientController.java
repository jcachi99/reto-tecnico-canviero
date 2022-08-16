package com.maintenance.entities.controllers;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.services.impl.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.maintenance.entities.util.Constants.*;

@RestController
@RequestMapping(path = "/api/entities/v1")
@Slf4j
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping(path = "/clients")
    public ResponseEntity<?> listAll(){
        List<Client> list = clientService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/clients/active/paged")
    public ResponseEntity<?> listAllActivePaged(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "3") Integer size){
        Page<Client> list = clientService.getAllActivePaged(page,size);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/clients/paged")
    public ResponseEntity<?> listAllPaged(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "3") Integer size){
        Page<Client> list = clientService.getAllPaged(page,size);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/client/{clientId}")
    public ResponseEntity<?> getById(@PathVariable Long clientId){
        Client responseC = clientService.getById(clientId);
        return responseC!=null ?
                new ResponseEntity<>(responseC, HttpStatus.OK)
                :new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,CLIENT_NOT_FOUND.replace(REPLACE,clientId.toString())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/client")
    public ResponseEntity<?> create(@RequestBody Client client){
        Client responseC = clientService.save(client);
        return responseC!=null ?
                new ResponseEntity<>(responseC, HttpStatus.OK)
                :new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,CLIENT_SAVE_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/client")
    public ResponseEntity<?> update(@RequestBody Client client){
        Client responseC = clientService.update(client);
        return responseC!=null ?
                new ResponseEntity<>(responseC, HttpStatus.OK)
                :new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,CLIENT_UPDATE_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/client/delete/{clientId}")
    public ResponseEntity<?> delete(@PathVariable Long clientId){
        ResponseDTO responseDTO = clientService.delete(clientId);
        return responseDTO.getProcessed() ?
                new ResponseEntity<>(responseDTO, HttpStatus.OK)
                :new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
