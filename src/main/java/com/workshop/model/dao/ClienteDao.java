package com.workshop.model.dao;

import com.workshop.model.entities.Cliente;

import java.util.List;

public interface ClienteDao {

    void insert(Cliente obj);
    void update(Cliente obj);
    void deleteById(Integer id);
    Cliente findById(Integer id);
    List<Cliente> findAll();
}
