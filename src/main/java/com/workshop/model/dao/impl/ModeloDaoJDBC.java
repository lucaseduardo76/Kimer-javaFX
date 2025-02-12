package com.workshop.model.dao.impl;

import com.workshop.db.DB;
import com.workshop.db.DbException;
import com.workshop.db.DbIntegrityException;
import com.workshop.kimer.util.Utils;
import com.workshop.model.dao.DaoFactory;
import com.workshop.model.dao.MarcaDao;
import com.workshop.model.dao.ModeloDao;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDaoJDBC implements ModeloDao {
    private final Connection conn;
    private final MarcaDao marcaDao;

    public ModeloDaoJDBC(Connection conn) {
        this.conn = conn;
        this.marcaDao = DaoFactory.createMarcaDao();
    }

    @Override
    public Modelo findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM modelo WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Modelo obj = new Modelo();
                obj.setId(rs.getInt("Id"));
                obj.setModelo(rs.getString("Modelo"));
                obj.setCilindrada(rs.getString("Cilindrada"));
                obj.setMarca(marcaDao.findById(rs.getInt("Marca")));
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
    public List<Modelo> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM modelo ORDER BY id");
            rs = st.executeQuery();

            List<Modelo> list = new ArrayList<>();

            while (rs.next()) {
                Modelo obj = new Modelo();
                obj.setId(rs.getInt("Id"));
                obj.setModelo(rs.getString("Modelo"));
                obj.setCilindrada(rs.getString("Cilindrada"));
                obj.setMarca(marcaDao.findById(rs.getInt("Marca_id")));
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
    public void insert(Modelo obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO modelo " +
                            "(Modelo, Cilindrada, Marca_id) " +
                            "VALUES " +
                            "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getModelo());
            st.setString(2, obj.getCilindrada());
            st.setInt(3, obj.getMarca().getId());

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
    public void update(Modelo obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE modelo " +
                            "SET Modelo = ?, " +
                            "Cilindrada = ?, " +
                            "Marca_id = ? " +
                            "WHERE Id = ?"
            );


            st.setString(1, obj.getModelo());
            st.setString(2, obj.getCilindrada());
            st.setInt(3, obj.getMarca().getId());
            st.setInt(4, obj.getId());

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
                    "DELETE FROM modelo WHERE Id = ?");

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
