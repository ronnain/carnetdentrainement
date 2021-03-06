package com.example.romain.carnetdentrainement.vue;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.list.Seances_List_Adapter_Recycler;
import com.example.romain.carnetdentrainement.modele.Exercice;
import com.example.romain.carnetdentrainement.modele.Serie;
import com.example.romain.carnetdentrainement.modele.Seance;

import java.io.IOException;
import java.util.ArrayList;

public class SeancesActivity extends AppCompatActivity {

    private Controle controle;
    //private Entrainement entrainement;
    private int idExercice;
    private Exercice exercice;
    private ArrayList<Seance> lesSeances = null;
    private Seances_List_Adapter_Recycler adapterSeances;
    public CountDownTimer mainTimer;
    public CountDownTimer secondTimer;
    private int mainTimeTimer = 5000;
    private int secondTimeTimer = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        Intent intent = getIntent();
        //entrainement = (Entrainement) intent.getParcelableExtra("entrainement");
        String messageIdExercice = intent.getStringExtra(AccueilActivity.EXTRA_MESSAGE_EXERCICE_ID);
        idExercice = Integer.parseInt(messageIdExercice);

        init();
        addRecyclerSeances();
        //addObjectifContainer();

    }



    private void init(){
        this.controle = Controle.getInstance(this);
        initbtnAddSeance();
        initbtnChronoSetting();
}

    /**
     * créer liste adpater
     */
    private void addRecyclerSeances(){

        RecyclerView mRecyclerView;
        Seances_List_Adapter_Recycler mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerSeances);

        mLayoutManager =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new Seances_List_Adapter_Recycler(this, controle, idExercice);
        mRecyclerView.setAdapter(mAdapter);

        this.adapterSeances = mAdapter;
    }


    private void initbtnAddSeance(){
        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SeancesActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_serie, null);

                mBuilder.setView(mView);
                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                //TextView textDialogTitre = (TextView) mView.findViewById(R.id.textDialogTitre);
                final EditText editRep = (EditText) mView.findViewById(R.id.editRep);
                final EditText editCharge = (EditText) mView.findViewById(R.id.editCharge);
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                final Button btnRemove = (Button) mView.findViewById(R.id.btnRemove);

                btnRemove.setVisibility(View.GONE);

                Serie lastSerie = controle.getLastSerieOfSeanceByExercice(idExercice);
                if(lastSerie != null){
                    editRep.setText(lastSerie.getRep().toString());
                    editCharge.setText(lastSerie.getCharge().toString());
                }

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if(editRep.getText().toString().isEmpty() || editCharge.getText().toString().isEmpty()){
                            Toast.makeText(SeancesActivity.this, "Veuillez renseigner le nombre de répétitions et la charge utlisée.", Toast.LENGTH_SHORT).show();
                        }else {
                            //Create new seance and the associate serie and add it to the seances array of the current adpater
                            adapterSeances.addSeance(controle.addSeance(Integer.parseInt(editRep.getText().toString()), Integer.parseInt(editCharge.getText().toString()), idExercice));
                            adapterSeances.notifyItemInserted(0);
                            Toast.makeText(SeancesActivity.this, "Nouvelle séance créée.", Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void initbtnChronoSetting(){
        final Button btnTimer = (Button) findViewById(R.id.btnTimer);
        btnTimer.setText(Integer.toString(mainTimeTimer/1000)+ "\"");
        ImageView btnChronoSetting = (ImageView) findViewById(R.id.btnChronoSetting);
        btnChronoSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SeancesActivity.this);
                View mViewChrono = getLayoutInflater().inflate(R.layout.dialog_chrono, null);
                mBuilder.setView(mViewChrono);



                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                final EditText editChronoDefaut = (EditText) mViewChrono.findViewById(R.id.editChronoDefaut);
                final Button btnValid = (Button) mViewChrono.findViewById(R.id.btnValid);

                final Button btnChrono1 = (Button) mViewChrono.findViewById(R.id.btnChrono1);
                final Button btnChrono2 = (Button) mViewChrono.findViewById(R.id.btnChrono2);
                final Button btnChrono3 = (Button) mViewChrono.findViewById(R.id.btnChrono3);
                final Button btnChrono4 = (Button) mViewChrono.findViewById(R.id.btnChrono4);
                final Button btnChrono5 = (Button) mViewChrono.findViewById(R.id.btnChrono5);
                final Button btnChrono6 = (Button) mViewChrono.findViewById(R.id.btnChrono6);
                final Button btnChrono7 = (Button) mViewChrono.findViewById(R.id.btnChrono7);
                final Button btnChrono8 = (Button) mViewChrono.findViewById(R.id.btnChrono8);
                final Button btnChrono9 = (Button) mViewChrono.findViewById(R.id.btnChrono9);
                final Button btnChrono10 = (Button) mViewChrono.findViewById(R.id.btnChrono10);
                final Button btnChrono11 = (Button) mViewChrono.findViewById(R.id.btnChrono11);
                final Button btnChrono12 = (Button) mViewChrono.findViewById(R.id.btnChrono12);

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if(mainTimer != null){
                            mainTimer.cancel();
                        }
                        if(secondTimer != null){
                            secondTimer.cancel();
                        }
                            mainTimeTimer = Integer.parseInt(editChronoDefaut.getText().toString())*1000;
                            btnTimer.setText(Integer.toString(mainTimeTimer/1000)+ "\"");
                            dialog.hide();
                            mainTimer = new CountDownTimer (mainTimeTimer, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    btnTimer.setText(String.valueOf(Math.ceil(millisUntilFinished/1000)));
                                }
                                public void onFinish() {
                                    btnTimer.setText(Integer.toString(mainTimeTimer/1000)+ "\"");
                                    try {
                                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                        r.play();
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                    }
                });

                btnChrono1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 30*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 45*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 60*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 75*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 90*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 105*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 120*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 135*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 150*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 165*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 180*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });
                btnChrono12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        secondTimeTimer = 300*1000;
                        setSencondTimer();
                        dialog.hide();
                    }
                });

                dialog.show();
            }

            void setSencondTimer(){
                if(mainTimer != null){
                    mainTimer.cancel();
                }
                if(secondTimer != null){
                    secondTimer.cancel();
                }
                secondTimer = new CountDownTimer (secondTimeTimer, 1000) {
                    public void onTick(long millisUntilFinished) {
                        String messageTime = "";
                        int min = (int) Math.ceil(millisUntilFinished/60000);
                        if(min >0){
                            messageTime += String.valueOf(min);
                            messageTime += "' ";
                        }
                        int sec = (int) millisUntilFinished/1000%60;
                        messageTime += String.valueOf(sec);
                        messageTime += "\"";
                        btnTimer.setText(messageTime);
                    }
                    public void onFinish() {
                        String messageTime = "";
                        int min = (int) Math.ceil(mainTimeTimer/60000);
                        if(min >0){
                            messageTime += String.valueOf(min);
                            messageTime += "' ";
                        }
                        int sec = (int) mainTimeTimer/1000%60;
                        messageTime += String.valueOf(sec);
                        messageTime += "\"";
                        btnTimer.setText(messageTime);
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainTimer != null){
                    mainTimer.cancel();
                }
                if(secondTimer != null){
                    secondTimer.cancel();
                }

                mainTimer = new CountDownTimer (mainTimeTimer, 1000) {
                    public void onTick(long millisUntilFinished) {
                        String messageTime = "";
                        int min = (int) Math.ceil(millisUntilFinished/60000);
                        if(min >0){
                            messageTime += String.valueOf(min);
                            messageTime += "' ";
                        }
                        int sec = (int) millisUntilFinished/1000%60;
                            messageTime += String.valueOf(sec);
                        messageTime += "\"";
                        btnTimer.setText(messageTime);
                    }
                    public void onFinish() {
                        String messageTime = "";
                        int min = (int) Math.ceil(mainTimeTimer/60000);
                        if(min >0){
                            messageTime += String.valueOf(min);
                            messageTime += "' ";
                        }
                        int sec = (int) mainTimeTimer/1000%60;
                        messageTime += String.valueOf(sec);
                        messageTime += "\"";
                        btnTimer.setText(messageTime);
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private void addObjectifContainer(){

        ImageButton imgBtnObjectif = (ImageButton) findViewById(R.id.imgBtnObjectif);
        imgBtnObjectif.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SeancesActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_objectifs, null);
                mBuilder.setView(mView);

                final android.support.v7.app.AlertDialog dialog = mBuilder.create();

                LinearLayout lytSeance = (LinearLayout) mView.findViewById(R.id.lytSeance);
                View mObjectifView = getLayoutInflater().inflate(R.layout.layout_seances, null);
                LinearLayout linearLayoutSeries = (LinearLayout) mObjectifView.findViewById(R.id.linearLayoutSeries);
                lytSeance.addView(mObjectifView);

                ArrayList<Serie> lesSeries = exercice.getLesSeances().get(0).getLesSeries();

                if(lesSeries != null) {
                    Serie s;
                    for(int k = 0; k < lesSeries.size(); k++) {
                        s = lesSeries.get(k);
                        View child = getLayoutInflater().inflate(R.layout.layout_series, null);
                        TextView txtRep = (TextView) child.findViewById(R.id.txtRep);
                        TextView txtCharge = (TextView) child.findViewById(R.id.txtCharge);
                        txtRep.setText(s.getRep().toString());
                        txtCharge.setText(s.getCharge().toString());

                        linearLayoutSeries.addView(child);
                    }
                }

                dialog.show();
            }
        });





        /*LinearLayout lytObjectif = (LinearLayout) findViewById(R.id.lytObjectif);
        View mView = getLayoutInflater().inflate(R.layout.objectifs_container, null);
        LinearLayout linearLayoutSeries = (LinearLayout) mView.findViewById(R.id.linearLayoutSeance);
        lytObjectif.addView(mView);



        /*ArrayList<Serie> lesSeries = this.controle.getLesSeries();
        //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
        if(lesSeries != null) {
            /*Serie_List_Adapter adapter = new Serie_List_Adapter(this.contexte, lesSeries);
            holder.lstSeriesSeance.setAdapter(adapter);*/
            /*Serie s;
            for(int k = 0; k < lesSeries.size(); k++) {
                s = lesSeries.get(k);
                View child = getLayoutInflater().inflate(R.layout.layout_series, null);
                TextView txtRep = (TextView) child.findViewById(R.id.txtRep);
                TextView txtCharge = (TextView) child.findViewById(R.id.txtCharge);
                txtRep.setText(s.getRep().toString());
                txtCharge.setText(s.getCharge().toString());

                linearLayoutSeries.addView(child);
            }
        }*/
    }
}
