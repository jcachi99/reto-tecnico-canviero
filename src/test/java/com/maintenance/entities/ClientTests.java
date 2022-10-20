package com.maintenance.entities;

import static org.junit.jupiter.api.Assertions.*;
import com.maintenance.entities.domain.entities.Client;
import com.maintenance.entities.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Rollback(value = false)
    public void testSaveClient(){
        Client client = new Client(10L,"nombre2","ape pate","12356793","999901231","j_bncs@outloook.com",true,"2022-05-21","2022-05-21");
        Client client1 = clientRepository.save(client);

        assertNotNull(client1);
    }
}
