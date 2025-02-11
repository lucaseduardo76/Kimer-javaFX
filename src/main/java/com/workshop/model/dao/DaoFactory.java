package com.workshop.model.dao;


import com.workshop.db.DB;
import com.workshop.model.dao.impl.MarcaDaoJDBC;
import com.workshop.model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {

	public static UsuarioInterface createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}

	public static MarcaDao createMarcaDao() {
		return new MarcaDaoJDBC(DB.getConnection());
	}
}
