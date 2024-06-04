package com.example.cafefront.PedidoRecyclerView.Cliente;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.MesasRecyclerView.MesasData;
import com.example.cafefront.R;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> {
    //declaro las varibles de instancia
    private List<MesasData> pedido;
    private Activity activity;

    //constructor
    public PedidoAdapter(List<MesasData> pedido, Activity activity){
        this.pedido = pedido;
        this.activity = activity;
    }
    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_pedido_view_holder, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        MesasData pedidoActual = pedido.get(position);
        holder.showData(pedidoActual, activity);
    }

    @Override
    public int getItemCount() {
        return pedido.size();
    }
}
