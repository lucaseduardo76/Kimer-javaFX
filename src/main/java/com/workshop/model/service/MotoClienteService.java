package com.workshop.model.service;

import com.workshop.model.dao.factory.DaoFactory;
import com.workshop.model.dao.inter.MarcaDao;
import com.workshop.model.dao.inter.MotoClienteDao;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.MotoCliente;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@RequiredArgsConstructor
public class MotoClienteService {

    private final MotoClienteDao motoClienteDao;

    public MotoClienteService() {
        this.motoClienteDao = DaoFactory.createMotoClienteDao();
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
}
