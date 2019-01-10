package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.romain.carnetdentrainement.Controleur.Controle;
import com.example.romain.carnetdentrainement.R;
import com.example.romain.carnetdentrainement.modele.Entrainement;
import com.example.romain.carnetdentrainement.modele.Exercice;
import com.example.romain.carnetdentrainement.modele.Programme;

import java.util.ArrayList;

public class Accueil_List_Adapter_Recycler  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Programme> lesProgrammes;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;
    private int size;

    private static final int FOOTER_VIEW = 1;

    // Define a view holder for Footer view

    public class FooterViewHolder extends Accueil_List_Adapter_Recycler.ViewHolder {
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
    public class NormalViewHolder extends Accueil_List_Adapter_Recycler.ViewHolder {
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

            Accueil_List_Adapter_Recycler.FooterViewHolder vh = new Accueil_List_Adapter_Recycler.FooterViewHolder(v);

            return vh;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.programme_container, parent, false);

        Accueil_List_Adapter_Recycler.NormalViewHolder vh = new Accueil_List_Adapter_Recycler.NormalViewHolder(v);

        return vh;
    }

    // Now bind the viewholders in onBindViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            if (holder instanceof Accueil_List_Adapter_Recycler.NormalViewHolder) {
                Accueil_List_Adapter_Recycler.NormalViewHolder vh = (Accueil_List_Adapter_Recycler.NormalViewHolder) holder;

                vh.bindView(position);
            } else if (holder instanceof Accueil_List_Adapter_Recycler.FooterViewHolder) {
                Accueil_List_Adapter_Recycler.FooterViewHolder vh = (Accueil_List_Adapter_Recycler.FooterViewHolder) holder;
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
        if (lesProgrammes== null) {
            return 0;
        }

        if (lesProgrammes.size() == 0) {
            //Return 1 here to show nothing
            return 0;
        }

        // Add extra view to show the footer view
        return lesProgrammes.size();
    }

    // Now define getItemViewType of your own.

    @Override
    public int getItemViewType(int position) {
       /* if (position == lesProgrammes.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }*/

        return super.getItemViewType(position);
    }

    // So you're done with adding a footer and its action on onClick.
    // Now set the default ViewHolder for NormalViewHolder

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProgramme;
        ImageButton imgBtnSettingProgramme;
        ImageButton imgBtnSettingEntrainement;
        LinearLayout lytEntrainements;
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProgramme = (TextView) itemView.findViewById(R.id.txtProgramme);
            imgBtnSettingProgramme = (ImageButton) itemView.findViewById(R.id.imgBtnSettingProgramme);
            lytEntrainements = (LinearLayout) itemView.findViewById(R.id.lytEntrainements);
        }

        public void bindView(int position) {
            // bindView() method to implement actions
            txtProgramme.setText(lesProgrammes.get(position).getNom());
            itemView.setTag(lesProgrammes.get(position));
            addChildEntrainements(lesProgrammes.get(position));
            //afficherExerciceCardView(cardViewExercice);
        }

        /*public void bindFooterView(int position) {
            addBtnAddExercice(btnAddExercice, position);
        }*/



        private void addChildEntrainements(Programme programme) {

            //ArrayList<Entrainement> lesEntrainements = controle.getLesEntrainements();
            ArrayList<Entrainement> lesEntrainements = programme.getLesEntrainements();
            //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
            if (lesEntrainements != null) {
                Entrainement entrainement;
                for (int k = 0; k < lesEntrainements.size(); k++) {
                    View child = inflater.inflate(R.layout.entrainement_container, null); //Regarder si route != null
                    imgBtnSettingEntrainement = (ImageButton) child.findViewById(R.id.imgBtnSettingEntrainement);
                    TextView txtEntrainement = (TextView) child.findViewById(R.id.txtEntrainement);

                    entrainement = lesEntrainements.get(k);
                    addChildsExercicesHorizontalRecycler(child, entrainement); // Add horizontal recyclerView for exercices in AccueilActivity

                    txtEntrainement.setText(entrainement.getNom());
                    lytEntrainements.addView(child);

                    //initBtnSettingsEntrainement(holder, lesEntrainements, k);
                }
            }
        }

        private void addChildsExercicesHorizontalRecycler(View root, Entrainement entrainement){
            mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerExercices);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager =new LinearLayoutManager(contexte, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new Exercices_List_Adapter_Recycler(contexte, controle, entrainement);
            mRecyclerView.setAdapter(mAdapter);

        }

    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Accueil_List_Adapter_Recycler(Context contexte, Controle controle) {
        this.contexte = contexte;
        this.controle = controle;
        this.inflater = LayoutInflater.from(contexte);
        this.lesProgrammes = controle.getLesProgrammes();

    }
}
