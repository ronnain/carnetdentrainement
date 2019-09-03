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
    //////////////////// Programmes /////////////////////////////////////////////////
    /**
     * Add a program
     * @param nom
     * @param ordre
     */
    public int createProgramme(String nom, Integer ordre){
        bd = accesBD.getWritableDatabase();
        String req = "insert into programme (nom, ordre) values";
        req += "(\""+nom+"\",\""+ordre+"\");";
        Log.d("Requete ajout", req);
        bd.execSQL(req);
        return getLastIdProgramme();
    }

    public void updateProgrammeName(String newName, int idProgramme){
        bd = accesBD.getWritableDatabase();
        String req = "update programme set nom = \""+newName+"\" where idProgramme = "+idProgramme;
        bd.execSQL(req);
    }

    public void removeProgramme(int idProgramme){
        bd = accesBD.getWritableDatabase();
        String req = "delete from entrainement where idProgramme = "+idProgramme;
        bd.execSQL(req);
        req = "delete from programme where idProgramme = "+idProgramme;
        bd.execSQL(req);
    }

    public int getLastIdProgramme (){
        bd = accesBD.getReadableDatabase();
        String req = "select * from programme order by idProgramme desc limit 1 ";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        int lastId = curseur.getInt(0);
        curseur.close();
        return lastId;
    }

    /////////////////// Entrainements //////////////////////////////////////////
    public int addEntrainement(String name, int ordre, int idProgramme ){
        bd = accesBD.getWritableDatabase();
        String req = "insert into entrainement (nom, ordre, idProgramme) values";
        req += "(\""+name+"\",\""+ordre+"\",\""+idProgramme+"\");";
        Log.d("Requete ajout", req);
        bd.execSQL(req);
        return getLastIdEntrainement();
    }

    /**
     * Récupération des programmes de la BD
     * @return
     */
    public ArrayList<Programme> getProgrammes (){
        bd = accesBD.getReadableDatabase();
        ArrayList<Programme> lesProgrammes = new ArrayList<Programme>();
        Programme programme = null;
        String req = "select * from programme";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        for(int i = 0; i < curseur.getCount() ; i++){
            programme = new Programme(curseur.getInt(0), curseur.getString(1), curseur.getInt(2));
            lesProgrammes.add(programme);
            curseur.moveToNext();
        }
        curseur.close();
        return lesProgrammes;
    }

    public void updateEntrainementName(String newName, int idEntrainement){
        bd = accesBD.getWritableDatabase();
        String req = "update entrainement set nom = \""+newName+"\" where idEntrainement = "+idEntrainement;
        Log.d("Requete updt", req);
        bd.execSQL(req);
    }

    public void removeEntrainement(int idEntrainement){
        bd = accesBD.getWritableDatabase();
        String req = "delete from entrainement where idEntrainement = "+idEntrainement;
        Log.d("Requete delete", req);
        bd.execSQL(req);
    }

    public void removeEntrainementByProgramme(int idProgramme){
        bd = accesBD.getWritableDatabase();
        String req = "delete from entrainement where idProgramme = "+idProgramme;
        Log.d("Requete delete", req);
        bd.execSQL(req);
    }
    public int getLastIdEntrainement (){
        bd = accesBD.getReadableDatabase();
        String req = "select * from entrainement order by idEntrainement desc limit 1 ";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        int lastId = curseur.getInt(0);
        curseur.close();
        return lastId;
    }
    public ArrayList<Entrainement> getEntrainements (int idProgramme){
        bd = accesBD.getReadableDatabase();
        ArrayList<Entrainement> lesEntrainements = new ArrayList<Entrainement>();
        Entrainement entrainement = null;
        String req = "select * from entrainement where idProgramme = "+idProgramme;
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        for(int i = 0; i < curseur.getCount() ; i++){
            entrainement = new Entrainement(curseur.getInt(0), curseur.getString(1), curseur.getInt(2), idProgramme);
            lesEntrainements.add(entrainement);
            curseur.moveToNext();
        }
        curseur.close();
        return lesEntrainements;
    }

    //////////////////// Exercice //////////////////////////////////////////
    public int addExercice(String name, int ordre, int idEntrainement){
        bd = accesBD.getWritableDatabase();
        String req = "insert into exercice (nom, ordre, idEntrainement) values";
        req += "(\""+name+"\",\""+ordre+"\",\""+ idEntrainement +"\");";
        Log.d("Requete ajout exercice", req);
        bd.execSQL(req);
        return getLastIdExercice();
    }
    public int getLastIdExercice (){
        bd = accesBD.getReadableDatabase();
        String req = "select * from exercice order by idExercice desc limit 1 ";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        int lastId = curseur.getInt(0);
        curseur.close();
        return lastId;
    }

    public ArrayList<Exercice> getExercices (int idEntrainement) {
        bd = accesBD.getReadableDatabase();
        ArrayList<Exercice> exercices = new ArrayList<Exercice>();
        Exercice exercice = null;
        String req = "select * from exercice where idEntrainement = "+idEntrainement;
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        for(int i = 0; i < curseur.getCount() ; i++){
            exercice = new Exercice(curseur.getInt(0), curseur.getString(1), curseur.getInt(2), idEntrainement);
            exercices.add(exercice);
            curseur.moveToNext();
        }
        curseur.close();
        return exercices;
    }

    ////////////////// Seances ////////////////////////////////////////

    public int addSeance(int ordre, int idExercice){
        bd = accesBD.getWritableDatabase();
        String req = "insert into seance (ordre, idExercice) values";
        req += "(\""+ordre+"\",\""+idExercice+"\");";
        Log.d("Requete ajout exercice", req);
        bd.execSQL(req);
        return getLastIdSeance();
    }
    public int getLastIdSeance (){
        bd = accesBD.getReadableDatabase();
        String req = "select * from seance order by idSeance desc limit 1 ";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        int lastId = curseur.getInt(0);
        curseur.close();
        return lastId;
    }

    /**
     * return the seances in desc order
     * @param idExercice
     * @return
     */
    public ArrayList<Seance> getSeances (int idExercice) {
        bd = accesBD.getReadableDatabase();
        ArrayList<Seance> seances = new ArrayList<Seance>();
        Seance seance = null;
        String req = "select * from seance where idExercice = \""+idExercice+"\" order by idSeance desc";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        for(int i = 0; i < curseur.getCount() ; i++){
            seance = new Seance(curseur.getInt(0), curseur.getInt(1), curseur.getInt(2), curseur.getString(3), idExercice);
            seances.add(seance);
            curseur.moveToNext();
        }
        curseur.close();
        return seances;
    }

    public int getLastIdSeanceByIdExercice (int idExercice){
        bd = accesBD.getReadableDatabase();
        String req = "select * from seance where idExercice = \""+idExercice+"\" order by idSeance desc limit 1 ";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        int lastId = 0;
        if(curseur != null && curseur.getCount() > 0) {
            lastId = curseur.getInt(0);
        }
        curseur.close();
        return lastId;
    }

    public void updateSeance(int idSeance, int intensity, String coment){
        bd = accesBD.getReadableDatabase();
        String req = "update seance set intensite = \""+ intensity +"\", commentaire = \""+ coment +"\" where idSeance = \""+idSeance+"\" ";
        bd.execSQL(req);
    }

    public void removeSeance(int idSeance){
        String req = "delete from seance where idSeance = "+idSeance;
        bd.execSQL(req);
        req = "delete from serie where idSeance = "+idSeance;
        bd.execSQL(req);
    }


    ///////////////// Series /////////////////////////////////////////////////
    public int addSerie(int rep, int charge, int ordre, int idSeance){
        bd = accesBD.getWritableDatabase();
        String req = "insert into serie (rep, charge, ordre, idSeance) values";
        req += "(\""+rep+"\",\""+charge+"\",\""+ ordre +"\",\""+ idSeance+"\");";
        bd.execSQL(req);
        return getLastIdSerie();
    }

    public int getLastIdSerie (){
        bd = accesBD.getReadableDatabase();
        String req = "select * from serie order by idSerie desc limit 1 ";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        int lastId = curseur.getInt(0);
        curseur.close();
        return lastId;
    }

    public ArrayList<Serie> getSeries (int idSeance) {
        bd = accesBD.getReadableDatabase();
        ArrayList<Serie> series = new ArrayList<Serie>();
        Serie serie = null;
        String req = "select * from serie where idSeance = "+idSeance;
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        for(int i = 0; i < curseur.getCount() ; i++){
            serie = new Serie(curseur.getInt(0), curseur.getInt(1), curseur.getInt(2), curseur.getInt(3), idSeance);
            series.add(serie);
            curseur.moveToNext();
        }
        curseur.close();
        return series;
    }

    public Serie getLastSerieOfSeance (int idSeance){
        bd = accesBD.getReadableDatabase();
        String req = "select * from serie where idSeance = \""+ idSeance +"\" order by idSerie desc limit 1 ";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        if(curseur.getInt(0) > 0){
            Serie serie = new Serie(curseur.getInt(0), curseur.getInt(1), curseur.getInt(2),curseur.getInt(3), idSeance);
            curseur.close();
            return serie;
        }else{
            curseur.close();
            return null;
        }
    }

    public Serie getLastSerieOfSeanceByExercice (int idExercice){
        bd = accesBD.getReadableDatabase();
        int idSeance = getLastIdSeanceByIdExercice(idExercice);
        if(idSeance != 0){
            String req = "select * from serie where idSeance = \""+ idSeance +"\" order by idSeance desc limit 1";
            Cursor curseur = bd.rawQuery(req, null);
            curseur.moveToFirst();
            if(curseur.getInt(0) > 0){
                Serie serie = new Serie(curseur.getInt(0), curseur.getInt(1), curseur.getInt(2),curseur.getInt(3), idSeance);
                curseur.close();
                return serie;
            }else{
                curseur.close();
                return null;
            }
        }else{
            return null;
        }
    }

    public void updateSerie(int rep, int weight, int idSerie){
        bd = accesBD.getReadableDatabase();
        String req = "update serie set rep = \""+ rep +"\", charge = \""+ weight +"\" where idSerie = \""+idSerie+"\" ";
        bd.execSQL(req);
    }

    public void delSerie(int idSerie){
        bd = accesBD.getWritableDatabase();
        String req = "delete from serie where idSerie = "+idSerie;
        bd.execSQL(req);
    }

}