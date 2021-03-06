package com.example.restauclient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Tigig on 10/04/2016.
 */
public class AbstractCustom2Activity extends Activity {
    protected Commande commande;

    public void initialize() {

        Intent intent = getIntent();
        if (intent == null)
            this.commande = new Commande();
        else
            this.commande = (Commande) intent.getSerializableExtra("commande");

        this.drawOrder();
    }

    public Commande getCommande() {
        return this.commande;
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("commande", this.commande);
        startActivity(intent);
    }

    public void goToMenus(View view) {
        if (!this.getClass().equals(MenusActivity.class)) {
            Intent intent = new Intent(this, MenusActivity.class);
            intent.putExtra("commande", this.commande);
            startActivity(intent);
        }
    }
    public void goToCarte(View view) {
        if (!this.getClass().equals(CarteActivity.class)) {
            Intent intent = new Intent(this, CarteActivity.class);
            intent.putExtra("commande", this.commande);
            startActivity(intent);
        }
    }

    public void goToBilan(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.sure));
        alertDialogBuilder.setPositiveButton(getString(R.string.yes),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                Intent intent = new Intent(getApplicationContext(), RecapitulatifActivity.class);
                intent.putExtra("commande", getCommande());
                startActivity(intent);
            }

        });
        alertDialogBuilder.setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void popServeur(View view) {
        //Intent intent = new Intent(this, PopUpPap.class);
        //startActivity(intent);

        FrameLayout popUpPap = (FrameLayout) findViewById(R.id.popUpPap);
        popUpPap.setVisibility(View.VISIBLE);
    }

    public void unPopServeur(View view) {
        FrameLayout popUpPap = (FrameLayout) findViewById(R.id.popUpPap);
        popUpPap.setVisibility(View.INVISIBLE);
    }

    public void tickEau(View view) {
        this.commande.setCallWater(true);
        setTickEau();
    }

    private void setTickEau() {
        if (this.commande.isCallWater()) {
            ImageView tick = new ImageView(this);
            tick.setBackground(getResources().getDrawable(R.mipmap.ic_tick));
            FrameLayout frame = (FrameLayout) findViewById(R.id.pin_eau);
            frame.addView(tick);
        }
    }

    public void tickPain(View view) {
        this.commande.setCallBread(true);
        setTickPain();
    }

    private void setTickPain() {
        if (this.commande.isCallBread()) {
            ImageView tick = new ImageView(this);
            tick.setBackground(getResources().getDrawable(R.mipmap.ic_tick));
            FrameLayout frame = (FrameLayout) findViewById(R.id.pin_pain);
            frame.addView(tick);
        }
    }

    public void tickServeur(View view) {
        this.commande.setCallWaiter(true);
        setTickServeur();
    }

    private void setTickServeur() {
        if (this.commande.isCallWaiter()) {
            ImageView tick = new ImageView(this);
            tick.setBackground(getResources().getDrawable(R.mipmap.ic_tick));
            FrameLayout frame = (FrameLayout) findViewById(R.id.pin_serveur);
            frame.addView(tick);
        }
    }
    /**
     * Pour éviter que l'utilisateur n'efface tout ou partie de sa commande en appuyant sur le "back button"
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode != KeyEvent.KEYCODE_BACK && super.onKeyDown(keyCode, event);

    }

    public void refreshRecap() {
        TableLayout tl = (TableLayout) findViewById(R.id.recapitulatif);
        tl.removeAllViews();
        this.drawOrder();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void drawOrder() {
        /* Récupération du table layout */
        TableLayout tl = (TableLayout) findViewById(R.id.recapitulatif);
        TableRow tr;
        TextView label;

        TableRow.LayoutParams rowlayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        rowlayoutParams.setMargins(5, 5, 5, 5);
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

        TableRow.LayoutParams imageLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        int marginTopBottom = getResources().getDrawable(R.mipmap.minus).getMinimumHeight() / 5;
        imageLayoutParams.setMargins(5,-marginTopBottom,5,-marginTopBottom);

        /*
        Ajout des entrées
         */
        if (!this.commande.getListEntrees().isEmpty()) {
            /* Création de la nouvelle ligne à rajouter */
            tr = new TableRow(this);
            tr.setLayoutParams(rowlayoutParams);
            label = new TextView(this);
            label.setText(R.string.entree);
            label.setLayoutParams(rowlayoutParams);
            label.setTextSize(50);
            tr.addView(label);
            tl.addView(tr, tableLayoutParams);

            for (final String key : this.getCommande().getListEntrees().keySet()) {
                tr = new TableRow(this);
                tr.setLayoutParams(rowlayoutParams);

                label = new TextView(this);
                label.setText(key);
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                label = new TextView(this);
                label.setText(" (x" + Integer.toString(this.getCommande().getListEntrees().get(key)) + ")");
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                tl.addView(tr, tableLayoutParams);
            }
        }

        /*
        Ajout des Plats
         */
        if (!this.commande.getListPlats().isEmpty()) {
            /* Création de la nouvelle ligne à rajouter */
            tr = new TableRow(this);
            tr.setLayoutParams(rowlayoutParams);
            label = new TextView(this);
            label.setText(R.string.plats);
            label.setLayoutParams(rowlayoutParams);
            label.setTextSize(50);
            tr.addView(label);
            tl.addView(tr, tableLayoutParams);

            for (final String key : this.getCommande().getListPlats().keySet()) {
                tr = new TableRow(this);
                tr.setLayoutParams(rowlayoutParams);

                label = new TextView(this);
                label.setText(key);
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                label = new TextView(this);
                label.setText(" (x" + Integer.toString(this.getCommande().getListPlats().get(key)) + ")");
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                tl.addView(tr, tableLayoutParams);
            }
        }

        /*
        Ajout des Desserts
         */
        if (!this.commande.getListDesserts().isEmpty()) {
            /* Création de la nouvelle ligne à rajouter */
            tr = new TableRow(this);
            tr.setLayoutParams(rowlayoutParams);
            label = new TextView(this);
            label.setText(R.string.desserts);
            label.setLayoutParams(rowlayoutParams);
            label.setTextSize(50);
            tr.addView(label);
            tl.addView(tr, tableLayoutParams);

            for (final String key : this.getCommande().getListDesserts().keySet()) {
                tr = new TableRow(this);
                tr.setLayoutParams(rowlayoutParams);

                label = new TextView(this);
                label.setText(key);
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                label = new TextView(this);
                label.setText(" (x" + Integer.toString(this.getCommande().getListDesserts().get(key)) + ")");
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                tl.addView(tr, tableLayoutParams);
            }
        }

        /*
        Ajout des Boissons
         */
        if (!this.commande.getListBoissons().isEmpty()) {
            /* Création de la nouvelle ligne à rajouter */
            tr = new TableRow(this);
            tr.setLayoutParams(rowlayoutParams);
            label = new TextView(this);
            label.setText(R.string.boissons);
            label.setTextSize(50);
            label.setLayoutParams(rowlayoutParams);
            tr.addView(label);
            tl.addView(tr, tableLayoutParams);

            for (final String key : this.getCommande().getListBoissons().keySet()) {
                tr = new TableRow(this);
                tr.setLayoutParams(rowlayoutParams);

                label = new TextView(this);
                label.setText(key);
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                label = new TextView(this);
                label.setText(" (x" + Integer.toString(this.getCommande().getListBoissons().get(key)) + ")");
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);
                tl.addView(tr, tableLayoutParams);
            }
        }

        /*
        Ajout des Menus
         */
        if (!this.commande.getListMenus().isEmpty()) {
            /* Création de la nouvelle ligne à rajouter */
            tr = new TableRow(this);
            tr.setLayoutParams(rowlayoutParams);
            label = new TextView(this);
            label.setText(R.string.text_menus);
            label.setTextSize(50);
            label.setLayoutParams(rowlayoutParams);
            tr.addView(label);
            tl.addView(tr, tableLayoutParams);

            for (final String key : this.getCommande().getListMenus().keySet()) {
                tr = new TableRow(this);
                tr.setLayoutParams(rowlayoutParams);

                label = new TextView(this);
                label.setText(key);
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                label = new TextView(this);
                label.setText(" (x" + Integer.toString(this.getCommande().getListMenus().get(key)) + ")");
                label.setTextSize(25);
                label.setLayoutParams(rowlayoutParams);
                tr.addView(label);

                tl.addView(tr, tableLayoutParams);
            }
        }
    }
}
