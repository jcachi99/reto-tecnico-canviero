package com.maintenance.entities.services.impl;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.dtos.ResponseLoadFileDTO;
import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.exception.ResourceNotFoundException;
import com.maintenance.entities.repository.ClientRepository;
import com.maintenance.entities.services.IClientService;
import com.maintenance.entities.util.ClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public ResponseLoadFileDTO loadFile(MultipartFile multipartFile) {
        log.info(multipartFile.getOriginalFilename());
        List<Client> clientsToSave = new ArrayList<>();
        List<Client> clientsError = new ArrayList<>();
        try {
            InputStream inputStream = multipartFile.getInputStream();
            int[] p={0};
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .forEach(l -> {
                        processLines(p[0],l,clientsToSave,clientsError);
                        p[0]++;
                    });
            if(clientsToSave.size()>0){
                clientRepository.saveAll(clientsToSave);
            }
            return new ResponseLoadFileDTO(clientsToSave, clientsError);
        }catch (Exception e){
            log.error("Error al leer lineas: "+e.getMessage());
            return new ResponseLoadFileDTO();
        }
    }

    private void processLines(int p,String s,List<Client> clientsToSave, List<Client> clientsError) {
        if(p>0){
            log.info("linea: "+s);
            List<String> attributes = new ArrayList<>();
            int index=0;
            int values = 0;
            for(int i=0;i<=4;i++){
                if(i==0){
                    index = s.indexOf(",");
                    String name = s.substring(0,index)!=""?ClientUtil.getNameOrLastFormated(s.substring(0,index)):"ERROR";
                    attributes.add(name);
                    if(ClientUtil.validateValue(name))values++;
                }
                if(i==1){
                    index = s.indexOf("|");
                    String doc = s.substring(0,index)!=""?ClientUtil.getDocumentFormated(s.substring(0,index)):"ERROR";
                    attributes.add(doc);
                    if(ClientUtil.validateValue(doc))values++;
                }
                if(i==2){
                    index = s.indexOf(",");
                    String phone = s.substring(0,index)!=""?ClientUtil.getPhoneFormated(s.substring(0,index)):"ERROR";
                    attributes.add(phone);
                    if(ClientUtil.validateValue(phone))values++;
                }
                if(i==3){
                    index=15;
                    String lastName = s.substring(0,index)!=""?ClientUtil.getNameOrLastFormated(s.substring(0,index)):"ERROR";
                    attributes.add(lastName);
                    s=s.substring(index);
                    if(ClientUtil.validateValue(lastName))values++;
                }
                if(i==4){
                    index=s.length()>20?20:s.length();
                    String email = s.substring(0,index)!=""?ClientUtil.getEmailFormated(s.substring(0,index)):"ERROR";
                    attributes.add(email);
                    if(ClientUtil.validateValue(email))values++;
                }
                if(i!=3 && i!=4){
                    s=s.substring(index+1);
                }
            }
            log.info(attributes.toString());
            Client client = new Client();
            client.setName(attributes.get(0));
            client.setDocument(attributes.get(1));
            client.setPhone(attributes.get(2));
            client.setLast_name(attributes.get(3));
            client.setEmail(attributes.get(4));

            if(values==5){
                client.setCreated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                client.setActive(Boolean.TRUE);
                clientsToSave.add(client);
            }
            else{
                clientsError.add(client);
            }

        }
    }

}
