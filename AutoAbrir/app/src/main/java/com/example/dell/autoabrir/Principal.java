package com.example.dell.autoabrir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;

public class Principal extends AppCompatActivity {
    ImageButton abrir;
    RadioButton MLibre;
    RadioButton MSeguro;
    Button salir;
    Boolean puerta;
    TextView tv;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv=(TextView) findViewById(R.id.textView);
        setSupportActionBar(toolbar);


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
    public void enviar_recibe(){
        String URL;
        RequestQueue res = Volley.newRequestQueue(this);
        if (puerta==true){
            URL=  "http://192.168.0.105/?puerta=1";
        }else {
            URL =  "http://192.168.0.105/?puerta=0";
        }
        StringRequest respuesta = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(puerta==true) {
                    //estado.setText("Abierto");
                    Toast.makeText(Principal.this,"Abierto", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Principal.this,"Cerrado", Toast.LENGTH_SHORT).show();
                    //estado.setText("Cerrado");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Principal.this, "Error de comunicacion", Toast.LENGTH_SHORT).show();
                //estado.setText("Error de comunicacion");
            }
        });
        res.add(respuesta);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item1= menu.add(0,1,1,"Cambiar Password");
        MenuItem item2= menu.add(0,2,2,"Opción 2");
        MenuItem item3= menu.add(0,3,3,"Opción 3");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch(id){
            case 1:
                Intent loginpass = new Intent(Principal.this,PassActivity.class);
                startActivity(loginpass);
                break;
            default:
                tv.append("\n Ha pulsado la opción "+id);
        }
        return super.onOptionsItemSelected(item);
    }
}
