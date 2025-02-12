package com.workshop.model.service;

import com.workshop.model.dao.ClienteDao;
import com.workshop.model.dao.DaoFactory;
import com.workshop.model.entities.Cliente;


import java.util.List;

public class ClienteService {

    private final ClienteDao clienteDao;

    public ClienteService() {
        this.clienteDao = DaoFactory.createClienteDao();
    }

    public void insertOrUpdateMarca(Cliente cliente) {
        if(cliente.getId() == null)
            clienteDao.insert(cliente);
        else
            clienteDao.update(cliente);
    }

    public void delete(Cliente cliente) {
        clienteDao.deleteById(cliente.getId());
    }

    public Cliente findById(int id) {
        return clienteDao.findById(id);
    }

    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }
}
