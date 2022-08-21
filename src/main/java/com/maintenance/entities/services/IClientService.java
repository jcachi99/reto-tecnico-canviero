package com.maintenance.entities.services;

import com.maintenance.entities.domain.dtos.ResponseLoadFileDTO;
import com.maintenance.entities.domain.entities.Client;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IClientService {

    List<Client> getAll();
    Client save(Client client);

    Client getById(Long id);

    Client update(Long clientId, Client client);

    Page<Client> getAllActivePaged(Integer page, Integer size);
    Page<Client> getAllPaged(Integer page, Integer size);

    void delete(Long clientId);

    ResponseLoadFileDTO loadFile(MultipartFile multipartFile);

}
