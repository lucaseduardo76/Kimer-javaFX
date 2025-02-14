package com.workshop.model.entities;

public class PecaNoEstoque {
    private int id;
    private Peca peca;
    private Double quantidade;

    public PecaNoEstoque() {
        this.quantidade = 0.0;
    }

    public PecaNoEstoque(int id, Peca peca, Double quantidade) {
        this.id = id;
        this.peca = peca;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void addQuantidade(Double quantidade) {
        this.quantidade += quantidade;
    }
    public void lessQuantidade(Double quantidade) {
        this.quantidade -= quantidade;
    }
}
