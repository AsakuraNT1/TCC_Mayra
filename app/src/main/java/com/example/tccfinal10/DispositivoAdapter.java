/*
Codigo Aplicativo de Controle de Velocidade de Ventilador

Disciplina: TE348 - TRABALHO DE CONCLUSÃO DE CURSO II

Autor: Jose Miranda Netto
GRR20135624

Classe: DispositivoAdapter

Classe utilizada para integrar a lista de dispositivo com a exibição na tela principal.

*/





package com.example.tccfinal10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DispositivoAdapter extends RecyclerView.Adapter<DispositivoAdapter.ViewHolder>{

    private final List<Dispositivo> DispositivoList;
    private final OnDispositivoListener onDispositivoListener;



    public DispositivoAdapter(List<Dispositivo> Dispositivos, OnDispositivoListener onDispositivoListener) {
        this.DispositivoList = Dispositivos;
        this.onDispositivoListener = onDispositivoListener;

    }

    // Holder para exibição dos dispositivos
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnDispositivoListener onDispositivoListener;

        private final TextView tvnomeDispositivo;
        private final TextView tvIPDispositivo;
        private final ImageView ivNome_Holder;
        private final ImageView ivIP_Holder;



        public ViewHolder(View itemView,OnDispositivoListener onDispositivoListener) {

            super(itemView);

            tvnomeDispositivo = itemView.findViewById(R.id.tvNome_Holder);
            tvIPDispositivo = itemView.findViewById(R.id.tvIP_holder);
            ivNome_Holder = itemView.findViewById(R.id.ivNome_Holder);
            ivIP_Holder = itemView.findViewById(R.id.ivIP_Holder);


            this.onDispositivoListener = onDispositivoListener;

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {

            onDispositivoListener.OnDispositivoClick(getAdapterPosition());

        }
    }

    @NonNull
    @Override
    public DispositivoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Utiliza o Layout da View
        View contactView = inflater.inflate(R.layout.view_layout_constraint, parent, false);
        return new ViewHolder(contactView, onDispositivoListener);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(DispositivoAdapter.ViewHolder viewHolder, int position) {



        // Obtem os dados do dispositivo de acordo com a posição
        Dispositivo Dispositivo = DispositivoList.get(position);

        TextView tvnomeDispositivo = viewHolder.tvnomeDispositivo;
        tvnomeDispositivo.setText(Dispositivo.getdNome());

        TextView tvIPDispositivo = viewHolder.tvIPDispositivo;
        tvIPDispositivo.setText("IP: " + Dispositivo.getdIP());

        ImageView ivNome = viewHolder.ivNome_Holder;
        ImageView ivIP = viewHolder.ivIP_Holder;

        ivNome.setImageResource(R.mipmap.ic_plug_round);
        ivIP.setImageResource(R.mipmap.ic_http_round);


    }



    // Retorna o numero de itens da lista
    @Override
    public int getItemCount() {
        return DispositivoList.size();
    }

    public interface OnDispositivoListener{

        void OnDispositivoClick(int position);
    }


}
