package com.workshop.model.dao.factory;


import com.workshop.db.DB;
import com.workshop.model.dao.implementation.*;
import com.workshop.model.dao.inter.*;

public class DaoFactory {

	public static UsuarioInterface createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}

	public static MarcaDao createMarcaDao() {
		return new MarcaDaoJDBC(DB.getConnection());
	}

	public static ModeloDao createModeloDao() {
		return new ModeloDaoJDBC(DB.getConnection());
	}

	public static ClienteDao createClienteDao() {return new ClienteDaoJDBC(DB.getConnection());}

	public static MotoClienteJDBC createMotoClienteDao() {return new MotoClienteJDBC(DB.getConnection());}

    public static PecaDao createPecaDao() {
		return new PecaDaoJDBC(DB.getConnection());
    }
}
