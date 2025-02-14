package com.workshop.model.service;



import com.workshop.model.dao.factory.DaoFactory;
import com.workshop.model.dao.inter.EstoqueDao;
import com.workshop.model.entities.Estoque;
import com.workshop.model.entities.PecaNoEstoque;

import java.util.List;

public class EstoqueService {

    private EstoqueDao estoqueDao;

    public EstoqueService() {
        this.estoqueDao = DaoFactory.createEstoqueDao();
    }

    public void insertOrUpdateMarca(PecaNoEstoque estoque) {
        estoqueDao.insert(estoque);
    }

    public void decreaseByIdPeca(PecaNoEstoque pecaNoEstoque) {
        estoqueDao.decreaseByIdPeca(pecaNoEstoque);
    }

    public List<PecaNoEstoque> findAll() {return estoqueDao.findAll();}

}
