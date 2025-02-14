package com.workshop.model.service;



import com.workshop.model.dao.factory.DaoFactory;
import com.workshop.model.dao.inter.PecaDao;
import com.workshop.model.entities.Peca;

import java.util.List;

public class PecaService {
    private PecaDao pecaDao;

    public PecaService() {
        this.pecaDao = DaoFactory.createPecaDao();
    }

    public void insertOrUpdateMarca(Peca peca) {
        if(peca.getId() == null)
            pecaDao.insert(peca);
        else
            pecaDao.update(peca);
    }

    public void delete(Peca peca) {
        pecaDao.deleteById(peca.getId());
    }

    public Peca findById(int id) {
        return pecaDao.findById(id);
    }

    public List<Peca> findAll() {
        return pecaDao.findAll();
    }

}
