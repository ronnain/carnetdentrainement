package com.example.romain.carnetdentrainement.modele;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Serie implements  Parcelable {

    //propriétés
    private Integer id;
    private Integer rep;
    private Integer charge;
    private Integer ordre;
    private Integer idSeance;

    public Serie(Integer nbrep, Integer charge) {
        this.rep = nbrep;
        this.charge = charge;
    }

    public Serie(Integer id, Integer rep, Integer charge, Integer ordre, Integer idSeance) {
        this.id = id;
        this.rep = rep;
        this.charge = charge;
        this.ordre = ordre;
        this.idSeance = idSeance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Integer getidSeance() {
        return idSeance;
    }

    public void setidSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }


    public Integer getRep() {
        return rep;
    }

    public Integer getCharge() {
        return charge;
    }

    public Integer setRep(Integer nbrep) {
        return this.rep = nbrep;
    }

    public Integer setCharge(Integer charge) {
        return this.charge = charge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeInt(rep);
        out.writeInt(charge);
        out.writeInt(ordre);
        out.writeInt(idSeance);
    }


    public static final Parcelable.Creator<Serie> CREATOR = new Parcelable.Creator<Serie>() {
        public Serie createFromParcel(Parcel in) {
            return new Serie(in);
        }

        public Serie[] newArray(int size) {
            return new Serie[size];
        }
    };

    private Serie(Parcel in) {
        id = in.readInt();
        rep = in.readInt();
        charge = in.readInt();
        ordre = in.readInt();
        idSeance = in.readInt();
    }

}
