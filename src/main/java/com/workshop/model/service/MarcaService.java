package com.workshop.model.service;

import com.workshop.kimer.util.Utils;
import com.workshop.model.dao.DaoFactory;
import com.workshop.model.dao.MarcaDao;
import com.workshop.model.entities.Marca;
import jdk.jshell.execution.Util;

import java.util.List;
import java.util.stream.Collectors;

public class MarcaService {

    private MarcaDao marcaDao;

    public MarcaService() {
        this.marcaDao = DaoFactory.createMarcaDao();
    }

    public void insertOrUpdateMarca(Marca marca) {
        if(marca.getId() == null)
            marcaDao.insert(marca);
        else
            marcaDao.update(marca);
    }

    public void delete(Marca marca) {
        marcaDao.deleteById(marca.getId());
    }

    public Marca findById(int id) {
        return marcaDao.findById(id);
    }

    public List<Marca> findAll() {
        return marcaDao.findAll();
    }


}
