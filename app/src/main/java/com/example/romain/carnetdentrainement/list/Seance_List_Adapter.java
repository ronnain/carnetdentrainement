package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.modele.Serie;
import com.example.romain.carnetdentrainement.modele.Seance;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class Seance_List_Adapter extends BaseAdapter {
    private ArrayList<Seance> lesSeances;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;


    public Seance_List_Adapter(Context contexte, Controle controle, ArrayList<Seance> lesSeances){
        this.lesSeances = lesSeances;
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
        return lesSeances.size();
    }


    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesSeances.get(i);
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
        final Seance_List_Adapter.ViewHolder holder;
        //si la ligne n'existe pas encore
        if(view == null){
            holder = new Seance_List_Adapter.ViewHolder();

            //la ligne est construire avec un formatage (inflater) relié à layout_list
            view = inflater.inflate(R.layout.layout_seances, null);

            // chaque propriété du holder est relié à une propriété graphique
            holder.linearLayoutSeries = (LinearLayout) view.findViewById(R.id.linearLayoutSeries);
            holder.linearLayoutBtnAddSerie = (LinearLayout) view.findViewById(R.id.linearLayoutBtnAddSerie);
            // affecter le holder
            view.setTag(holder);

            // valorisation du contenu du holder (donc de la ligne)
            ArrayList<Serie> lesSeries = this.controle.getLesSeries();
            //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());

            if(lesSeries != null) {
            /*Serie_List_Adapter adapter = new Serie_List_Adapter(this.contexte, lesSeries);
            holder.lstSeriesSeance.setAdapter(adapter);*/
                Serie s;
                for(int k = 0; k < lesSeries.size(); k++) {
                    s = lesSeries.get(k);
                    addChildSerie(holder, s);
                }
                addBtnAddSerie(holder);
            }

        }else{
            //Récupération du holder dans la ligne existante
            holder = (Seance_List_Adapter.ViewHolder) view.getTag();
        }


        return  view;
    }

    private class ViewHolder{
        LinearLayout linearLayoutSeries;
        LinearLayout linearLayoutBtnAddSerie;
    }

    private void addChildSerie(final ViewHolder holder, final Serie s){
        View child = inflater.inflate(R.layout.layout_series, null);
        TextView txtRep = (TextView) child.findViewById(R.id.txtRep);
        TextView txtCharge = (TextView) child.findViewById(R.id.txtCharge);
        txtRep.setText(s.getRep().toString());
        txtCharge.setText(s.getCharge().toString());

        holder.linearLayoutSeries.addView(child);
        notifyDataSetChanged();
    }


    private void addBtnAddSerie(final ViewHolder holder){
        ImageButton imgBtnAddSerie = new ImageButton(contexte);
        imgBtnAddSerie.setImageResource(R.drawable.btn_add_24dp);
        //holder.linearLayoutBtnAddSerie.addView(imgBtnAddSerie);

        imgBtnAddSerie.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                View mView = inflater.inflate(R.layout.dialog_add_serie, null);

                mBuilder.setView(mView);
                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                //TextView textDialogTitre = (TextView) mView.findViewById(R.id.textDialogTitre);
                final EditText editRep = (EditText) mView.findViewById(R.id.editRep);
                final EditText editCharge = (EditText) mView.findViewById(R.id.editCharge);
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);

                txtDialogTitre.setText("Ajouter une série");
                ArrayList<Serie> lesSeries = controle.getLesSeries();
                Serie sLast = lesSeries.get(lesSeries.size()-1);
                editRep.setText(sLast.getRep().toString());
                editCharge.setText(sLast.getCharge().toString());

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if(editRep.getText().toString().isEmpty() || editCharge.getText().toString().isEmpty()){
                            Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }else {
                            controle.creerSerie(Integer.parseInt(editRep.getText().toString()), Integer.parseInt(editCharge.getText().toString()), contexte);
                            ArrayList<Serie> lesSeries = controle.getLesSeries();
                            Serie sAdd = lesSeries.get(lesSeries.size()-1);
                            addChildSerie(holder, sAdd);//envoyer la dernière s
                            dialog.hide();
                        }
                    }
                });
                dialog.show();
            }
        });
        holder.linearLayoutBtnAddSerie.addView(imgBtnAddSerie);
    }

}
