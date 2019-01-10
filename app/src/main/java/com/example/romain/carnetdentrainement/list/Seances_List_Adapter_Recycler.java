package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.modele.Entrainement;
import com.example.romain.carnetdentrainement.modele.Exercice;
import com.example.romain.carnetdentrainement.modele.Programme;
import com.example.romain.carnetdentrainement.modele.Seance;
import com.example.romain.carnetdentrainement.modele.Serie;

import java.util.ArrayList;

public class Seances_List_Adapter_Recycler  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Seance> lesSeances;
    private Exercice exercice;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;

    private static final int FOOTER_VIEW = 1;

    // Define a view holder for Footer view

    public class FooterViewHolder extends Seances_List_Adapter_Recycler.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the item

                }
            });
        }
    }

    // Now define the viewholder for Normal list item
    public class NormalViewHolder extends Seances_List_Adapter_Recycler.ViewHolder {
        public NormalViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }
    }

    // And now in onCreateViewHolder you have to pass the correct view
    // while populating the list item.

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_add_entite, parent, false);

            Seances_List_Adapter_Recycler.FooterViewHolder vh = new Seances_List_Adapter_Recycler.FooterViewHolder(v);

            return vh;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_seances, parent, false);

        Seances_List_Adapter_Recycler.NormalViewHolder vh = new Seances_List_Adapter_Recycler.NormalViewHolder(v);

        return vh;
    }

    // Now bind the viewholders in onBindViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            if (holder instanceof Seances_List_Adapter_Recycler.NormalViewHolder) {
                Seances_List_Adapter_Recycler.NormalViewHolder vh = (Seances_List_Adapter_Recycler.NormalViewHolder) holder;

                vh.bindView(position);
            } else if (holder instanceof Seances_List_Adapter_Recycler.FooterViewHolder) {
                Seances_List_Adapter_Recycler.FooterViewHolder vh = (Seances_List_Adapter_Recycler.FooterViewHolder) holder;
                //vh.bindFooterView(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Now the critical part. You have return the exact item count of your list
    // I've only one footer. So I returned data.size() + 1
    // If you've multiple headers and footers, you've to return total count
    // like, headers.size() + data.size() + footers.size()

    @Override
    public int getItemCount() {
        if (lesSeances== null) {
            return 0;
        }

        if (lesSeances.size() == 0) {
            //Return 1 here to show nothing
            return 0;
        }

        // Add extra view to show the footer view
        return lesSeances.size();
    }

    // Now define getItemViewType of your own.

    @Override
    public int getItemViewType(int position) {
       /* if (position == lesSeances.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }*/

        return super.getItemViewType(position);
    }

    // So you're done with adding a footer and its action on onClick.
    // Now set the default ViewHolder for NormalViewHolder

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayoutSeries;
        LinearLayout linearLayoutBtnAddSerie;
        TextView txtNumSeance;
        EditText editNumIntensite;
        EditText editCommentaire;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNumSeance = (TextView) itemView.findViewById(R.id.txtNumSeance);
            editNumIntensite = (EditText) itemView.findViewById(R.id.editNumIntensite);
            editCommentaire = (EditText) itemView.findViewById(R.id.editCommentaire);

            linearLayoutSeries = (LinearLayout) itemView.findViewById(R.id.linearLayoutSeries);
            linearLayoutBtnAddSerie = (LinearLayout) itemView.findViewById(R.id.linearLayoutBtnAddSerie);
        }

        public void bindView(int position) {
            // bindView() method to implement actions

            itemView.setTag(lesSeances.get(position));

            Integer intensite = lesSeances.get(position).getIntensite();
            Integer ordre = lesSeances.get(position).getOrdre();
            String commentaire = lesSeances.get(position).getCommentaire();
            ArrayList<Serie> lesSeries = lesSeances.get(position).getLesSeries();
            //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());

            txtNumSeance.setText("S" + ordre.toString() + " :");
            editNumIntensite.setText(intensite.toString());
            editCommentaire.setText(commentaire);

            if(lesSeries != null) {
                Serie serie;
                for(int k = 0; k < lesSeries.size(); k++) {
                    serie = lesSeries.get(k);
                    addChildSerie(serie);
                }
                addBtnAddSerie();
            }

        }

        /*public void bindFooterView(int position) {
            addBtnAddExercice(btnAddExercice, position);
        }*/

        private void addChildSerie(final Serie s){
            View child = inflater.inflate(R.layout.layout_series, null);
            TextView txtRep = (TextView) child.findViewById(R.id.txtRep);
            TextView txtCharge = (TextView) child.findViewById(R.id.txtCharge);
            txtRep.setText(s.getRep().toString());
            txtCharge.setText(s.getCharge().toString());

            linearLayoutSeries.addView(child);
            //notifyDataSetChanged();
        }

        private void addBtnAddSerie(){
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
                                /*controle.creerSerie(Integer.parseInt(editRep.getText().toString()), Integer.parseInt(editCharge.getText().toString()), contexte);
                                ArrayList<Serie> lesSeries = controle.getLesSeries();
                                Serie serieAdd = lesSeries.get(lesSeries.size()-1);
                                addChildSerie(serieAdd);//envoyer la dernière serie ajouté
                                dialog.hide();*/
                            }
                        }
                    });
                    dialog.show();
                }
            });
            linearLayoutBtnAddSerie.addView(imgBtnAddSerie);
        }




    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Seances_List_Adapter_Recycler(Context contexte, Controle controle, Exercice exercice) {
        this.contexte = contexte;
        this.controle = controle;
        this.inflater = LayoutInflater.from(contexte);
        this.lesSeances = exercice.getLesSeances();

    }
}
