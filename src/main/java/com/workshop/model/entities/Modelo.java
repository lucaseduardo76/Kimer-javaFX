package com.workshop.model.entities;

public class Modelo {

    private Integer id;
    private String modelo;
    private String cilindrada;
    private Marca marca;

    public Modelo() {
    }

    public Modelo(Integer id, String modelo, String cilindrada, Marca marca) {
        this.id = id;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.marca = marca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(String cilindrada) {
        this.cilindrada = cilindrada;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return modelo + " " + cilindrada;
    }
}
