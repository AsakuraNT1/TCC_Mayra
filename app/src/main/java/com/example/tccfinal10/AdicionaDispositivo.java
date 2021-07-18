/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: AdicionaDispositivo

Activity utilizada para para adicionar um novo dispositivo
ao banco de dados local.

*/
package com.example.tccfinal10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;


public class AdicionaDispositivo extends AppCompatActivity {


    ArrayList<Dispositivo> alDispositivos;
    Dispositivo dispositivo;
    int dispositivoPos;
    EditText etNome;
    EditText etIP;
    Button btnEnter;


    int iDispoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_dispositivo);

        btnEnter = findViewById(R.id.btnProjectOK);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        ActionBar ab = getSupportActionBar(); // acessa a action bar
        ab.setDisplayHomeAsUpEnabled(true);  // seta retorno na action bar
        ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));


        dispositivoPos = extras.getInt("dispositivoId");
        iDispoId = extras.getInt("from");
        // pegar referencia a todos os filmes (ArrayList)
        alDispositivos = ListaDispositivos.get(getApplicationContext()).getAlDispositivos();
        // associa os elementos da interface
        etNome = findViewById(R.id.etNome);
        etIP = findViewById(R.id.etIP);

            ab.setTitle("Novo Dispositivo");
            dispositivo = new Dispositivo();
    }




    public void OnClickEnter(View view) {
        // Se nome ou IP do dispositivo esta vazio, informa que é necessário o preenchimento.

        if (etNome.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o nome do dispositivo", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etIP.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o IP do dispositivo", Toast.LENGTH_SHORT).show();
            return;

        }

        // Salva Nome e IP do dispositivo
        dispositivo.setdNome(etNome.getText().toString());
        dispositivo.setdIP(etIP.getText().toString());

        if (iDispoId == 1) {
            alDispositivos.add(dispositivo);         }
        ListaDispositivos.salvar();
        this.finish();


    }

}