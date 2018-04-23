package com.example.dell.autoabrir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText contra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contra = (EditText)findViewById(R.id.etcontraseña);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        final String contra = prefs.getString("contra","no hay dato");
        if (contra != "no hay dato"){
            Intent validacion1 = new Intent(MainActivity.this,Principal.class);
            startActivity(validacion1);
        }


    }

    public void crear (View view){
        Intent clave = new Intent(MainActivity.this,ClaveProducto.class);
        clave.putExtra("contraseña",contra.getText().toString());
        startActivity(clave);

    }
}
