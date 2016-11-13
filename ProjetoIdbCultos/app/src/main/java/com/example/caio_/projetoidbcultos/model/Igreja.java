package com.example.caio_.projetoidbcultos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by caio- on 13/07/2016.
 */
public class Igreja {

    @SerializedName("idb")
    private List<Categoria> categorias;

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
