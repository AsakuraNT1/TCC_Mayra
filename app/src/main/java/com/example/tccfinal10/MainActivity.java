/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: ListaDispositivo

Activity Principal

*/


package com.example.tccfinal10;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class MainActivity extends AppCompatActivity implements DispositivoAdapter.OnDispositivoListener{

    ArrayList<Dispositivo> alDispositivos;
    DispositivoAdapter adapter;
    boolean alphaClicked=false;




    private void createNotificationChannel() {

        // Criação do canal de notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notoffchannel);
            String description = getString(R.string.notoffdesc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NOT_POWER", name, importance);
            channel.setDescription(description);

            // Registro do canal de notificação no sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

       createNotificationChannel();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alDispositivos = ListaDispositivos.get(getApplicationContext()).getAlDispositivos();




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            Intent i = new Intent(getApplicationContext(), AdicionaDispositivo.class);
            i.putExtra("dispositivoId", 0);
            i.putExtra("from", 1); // 1 identifica que é inclusão de novo dispositivo
            startActivity(i);


        });

        RecyclerView rvDispositivos = findViewById(R.id.rvDispositivos);


        adapter = new DispositivoAdapter(alDispositivos, this);
        rvDispositivos.setAdapter(adapter);

        rvDispositivos = findViewById(R.id.rvDispositivos);

        rvDispositivos.setLayoutManager(new LinearLayoutManager(this));



    }

    @SuppressLint("NotifyDataSetChanged")
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {


            case R.id.miAlpha:
                // ordenar dispositivos em ordem alfabética crescente

                if(alphaClicked) {
                    Collections.sort(alDispositivos, new Comparator<Dispositivo>() {
                        @Override
                        public int compare(Dispositivo lhs, Dispositivo rhs) {
                            return lhs.getdNome().compareToIgnoreCase(rhs.getdNome());
                        }
                    });
                    alphaClicked=false;
                }
                else{
                    Collections.sort(alDispositivos, new Comparator<Dispositivo>() {
                        @Override
                        public int compare(Dispositivo lhs, Dispositivo rhs) {
                            return rhs.getdNome().compareToIgnoreCase(lhs.getdNome());
                        }
                    });
                    alphaClicked=true;

                }

                adapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    // Ao clicar abre atividade para controle de dispositivo.
    @Override
    public void OnDispositivoClick(int position) {
        Intent IntCD = new Intent(this, MonitoraDispositivo.class);
        IntCD.putExtra("dispositivoId",position);
        startActivity(IntCD);
    }
}