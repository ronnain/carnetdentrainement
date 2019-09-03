package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private ArrayList<Seance> seances;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;
    private static final int FOOTER_VIEW = 1;

    public void addSeance(Seance newSeance){
        seances.add(0, newSeance);
    }
    public int getSeancesLength(){
        if(seances != null) {
            return seances.size();
        }else{
            return 0;
        }
    }

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
        if (seances== null) {
            return 0;
        }

        if (seances.size() == 0) {
            //Return 1 here to show nothing
            return 0;
        }

        // Add extra view to show the footer view
        return seances.size();
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
        ImageButton btnSettingSeance;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNumSeance = (TextView) itemView.findViewById(R.id.txtNumSeance);
            editNumIntensite = (EditText) itemView.findViewById(R.id.editNumIntensite);
            editCommentaire = (EditText) itemView.findViewById(R.id.editCommentaire);
            linearLayoutSeries = (LinearLayout) itemView.findViewById(R.id.linearLayoutSeries);
            linearLayoutBtnAddSerie = (LinearLayout) itemView.findViewById(R.id.linearLayoutBtnAddSerie);
            btnSettingSeance = (ImageButton) itemView.findViewById(R.id.imgBtnSettingSeance);
            if(linearLayoutSeries.getChildCount() < 1){ //Creation of the first element
                int idSeance = controle.getLastIdSeance();
                addChildSerie(idSeance);
                addBtnAddSerie(idSeance);
                int position =0;
                String strSeance =  "S" + (getSeancesLength() - position);
                setSettingSeance(idSeance, strSeance);
            }
        }

        public void bindView(int position) {
            itemView.setTag(seances.get(position));

            Integer intensite = seances.get(position).getIntensite();
            String commentaire = seances.get(position).getCommentaire();
            int idSeance = seances.get(position).getId();
            String strSeance = "S" + (getSeancesLength() - position);

            txtNumSeance.setText(strSeance);
            editNumIntensite.setText(intensite.toString());
            editCommentaire.setText(commentaire);

            refreshLytSeries(idSeance);
            setSettingSeance(idSeance, strSeance);
            updateField(idSeance, editNumIntensite, intensite, editCommentaire, commentaire, position);
        }

        /*public void bindFooterView(int position) {
            addBtnAddExercice(btnAddExercice, position);
        }*/
        private void setSettingSeance(final int idSeance, final String strSeance){
            btnSettingSeance.setOnClickListener(null);
            btnSettingSeance.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    int posFind = -1; //look for the item position
                    for(int i =0; i< seances.size(); i++){
                        if(seances.get(i).getId() == idSeance){
                            posFind = i;
                        }
                    }
                    if(posFind == -1) return;
                    final int position = posFind;

                    android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                    View mView = inflater.inflate(R.layout.dialog_setting_seance, null);

                    mBuilder.setView(mView);
                    final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                    final TextView txtSeance = (TextView) mView.findViewById(R.id.txtSeance);
                    final Button btnRemove = (Button) mView.findViewById(R.id.btnRemove);
                    final Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);

                    txtSeance.setText(strSeance);

                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            controle.removeSeance(idSeance);
                            seances.remove(position);
                            notifyItemRemoved(position);
                            //notifyDataSetChanged();
                            dialog.hide();
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            dialog.hide();
                        }
                    });
                    dialog.show();
                }
            });
        }

        private void addChildSerie(final int idSeance){
            ArrayList<Serie> series = controle.getSeries(idSeance);
            if(series != null) {
                Serie serie;
                for (int k = 0; k < series.size(); k++) {
                    serie = series.get(k);
                    View child = inflater.inflate(R.layout.layout_series, null);
                    TextView txtRep = (TextView) child.findViewById(R.id.txtRep);
                    TextView txtCharge = (TextView) child.findViewById(R.id.txtCharge);
                    LinearLayout lytSerie = (LinearLayout) child.findViewById(R.id.lytSerie);

                    txtRep.setText(serie.getRep().toString());
                    txtCharge.setText(serie.getCharge().toString());
                    settingSerie(lytSerie, serie.getRep().toString(), serie.getCharge().toString(), serie.getId(), idSeance);
                    linearLayoutSeries.addView(child);
                }
            }

        }

        private void addBtnAddSerie(final int idSeance){
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
                    final Button btnRemove = (Button) mView.findViewById(R.id.btnRemove);
                    final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);

                    btnRemove.setVisibility(View.GONE);
                    txtDialogTitre.setText("Ajouter une série");

                    Serie lastSerie = controle.getLastSerieOfSeance(idSeance);
                    if(lastSerie != null){
                        editRep.setText(lastSerie.getRep().toString());
                        editCharge.setText(lastSerie.getCharge().toString());
                    }

                    btnValid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            if(editRep.getText().toString().isEmpty() || editCharge.getText().toString().isEmpty()){
                                Toast.makeText(contexte, "Veuillez remplir le nombre de répétiton et la charge utilisée.", Toast.LENGTH_SHORT).show();
                            }else {
                                controle.addSerie(Integer.parseInt(editRep.getText().toString()), Integer.parseInt(editCharge.getText().toString()), 0, idSeance);
                                refreshLytSeries(idSeance);
                                dialog.hide();
                            }
                        }
                    });
                    dialog.show();
                }
            });
            linearLayoutBtnAddSerie.addView(imgBtnAddSerie);
        }

        private void refreshLytSeries(int idSeance){
            if(linearLayoutSeries.getChildCount()>0){
                linearLayoutSeries.removeAllViews();;
            }
            if(linearLayoutBtnAddSerie.getChildCount()>0){
                linearLayoutBtnAddSerie.removeAllViews();
            }
            addChildSerie(idSeance);
            addBtnAddSerie(idSeance);
        }

        private void updateField(final int idSeance, final EditText editNumIntensite, final int initIntensite, final EditText editCommentaire, final String initComent, final int position){
            editNumIntensite.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus){
                    if(!hasFocus){
                        //check if the input is changed
                        if(Integer.parseInt(editNumIntensite.getText().toString()) != initIntensite){
                            controle.updateSeance(idSeance, Integer.parseInt(editNumIntensite.getText().toString()), editCommentaire.getText().toString());
                            Seance seance = seances.get(position);
                            seance.setCommentaire(editCommentaire.getText().toString());
                            seance.setIntensite(Integer.parseInt(editNumIntensite.getText().toString()));
                            notifyDataSetChanged();//a modifier ?
                        }
                    }
            }
            });
            editCommentaire.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus){
                    if(!hasFocus){
                        //check if the input is changed
                        if(!editCommentaire.getText().equals(initComent)){
                            controle.updateSeance(idSeance, Integer.parseInt(editNumIntensite.getText().toString()), editCommentaire.getText().toString());
                            Seance seance = seances.get(position);
                            seance.setCommentaire(editCommentaire.getText().toString());
                            seance.setIntensite(Integer.parseInt(editNumIntensite.getText().toString()));
                            notifyDataSetChanged();
                        }
                    }
                }
            });

        }

        private void settingSerie(LinearLayout lytSerie, final String rep, final String charge, final int idSerie, final int idSeance){
            lytSerie.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(final View v) {
                    android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                    View mView = inflater.inflate(R.layout.dialog_add_serie, null);

                    mBuilder.setView(mView);
                    final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                    final EditText editRep = (EditText) mView.findViewById(R.id.editRep);
                    final EditText editCharge = (EditText) mView.findViewById(R.id.editCharge);
                    final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                    final Button btnRemove = (Button) mView.findViewById(R.id.btnRemove);
                    final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);

                    txtDialogTitre.setText("Modifier une série");
                    editRep.setText(rep);
                    editCharge.setText(charge);

                    btnValid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            if(editRep.getText().toString().isEmpty() || editCharge.getText().toString().isEmpty()){
                                Toast.makeText(contexte, "Veuillez remplir le nombre de répétiton et la charge utilisée.", Toast.LENGTH_SHORT).show();
                            }else {
                                controle.updateSerie(Integer.parseInt(editRep.getText().toString()), Integer.parseInt(editCharge.getText().toString()), idSerie);
                                refreshLytSeries(idSeance);
                                dialog.hide();
                            }
                        }
                    });
                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            controle.delSerie(idSerie);
                            refreshLytSeries(idSeance);
                            dialog.hide();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Seances_List_Adapter_Recycler(Context contexte, Controle controle, int idExercice) {
        this.contexte = contexte;
        this.controle = controle;
        this.inflater = LayoutInflater.from(contexte);
        this.seances = controle.getSeances(idExercice);
    }
}
