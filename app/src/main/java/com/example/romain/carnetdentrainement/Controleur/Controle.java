package com.example.romain.carnetdentrainement.Controleur;


import android.content.Context;
import android.util.Log;

import com.example.romain.carnetdentrainement.modele.AccesLocal;
import com.example.romain.carnetdentrainement.modele.Entrainement;
import com.example.romain.carnetdentrainement.modele.Exercice;
import com.example.romain.carnetdentrainement.modele.Programme;
import com.example.romain.carnetdentrainement.modele.Serie;
import com.example.romain.carnetdentrainement.modele.Seance;


import java.util.ArrayList;

public final class Controle {

    private static Controle instance = null;
    private static Context contexte;

    private static Programme programme;
    private ArrayList<Programme> lesProgrammes = new ArrayList<Programme>();
    private static ArrayList<Programme> lesProgrammesBd = new ArrayList<Programme>();

    private static Entrainement entrainement;
    private ArrayList<Entrainement> lesEntrainements = new ArrayList<Entrainement>();

    private static Exercice exercice;
    private static Exercice exerciceSelected;
    private ArrayList<Exercice> lesExercices = new ArrayList<Exercice>();

    private static Serie serie;
    private ArrayList<Serie> lesSeries = new ArrayList<Serie>();

    private static Seance seance;
    private ArrayList<Seance> lesSeances = new ArrayList<Seance>();

    private static AccesLocal accesLocal;

    /*
    Constructeur private
     */
    private Controle(){super();}

    /*
    Création de l'instance
    @return instance
     */
    public static final Controle getInstance(Context contexte){
        if(contexte != null){
            Controle.contexte = contexte;
        }
        if(Controle.instance == null){
            Controle.instance = new Controle();
            accesLocal = new AccesLocal(contexte);
            lesProgrammesBd = accesLocal.getProgrammes();
            //profil = accesLocal.recupDernier();
           /*accesDistant = new AccesDistant();
            //accesDistant.envoi("dernier", new JSONArray());
            accesDistant.envoi("tous", new JSONArray());*/
        }
        return Controle.instance;
    }

    /////////////////////:Les Programmes ////////////////////////////////////////////////////////////////

    public void creerProgramme(String nom){
        int newId = accesLocal.createProgramme(nom,0);
        Programme programme = new Programme(newId, nom, 0);
        lesProgrammes.add(programme);
    }

    public ArrayList<Programme> getLesProgrammes() {
        if(lesProgrammes.isEmpty()){
            lesProgrammes = lesProgrammesBd;
        }
        return lesProgrammes;
    }

    public void setProgrammeName(int index, String newName){
        lesProgrammes.get(index).setNom(newName);
        accesLocal.updateProgrammeName(newName, lesProgrammes.get(index).getId());
    }

    public void removeProgramme(int idProgramme, int index){ //To improve
        lesProgrammes.remove(index);
        accesLocal.removeProgramme(idProgramme);
    }

    /////////////////////:Les entrainements ////////////////////////////////////////////////////////////////

    public ArrayList<Entrainement> getEntrainements(int idProgramme) {
        return accesLocal.getEntrainements(idProgramme);
    }

    public void createEntrainement(String nom, int idProgramme, int index){
        int newId = accesLocal.addEntrainement(nom, 0, idProgramme);
        Entrainement newEntrainement = new Entrainement(newId, nom, idProgramme);
        lesProgrammes.get(index).getLesEntrainements().add(newEntrainement);
    }

    public void setEntrainementName(String newName, int idEntrainement, int positionEntrainement, int positionProgramme){
        accesLocal.updateEntrainementName(newName, idEntrainement);
    }

    public void removeEntrainement(int idEntrainement){ //to improve
        accesLocal.removeEntrainement(idEntrainement);
    }


    /////////////////////:Les Exercices ////////////////////////////////////////////////////////////////

    public ArrayList<Exercice> getExercices(int idEntrainement) {
        return accesLocal.getExercices(idEntrainement);
    }


    public Exercice addExercice(String name, int idEntrainement, Entrainement entrainement){
        int newId = accesLocal.addExercice(name, 0, idEntrainement);
        Exercice newExercice = new Exercice(newId, name, idEntrainement);
        entrainement.getLesExercices().add(newExercice); //useful?
        return newExercice;
    }
    
    /////////////////////:Les séances ////////////////////////////////////////////////////////////////

    public ArrayList<Seance> getSeances(int idExercice) {
        return accesLocal.getSeances(idExercice);
    }

    public Seance addSeance(int rep, int charge, int idExercice){
        int newIdSeance = accesLocal.addSeance( 0, idExercice);
        addSerie(rep, charge, 0, newIdSeance);
        Seance newSeance = new Seance(newIdSeance, 0, idExercice);
        return newSeance;
    }

    public void updateSeance(int idSeance, int intensity, String coment){
        accesLocal.updateSeance(idSeance, intensity, coment);
    }

    public void removeSeance(int idSeance){
        accesLocal.removeSeance(idSeance);
    }

    public int getLastIdSeance() {
        return accesLocal.getLastIdSeance();
    }


    ///////////////////// Séries ////////////////////////////////////////////////////////////////

    public Serie addSerie(int rep, int charge, int ordre, int idSeance){
         int newId = accesLocal.addSerie(rep, charge, ordre, idSeance);
        Serie newSerie = new Serie(newId, rep, charge, ordre, idSeance);
        return newSerie;
    }

    public ArrayList<Serie> getSeries(int idSeance) {
        return accesLocal.getSeries(idSeance);
    }

    public Serie getLastSerieOfSeance(int idSeance) {
        return accesLocal.getLastSerieOfSeance(idSeance);
    }

    public Serie getLastSerieOfSeanceByExercice(int idExercice){
        return accesLocal.getLastSerieOfSeanceByExercice(idExercice);
    }

    public void updateSerie(int rep, int weight, int idSerie){
        accesLocal.updateSerie(rep, weight, idSerie);
    }

    public void delSerie(int idSerie){
        accesLocal.delSerie(idSerie);
    }
}
