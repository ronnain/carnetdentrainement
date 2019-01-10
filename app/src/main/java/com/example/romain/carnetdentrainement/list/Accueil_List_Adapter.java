package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.modele.Exercice;
import com.example.romain.carnetdentrainement.modele.Programme;
import com.example.romain.carnetdentrainement.modele.Entrainement;
import com.example.romain.carnetdentrainement.vue.SeancesActivity;

import java.util.ArrayList;

public class Accueil_List_Adapter extends BaseAdapter {

    private ArrayList<Programme> lesProgrammes;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;

    public Accueil_List_Adapter(Context contexte, Controle controle,ArrayList<Programme> lesProgrammes){
        this.lesProgrammes = lesProgrammes;
        this.contexte = contexte;
        this.controle = controle;
        this.inflater = LayoutInflater.from(contexte);
    }

    /**
     * Retourne le nombre de lignes
     * @return
     */
    @Override
    public int getCount() {
        return lesProgrammes.size();
    }


    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesProgrammes.get(i);
    }

    /**
     * Retourne un indice par rapport à la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Retourne l'objet View formaté avec la gestion des évènements
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //déclaration d'un holder
        final ViewHolder holder;
        //si la ligne n'existe pas encore
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.programme_container, null);

            holder.txtProgramme = (TextView) view.findViewById(R.id.txtProgramme);
            holder.imgBtnSettingProgramme = (ImageButton) view.findViewById(R.id.imgBtnSettingProgramme);
            holder.lytEntrainements = (LinearLayout) view.findViewById(R.id.lytEntrainements);
            addChildEntrainements(holder, lesProgrammes.get(i));
            addBtnAddEntrainement(holder);
            // affecter le holder
            view.setTag(holder);
        }else{

            //Récupération du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
            addChildEntrainements(holder, lesProgrammes.get(i));
        }

        holder.txtProgramme.setText(lesProgrammes.get(i).getNom());
        initBtnSettingsProgramme(holder, i);


        return  view;
    }

    private class ViewHolder{
        TextView txtProgramme;
        ImageButton imgBtnSettingProgramme;
        ImageButton imgBtnSettingEntrainement;
        LinearLayout lytEntrainements;
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
    }

    private void addChildEntrainements(final ViewHolder holder, Programme programme) {

        //ArrayList<Entrainement> lesEntrainements = controle.getLesEntrainements();
        ArrayList<Entrainement> lesEntrainements = programme.getLesEntrainements();
        //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
        if (lesEntrainements != null) {
            Entrainement entrainement;
            for (int k = 0; k < lesEntrainements.size(); k++) {
                View child = inflater.inflate(R.layout.entrainement_container, null); //Regarder si route != null
                holder.imgBtnSettingEntrainement = (ImageButton) child.findViewById(R.id.imgBtnSettingEntrainement);
                TextView txtEntrainement = (TextView) child.findViewById(R.id.txtEntrainement);


                entrainement = lesEntrainements.get(k);
                addChildsExercicesHorizontalRecycler(child, holder, entrainement); // Add horizontal recyclerView for exercices in AccueilActivity

                txtEntrainement.setText(entrainement.getNom());
                holder.lytEntrainements.addView(child);

                initBtnSettingsEntrainement(holder, lesEntrainements, k);
            }
        }
    }

    private void addChildsExercicesGride(View root) {
        TableLayout tExercices = (TableLayout) root.findViewById(R.id.tExercices);
        ArrayList<Exercice> lesExercices = controle.getLesExercices();
        //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
        if (lesExercices != null) {
            Exercice exercice;
            int nbrow = (int) Math.ceil(lesExercices.size()/3);
            ArrayList<TableRow> lesRow = new ArrayList<TableRow>();

            //création des lignes du tableau
            for(int nbligne = 0 ; nbligne<= nbrow ; nbligne++){
                TableRow row = new TableRow(contexte);
                row.setGravity(Gravity.CENTER);//Affectation paramètre aux lignes du tableau

                lesRow.add(row);
            }

            //insertion des exercices dans le tableau
            for (int l = 0; l < lesExercices.size(); l++) {
                //View childExercice = inflater.inflate(R.layout.exercice_container, null); //Regarder si route != null
                View childExercice = inflater.inflate(R.layout.card_exercice, null);
                TextView txtExercice = (TextView) childExercice.findViewById(R.id.txtExercice);
                //LinearLayout lytExercice = (LinearLayout) childExercice.findViewById(R.id.lytExercice);
                exercice = lesExercices.get(l);
                txtExercice.setText(exercice.getNom());
                //txtExercice.setText("ok");


                afficherExerciceTxtView(txtExercice);

                lesRow.get((int)Math.ceil(l/3)).addView(childExercice);
            }

            for (int iTable = 0 ; iTable < lesRow.size(); iTable ++) {
                tExercices.addView(lesRow.get(iTable));
            }
        }
    }

    private void addChildsExercicesHorizontalRecycler(View root, ViewHolder holder, Entrainement entrainement){
        holder.mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerExercices);
        holder.mRecyclerView.setHasFixedSize(true);

        holder.mLayoutManager =new LinearLayoutManager(contexte, LinearLayoutManager.HORIZONTAL, false);
        holder.mRecyclerView.setLayoutManager(holder.mLayoutManager);

        holder.mAdapter = new Exercices_List_Adapter_Recycler(contexte, controle, entrainement);
        holder.mRecyclerView.setAdapter(holder.mAdapter);

    }


    private void afficherExerciceTxtView(TextView txtExercice) {
        txtExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent = new Intent(contexte, SeancesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                contexte.startActivity(intent);
            }
        });
    }


    private void addBtnAddEntrainement(ViewHolder holder) {

        ImageButton imgBtnAddEntrainement = new ImageButton(contexte);
        imgBtnAddEntrainement.setImageResource(R.drawable.btn_add_24dp);

        imgBtnAddEntrainement.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                View mView = inflater.inflate(R.layout.dialog_add_entite, null);

                mBuilder.setView(mView);
                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);
                final EditText editNomEntite = (EditText) mView.findViewById(R.id.editNomEntite);

                txtDialogTitre.setText("Ajouter un Entrainement");


                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        String nomNewEntite = editNomEntite.getText().toString().trim();

                        if (!nomNewEntite.isEmpty()) {

                            controle.creerEntrainement(nomNewEntite, contexte);
                            //notifyDataSetChanged();

                            Toast.makeText(contexte, "Entrainement : " + nomNewEntite + " a été ajouté.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }

                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        holder.lytEntrainements.addView(imgBtnAddEntrainement);
    }


    private void initBtnSettingsProgramme(ViewHolder holder, final int position){
        holder.imgBtnSettingProgramme.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                View mView = inflater.inflate(R.layout.dialog_setting, null);

                mBuilder.setView(mView);
                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                final Button btnSupprimer = (Button) mView.findViewById(R.id.btnSupprimer);
                final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);
                final EditText editNomEntite = (EditText) mView.findViewById(R.id.editNomEntite);

                txtDialogTitre.setText("Modidier un Programme");
                editNomEntite.setText(lesProgrammes.get(position).getNom());


                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        String nomNewEntite = editNomEntite.getText().toString().trim();

                        if (!nomNewEntite.isEmpty()) {
                            controle.getLesProgrammes().get(position).setNom(nomNewEntite);
                            notifyDataSetChanged();

                            Toast.makeText(contexte, "Programme : " + nomNewEntite + " a été modifié.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }

                        dialog.hide();
                    }
                });

                btnSupprimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {

                            Programme programmeSupprimer = controle.getLesProgrammes().get(position);
                            controle.supprimerProgramme(programmeSupprimer, contexte);
                            notifyDataSetChanged();

                            Toast.makeText(contexte, "Programme supprimé.", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });
    }

    private void initBtnSettingsEntrainement(ViewHolder holder, final ArrayList<Entrainement> lesEntrainements, final int position){
        holder.imgBtnSettingEntrainement.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                View mView = inflater.inflate(R.layout.dialog_setting, null);

                mBuilder.setView(mView);
                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                final Button btnSupprimer = (Button) mView.findViewById(R.id.btnSupprimer);
                final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);
                final EditText editNomEntite = (EditText) mView.findViewById(R.id.editNomEntite);

                txtDialogTitre.setText("Modidier un Entrainement");
                editNomEntite.setText(lesEntrainements.get(position).getNom());


                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        String nomNewEntite = editNomEntite.getText().toString().trim();

                        if (!nomNewEntite.isEmpty()) {
                            controle.getLesEntrainements().get(position).setNom(nomNewEntite);
                            notifyDataSetChanged();

                            Toast.makeText(contexte, "Entrainement : " + nomNewEntite + " a été modifié.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }

                        dialog.hide();
                    }
                });

                btnSupprimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {

                        Entrainement entrementSupprimer = controle.getLesEntrainements().get(position);
                        controle.supprimerEntrainement(entrementSupprimer, contexte);
                        notifyDataSetChanged();

                        Toast.makeText(contexte, "Programme supprimé.", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });
    }
}
