/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: BackgroundOFF

Classe utilizada para enviar comandos em segundo plano para o dispositivo.

*/




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

public class BackgroundOFF extends AppCompatActivity {

    private String disNome;
    private NotificationCompat.Builder builder;
    private Dispositivo dispositivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestQueue queue;
        String disIP;
        String status = "";

    // Monta a notificação
        builder = new NotificationCompat.Builder(this, "NOT_POWER")
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentTitle(disNome)
                .setContentText(status)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


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

        StringRequest stringOFFRequest;
        if(operType == 1) {

            stringOFFRequest = new StringRequest(Request.Method.GET, disURL + "/status=OFF",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            builder.setContentText(disNome + "desligado!");
                            dispositivo.setdStatus(false);
                            notificationManager.notify(0, builder.build());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    builder.setContentText("Falha de Conexão!");
                    notificationManager.notify(0, builder.build());
                }
            });

            queue = Volley.newRequestQueue(this);

            notificationManager.notify(0, builder.build());
            queue.add(stringOFFRequest);
        }

        if(operType == 2) {


            int newSpeed = dispositivo.getSpeed() - 450;
            dispositivo.setSpeed(newSpeed);


            if(newSpeed > 150) {


                // Display the first 500 characters of the response string.
                StringRequest speedRequest = new StringRequest(Request.Method.GET, disURL + "/speed=" + newSpeed,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue = Volley.newRequestQueue(this);

                queue.add(speedRequest);

            }else{

                stringOFFRequest = new StringRequest(Request.Method.GET, disURL + "/status=OFF",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                builder.setContentText("Desligado!");
                                dispositivo.setdStatus(false);
                                notificationManager.notify(0, builder.build());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        builder.setContentText("Falha de Conexão!");
                        notificationManager.notify(0, builder.build());
                    }
                });

                queue = Volley.newRequestQueue(this);
                notificationManager.notify(0, builder.build());
                queue.add(stringOFFRequest);

                AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                intent.setAction("fade");
                intent.putExtra("NomeDisp", dispositivo.getdNome());
                intent.putExtra("IPdisp", dispositivo.getdIP());
                intent.putExtra("IDdisp", disID);
                intent.putExtra("Oper",2);

                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), disID, intent, 0);
                alarmManager.cancel(alarmIntent);






            }


        }

        ListaDispositivos.salvar();
        finish();

        super.onCreate(savedInstanceState);
    }


}

