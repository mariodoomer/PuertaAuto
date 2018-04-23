package com.example.dell.autoabrir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginLocal extends AppCompatActivity {
    Button iniciar;
    EditText contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_local);
        iniciar = (Button)findViewById(R.id.btniniciar);
        contraseña = (EditText)findViewById(R.id.etcontralogin);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                final String contra = prefs.getString("contra","no hay dato");
                if (contraseña.getText().toString().equals(contra)){

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("modo","libre");
                    editor.commit();

                    Intent irprincipal = new Intent(LoginLocal.this,Principal.class);
                    startActivity(irprincipal);
                }
                else {
                    Toast.makeText(LoginLocal.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
