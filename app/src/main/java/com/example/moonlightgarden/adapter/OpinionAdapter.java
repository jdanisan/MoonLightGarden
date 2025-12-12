package com.example.moonlightgarden.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonlightgarden.R;
import com.example.moonlightgarden.models.Opinion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.OpinionViewHolder> {

    private ArrayList<Opinion> opiniones;

    public OpinionAdapter(ArrayList<Opinion> opiniones) {
        this.opiniones = opiniones;
    }

    @NonNull
    @Override
    public OpinionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_opinion, parent, false);
        return new OpinionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OpinionViewHolder holder, int position) {
        Opinion op = opiniones.get(position);
        holder.tvCliente.setText(op.getCliente());
        holder.tvTextoOpinion.setText(op.getTexto());

        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date(op.getTimestamp()));
        holder.tvFechaOpinion.setText(fecha);
    }

    @Override
    public int getItemCount() {
        return opiniones.size();
    }

    static class OpinionViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente, tvTextoOpinion, tvFechaOpinion;

        OpinionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvTextoOpinion = itemView.findViewById(R.id.tvTextoOpinion);
            tvFechaOpinion = itemView.findViewById(R.id.tvFechaOpinion);
        }
    }
}
