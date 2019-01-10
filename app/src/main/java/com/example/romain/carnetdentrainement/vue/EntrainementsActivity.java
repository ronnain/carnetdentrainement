package com.example.romain.carnetdentrainement.vue;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.list.Entrainement_List_Adapter;
import com.example.romain.carnetdentrainement.modele.Entrainement;

import java.util.ArrayList;

public class EntrainementsActivity extends AppCompatActivity {

    private Controle controle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Séances");
        setContentView(R.layout.activity_entrainements);
        init();
        creerListe();
    }


    private void init(){
        this.controle = Controle.getInstance(this);
        initbtnAdd();
        if(controle.getLesEntrainements().isEmpty()) {
            this.controle.creerEntrainement("Entrainement 1", this);
            this.controle.creerEntrainement("Entrainement 2", this);
            this.controle.creerEntrainement("Entrainement 3", this);
            this.controle.creerEntrainement("Entrainement 4", this);
        }
    }

    /**
     * créer liste adpater
     */
    private void creerListe(){
        ArrayList<Entrainement> lesEntrainements = controle.getLesEntrainements();
        //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
        if(lesEntrainements != null){
            ListView lstSeances = (ListView) findViewById(R.id.lstSeances);
            Entrainement_List_Adapter adapter = new Entrainement_List_Adapter(this, lesEntrainements);
            lstSeances.setAdapter(adapter);
        }
    }

    /**
     * Demande d'afficher le programme dans SeancesActivity
     * @param entrainement
     */
    public void afficheSeance(Entrainement entrainement){
        controle.setEntrainement(entrainement);
        Intent intent = new Intent(EntrainementsActivity.this, ExercicesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK ); //| Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent);
    }


    private void initbtnAdd(){
        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EntrainementsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                TextView textDialogTitre = (TextView) mView.findViewById(R.id.textDialogTitre);
                final EditText editNom = (EditText) mView.findViewById(R.id.editNom);
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);

                textDialogTitre.setText("Nouvelle séance :");

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if(editNom.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }else {
                            controle.creerEntrainement(editNom.getText().toString(),EntrainementsActivity.this);
                            dialog.hide();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}
