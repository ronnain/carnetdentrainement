package com.example.romain.carnetdentrainement.modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.romain.carnetdentrainement.Outils.MySQLiteOpenHelper;

import java.util.Date;

public class AccesLocal {

    //propriétés
    private String nomBase = "bdWT.sqlite"; //nom de la base de donnée
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    /**
     * Constructeur
     * @param context
     */
    public AccesLocal(Context context){
        accesBD = new MySQLiteOpenHelper(context, nomBase, null, versionBase );
    }

    /**
     * Ajout d'un profil dans la BDD
     * @param profil
     */
    public void ajout(Profil profil){
        bd = accesBD.getWritableDatabase();
        String req = "insert into profil (datemesure, poids, taille, age, sexe) values";
        req += "(\""+profil.getDateMesure()+"\","+profil.getPoids()+","+profil.getTaille()+","+profil.getAge()+","+profil.getSexe()+");";
        Log.d("Requete", req);
        bd.execSQL(req);
    }

    /**
     * Récupération du dernier profil de la BD
     * @return
     */
    public Profil recupDernier (){
        bd = accesBD.getReadableDatabase();
        Profil profil = null;
        String req = "select * from profil";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToLast();
        if(!curseur.isAfterLast()){
            Date date = new Date();
            Integer poids = curseur.getInt(1);
            Integer taille = curseur.getInt(2);
            Integer age = curseur.getInt(3);
            Integer sexe = curseur.getInt(4);
            profil = new Profil(date, poids, taille, age, sexe);
        }
        curseur.close();
        return profil;
    }

}