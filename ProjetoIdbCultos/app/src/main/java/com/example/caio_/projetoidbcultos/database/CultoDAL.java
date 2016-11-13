package com.example.caio_.projetoidbcultos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caio_.projetoidbcultos.model.Culto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caio- on 14/07/2016.
 */
public class CultoDAL {

    private Context mContext;

    public CultoDAL(Context context) {
        this.mContext = context;
    }

    public long inserir(Culto culto) {
        CultoHelper helper = new CultoHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = valuesFromCulto(culto);
        long id = db.insert(CultoContract.TABLE_NAME, null, values);
        culto.setId(id);

        db.close();

        return id;
    }

    public int atualizar(Culto culto) {
        CultoHelper helper = new CultoHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = valuesFromCulto(culto);
        int rowsAffected = db.update(CultoContract.TABLE_NAME, values, CultoContract._ID + " = ?",
                new String[] {String.valueOf(culto.getId())});

        db.close();

        return rowsAffected;
    }

    public int excluir(Culto culto) {
        CultoHelper helper = new CultoHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(CultoContract.TABLE_NAME, CultoContract.LINK_YOUTUBE + " = ?",
                new String[] {String.valueOf(culto.getLinkYoutube())});

        db.close();

        return rowsAffected;
    }

    public List<Culto> listar() {
        CultoHelper helper = new CultoHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + CultoContract.TABLE_NAME, null);
        List<Culto> cultos = new ArrayList<>();

        if (cursor.getCount() > 0) {
            int idxId = cursor.getColumnIndex(CultoContract._ID);
            int idxFoto = cursor.getColumnIndex(CultoContract.FOTO);
            int idxLinkYoutube = cursor.getColumnIndex(CultoContract.LINK_YOUTUBE);
            int idxPregador = cursor.getColumnIndex(CultoContract.PREGADOR);
            int idxTema = cursor.getColumnIndex(CultoContract.TEMA);
            int idxVersiculoTema = cursor.getColumnIndex(CultoContract.VERSICULO_TEMA);
            int idxData = cursor.getColumnIndex(CultoContract.DATA);
            int idxNomeOriginalYoutube = cursor.getColumnIndex(CultoContract.NOME_ORIGINAL_YOUTUBE);

            while (cursor.moveToNext()) {
                Culto culto = new Culto();
                culto.setId(cursor.getLong(idxId));
                culto.setFoto(cursor.getString(idxFoto));
                culto.setLinkYoutube(cursor.getString(idxLinkYoutube));
                culto.setPregador(cursor.getString(idxPregador));
                culto.setTema(cursor.getString(idxTema));
                culto.setVersiculoTema(cursor.getString(idxVersiculoTema));
                culto.setData(cursor.getString(idxData));
                culto.setNomeOriginalYoutube(cursor.getString(idxNomeOriginalYoutube));
                cultos.add(culto);
            }
        }

        cursor.close();
        db.close();

        return cultos;
    }

    public boolean isFavorito(Culto culto) {
        CultoHelper helper = new CultoHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + CultoContract.TABLE_NAME
                                    + " WHERE " + CultoContract.LINK_YOUTUBE + " = ?",
                                    new String[] { culto.getLinkYoutube() });
        boolean existe = false;
        if (cursor != null) {
            cursor.moveToNext();
            existe = cursor.getInt(0) > 0;
            cursor.close();
        }
        db.close();
        return existe;
    }

    private ContentValues valuesFromCulto(Culto culto) {
        ContentValues values = new ContentValues();
        values.put(CultoContract.FOTO, culto.getFoto());
        values.put(CultoContract.LINK_YOUTUBE, culto.getLinkYoutube());
        values.put(CultoContract.PREGADOR, culto.getPregador());
        values.put(CultoContract.TEMA, culto.getTema());
        values.put(CultoContract.VERSICULO_TEMA, culto.getVersiculoTema());
        values.put(CultoContract.DATA, culto.getData());
        values.put(CultoContract.NOME_ORIGINAL_YOUTUBE, culto.getNomeOriginalYoutube());

        return values;
    }
}
