package com.maintenance.entities.services.impl;

import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.exception.ResourceNotFoundException;
import com.maintenance.entities.repository.ClientRepository;
import com.maintenance.entities.services.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.maintenance.entities.util.Constants.*;

@Service
@Slf4j
public class ClientService implements IClientService {

    @Autowired
    ClientRepository clientRepository;

    private final Path root = Paths.get("upload");


    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        client.setActive(Boolean.TRUE);
        client.setCreated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return clientRepository.save(client);
    }

    @Override
    public Client getById(Long id) {
            return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente"));
    }

    @Override
    public Client update(Long clientId, Client client) {
        Client existingClient = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente"));
        existingClient.setName(client.getName());
        existingClient.setLast_name(client.getLast_name());
        existingClient.setDocument(client.getDocument());
        existingClient.setPhone(client.getPhone());
        existingClient.setEmail(client.getEmail());
        existingClient.setSex(client.getSex());
        existingClient.setActive(client.getActive());
        existingClient.setUpdated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return clientRepository.save(existingClient);
    }

    @Override
    public Page<Client> getAllActivePaged(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return clientRepository.findAllByActiveIsTrue(paging);
    }

    @Override
    public Page<Client> getAllPaged(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return clientRepository.findAll(paging);
    }

    @Override
    public void delete(Long clientId) {
        Client existingClient = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente"));
        try {
            clientRepository.delete(existingClient);
        }catch (Exception e){
            log.info(CLIENT_DELETE_FAIL+e.getMessage());
            throw new ExpressionException(clientId.toString(),e.getMessage());
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void save(MultipartFile multipartFile) {
        log.info(multipartFile.getOriginalFilename());
    }

    @Override
    public Resource load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public String deleteFile(String filename) {
        return null;
    }
}
