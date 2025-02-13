package com.workshop.model.entities;

public class MotoCliente {

    private Integer id;
    private String placa;
    private Integer ano;
    private Modelo modelo;
    private Cliente cliente;

    public MotoCliente() {}


    public MotoCliente(Integer id, String placa, Integer ano, Modelo modelo, Cliente cliente) {
        this.id = id;
        this.placa = placa;
        this.ano = ano;
        this.modelo = modelo;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return placa;
    }
}
