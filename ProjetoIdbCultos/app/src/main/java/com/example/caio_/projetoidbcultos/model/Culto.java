package com.example.caio_.projetoidbcultos.model;

import org.parceler.Parcel;

@Parcel
public class Culto {
    private long id;
    private String foto;
    private String linkYoutube;
    private String nomeOriginalYoutube;
    private String pregador;
    private String tema;
    private String versiculoTema;
    private String data;
    private Boolean aovivo;

    public Culto() {}

    public Culto(String foto, String linkYoutube, String pregador, String tema, String versiculoTema, String data) {
        this.foto = foto;
        this.linkYoutube = linkYoutube;
        this.pregador = pregador;
        this.tema = tema;
        this.versiculoTema = versiculoTema;
        this.data = data;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getPregador() {
        return pregador;
    }

    public void setPregador(String pregador) {
        this.pregador = pregador;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getVersiculoTema() {
        return versiculoTema;
    }

    public void setVersiculoTema(String versiculoTema) {
        this.versiculoTema = versiculoTema;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return pregador + " - " + tema;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNomeOriginalYoutube() {
        return nomeOriginalYoutube;
    }

    public void setNomeOriginalYoutube(String nomeOriginalYoutube) {
        this.nomeOriginalYoutube = nomeOriginalYoutube;
    }

    public Boolean getAovivo() {
        return aovivo;
    }

    public void setAovivo(Boolean aovivo) {
        this.aovivo = aovivo;
    }
}
