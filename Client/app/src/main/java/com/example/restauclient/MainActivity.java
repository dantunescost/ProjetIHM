package com.example.restauclient;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void switchToFrench(View v) {
        Locale locale = new Locale("fr_FR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
        this.setContentView(R.layout.activity_main);
    }

    public void switchToEnglish(View v) {
        Locale locale = new Locale("us_US");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
        this.setContentView(R.layout.activity_main);
    }

    public void switchToItalian(View v) {

    }

    public void switchToGerman(View v) {

    }

    public void switchToSpanish(View v) {

    }

    public void goToCarte(View v) {
        Intent intent = new Intent(this, CarteActivity.class);
        startActivity(intent);
    }


}
