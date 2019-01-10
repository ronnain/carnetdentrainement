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
import android.widget.Toast;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.list.Program_List_Adapter;
import com.example.romain.carnetdentrainement.modele.Programme;

import java.util.ArrayList;

public class ProgrammesActivity extends AppCompatActivity {

    private Controle controle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        init();
        creerListe();
    }


    private void init(){
        this.controle = Controle.getInstance(this);
        initbtnAdd();
        if(controle.getLesProgrammes().isEmpty()) {
            this.controle.creerProgramme("programme 1", this);
            this.controle.creerProgramme("programme 2", this);
            this.controle.creerProgramme("Programme 3", this);
            this.controle.creerProgramme("Programme 4", this);
        }

    }

    /**
     * créer liste adpater
     */
    private void creerListe(){
        ArrayList<Programme> lesProgrammes = controle.getLesProgrammes();
        //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
        if(lesProgrammes != null){
            ListView lstPrograms = (ListView) findViewById(R.id.lstPrograms);
            Program_List_Adapter adapter = new Program_List_Adapter(this, lesProgrammes);
            lstPrograms.setAdapter(adapter);
        }
    }

    /**
     * Demande d'afficher le programme
     * @param programme
     */
    public void afficheProgramme(Programme programme){
        controle.setProgramme(programme);
        Intent intent = new Intent(ProgrammesActivity.this, EntrainementsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK ); //| Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent);
    }

    private void initbtnAdd(){
        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProgrammesActivity.this);
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
                            controle.creerProgramme(editNom.getText().toString(),ProgrammesActivity.this);
                            dialog.hide();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

}
