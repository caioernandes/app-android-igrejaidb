package com.example.caio_.projetoidbcultos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by caio- on 14/07/2016.
 */
public class CultoHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "cultos_db";

    public CultoHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CultoContract.TABLE_NAME + " (" +
                CultoContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CultoContract.FOTO + " TEXT NOT NULL, " +
                CultoContract.LINK_YOUTUBE + " TEXT NOT NULL, " +
                CultoContract.PREGADOR + " TEXT NOT NULL, " +
                CultoContract.TEMA + " TEXT NOT NULL, " +
                CultoContract.VERSICULO_TEMA + " TEXT NOT NULL, " +
                CultoContract.NOME_ORIGINAL_YOUTUBE + " TEXT NOT NULL, " +
                CultoContract.DATA + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if (oldVersion == 1) {
            db.execSQL("ALTER TABLE " + CultoContract.TABLE_NAME + " ADD COLUMN " + NEW COLUMN);
        }*/
    }
}
