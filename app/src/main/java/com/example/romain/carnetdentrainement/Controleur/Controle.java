package com.example.romain.carnetdentrainement.Controleur;


import android.content.Context;

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
            //profil = accesLocal.recupDernier();
           /*accesDistant = new AccesDistant();
            //accesDistant.envoi("dernier", new JSONArray());
            accesDistant.envoi("tous", new JSONArray());*/
        }
        return Controle.instance;
    }

    /////////////////////:Les Programmes ////////////////////////////////////////////////////////////////

    /**
     * Création programme
     * @param nom
     * @param context
     */
    public void creerProgramme(String nom, Context context){
        Programme unprogramme = new Programme(nom);
        lesProgrammes.add(unprogramme);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
        //accesLocal.ajout(profil);
    }
    public void creerProgramme(Integer id, String nom, Integer ordre){
        Programme programme = new Programme(id, nom, ordre);
        lesProgrammes.add(programme);
    }

    /**
     * Supprimer programme
     * @param programme à supprimer
     * @param context
     */
    public void supprimerProgramme(Programme programme, Context context){
        lesProgrammes.remove(programme);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }


    public void setProgramme(Programme programme){
        Controle.programme = programme;
        //((CalculActivity)contexte).recupProfil();
    }

    public Programme getProgramme(Integer index) {
        return   lesProgrammes.get(index);
    }

    public ArrayList<Programme> getLesProgrammes() {
        return lesProgrammes;
    }


    /////////////////////:Les entrainements ////////////////////////////////////////////////////////////////

    public ArrayList<Entrainement> getLesEntrainements() {
        return lesEntrainements;
    }


    /**
     * Création programme
     * @param nom
     * @param context
     */
    public void creerEntrainement(String nom, Context context){
        Entrainement uneEntrainement = new Entrainement(nom);
        lesEntrainements.add(uneEntrainement);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }

    public void creerEntrainement(Integer id, String nom, Integer ordre, Integer idProgramme, Programme programme){
        Entrainement newEntrainement = new Entrainement(id, nom, ordre, idProgramme);
        programme.addEntrainement(newEntrainement);
    }

    /**
     * Supprimer entrainement
     * @param entrainement à supprimer
     * @param context
     */
    public void supprimerEntrainement(Entrainement entrainement, Context context){
        lesEntrainements.remove(entrainement);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }


    public void setEntrainement(Entrainement entrainement){
        Controle.entrainement = entrainement;
        //((CalculActivity)contexte).recupProfil();
    }

    public Entrainement getEntrainement(Programme programme, Integer indexEntrainement){
        return  programme.getLesEntrainements().get(indexEntrainement);
    }
    /////////////////////:Les Exercices ////////////////////////////////////////////////////////////////

    public ArrayList<Exercice> getLesExercices() {
        return lesExercices;
    }


    /**
     * Création programme
     * @param nom
     * @param context
     */
    public void creerExercice(String nom, Context context){
        Exercice unExercice = new Exercice(nom);
        lesExercices.add(unExercice);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }

    public void creerExercice(Integer id, String nom, Integer ordre, Integer idEntrainement, Entrainement entrainement) {
        Exercice newExercice = new Exercice(id, nom, ordre, idEntrainement);
        entrainement.addExercice(newExercice);
    }
    
    public Exercice creerExerciceIndependant(Integer id, String nom, Integer ordre, Integer idEntrainement){
        Exercice newExercice = new Exercice(id, nom, ordre, idEntrainement);
        return newExercice;
    }

    public void supprimerExercice(Exercice exercice, Context context){
        lesExercices.remove(exercice);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }

    public void setExercice(Exercice exercice){
        Controle.exercice = exercice;
        //((CalculActivity)contexte).recupProfil();
    }

    public void setExerciceSelected(Exercice exerciceSelected){
        Controle.exerciceSelected = exerciceSelected;
    }
    public Exercice getExerciceSelected(){
        return  exerciceSelected;
    }

    public Exercice getExercice(Entrainement entrainement, int indexExercice){
        return entrainement.getLesExercices().get(indexExercice);
    }

    

    
    
    
    
    /////////////////////:Les séances ////////////////////////////////////////////////////////////////

    public ArrayList<Seance> getlesSeancess() {
        return lesSeances;
    }

    /**
     * Création programme
     * @param
     * @param
     */
    public void creerSeance(){
        Seance seance = new Seance(lesSeries);
        lesSeances.add(seance);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }



    public void creerSeriesSeance(Serie s){
        ArrayList<Serie> lesSeries = new ArrayList<Serie>();
        lesSeries.add(s);
        Seance uneSerieSeance = new Seance(lesSeries);
        lesSeances.add(uneSerieSeance);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }

    public void creerSeance(Integer id, Integer ordre, Integer intensite, String commentaire, Integer idExercice, Exercice exercice){
        Seance newSeance = new Seance(id, ordre, intensite, commentaire, idExercice);
        exercice.addSeance(newSeance);
    }

    public Seance getSeance(Exercice exercice, int index){
        return  exercice.getLesSeances().get(index);
    }


    /////////////////////Une Série ////////////////////////////////////////////////////////////////

    public ArrayList<Serie> getLesSeries() {
        return lesSeries;
    }


    /**
     * Création programme
     * @param
     * @param context
     */
    public void creerSerie(Integer nbrep,Integer charge, Context context){
        Serie uneSerie = new Serie(nbrep, charge);
        lesSeries.add(uneSerie);
        //accesDistant.envoi("enreg", unprofil.convertToJSONArray());
    }

    public void setSerie(Serie serie){
        Controle.serie = serie;
        //((CalculActivity)contexte).recupProfil();
    }

    public  void creerSerie(Integer id, Integer rep, Integer charge, Integer ordre, Integer id_seance, Seance seance){
        Serie newSerie = new Serie( id,  rep, charge, ordre, id_seance);
        seance.addSerie(newSerie);
    }


}
