package com.example.caio_.projetoidbcultos.database;

import android.provider.BaseColumns;

/**
 * Created by caio- on 14/07/2016.
 */
public interface CultoContract extends BaseColumns {
    String TABLE_NAME = "cultos";

    String FOTO = "foto";
    String LINK_YOUTUBE = "link_youtube";
    String PREGADOR = "pregador";
    String TEMA = "tema";
    String VERSICULO_TEMA = "versiculo_tema";
    String DATA = "data";
    String NOME_ORIGINAL_YOUTUBE = "nomeOriginalYoutube";
}
