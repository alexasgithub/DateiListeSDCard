package com.example.standard.dateilistesdcard;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(this);
    }



    private List<String> lese_SD_DateiListe() {
        List<String> dateiListe = new ArrayList<String>();

        String status = "neu";

        // TODO hier muss was Sinnvolles passieren

        File sdKarte = Environment.getExternalStorageDirectory();

        if(sdKarte.exists() && sdKarte.canRead()) {
            return sammleDateiNamen(0, sdKarte);
        }

        return dateiListe;
    }

    private List<String> sammleDateiNamen(int einrueckTiefe,
                                          File verzeichnis) {
        List<String> dateiListe = new ArrayList<>();
        File[] dateien = verzeichnis.listFiles();

        if(dateien == null) {
            return dateiListe;
        }

        for(File f : dateien) {
            try {
                String name = f.getName();

                if(f.isDirectory()) {
                    name += " <DIR>";
                    dateiListe.add(name);
                    dateiListe.addAll(sammleDateiNamen(einrueckTiefe+1, f));
                }
                else {
                    dateiListe.add(name);
                }
            }
            catch(Exception ex) {
                Log.d("MeineApp", ex.getMessage());
            }
        }
        return dateiListe;
    }

    @Override
    public void onClick(View arg0) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Datei-Liste");

        List<String> dateiNamen = lese_SD_DateiListe();
        String[] eintraege      = dateiNamen.toArray(new String[dateiNamen.size()]);

        builder.setItems(eintraege, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
