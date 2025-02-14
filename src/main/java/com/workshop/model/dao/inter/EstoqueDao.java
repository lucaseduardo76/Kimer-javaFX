package com.workshop.model.dao.inter;

import com.workshop.model.entities.Estoque;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.PecaNoEstoque;

import java.util.List;

public interface EstoqueDao {
    void insert(PecaNoEstoque obj);

    PecaNoEstoque findByIdPeca(Integer id);

    List<PecaNoEstoque> findAll();

    void decreaseByIdPeca(PecaNoEstoque obj);
}
