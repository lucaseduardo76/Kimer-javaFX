package com.workshop.model.service;

import com.workshop.model.dao.factory.DaoFactory;
import com.workshop.model.dao.inter.ClienteDao;
import com.workshop.model.dao.inter.MotoClienteDao;
import com.workshop.model.entities.Cliente;
import com.workshop.model.entities.MotoCliente;


import java.util.ArrayList;
import java.util.List;


public class MotoClienteService {

    private final MotoClienteDao motoClienteDao;
    private final ClienteDao clienteDao;

    public MotoClienteService() {
        this.motoClienteDao = DaoFactory.createMotoClienteDao();
        this.clienteDao = DaoFactory.createClienteDao();
    }

    public void insertOrUpdateMarca(MotoCliente motoCliente) {
        if(motoCliente.getId() == null)
            motoClienteDao.insert(motoCliente);
        else
            motoClienteDao.update(motoCliente);
    }

    public void delete(MotoCliente motoCliente) {
        motoClienteDao.deleteById(motoCliente.getId());
    }

    public MotoCliente findById(int id) {
        return motoClienteDao.findById(id);
    }

    public List<MotoCliente> findAll() {
        return motoClienteDao.findAll();
    }

    public List<MotoCliente> findByClienteCpf(String cpf) {
        List<Cliente> clientes = clienteDao.findByCpf(cpf);
        List<MotoCliente> motoClientes = new ArrayList<>();

        clientes.forEach(cliente -> {
            List<MotoCliente> motos = motoClienteDao.findByClienteId(cliente.getId());
            motoClientes.addAll(motos);
        });

        return motoClientes;
    }

    public List<MotoCliente> findByClienteName(String nome) {
        List<Cliente> clientes = clienteDao.findByName(nome);
        List<MotoCliente> motoClientes = new ArrayList<>();

        clientes.forEach(cliente -> {
            List<MotoCliente> motos = motoClienteDao.findByClienteId(cliente.getId());
            motoClientes.addAll(motos);
        });
        return motoClientes;
    }

    public List<MotoCliente> findByPlaca(String placa) {
        return motoClienteDao.findByPlaca(placa);
    }
}
