package com.example.romain.carnetdentrainement.modele;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Seance implements Parcelable {

    //propriétés
    private Integer id;
    private Integer ordre;
    private Integer intensite;
    private String commentaire;
    private Integer idExercice;
    private ArrayList<Serie> lesSeries = new ArrayList<Serie>();



    public Seance(Integer id, Integer ordre, Integer intensite, String commentaire, Integer idExercice) {
        this.id = id;
        this.ordre = ordre;
        this.intensite = intensite;
        this.commentaire = commentaire;
        this.idExercice = idExercice;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(Integer idExercice) {
        this.idExercice = idExercice;
    }


    private static Integer ordrebis = 0;

    public ArrayList<Serie> getLesSeries() {
        return lesSeries;
    }

    public void setLesSeries(ArrayList<Serie> lesSeries) {
        this.lesSeries = lesSeries;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public Integer getIntensite() {
        return intensite;
    }

    public void setIntensite(Integer intensite) {
        this.intensite = intensite;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void addSerie(Serie serie){
        lesSeries.add(serie);
    }


    public Seance(ArrayList<Serie> lesSeries){
        this.lesSeries = lesSeries;
        ordrebis++;
        this.ordre = ordrebis;
    }





    /**
     Convertion du programme au format JSONAraay
     @return
     */
    public JSONArray convertToJSONArray(){
        List laListe = new ArrayList();
        laListe.add(lesSeries);
        laListe.add(ordre);
        return new JSONArray(laListe);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeInt(ordre);
        out.writeInt(intensite);
        out.writeString(commentaire);
        out.writeInt(idExercice);
        out.writeList(lesSeries);
    }


    public static final Parcelable.Creator<Seance> CREATOR = new Parcelable.Creator<Seance>() {
        public Seance createFromParcel(Parcel in) {
            return new Seance(in);
        }

        public Seance[] newArray(int size) {
            return new Seance[size];
        }
    };

    private Seance(Parcel in) {
        id = in.readInt();
        ordre = in.readInt();
        intensite = in.readInt();
        commentaire = in.readString();
        idExercice = in.readInt();
        lesSeries = new ArrayList<>();
        in.readList(lesSeries,null);
    }
}
