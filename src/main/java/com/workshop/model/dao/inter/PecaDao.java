package com.workshop.model.dao.inter;

import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Peca;

import java.util.List;

public interface PecaDao {
    void insert(Peca obj);
    void update(Peca obj);
    void deleteById(Integer id);
    Peca findById(Integer id);
    List<Peca> findAll();
}
