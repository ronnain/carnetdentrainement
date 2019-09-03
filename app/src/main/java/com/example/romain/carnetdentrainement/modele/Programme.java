package com.example.romain.carnetdentrainement.modele;



import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Programme {


    //propriétés
    private Integer id;
    private String nom;
    private Integer ordre;
    private ArrayList<Entrainement> lesEntrainements = new ArrayList<Entrainement>(); //Useless
    private static Integer count = 0; // start to 0

    public Programme(Integer id, String nom, Integer ordre) {
        this.id = id;
        this.nom = nom;
        this.ordre = ordre;
        count++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public ArrayList<Entrainement> getLesEntrainements() {

        return lesEntrainements;
    }

    public void setLesEntrainements(ArrayList<Entrainement> lesEntrainements) {
        this.lesEntrainements = lesEntrainements;
    }
    public void addEntrainement(Entrainement newEntrainement){
        this.lesEntrainements.add(newEntrainement);
    }

    public void supprimerEntrainement(Entrainement entrainement){
        this.lesEntrainements.remove(entrainement);
    }

    /**
     Convertion du programme au format JSONAraay
     @return
     */
    public JSONArray convertToJSONArray(){
        List laListe = new ArrayList();
        laListe.add(nom);
        laListe.add(ordre);
        return new JSONArray(laListe);
    }

}