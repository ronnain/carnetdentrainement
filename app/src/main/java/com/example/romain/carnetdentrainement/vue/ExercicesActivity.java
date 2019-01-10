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
import com.example.romain.carnetdentrainement.list.Exercice_List_Adapter;
import com.example.romain.carnetdentrainement.modele.Exercice;

import java.util.ArrayList;

public class ExercicesActivity extends AppCompatActivity {

    private Controle controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Exercices");
        setContentView(R.layout.activity_exercices);
        init();
        creerListe();
    }

    private void init(){
        this.controle = Controle.getInstance(this);
        initbtnAdd();
        if(controle.getLesExercices().isEmpty()) {
            this.controle.creerExercice("Exercice 1", this);
            this.controle.creerExercice("Exercice 2", this);
            this.controle.creerExercice("Exercice 3", this);
            this.controle.creerExercice("Exercice 4", this);
        }

    }

    /**
     * créer liste adpater
     */
    private void creerListe(){
        ArrayList<Exercice> lesExercices = controle.getLesExercices();
        //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
        if(lesExercices != null){
            ListView lstExercice = (ListView) findViewById(R.id.lstExercices);
            Exercice_List_Adapter adapter = new Exercice_List_Adapter(this, lesExercices);
            lstExercice.setAdapter(adapter);
        }
    }

    /**
     * Demande d'afficher l'exercice
     * @param exercice
     */
    public void afficheSerie(Exercice exercice){
        controle.setExercice(exercice);
        Intent intent = new Intent(ExercicesActivity.this, SeancesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(intent);
    }

    private void initbtnAdd(){
        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ExercicesActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                TextView textDialogTitre = (TextView) mView.findViewById(R.id.textDialogTitre);
                final EditText editNom = (EditText) mView.findViewById(R.id.editNom);
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);

                textDialogTitre.setText("Nouveau exerice. :");

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if(editNom.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }else {
                            controle.creerExercice(editNom.getText().toString(),ExercicesActivity.this);
                            dialog.hide();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}
