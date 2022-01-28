package com.example.demogra.mapper;


import com.example.demogra.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface User2DTO {

    Client clientToClient(Client cl);

}