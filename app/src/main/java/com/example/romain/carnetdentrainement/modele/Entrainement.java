package com.example.romain.carnetdentrainement.modele;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Entrainement implements Parcelable {


    //propriétés
    private Integer id;
    private String nom;
    private Integer ordre;
    private Integer idProgramme;
    private ArrayList<Exercice> lesExercices = new ArrayList<Exercice>();
    private static Integer ordrebis = 0;


    public Entrainement(String nom){
        this.nom = nom;
        ordrebis++;
        this.ordre = ordrebis;
    }

    public Entrainement(Integer id, String nom, Integer ordre, Integer idProgramme) {
        this.id = id;
        this.nom = nom;
        this.ordre = ordre;
        this.idProgramme = idProgramme;
    }

    public ArrayList<Exercice> getLesExercices() {
        return lesExercices;
    }

    public void setLesExercices(ArrayList<Exercice> lesExercices) {
        this.lesExercices = lesExercices;
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

    public Integer getIdProgramme() {
        return idProgramme;
    }

    public void setIdProgramme(Integer idProgramme) {
        idProgramme = idProgramme;
    }

    public static Integer getOrdrebis() {
        return ordrebis;
    }

    public static void setOrdrebis(Integer ordrebis) {
        Entrainement.ordrebis = ordrebis;
    }

    public void addExercice(Exercice exercice){
        this.lesExercices.add(exercice);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(nom);
        out.writeInt(ordre);
        out.writeInt(idProgramme);
        out.writeList(lesExercices);
    }


    public static final Parcelable.Creator<Entrainement> CREATOR = new Parcelable.Creator<Entrainement>() {
        public Entrainement createFromParcel(Parcel in) {
            return new Entrainement(in);
        }

        public Entrainement[] newArray(int size) {
            return new Entrainement[size];
        }
    };

    private Entrainement(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.ordre = in.readInt();
        this.idProgramme = in.readInt();
        this.lesExercices = new ArrayList<Exercice>();
        in.readList(this.lesExercices,null);
    }
}

/*

Cette deuxième option, bien meilleur en terme de performance que les Serializables n’et pas beaucoup plus complexe à mettre en place. Ici nous allons demander à notre objet d’être Parcelable (qu’on pourrait traduire en français en « Empaquetté »).

Première étape, implémenter Parcelable

1
2
3
4
5
6
public class User implements Parcelable{
    int age;
    String name;
    ArrayList<String> nicknames;
    ...
}
Ce qui implique de remplir les fonctions suivantes : describeContents() et writeToParcel(Parcel out, int flags).
Dans describeContents nous retournerons 0, puis dans writeToParcel nous allons placer nos propriétés 1 à 1 à la même façon qu’un intent.putExtra()

01
02
03
04
05
06
07
08
09
10
11
12
13
14
15
16
17
18
public class User implements Parcelable{
    int age;
    String name;
    ArrayList<String> nicknames;
    ...

    @Override
    public int describeContents() {
         return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
         out.writeInt(age);
         out.writeString(name);
         out.writeList(nicknames);
    }
}
Nous devons ensuite créer un Parcelable.CREATOR, qui va se charger de la création d’un User depuis un parcel. Il va être déclaré en static final Parcelable.Creator<CLASSE> CREATOR à l’intérieur de notre Parcelable. Puis n’oublions pas un constructeur de notre objet qui va prendre en entrée un Parcel, pour se construire avec les données que nous avons écrit auparavant.

Penser à écrire et lire les données dans le même ordre !

01
02
03
04
05
06
07
08
09
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
public class User implements Parcelable{
    int age;
    String name;
    ArrayList<String> nicknames;
    ...

    @Override
    public int describeContents() {
         return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
         out.writeInt(age);
         out.writeString(name);
         out.writeList(nicknames);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        age = in.readInt();
        name = in.readString();

        nicknames = new ArrayList<>();
        in.readList(nicknames,null);
    }
}
Vous pourrez ensuite l’ajouter en tant qu’extra à votre nouvelle activité.

1
2
3
4
5
User user = new User(...);

Intent intent = new Intent(this,DetailActivity.class);
intent.putExtra("user",user);
startActivity(intent);
Et le récupérer en tant que Parcelable

1
2
3
4
5
6
public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    User user = (User)intent.getParcelableExtra("user");
}
*/
