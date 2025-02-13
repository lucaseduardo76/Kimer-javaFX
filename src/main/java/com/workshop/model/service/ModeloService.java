package com.workshop.model.service;

import com.workshop.model.dao.factory.DaoFactory;
import com.workshop.model.dao.inter.ModeloDao;
import com.workshop.model.entities.Modelo;

import java.util.List;

public class ModeloService {

    private ModeloDao modeloDao;

    public ModeloService() {
        this.modeloDao = DaoFactory.createModeloDao();
    }

    public void insertOrUpdateMarca(Modelo modelo) {
        if(modelo.getId() == null)
            modeloDao.insert(modelo);
        else
            modeloDao.update(modelo);
    }

    public void delete(Modelo modelo) {
        modeloDao.deleteById(modelo.getId());
    }

    public Modelo findById(int id) {
        return modeloDao.findById(id);
    }

    public List<Modelo> findAll() {
        return modeloDao.findAll();
    }


}
