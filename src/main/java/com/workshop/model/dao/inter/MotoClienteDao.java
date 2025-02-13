package com.workshop.model.dao.inter;


import com.workshop.model.entities.MotoCliente;

import java.util.List;

public interface MotoClienteDao {
    void insert(com.workshop.model.entities.MotoCliente obj);
    void update(com.workshop.model.entities.MotoCliente obj);
    void deleteById(Integer id);
    com.workshop.model.entities.MotoCliente findById(Integer id);
    List<com.workshop.model.entities.MotoCliente> findAll();

    List<MotoCliente> findByClienteId(int id);

    List<MotoCliente> findByPlaca(String placa);
}
