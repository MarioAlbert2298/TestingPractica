package com.example.demogra.service;

import com.example.demogra.entity.Client;
import com.example.demogra.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository dao;
    @Override
    public List<Client> getAll() {
        return  dao.findAll();
    }

    @Override
    public Client save(Client entity) {
        Client existentClient = null;
        if (entity.getId() != null) existentClient = dao.findById(entity.getId()).orElse(null);

        if (existentClient != null)
            return existentClient;
        else
            return dao.save(entity);

    }

    @Override
    public Client update(Client entity) {
        Client existentClient = null;
        if (entity.getId() != null) existentClient = dao.findById(entity.getId()).orElse(null);

        existentClient.setName( entity.getName());
        existentClient.setCity( entity.getCity());


            return dao.save(existentClient);

    }

    @Override
    public Boolean delete(Long id) {

        Client existentClient = dao.findById(id).orElse(null);

        dao.delete(existentClient);
        return true;
    }

    @Override
    public Client getOne(Long id) {

        return dao.findById(id).orElse(null);

    }
}
