package com.workshop.model.dao.implementation;

import com.workshop.db.DB;
import com.workshop.db.DbException;
import com.workshop.db.DbIntegrityException;
import com.workshop.model.dao.inter.PecaDao;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Peca;
import com.workshop.util.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaDaoJDBC implements PecaDao {
    private Connection conn;

    public PecaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Peca findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM peca WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Peca obj = new Peca();
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
    public List<Peca> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM peca ORDER BY id");
            rs = st.executeQuery();

            List<Peca> list = new ArrayList<>();

            while (rs.next()) {
                Peca obj = new Peca();
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
    public void insert(Peca obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO peca " +
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
    public void update(Peca obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE peca " +
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
                    "DELETE FROM peca WHERE Id = ?");

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
