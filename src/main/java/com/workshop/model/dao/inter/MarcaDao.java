package com.workshop.model.dao.inter;



import com.workshop.model.entities.Marca;

import java.util.List;

public interface MarcaDao {

	void insert(Marca obj);
	void update(Marca obj);
	void deleteById(Integer id);
	Marca findById(Integer id);
	List<Marca> findAll();

	Marca findByName(String name);
}
