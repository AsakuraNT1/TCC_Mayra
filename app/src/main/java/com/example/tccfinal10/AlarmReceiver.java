/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: AlarmReceiver

Classe utilizada para lidar com chamadas de alarme em segundo plano

*/

package com.example.tccfinal10;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class AlarmReceiver extends BroadcastReceiver {


    // Escuta o alarm Manager e abre a Activity necessára passando informações
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        String disNome = extras.getString("NomeDisp");
        String disIP = extras.getString("IPdisp");
        int disID = extras.getInt("IDdisp");
        int operType = extras.getInt("Oper");


        Intent intent2 = new Intent(context, BackgroundOFF.class);
        intent2.putExtra("NomeDisp", disNome);
        intent2.putExtra("IPdisp", disIP);
        intent2.putExtra("IDdisp", disID);
        intent2.putExtra("Oper", operType);
        context.startActivity(intent2);

    }



}
