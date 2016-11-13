package com.example.caio_.projetoidbcultos.model;

import java.util.List;

/**
 * Created by caio- on 13/07/2016.
 */
public class Categoria {
    private String nome;
    private List<Culto> culto;

    public Categoria() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Culto> getCultos() {
        return culto;
    }

    public void setCultos(List<Culto> cultos) {
        this.culto = cultos;
    }
}
