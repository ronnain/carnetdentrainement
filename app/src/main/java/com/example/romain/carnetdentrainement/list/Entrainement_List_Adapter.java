package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.modele.Entrainement;
import com.example.romain.carnetdentrainement.vue.EntrainementsActivity;

import java.util.ArrayList;

public class Entrainement_List_Adapter extends BaseAdapter{
    private ArrayList<Entrainement> lesEntrainements;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;

    public Entrainement_List_Adapter(Context contexte, ArrayList<Entrainement> lesEntrainements){
        this.lesEntrainements = lesEntrainements;
        this.contexte = contexte;
        this.inflater = LayoutInflater.from(contexte);
    }

    /**
     * Retourne le nombre de lignes
     * @return
     */
    @Override
    public int getCount() {
        return lesEntrainements.size();
    }


    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesEntrainements.get(i);
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
        final Entrainement_List_Adapter.ViewHolder holder;
        //si la ligne n'existe pas encore
        if(view == null){
            holder = new Entrainement_List_Adapter.ViewHolder();
            //la ligne est construire avec un formatage (inflater) relié à layout_list_histo
            view = inflater.inflate(R.layout.layout_list, null);
            // chaque propriété du holder est relié à une propriété graphique
            holder.txtEntite = (TextView) view.findViewById(R.id.txtEntite);
            holder.btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
            holder.btnSuppr = (ImageButton) view.findViewById(R.id.btnSuppr);
            // affecter le holder
            view.setTag(holder);
        }else{
            //Récupération du holder dans la ligne existante
            holder = (Entrainement_List_Adapter.ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la ligne)
        holder.txtEntite.setText(lesEntrainements.get(i).getNom());

        //click sur le boutton crayon pour modifier le titre
        initbtnEdit(holder, i);

        //Clic sur la poubelle pour supprimer le programme
        initbtnSuppr(holder, i);

        //Clic sur le tritre pour afficher son contenu
        // long click et affiche les boutons d'options
        inittxtEntite(holder,i);

        return  view;
    }

    private class ViewHolder{
        ImageButton btnSuppr;
        ImageButton btnEdit;
        TextView txtEntite;
    }

    /**
     * Affecte les fonctions au btn crayon
     * @param holder
     * @param i
     */
    private void initbtnEdit(final Entrainement_List_Adapter.ViewHolder holder, int i){

        holder.btnEdit.setTag(i);
        //Clic sur le boutton crayon pour éditer le programme enregistré
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int position;
                position = (int)v.getTag();
                //demande suppresion au controleur.
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(contexte);
                View mView = inflater.inflate(R.layout.dialog, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                TextView textDialogTitre = (TextView) mView.findViewById(R.id.textDialogTitre);
                final EditText editNom = (EditText) mView.findViewById(R.id.editNom);
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);

                //Cache les button edit et suppr
                holder.btnEdit.setVisibility(View.INVISIBLE);
                holder.btnSuppr.setVisibility(View.INVISIBLE);

                textDialogTitre.setText("Modifiez le nom :");
                editNom.setText(holder.txtEntite.getText().toString());

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if(editNom.getText().toString().isEmpty()){
                            Toast.makeText(contexte, "Aucun nouveau nom.", Toast.LENGTH_SHORT).show();
                        }else {
                            lesEntrainements.get(position).setNom(editNom.getText().toString());
                            //raffraichir la liste
                            notifyDataSetChanged();
                            dialog.hide();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    /**
     * Affecte les fonctions au bouton poubelle
     * @param holder
     * @param i
     */
    private void initbtnSuppr(final Entrainement_List_Adapter.ViewHolder holder, int i){
        holder.btnSuppr.setTag(i);
        holder.btnSuppr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int position;
                position = (int)v.getTag();
                //demande suppresion au controleur.
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(contexte);
                View mView = inflater.inflate(R.layout.dialog, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                TextView textDialogTitre = (TextView) mView.findViewById(R.id.textDialogTitre);
                final EditText editNom = (EditText) mView.findViewById(R.id.editNom);
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);

                //Cache les button edit et suppr
                holder.btnEdit.setVisibility(View.INVISIBLE);
                holder.btnSuppr.setVisibility(View.INVISIBLE);


                textDialogTitre.setText("Voulez-vous supprimer le programe : \n"+holder.txtEntite.getText().toString());
                editNom.setVisibility(View.INVISIBLE);

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        lesEntrainements.remove(position);
                        //raffraichir la liste
                        notifyDataSetChanged();
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });
    }

    private void inittxtEntite(final Entrainement_List_Adapter.ViewHolder holder, int i){
        holder.txtEntite.setTag(i);
        holder.txtEntite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = (int)v.getTag();
                //demande de l'affichage du programme dans EntrainementsActivity
                ((EntrainementsActivity)contexte).afficheSeance(lesEntrainements.get(position));
            }
        });

        holder.txtEntite.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                int position = (int)v.getTag();
                //demande de l'affichage du programme dans EntrainementsActivity
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnSuppr.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }
}

