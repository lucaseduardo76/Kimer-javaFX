package com.workshop.model.entities;

public class Peca {

    private Integer id;
    private String nome;

    public Peca(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Peca(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        return nome;
    }
}
