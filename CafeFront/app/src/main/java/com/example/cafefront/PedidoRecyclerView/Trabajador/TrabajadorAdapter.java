package com.example.cafefront.PedidoRecyclerView.Trabajador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.R;

import java.util.List;

public class TrabajadorAdapter extends RecyclerView.Adapter<TrabajadorViewHolder> {
    //declaro las varibles de instancia
    private List<TrabajadorData> pedido;
    private Activity activity;

    //constructor
    public TrabajadorAdapter(List<TrabajadorData> pedido, Activity activity){
        this.pedido = pedido;
        this.activity = activity;
    }
    @NonNull
    @Override
    public TrabajadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_trabajador_view_holder, parent, false);
        return new TrabajadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrabajadorViewHolder holder, int position) {
        TrabajadorData pedidoActual = pedido.get(position);
        holder.showData(pedidoActual, activity);
    }

    @Override
    public int getItemCount() {
        return pedido.size();
    }
}
