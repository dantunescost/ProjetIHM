package com.example.restauclient;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MenuCActivity extends AbstractCustomActivity {

    RadioButton entree1, entree2, plat1, plat2, dessert1, dessert2, dessert3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_c);
        this.initialize();
        setButton();
    }

    private void setButton(){
        entree1 = (RadioButton) findViewById(R.id.button_salade3);
        entree2 = (RadioButton) findViewById(R.id.button_charcuterie3);
        plat1 = (RadioButton) findViewById(R.id.button_viande3);
        plat2 = (RadioButton) findViewById(R.id.button_poisson3);
        dessert1 = (RadioButton) findViewById(R.id.button_glace1);
        dessert2 = (RadioButton) findViewById(R.id.button_glace2);
        dessert3 = (RadioButton) findViewById(R.id.button_glace3);
        entree1.setChecked(true);
        plat1.setChecked(true);
        dessert1.setChecked(true);
        entree1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entree1.setChecked(true);
                entree2.setChecked(false);
            }
        });

        entree2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entree1.setChecked(false);
                entree2.setChecked(true);
            }
        });
        plat1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                plat1.setChecked(true);
                plat2.setChecked(false);
            }
        });

        plat2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                plat1.setChecked(false);
                plat2.setChecked(true);
            }
        });
        dessert1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dessert1.setChecked(true);
                dessert2.setChecked(false);
                dessert3.setChecked(false);
            }
        });

        dessert2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dessert1.setChecked(false);
                dessert2.setChecked(true);
                dessert3.setChecked(false);
            }
        });
        dessert3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dessert1.setChecked(false);
                dessert2.setChecked(false);
                dessert3.setChecked(true);
            }
        });
    }

    public void validationMenu(View view) {

        boolean allChecked=true;
        String entree=null;
        String plat=null;
        String dessert=null;
        //String entree
        if(entree1.isChecked())
            entree=getString(R.string.entree_salade3);
        else if(entree2.isChecked())
            entree=getString(R.string.entree_charcuterie3);
        else
            allChecked=false;
        //String plat
        if(plat1.isChecked())
            plat=getString(R.string.plat_viande3);
        else if(plat2.isChecked())
            plat=getString(R.string.plat_poisson3);
        else
            allChecked=false;
        //String dessert
        if(dessert1.isChecked())
            dessert=getString(R.string.dessert_glace1);
        else if(dessert2.isChecked())
            dessert=getString(R.string.dessert_glace2);
        else if(dessert3.isChecked())
            dessert=getString(R.string.dessert_glace3);
        else
            allChecked=false;

        if(allChecked){
            String menu=getString(R.string.menuCshort);
            getCommande().addMenu(menu+"\n"+entree+"\n"+plat+"\n"+dessert);
            Intent intent = new Intent(this, CarteActivity.class);
            getCommande().ajoutMenuC(menu+"\n"+entree+"\n"+plat+"\n"+dessert);
            intent.putExtra("commande", this.commande);
            startActivity(intent);
        }
    }

}
