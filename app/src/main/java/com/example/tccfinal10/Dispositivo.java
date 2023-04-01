/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: Dispositivo

Classe que define o objeto dispositivo e seus respectivos métodos.

*/



package com.example.tccfinal10;


import android.app.AlarmManager;
import android.app.PendingIntent;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.io.Serializable;

public class Dispositivo implements Serializable {


    private String dNome;
    private String dIP;
    private boolean dStatus = false;
    private int speed = 0;
    private int meanPower = 0;
    private int maxPower = 0;
    private PowerThread powerThread;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    Dispositivo(String dNome, String dIP) {
        this.dNome = dNome;
        this.dIP = dIP;

    }

    Dispositivo() {
        this.dNome = "";
        this.dIP = "";
    }


    String getdNome() {
        return dNome;
    }

    public void setdNome(String dNome) {
        this.dNome = dNome;
    }

    String getdIP() {
        return dIP;
    }

    public void setdIP(String dIP) {
        this.dIP = dIP;
    }

    boolean getdStatus(){ return dStatus; }

    public void setdStatus(boolean dStatus){this.dStatus = dStatus;}

    AlarmManager getdAlarm(){ return alarmMgr; }

    public void setdAlarm(AlarmManager alarmMgr){this.alarmMgr = alarmMgr;}

    int getSpeed() {return  speed;}

    public void setSpeed(int speed) {this.speed = speed; }

    int getMeanPower() {return meanPower;}

    public void setMeanPower(int meanPower) {this.meanPower = meanPower;}

    int getMaxPower() {return maxPower;}

    public void setMaxPower(int maxPower) {this.maxPower = maxPower;}

    PendingIntent getAlarmIntent(){ return alarmIntent; }

    public void setAlarmIntent(PendingIntent alarmIntent) { this.alarmIntent = alarmIntent; }

    public void setPowerThread(RequestQueue queue, StringRequest PowerRequest)
    {

     this.powerThread = new PowerThread(queue, PowerRequest);
     this.powerThread.start();

    }

    public void stopPowerThread()
    {
        this.powerThread.setMustRun(false);
    }


}
