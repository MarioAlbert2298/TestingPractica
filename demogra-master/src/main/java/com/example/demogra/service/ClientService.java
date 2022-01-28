package com.example.demogra.service;


import com.example.demogra.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ClientService {

    List<Client> getAll();
    Client save(Client entity);
    Boolean delete(Long id);
    Client getOne (Long id );
    Client update(Client entity);

}
