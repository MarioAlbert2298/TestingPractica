package com.example.demogra.controller;


import com.example.demogra.entity.Client;
import com.example.demogra.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService dao;

    @GetMapping
    public ResponseEntity<List<Client>> getall(){

        List<Client> lisClient = dao.getAll();

        if (lisClient.isEmpty())
            return ResponseEntity.noContent().build();


        return ResponseEntity.ok( lisClient );
    }

    @GetMapping("{id}")
    public ResponseEntity<Client> getOne( @PathVariable Long id){
         Client cli = dao.getOne(id);

         if (cli == null)
             return ResponseEntity.noContent().build();

        return ResponseEntity.ok( cli );
    }

    @PostMapping
    public ResponseEntity<Client>  save(@RequestBody Client cli){
         Client cliente = dao.save(cli);
         if (cliente !=null)
             return ResponseEntity.created(null).build();
         else
             return  ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok( dao.delete(id));
    }

    @PutMapping
    public ResponseEntity<Client> update(@RequestBody Client cli ){

        Client cliente = dao.update(cli);
        if (cliente ==null)
             return  ResponseEntity.badRequest().build();

        return ResponseEntity.ok( cliente );
    }

}
