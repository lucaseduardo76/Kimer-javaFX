package com.workshop.model.dao.implementation;

import com.workshop.db.DB;
import com.workshop.db.DbException;
import com.workshop.db.DbIntegrityException;
import com.workshop.model.dao.factory.DaoFactory;
import com.workshop.model.dao.inter.EstoqueDao;
import com.workshop.model.dao.inter.PecaDao;
import com.workshop.model.entities.Peca;
import com.workshop.model.entities.PecaNoEstoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDaoJDBC implements EstoqueDao {

    private Connection conn;
    private PecaDao pecaDao;

    public EstoqueDaoJDBC(Connection conn) {
        this.conn = conn;
        this.pecaDao = DaoFactory.createPecaDao();
    }

    @Override
    public PecaNoEstoque findByIdPeca(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM estoque WHERE peca = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            PecaNoEstoque pecaNoEstoque = new PecaNoEstoque();
            if (rs.next()) {

                Peca peca = pecaDao.findById(rs.getInt("peca"));
                pecaNoEstoque.setPeca(peca);
                pecaNoEstoque.setId(rs.getInt("id"));
                pecaNoEstoque.addQuantidade(rs.getDouble("quantidade"));
                return pecaNoEstoque;
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
    public List<PecaNoEstoque> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM estoque ORDER BY id");
            rs = st.executeQuery();

            List<PecaNoEstoque> list = new ArrayList<>();
            while (rs.next()) {
                PecaNoEstoque pecaNoEstoque = new PecaNoEstoque();
                pecaNoEstoque.setId(rs.getInt("Id"));
                pecaNoEstoque.addQuantidade(rs.getDouble("quantidade"));
                pecaNoEstoque.setPeca(pecaDao.findById(rs.getInt("peca")));
                list.add(pecaNoEstoque);

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
    public void decreaseByIdPeca(PecaNoEstoque pecaNoEstoque) {

        PecaNoEstoque oldPecaInfo = this.findByIdPeca(pecaNoEstoque.getPeca().getId());

        if(oldPecaInfo == null) {
            throw new DbException("Peca não encontrada");
        }

        if(oldPecaInfo.getQuantidade() - pecaNoEstoque.getQuantidade() < 0){
            throw new DbException("Quantidade insuficiente no estoque");
        }else if(oldPecaInfo.getQuantidade() - pecaNoEstoque.getQuantidade() == 0){
            this.delete(oldPecaInfo.getId());
        }



        double newAmount = oldPecaInfo.getQuantidade() - pecaNoEstoque.getQuantidade();

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE estoque " +
                            "SET quantidade = ? " +
                            "WHERE peca = ?");

            st.setDouble(1, newAmount);
            st.setInt(2, pecaNoEstoque.getPeca().getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    private void delete(int id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM estoque WHERE Id = ?");

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


    @Override
    public void insert(PecaNoEstoque obj) {

        PecaNoEstoque pecaNoEstoque = this.findByIdPeca(obj.getPeca().getId());

        if(pecaNoEstoque != null){

            this.increase(obj, pecaNoEstoque.getQuantidade());
            return;
        }

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO estoque " +
                            "(peca, quantidade) " +
                            "VALUES " +
                            "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getPeca().getId());
            st.setDouble(2, obj.getQuantidade());

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

    private void increase(PecaNoEstoque pecaNoEstoque, Double oldAmount) {

        double newAmount = pecaNoEstoque.getQuantidade() + oldAmount;

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE estoque " +
                            "SET quantidade = ? " +
                            "WHERE peca = ?");

            st.setDouble(1, newAmount);
            st.setInt(2, pecaNoEstoque.getPeca().getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

}
