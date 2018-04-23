package com.example.dell.autoabrir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ClaveProducto extends AppCompatActivity {
    EditText clave;
    String contraseña;
    Button finalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave_producto);
        clave = (EditText)findViewById(R.id.etclave);
        finalizar = (Button)findViewById(R.id.btnfinalizar);
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        Bundle datos = this.getIntent().getExtras();
        contraseña = datos.getString("contraseña");


        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("clave",clave.getText().toString());
                editor.putString("contra",contraseña);
                editor.putString("modo","libre");
                editor.commit();



                Intent finalizar = new Intent(ClaveProducto.this,Principal.class);
                startActivity(finalizar);


            }
        });

    }

}
