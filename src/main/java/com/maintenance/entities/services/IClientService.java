package com.maintenance.entities.services;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.entities.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClientService {

    List<Client> getAll();
    Client save(Client client);

    Client getById(Long id);

    Client update(Client client);

    Page<Client> getAllActivePaged(Integer page, Integer size);
    Page<Client> getAllPaged(Integer page, Integer size);

    ResponseDTO delete(Long clientId);
}
