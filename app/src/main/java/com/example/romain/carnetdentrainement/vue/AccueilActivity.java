package com.example.romain.carnetdentrainement.vue;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.list.Accueil_List_Adapter;
import com.example.romain.carnetdentrainement.list.Accueil_List_Adapter_Recycler;
import com.example.romain.carnetdentrainement.list.Exercices_List_Adapter_Recycler;
import com.example.romain.carnetdentrainement.modele.Entrainement;
import com.example.romain.carnetdentrainement.modele.Exercice;
import com.example.romain.carnetdentrainement.modele.Programme;

import java.util.ArrayList;

public class AccueilActivity extends AppCompatActivity {

    private Controle controle;
    public final static String EXTRA_MESSAGE_EXERCICE_ID = "com.ltm.ltmactionbar.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Accueil");
        setContentView(R.layout.activity_accueil);
        init();
        creerListe();
    }

    private void init(){
        this.controle = Controle.getInstance(this);
        /*if(controle.getLesProgrammes().isEmpty()) {
            this.controle.creerProgramme("Programme 1", this);
            this.controle.creerProgramme("Programme 2", this);
            this.controle.creerProgramme("Programme 3", this);
            this.controle.creerProgramme("Programme 4", this);
        }
        if(controle.getLesEntrainements().isEmpty()) {
            this.controle.creerEntrainement("Haut du corps", this);
            this.controle.creerEntrainement("Bas du corps", this);
            this.controle.creerEntrainement("Entrainement 3", this);
        }
        if(controle.getLesExercices().isEmpty()) {
            this.controle.creerExercice("Développé couché haltères incliné", this);
            this.controle.creerExercice("Développé couché", this);
            this.controle.creerExercice("Pull Over", this);
            this.controle.creerExercice("Curl incliné", this);
            this.controle.creerExercice("Curl marteau", this);
            this.controle.creerExercice("Abdos à la poulie haute", this);
        }*/
        /*if(controle.getLesSeries().isEmpty()) {
            this.controle.creerSerie(5, 70, this);
            this.controle.creerSerie(6, 80, this);
            this.controle.creerSerie(7, 90, this);
            this.controle.creerSerie(8, 100, this);
            this.controle.creerSerie(8, 105, this);
            this.controle.creerSerie(8, 110, this);
            this.controle.creerSeriesSeance();
            this.controle.creerSeriesSeance();
        }*/
        if(controle.getLesProgrammes().isEmpty()) {
            this.controle.creerProgramme(0,"Programme 1 Pecs",1 );
            this.controle.creerProgramme(1,"Programme 2 Biceps",2 );
            this.controle.creerProgramme(2,"Programme 3 Null",3 );
            this.controle.creerProgramme(3,"Programme 4 A chier",4 );


            creerProgrammeTest(0);
            creerProgrammeTest(1);
            creerProgrammeTest(2);
            creerProgrammeTest(3);
           }

        initbtnAdd();

    }

    /**
     * créer liste adpater
     */
    private void creerListe(){
        ArrayList<Programme> lesProgrammes = controle.getLesProgrammes();
        //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
        if(lesProgrammes != null){
            /*ListView lstPrograms = (ListView) findViewById(R.id.lstPrograms);
            Accueil_List_Adapter adapter = new Accueil_List_Adapter(this, controle, lesProgrammes);
            lstPrograms.setAdapter(adapter);*/

            RecyclerView mRecyclerView;
            RecyclerView.Adapter mAdapter;
            RecyclerView.LayoutManager mLayoutManager;

            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerProgrammes);

            mLayoutManager =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new Accueil_List_Adapter_Recycler(this, controle);
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    private void initbtnAdd(){
        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccueilActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                //TextView textDialogTitre = (TextView) mView.findViewById(R.id.textDialogTitre);
                final EditText editNom = (EditText) mView.findViewById(R.id.editNom);
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if(editNom.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }else {
                            controle.creerProgramme(editNom.getText().toString(),AccueilActivity.this);
                            dialog.hide();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void creerProgrammeTest(Integer index){
        Programme programme = controle.getProgramme(index);


        this.controle.creerEntrainement(0,"Haut du corps 1 + " +index.toString(), 1,1, programme);
        this.controle.creerEntrainement(1,"Bas du corps 1+ " +index.toString(), 2,1, programme);
        this.controle.creerEntrainement(2,"Haut du corps 2+ " +index.toString(), 3,1, programme);
        this.controle.creerEntrainement(3,"Bas du corps 2+ " +index.toString(), 4,1, programme);

        this.controle.creerExercice(0, "Developpé couché " +index.toString(), 1, 1, controle.getEntrainement(programme,0));
        this.controle.creerExercice(1, "Developpé couhé incliné haltères " +index.toString(), 2, 1, controle.getEntrainement(programme,0));
        this.controle.creerExercice(2, "Pull over " +index.toString(), 3, 1, controle.getEntrainement(programme,0));
        this.controle.creerExercice(3, "Curl incliné " +index.toString(), 4, 1, controle.getEntrainement(programme,0));
        this.controle.creerExercice(4, "Curl marteau " +index.toString(), 5, 1, controle.getEntrainement(programme,0));
        this.controle.creerExercice(5, "Abdos à la poulis haute " +index.toString(), 6, 1, controle.getEntrainement(programme,0));

        this.controle.creerExercice(6, "Squat avant " +index.toString(), 1, 1, controle.getEntrainement(programme,1));
        this.controle.creerExercice(7, "Presse à cuisses " +index.toString(), 2, 1, controle.getEntrainement(programme,1));
        this.controle.creerExercice(8, "Fentes " +index.toString(), 3, 1, controle.getEntrainement(programme,1));
        this.controle.creerExercice(9, "Leg curl " +index.toString(), 4, 1, controle.getEntrainement(programme,1));
        this.controle.creerExercice(10, "Hip thrust " +index.toString(), 5, 1, controle.getEntrainement(programme,1));
        this.controle.creerExercice(11, "Gainage " +index.toString(), 6, 1, controle.getEntrainement(programme,1));

        this.controle.creerExercice(12, "Developpé cocouché 2 " +index.toString(), 1, 1, controle.getEntrainement(programme,2));
        this.controle.creerExercice(13, "Developpé cocouhé incliné haltères 2 " +index.toString(), 2, 1, controle.getEntrainement(programme,2));
        this.controle.creerExercice(14, "Pull over 2 " +index.toString(), 3, 1, controle.getEntrainement(programme,2));
        this.controle.creerExercice(15, "Curl incliné 2 " +index.toString(), 4, 1, controle.getEntrainement(programme,2));
        this.controle.creerExercice(16, "Curl marteau 2 " +index.toString(), 5, 1, controle.getEntrainement(programme,2));
        this.controle.creerExercice(17, "Abdos à la poulis haute 2 " +index.toString(), 6, 1, controle.getEntrainement(programme,2));

        this.controle.creerExercice(6, "Squat avant 2 " +index.toString(), 1, 1, controle.getEntrainement(programme,3));
        this.controle.creerExercice(7, "Presse à cuisses 2 " +index.toString(), 2, 1, controle.getEntrainement(programme,3));
        this.controle.creerExercice(8, "Fentes 2 " +index.toString(), 3, 1, controle.getEntrainement(programme,3));
        this.controle.creerExercice(9, "Leg curl 2 " +index.toString(), 4, 1, controle.getEntrainement(programme,3));
        this.controle.creerExercice(10, "Hip thrust 2 " +index.toString(), 5, 1, controle.getEntrainement(programme,3));
        this.controle.creerExercice(11, "Gainage 2 " +index.toString(), 6, 1, controle.getEntrainement(programme,3));

        /*for(int i = 0; i<=3 ; i++){
            Entrainement entrainement = controle.getEntrainement(programme, i);
            for(int j =0; j<=5; j++){
                Exercice exercice = entrainement.getLesExercices().get(j);
                this.controle.creerSeance(i+j, j+1, 5,"Trop facile",i, exercice);
            }
        }*/


    }


}