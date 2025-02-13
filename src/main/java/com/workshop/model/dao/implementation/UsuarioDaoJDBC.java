package com.workshop.model.dao.implementation;



import com.workshop.db.DB;
import com.workshop.db.DbException;
import com.workshop.model.dao.inter.UsuarioInterface;
import com.workshop.model.entities.Usuario;

import java.sql.*;


public class UsuarioDaoJDBC implements UsuarioInterface {

    	private Connection conn;

	public UsuarioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

    @Override
    public Usuario getUsuario(String username) {
        PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM usuario WHERE username = ?");
			st.setString(1, username);
			rs = st.executeQuery();
			if (rs.next()) {
                Usuario obj = new Usuario();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
                obj.setUsername(rs.getString("Username"));
                obj.setSenha(rs.getString("Senha"));
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
    }
}
