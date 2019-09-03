package com.example.romain.carnetdentrainement.modele;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Exercice  implements Parcelable   {
    //propriétés
    private Integer id;
    private String nom;
    private Integer ordre;
    private Integer idEntrainement;
    private ArrayList<Seance> lesSeances = new ArrayList<Seance>();
    private static Integer count = 0; // start to 0



    public Exercice(int id, String nom, int idEntrainement){
        count++;
        this.id = id;
        this.nom = nom;
        this.ordre = count;
        this.idEntrainement = idEntrainement;
    }

    public Exercice(Integer id, String nom, Integer ordre, Integer idEntrainement) { //Useless?
        this.id = id;
        this.nom = nom;
        this.ordre = ordre;
        this.idEntrainement = idEntrainement;
        count++;
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

    public Integer getidEntrainement() {
        return idEntrainement;
    }

    public void setidEntrainement(Integer idEntrainement) {
        idEntrainement = idEntrainement;
    }

    public ArrayList<Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(ArrayList<Seance> lesSeances) {
        this.lesSeances = lesSeances;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addSeance(Seance seance){
        lesSeances.add(seance);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(nom);
        out.writeInt(ordre);
        out.writeInt(idEntrainement);
        out.writeList(lesSeances);
    }


    public static final Parcelable.Creator<Exercice> CREATOR = new Parcelable.Creator<Exercice>() {
        public Exercice createFromParcel(Parcel in) {
            return new Exercice(in);
        }

        public Exercice[] newArray(int size) {
            return new Exercice[size];
        }
    };

    private Exercice(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        ordre = in.readInt();
        idEntrainement = in.readInt();
        lesSeances = new ArrayList<>();
        in.readList(lesSeances,null);
    }
}