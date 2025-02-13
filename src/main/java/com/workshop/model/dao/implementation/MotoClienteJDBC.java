package com.workshop.model.dao.implementation;

import com.workshop.db.DB;
import com.workshop.db.DbException;
import com.workshop.db.DbIntegrityException;
import com.workshop.model.dao.inter.MotoClienteDao;
import com.workshop.model.entities.MotoCliente;
import com.workshop.model.service.ClienteService;
import com.workshop.model.service.ModeloService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoClienteJDBC implements MotoClienteDao {

    private Connection conn;
    private ModeloService modeloService;
    private ClienteService clienteService;

    public MotoClienteJDBC(Connection conn) {
        this.conn = conn;
        this.modeloService = new ModeloService();
        this.clienteService = new ClienteService();
    }

    @Override
    public MotoCliente findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM moto_cliente WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                MotoCliente obj = new MotoCliente();
                obj.setId(id);
                obj.setPlaca(rs.getString("placa"));
                obj.setAno(rs.getInt("ano"));
                obj.setModelo(modeloService.findById(rs.getInt("modelo_id")));
                obj.setCliente(clienteService.findById(rs.getInt("cliente_id")));
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
    public List<MotoCliente> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM moto_cliente ORDER BY id");
            rs = st.executeQuery();

            List<MotoCliente> list = new ArrayList<>();

            while (rs.next()) {
                MotoCliente obj = new MotoCliente();
                obj.setId(rs.getInt("id"));
                obj.setPlaca(rs.getString("placa"));
                obj.setAno(rs.getInt("ano"));
                obj.setModelo(modeloService.findById(rs.getInt("modelo_id")));
                obj.setCliente(clienteService.findById(rs.getInt("cliente_id")));
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
    public void insert(MotoCliente obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO moto_cliente " +
                            "(placa, ano, modelo_id, cliente_id) " +
                            "VALUES " +
                            "(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getPlaca());
            st.setInt(2, obj.getAno());
            st.setInt(3, obj.getModelo().getId());
            st.setInt(4, obj.getCliente().getId());

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
    public void update(MotoCliente obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE moto_cliente " +
                            "SET placa = ?, ano = ?, modelo_id = ?, cliente_id = ? " +
                            "WHERE id = ?"
            );

            st.setString(1, obj.getPlaca());
            st.setInt(2, obj.getAno());
            st.setInt(3, obj.getModelo().getId());
            st.setInt(4, obj.getCliente().getId());
            st.setInt(5, obj.getId());

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
                    "DELETE FROM moto_cliente WHERE Id = ?");

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
