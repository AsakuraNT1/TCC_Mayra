/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: Controla Dispositivo

Activity utilizada para enviar comandos via HTTP para o dispositivo.

*/


package com.example.tccfinal10;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class MonitoraDispositivo extends AppCompatActivity {

    private TextView txvActualPower;
    private TextView txvMeanPower;
    private TextView txvMaxPower;
    private TextView txtStatus;
    private RequestQueue queue;
    private StringRequest stringONRequest;
    private StringRequest stringOFFRequest;
    private Switch swLigar;
    private String disURL;
    private ArrayList<Dispositivo> alDispositivos;
    private Dispositivo dispositivo;
    private int dispositivoPos = 1;
    private int tminutos=0;
    private int thoras=0;
    private TextView txtTimer;
    private Button btnTimerCancel;
    private boolean dStatus=false;
    public AlarmManager alarmMgr;
    public PendingIntent alarmIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitora_dispositivo);
        txtStatus = findViewById(R.id.txtStatusCTL);
        txvActualPower = findViewById(R.id.tvValueActualConsumeCTL);
        txvMeanPower = findViewById(R.id.tvValueMeanConsumeCTL);
        txvMaxPower = findViewById(R.id.tvValueMaxPowerCTL);
        TextView txtNome = findViewById(R.id.tvTitleCTL);
        TextView txtData = findViewById(R.id.tvResponse);
        swLigar = findViewById(R.id.swLigarCTL);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        ActionBar ab = getSupportActionBar();

        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);  // seta retorno na action bar
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        dispositivoPos = extras.getInt("dispositivoId");

        alDispositivos = ListaDispositivos.get(getApplicationContext()).getAlDispositivos();

        dispositivo = alDispositivos.get(dispositivoPos)
        ;
        // atualiza nome do dispositivo na action bar
        ab.setTitle(dispositivo.getdNome());

        // atualiza dados do dispositivo nas views
        txtNome.setText(dispositivo.getdNome());
        dStatus = dispositivo.getdStatus();
        if(dStatus){
            swLigar.setChecked(true);
            txtStatus.setText(R.string.ON);
        } else { swLigar.setChecked(false);
            txtStatus.setText(R.string.OFF);
        }
        alarmMgr = dispositivo.getdAlarm();
        alarmIntent = dispositivo.getAlarmIntent();

        // Compoe a URL com o IP do dispositivo.
        disURL ="http://" + dispositivo.getdIP();

        // Instancia fila de envio
        queue = Volley.newRequestQueue(this);


        stringONRequest = new StringRequest(Request.Method.GET, disURL + "/status=ON",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        txtData.setText(response);
                        txtStatus.setText(R.string.ON);
                        dispositivo.setdStatus(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtData.setText(R.string.Falha_conexao);
            }
        });

        stringOFFRequest = new StringRequest(Request.Method.GET, disURL + "/status=OFF",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        txtData.setText(response);
                        txtStatus.setText(R.string.OFF);
                        dispositivo.setdStatus(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtData.setText(R.string.Falha_conexao);
            }
        });



        swLigar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtStatus.setText(R.string.ON);
                    txtTimer.setText("");
                    queue.add(stringONRequest);


                } else {
                    txtStatus.setText(R.string.OFF);
                    queue.add(stringOFFRequest);
                }
                ListaDispositivos.salvar();
            }
        });


    }

    public void onResume() {
        super.onResume();
        skBarraVelocidade.setProgress(dispositivo.getSpeed());
        dStatus = dispositivo.getdStatus();
        if(dStatus){
            swLigar.setChecked(true);
            txtStatus.setText(R.string.ON);
        } else { swLigar.setChecked(false);
            txtStatus.setText(R.string.OFF);
        }

    }

@Override


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_controla_dispositivo, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.miDelete:

                // Cria janela de alerta
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Deseja remover o dispositivo " + dispositivo.getdNome() +"?").setTitle("Aviso");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        alDispositivos.remove(dispositivoPos);
                        finish();
                        ListaDispositivos.salvar();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            case R.id.miTimer:


                int hour = 0;
                int minutes = 0;

                // Picker de tempo do android
                TimePickerDialog picker = new TimePickerDialog(MonitoraDispositivo.this, 2,
                        (tp, sHour, sMinute) -> {
                            tminutos = sMinute;
                            thoras = sHour;

                            if (dispositivo.getdAlarm() != null) {
                                dispositivo.getdAlarm().cancel(dispositivo.getAlarmIntent());
                            }

                            alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            intent.putExtra("NomeDisp", dispositivo.getdNome());
                            intent.putExtra("IPdisp", dispositivo.getdIP());
                            intent.putExtra("IDdisp", dispositivoPos);
                            intent.putExtra("Oper", 1);

                            alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                            dispositivo.setAlarmIntent(alarmIntent);

                            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() + 3600000 * thoras + 60000 * tminutos, dispositivo.getAlarmIntent());

                            String message = "Desligará em " + thoras + " horas e " + tminutos + " minutos.";

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            dispositivo.setdAlarm(alarmMgr);


                            btnTimerCancel.setClickable(true);
                            btnTimerCancel.setVisibility(View.VISIBLE);
                            txtTimer.setText(R.string.timeractive);


                        }, hour, minutes, true);

                picker.show();

                return true;

            case R.id.miFade:


                AlertDialog.Builder builder_fd = new AlertDialog.Builder(this);
                builder_fd.setMessage("O ventilador diminuirá a velocidade em 5% a cada minuto. Deseja continuar?");

                builder_fd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (dispositivo.getdAlarm() != null) {
                            dispositivo.getdAlarm().cancel(dispositivo.getAlarmIntent());
                        }

                        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        intent.setAction("fade");
                        intent.putExtra("NomeDisp", dispositivo.getdNome());
                        intent.putExtra("IPdisp", dispositivo.getdIP());
                        intent.putExtra("IDdisp",dispositivoPos);
                        intent.putExtra("Oper",2);

                        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), dispositivoPos, intent, 0);

                        dispositivo.setAlarmIntent(alarmIntent);
                        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                SystemClock.elapsedRealtime() + 60000,
                                60000, dispositivo.getAlarmIntent());

                        String message = "Desligamento Suave Ativado.";

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        dispositivo.setdAlarm(alarmMgr);

                        btnTimerCancel.setClickable(true);
                        btnTimerCancel.setVisibility(View.VISIBLE);
                        txtTimer.setText(R.string.timeractive);


                    }
                });
                builder_fd.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                });
                AlertDialog dialog_fd = builder_fd.create();
                dialog_fd.show();


                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

// Cancela alarme se existir
public void CancelClick(View v){

    if (dispositivo.getdAlarm() != null) {
        dispositivo.getdAlarm().cancel(dispositivo.getAlarmIntent());
    }

    btnTimerCancel.setClickable(false);
    btnTimerCancel.setVisibility(View.INVISIBLE);
    txtTimer.setText(R.string.timercancel);


}






}