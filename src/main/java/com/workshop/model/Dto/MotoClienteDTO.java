package com.workshop.model.Dto;

import com.workshop.model.entities.Cliente;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Modelo;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
public class MotoClienteDTO {
    private Integer id;
    private String placa;
    private Integer ano;
    private Modelo modelo;
    private Cliente dono;
    private Marca marca;

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    @Override
    public String toString() {
        return "MotoClienteDTO{" +
                "placa='" + placa + '\'' +
                ", ano=" + ano +
                ", modelo=" + modelo +
                ", dono=" + dono +
                '}';
    }
}

