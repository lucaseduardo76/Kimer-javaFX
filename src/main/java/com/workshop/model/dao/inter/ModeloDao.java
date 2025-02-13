package com.workshop.model.dao.inter;



import com.workshop.model.entities.Modelo;

import java.util.List;

public interface ModeloDao {

	void insert(Modelo obj);
	void update(Modelo obj);
	void deleteById(Integer id);
	Modelo findById(Integer id);
	List<Modelo> findAll();
}
