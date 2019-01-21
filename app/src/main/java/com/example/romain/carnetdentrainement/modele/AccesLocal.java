package com.example.romain.carnetdentrainement.modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.romain.carnetdentrainement.Outils.MySQLiteOpenHelper;

import java.util.ArrayList;
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
     * Ajout d'un programme dans la BDD
     * @param programme
     */
    public void ajoutProgramme(Programme programme){
        bd = accesBD.getWritableDatabase();
        String req = "insert into programme (nom, ordre) values";
        req += "(\""+programme.getNom()+"\",\""+programme.getOrdre()+"\");";
        Log.d("Requete ajout programme", req);
        bd.execSQL(req);
    }

    public void ajoutEntrainement(Entrainement entrainement){
        bd = accesBD.getWritableDatabase();
        String req = "insert into entrainement (nom, ordre, idProgramme) values";
        req += "(\""+entrainement.getNom()+"\",\""+entrainement.getOrdre()+"\",\""+ entrainement.getIdProgramme() +"\");";
        Log.d("Requete ajout entraine", req);
        bd.execSQL(req);
    }

    public void ajoutExercice(Exercice exercice){
        bd = accesBD.getWritableDatabase();
        String req = "insert into exercice (nom, ordre, idEntrainement) values";
        req += "(\""+exercice.getNom()+"\",\""+exercice.getOrdre()+"\",\""+ exercice.getidEntrainement() +"\");";
        Log.d("Requete ajout exercice", req);
        bd.execSQL(req);
    }

    public void ajoutSeance(Seance seance){
        bd = accesBD.getWritableDatabase();
        String req = "insert into seance (ordre, intensite, commentaire, idExercice) values";
        req += "(\""+seance.getOrdre()+"\",\""+seance.getIntensite()+"\",\""+ seance.getCommentaire() +"\",\""+ seance.getIdExercice()+"\");";
        Log.d("Requete ajout seance", req);
        bd.execSQL(req);
    }

    public void ajoutSerie(Serie serie){
        bd = accesBD.getWritableDatabase();
        String req = "insert into serie (rep, charge, ordre, idSeance) values";
        req += "(\""+serie.getRep()+"\",\""+serie.getCharge()+"\",\""+ serie.getOrdre() +"\",\""+ serie.getidSeance()+"\");";
        Log.d("Requete ajout serie", req);
        bd.execSQL(req);
    }

    /**
     * Récupération des programmes de la BD
     * @return
     */
    public ArrayList<Programme> getProgrammes (){
        bd = accesBD.getReadableDatabase();
        Programme programme = null;
        String req = "select * from programme";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        for(int i = 0; i < curseur.getCount() ; i++){
            programme = new Programme(curseur.getInt(0), curseur.getString(1), curseur.getInt(2));
            curseur.moveToNext();
        }
        curseur.close();
        return programme;
    }

}