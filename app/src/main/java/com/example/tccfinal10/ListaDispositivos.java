/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: ListaDispositivo

Classe que define o objeto de ListaDispositivo e seus respectivos métodos.

*/


package com.example.tccfinal10;


import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListaDispositivos {

    private ArrayList<Dispositivo> alDispositivos;
    private static ListaDispositivos sListaDispositivos;
    private final Context appContext;

    // Nome do arquivo a ser gravado localmente
    private static final String fileName = "ListaDispositivos.ser";

    private ListaDispositivos(Context appContext) {

        this.appContext = appContext;
        alDispositivos = new ArrayList<>();
        this.readFromFile();
    }

    public static ListaDispositivos get (Context c){
        if (sListaDispositivos == null) {
            sListaDispositivos = new ListaDispositivos(c.getApplicationContext());
        }
        return sListaDispositivos;
    }

    // Retorna lista de dispositivo
    public ArrayList<Dispositivo> getAlDispositivos() {
        return alDispositivos;
    }

    // Grava a lista de dispositivo
    public static void salvar() {
        sListaDispositivos.saveToFile();
    }

    // Serialização para gravação em arquivo.
    private void saveToFile() {
        try {
            FileOutputStream fileOutputStream = this.appContext.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.alDispositivos);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Leitura de arquivo
    private void readFromFile() {
        try {
            FileInputStream fileInputStream = this.appContext.getApplicationContext().openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.alDispositivos = (ArrayList<Dispositivo>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
        catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
