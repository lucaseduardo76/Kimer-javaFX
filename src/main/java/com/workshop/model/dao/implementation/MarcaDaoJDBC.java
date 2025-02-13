package com.workshop.model.dao.implementation;

import com.workshop.db.DB;
import com.workshop.db.DbException;
import com.workshop.db.DbIntegrityException;
import com.workshop.util.Utils;
import com.workshop.model.dao.inter.MarcaDao;
import com.workshop.model.entities.Marca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDaoJDBC implements MarcaDao {
    private Connection conn;

    public MarcaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Marca findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM marca WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Marca obj = new Marca();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
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

    @Override
    public List<Marca> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM marca ORDER BY id");
            rs = st.executeQuery();

            List<Marca> list = new ArrayList<>();

            while (rs.next()) {
                Marca obj = new Marca();
                obj.setId(rs.getInt("Id"));
                obj.setNome(Utils.capitalizeWords(rs.getString("Nome")));
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Marca findByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM marca WHERE Nome = ?");
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                Marca obj = new Marca();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
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

    @Override
    public void insert(Marca obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO marca " +
                            "(Nome) " +
                            "VALUES " +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Marca obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE marca " +
                            "SET Nome = ? " +
                            "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM marca WHERE Id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }
}
