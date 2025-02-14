package com.workshop.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Estoque {

    private Integer id;
    private List<PecaNoEstoque> peca;

    public Estoque() {
        peca = new ArrayList<>();
    }

    public Estoque(Integer id, List<PecaNoEstoque> peca) {
        this.id = id;
        this.peca = peca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addPecaNoEstoque(PecaNoEstoque peca) {
        this.peca.add(peca);
    }

    public List<PecaNoEstoque> getPeca() {
        return peca;
    }
}
