package com.example.tccfinal10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class BackGroundGetPower extends AppCompatActivity {


    private String disNome;
    private Dispositivo dispositivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestQueue queue;
        String disIP;
        String status = "";



        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        ArrayList<Dispositivo> alDispositivos = ListaDispositivos.get(getApplicationContext()).getAlDispositivos();

        int disID = extras.getInt("IDdisp");
        dispositivo = alDispositivos.get(disID);

        disNome = dispositivo.getdNome();
        disIP = dispositivo.getdIP();

        int operType = extras.getInt("Oper");

        String disURL = "http://" + disIP;



        ListaDispositivos.salvar();
        finish();

        super.onCreate(savedInstanceState);
    }







}
