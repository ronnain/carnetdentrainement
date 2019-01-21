package com.example.romain.carnetdentrainement.Outils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //propriétés modifier
    private String creation1 = "CREATE TABLE `programme` ( `idProgramme` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `nom` TEXT NOT NULL, `ordre` INTEGER NOT NULL ) ";
    private String creation2 = "CREATE TABLE `entrainement` ( `idEntrainement` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `nom` TEXT NOT NULL, `ordre` INTEGER NOT NULL, `idProgramme` TEXT NOT NULL ); ";
    private String creation3 = "CREATE TABLE `exercice` ( `idExercice` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `nom` TEXT NOT NULL, `ordre` INTEGER NOT NULL, `idEntrainement` INTEGER NOT NULL ); " ;
    private String creation4 = "CREATE TABLE `seance` ( `idSeance` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `ordre` INTEGER NOT NULL, `intensite` INTEGER, `commentaire` TEXT, `idExercice` INTEGER NOT NULL ); " ;
    private String creation5 = "CREATE TABLE `serie` ( `idSerie` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `rep` INTEGER NOT NULL, `charge` INTEGER NOT NULL, `ordre` INTEGER NOT NULL, `idSeance` INTEGER NOT NULL ); ";

    /**
     * Constructeur
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Si changement de base de données
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("oncreate ","************************************");
        sqLiteDatabase.execSQL(creation1);
        sqLiteDatabase.execSQL(creation2);
        sqLiteDatabase.execSQL(creation3);
        sqLiteDatabase.execSQL(creation4);
        sqLiteDatabase.execSQL(creation5);

    }

    /**
     * Si version différente
     * @param sqLiteDatabase
     * @param i ancien version
     * @param i1 nouvelle version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}