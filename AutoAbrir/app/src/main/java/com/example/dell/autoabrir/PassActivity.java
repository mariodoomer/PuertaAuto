package com.example.dell.autoabrir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PassActivity extends AppCompatActivity {
    EditText contraA,contra1,contra2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        contraA = (EditText)findViewById(R.id.etcontraseñaA);
        contra1 = (EditText)findViewById(R.id.etcontraseña1);
        contra2 = (EditText)findViewById(R.id.etcontraseña2);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

    }

    public void crear (View view){
        String passA=contraA.getText().toString();
        String pass1=contra1.getText().toString();
        String pass2=contra2.getText().toString();

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        final String contra = prefs.getString("contra","no hay dato");
        if (passA.equals(contra)) {

            if (!pass1.equals(pass2)) {
                Toast.makeText(PassActivity.this, "Las contraseñas no coinciden("+ pass1 + "," + pass2 + ")", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("contra", contra1.getText().toString());
                editor.commit();

                Intent clave = new Intent(PassActivity.this, LoginLocal.class);
                startActivity(clave);
            }
        }else{
            Toast.makeText(PassActivity.this, "Contraseña actual incorrecta(" + passA + ")", Toast.LENGTH_SHORT).show();
        }

    }
}

