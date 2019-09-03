package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        LinearLayout lytAddEntrainement;
        LinearLayout lytEntrainements;
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProgramme = (TextView) itemView.findViewById(R.id.txtProgramme);
            imgBtnSettingProgramme = (ImageButton) itemView.findViewById(R.id.imgBtnSettingProgramme);
            lytEntrainements = (LinearLayout) itemView.findViewById(R.id.lytEntrainements);
            lytAddEntrainement= (LinearLayout) itemView.findViewById(R.id.lytAddEntrainement);
        }

        public void bindView(int position) {
            // bindView() method to implement actions
            Programme currentProgramme = lesProgrammes.get(position);
            txtProgramme.setText(currentProgramme.getNom());
            itemView.setTag(currentProgramme);
            if(lytEntrainements.getChildCount()>0){
                lytEntrainements.removeAllViews();;
            }
            if(lytAddEntrainement.getChildCount()>0){
                lytAddEntrainement.removeAllViews();;
            }
            addChildEntrainements(currentProgramme.getId(), position);
            addBtnAddEntite(position, "Entrainement", currentProgramme.getId());
            initBtnSettingProgramme(currentProgramme.getNom(), position, currentProgramme.getId());
        }

        private void addChildEntrainements(int idProgramme, int position) {

            //ArrayList<Entrainement> lesEntrainements = controle.getLesEntrainements();
            ArrayList<Entrainement> lesEntrainements = controle.getEntrainements(idProgramme);
            //Collections.sort(lesProgrammes, Collections.<Programme>reverseOrder());
            if (lesEntrainements != null) {
                Entrainement entrainement;
                for (int k = 0; k < lesEntrainements.size(); k++) {
                    View child = inflater.inflate(R.layout.entrainement_container, null); //Regarder si route != null
                    ImageButton imgBtnSettingEntrainement = (ImageButton) child.findViewById(R.id.imgBtnSettingEntrainement);
                    TextView txtEntrainement = (TextView) child.findViewById(R.id.txtEntrainement);

                    entrainement = lesEntrainements.get(k);

                    initBtnSettingEntrainement(imgBtnSettingEntrainement, txtEntrainement, child, entrainement.getNom(), entrainement.getId(), k, position);

                    addChildsExercicesHorizontalRecycler(child, entrainement); // Add horizontal recyclerView for exercices in AccueilActivity

                    txtEntrainement.setText(entrainement.getNom());
                    lytEntrainements.addView(child);
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

        private void addBtnAddEntite(final int position, final String name, final Integer idProgramme) {
            ImageButton imgBtnAddEntite = new ImageButton(contexte);
            imgBtnAddEntite.setImageResource(R.drawable.btn_add_24dp);

            imgBtnAddEntite.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                    View mView = inflater.inflate(R.layout.dialog_add_entite, null);

                    mBuilder.setView(mView);
                    final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                    final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                    final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);
                    final EditText editNomEntite = (EditText) mView.findViewById(R.id.editNomEntite);

                    txtDialogTitre.setText("Ajouter un "+ name);

                    btnValid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            String nameNewEntite = editNomEntite.getText().toString().trim();
                            if (!nameNewEntite.isEmpty()) {
                                controle.createEntrainement(nameNewEntite, idProgramme, position);
                                notifyItemInserted(position);
                                notifyDataSetChanged();
                                Toast.makeText(contexte, name+ ": " + nameNewEntite + " a été ajouté.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                            }
                            dialog.hide();

                        }
                    });
                    dialog.show();
                }
            });
            lytAddEntrainement.addView(imgBtnAddEntite);
        }

        private void initBtnSettingProgramme(final String name, final int position, final int idProgramme){
            imgBtnSettingProgramme.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                    View mView = inflater.inflate(R.layout.dialog_setting, null);
                    mBuilder.setView(mView);
                    final android.support.v7.app.AlertDialog dialog = mBuilder.create();

                    final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                    final Button btnRemove = (Button) mView.findViewById(R.id.btnRemove);
                    final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);
                    final EditText editNomEntite = (EditText) mView.findViewById(R.id.editNomEntite);

                    txtDialogTitre.setText("Mettre à jour le Programme");
                    editNomEntite.setText(name);
                    btnValid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            String nameNewEntite = editNomEntite.getText().toString().trim();
                            if (!nameNewEntite.isEmpty()) {
                                controle.setProgrammeName(position, nameNewEntite);
                                notifyDataSetChanged();
                                Toast.makeText(contexte, name+ " -> " + nameNewEntite + "", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                            }
                            dialog.hide();
                        }
                    });
                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            controle.removeProgramme(idProgramme, position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            Toast.makeText(contexte, "Suppression du programme "+name, Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }
                    });
                    dialog.show();
                }
            });
        }

        private void initBtnSettingEntrainement(
                ImageButton imgBtnSettingEntrainement, final TextView txtEntrainement, final View child,
                final String name, final int idEntrainement, final int positionEntrainement,
                final int positionProgramme){
            imgBtnSettingEntrainement.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                    View mView = inflater.inflate(R.layout.dialog_setting, null);
                    mBuilder.setView(mView);
                    final android.support.v7.app.AlertDialog dialog = mBuilder.create();

                    final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                    final Button btnRemove = (Button) mView.findViewById(R.id.btnRemove);
                    final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);
                    final EditText editNomEntite = (EditText) mView.findViewById(R.id.editNomEntite);

                    txtDialogTitre.setText("Mettre à jour l'entrainement");
                    editNomEntite.setText(name);
                    btnValid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            String nameNewEntite = editNomEntite.getText().toString().trim();
                            if (!nameNewEntite.isEmpty()) {
                                controle.setEntrainementName(nameNewEntite, idEntrainement, positionEntrainement, positionProgramme);
                                txtEntrainement.setText(nameNewEntite);
                                notifyDataSetChanged();
                                Toast.makeText(contexte, name+ " -> " + nameNewEntite + "", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                            }
                            dialog.hide();
                        }
                    });
                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            controle.removeEntrainement(idEntrainement);
                            lytEntrainements.removeView(child);
                            notifyDataSetChanged();
                            Toast.makeText(contexte, "Suppression de l'entrainement "+name, Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }
                    });
                    dialog.show();
                }
            });
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
