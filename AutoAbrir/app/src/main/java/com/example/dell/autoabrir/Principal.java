package com.example.dell.autoabrir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.net.URL;

public class Principal extends AppCompatActivity {
ImageButton abrir;
    RadioButton MLibre;
    RadioButton MSeguro;
    Button salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        abrir = (ImageButton)findViewById(R.id.btnabrir);
        MLibre = (RadioButton)findViewById(R.id.rblibre);
        MSeguro = (RadioButton)findViewById(R.id.rbseguro);
        salir = (Button)findViewById(R.id.btnsalir);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        final String clave = prefs.getString("clave","no hay dato");
        final String mode = prefs.getString("modo","no hay dato");
        //final String contra = prefs.getString("contra","no hay dato");

        if (mode.equals("seguro")){
            Intent login = new Intent(Principal.this,LoginLocal.class);
            startActivity(login);
        }




        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Principal.this, clave, Toast.LENGTH_SHORT).show();

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modo;
                if (MLibre.isChecked()){modo="libre";}
                else if (MSeguro.isChecked()){modo="seguro";}
                else {modo="libre";}

                SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("modo",modo);
                editor.commit();

                finishAffinity();

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
