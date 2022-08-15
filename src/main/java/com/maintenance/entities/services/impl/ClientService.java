package com.maintenance.entities.services.impl;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.repository.ClientRepository;
import com.maintenance.entities.services.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.maintenance.entities.util.Constants.*;

@Service
@Slf4j
public class ClientService implements IClientService {

    @Autowired
    ClientRepository clientRepository;


    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        try {
            return clientRepository.save(client);
        }catch (Exception e){
            log.info(CLIENT_SAVE_FAIL+e.getMessage());
            return null;
        }
    }

    @Override
    public Client getById(Long id) {
        try {
            return clientRepository.findById(id).get();
        }catch (Exception e){
            log.info(CLIENT_NOT_FOUND+e.getMessage());
            return null;
        }

    }

    @Override
    public Client update(Client client) {
        try{
            Client c = this.getById(client.getId());
            if(c!=null){
                return clientRepository.save(client);
            }else{
                return null;
            }
        }catch (Exception e){
            log.info(CLIENT_UPDATE_FAIL+e.getMessage());
            return null;
        }
    }

    @Override
    public Page<Client> getAllActivePaged(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return clientRepository.findAllByActiveIsTrue(paging);
    }

    @Override
    public ResponseDTO delete(Long clientId) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setProcessed(Boolean.FALSE);
        Client client = this.getById(clientId);
        if(client!=null){
            try {
                clientRepository.deleteById(clientId);
                responseDTO.setProcessed(Boolean.TRUE);
                responseDTO.setMessage(CLIENT_DELETE_SUCCESS.replace(REPLACE,clientId.toString()));
            }catch (Exception e){
                responseDTO.setMessage(CLIENT_DELETE_FAIL.replace(REPLACE,clientId.toString()));
                log.info(CLIENT_DELETE_FAIL+e.getMessage());
            }
        }else{
            responseDTO.setMessage(CLIENT_NOT_FOUND.replace(REPLACE,clientId.toString()));
        }
        return responseDTO;
    }
}
