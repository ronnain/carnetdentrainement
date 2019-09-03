package com.example.romain.carnetdentrainement.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import com.example.romain.carnetdentrainement.vue.AccueilActivity;
import com.example.romain.carnetdentrainement.vue.SeancesActivity;

import java.util.ArrayList;

public class Exercices_List_Adapter_Recycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Exercice> lesExercices;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;
    private Entrainement entrainement;
    private int size;
    private int idEntrainement;
    public  final static  String EXTRA_MESSAGE = "com.ltm.ltmactionbar.MESSAGE";

    private static final int FOOTER_VIEW = 1;

    // Define a view holder for Footer view

    public class FooterViewHolder extends ViewHolder {
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
    public class NormalViewHolder extends ViewHolder {
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

            FooterViewHolder vh = new FooterViewHolder(v);

            return vh;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_exercice, parent, false);

        NormalViewHolder vh = new NormalViewHolder(v);

        return vh;
    }

    // Now bind the viewholders in onBindViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            if (holder instanceof NormalViewHolder) {
                NormalViewHolder vh = (NormalViewHolder) holder;

                vh.bindView(position);
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
                vh.bindFooterView(position);
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
        if (lesExercices== null) {
            return 0;
        }

        if (lesExercices.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return lesExercices.size() + 1;
    }

    // Now define getItemViewType of your own.

    @Override
    public int getItemViewType(int position) {
        if (position == lesExercices.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }

    // So you're done with adding a footer and its action on onClick.
    // Now set the default ViewHolder for NormalViewHolder

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtExerciceName;
        public CardView cardViewExercice;
        public ImageView imgExercice;
        public LinearLayout lytCardExercice;
        public ImageButton btnAddExercice;

        public ViewHolder(View itemView) {
            super(itemView);
            txtExerciceName = itemView.findViewById(R.id.txtExercice);
            cardViewExercice = itemView.findViewById(R.id.cardViewExercice);
            imgExercice = itemView.findViewById(R.id.imgExercice);
            lytCardExercice = itemView.findViewById(R.id.lytCardExercice);
            btnAddExercice = itemView.findViewById(R.id.btnAddEntite);

        }

        public void bindView(int position) {
            // bindView() method to implement actions
            txtExerciceName.setText(lesExercices.get(position).getNom());
            itemView.setTag(lesExercices.get(position));
            afficherExerciceCardView(cardViewExercice, lesExercices.get(position).getId());
        }

        public void bindFooterView(int position) {
            addBtnAddExercice(btnAddExercice, position);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Exercices_List_Adapter_Recycler(Context contexte, Controle controle, Entrainement entrainement) {
        this.contexte = contexte;
        this.controle = controle;
        this.inflater = LayoutInflater.from(contexte);
        this.entrainement = entrainement;
        this.idEntrainement = entrainement.getId();
        this.lesExercices = controle.getExercices(this.idEntrainement);
        this.size = lesExercices.size()+1;

    }

    private void afficherExerciceCardView(final CardView cardExercice, final Integer position) {
        cardExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent = new Intent(contexte, SeancesActivity.class);
                //intent.putExtra("entrainement",entrainement);
                intent.putExtra(AccueilActivity.EXTRA_MESSAGE_EXERCICE_ID, position.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                contexte.startActivity(intent);

            }
        });
    }

    private void addBtnAddExercice(ImageButton imgBtnAddExercice, final int position) {

        imgBtnAddExercice.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(contexte);
                View mView = inflater.inflate(R.layout.dialog_add_entite, null);

                mBuilder.setView(mView);
                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                final Button btnValid = (Button) mView.findViewById(R.id.btnValid);
                final TextView txtDialogTitre = (TextView) mView.findViewById(R.id.txtDialogTitre);
                final EditText editNomEntite = (EditText) mView.findViewById(R.id.editNomEntite);

                txtDialogTitre.setText("Ajouter un Exercice");

                btnValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        String nameNewEntite = editNomEntite.getText().toString().trim();
                        if (!nameNewEntite.isEmpty()) {
                            lesExercices.add(controle.addExercice(nameNewEntite, idEntrainement, entrainement));
                            notifyItemInserted(position);
                            notifyDataSetChanged();
                            Toast.makeText(contexte, "Exercice : " + nameNewEntite + " a été ajouté.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(contexte, "Aucun nom n'a été entré.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.hide();


                    }
                });
                dialog.show();
            }
        });
    }
}
