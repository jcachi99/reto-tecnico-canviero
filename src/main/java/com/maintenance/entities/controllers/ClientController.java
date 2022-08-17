package com.maintenance.entities.controllers;

import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.services.impl.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.maintenance.entities.util.Constants.CLIENT_DELETE_SUCCESS;
import static com.maintenance.entities.util.Constants.REPLACE;

@RestController
@RequestMapping(path = "/api/client/v1")
@Slf4j
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public ResponseEntity<?> listAll(){
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping(path = "/active/paged")
    public ResponseEntity<?> listAllActivePaged(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "3") Integer size){
        Page<Client> list = clientService.getAllActivePaged(page,size);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/paged")
    public ResponseEntity<?> listAllPaged(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "3") Integer size){
        Page<Client> list = clientService.getAllPaged(page,size);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/{clientId}")
    public ResponseEntity<?> getById(@PathVariable Long clientId){
        return ResponseEntity.ok(clientService.getById(clientId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Client client){
        return ResponseEntity.ok(clientService.save(client));
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<?> update(@PathVariable Long clientId,@RequestBody Client client){
        return ResponseEntity.ok(clientService.update(clientId, client));
    }

    @PostMapping(path = "/delete/{clientId}")
    public ResponseEntity<?> delete(@PathVariable Long clientId) throws Exception {
        clientService.delete(clientId);
        return ResponseEntity.ok(CLIENT_DELETE_SUCCESS.replace(REPLACE,clientId.toString()));
    }

}
