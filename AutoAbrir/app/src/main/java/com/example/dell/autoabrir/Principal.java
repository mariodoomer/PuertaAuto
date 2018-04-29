package com.example.dell.autoabrir;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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
    RadioButton MLibre,MSeguro;
    Button salir, iniciar;
    EditText contraseña;
    Boolean puerta;
    int id = 0;
    String clave, mode, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        abrir = (ImageButton)findViewById(R.id.btnabrir);
        MLibre = (RadioButton)findViewById(R.id.rblibre);
        MSeguro = (RadioButton)findViewById(R.id.rbseguro);
        salir = (Button)findViewById(R.id.btnsalir);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        clave = prefs.getString("clave","no hay dato");
        mode = prefs.getString("modo","no hay dato");
        contra = prefs.getString("contra","no hay dato");
        MSeguro.setChecked(true);

        if (mode.equals("seguro")){
            MSeguro.setChecked(true);
            showDialog(id);
            //Intent login = new Intent(Principal.this,LoginLocal.class);
            //startActivity(login);
        }else{
            MLibre.setChecked(true);
        }

        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Principal.this, clave, Toast.LENGTH_SHORT).show();
               if (puerta==false){
                    puerta=true;
                }else {
                    puerta=false;
                }
                enviar_recibe();
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
    protected Dialog onCreateDialog(int id) {
        Dialog dialogo = new Dialog(this);

        Window w = dialogo.getWindow();

        int flag = WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER;
        w.setFlags(flag, flag);

        dialogo.setTitle("LOGIN");
        dialogo.setContentView(R.layout.activity_login_local);
        iniciar = (Button) dialogo.findViewById(R.id.btniniciar);
        contraseña = (EditText) dialogo.findViewById(R.id.etcontralogin);
        iniciar.setOnClickListener(new AcepListener());
        return dialogo;
    }
    class AcepListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String password = contraseña.getText().toString().trim();
            if (password.matches(contra)) {
                dismissDialog(id);
                Toast.makeText(Principal.this, "Bienvenido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Principal.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        }
    }
}