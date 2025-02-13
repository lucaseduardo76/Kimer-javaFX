package com.workshop.model.dao.inter;


import java.util.List;

public interface MotoClienteDao {
    void insert(com.workshop.model.entities.MotoCliente obj);
    void update(com.workshop.model.entities.MotoCliente obj);
    void deleteById(Integer id);
    com.workshop.model.entities.MotoCliente findById(Integer id);
    List<com.workshop.model.entities.MotoCliente> findAll();
}
