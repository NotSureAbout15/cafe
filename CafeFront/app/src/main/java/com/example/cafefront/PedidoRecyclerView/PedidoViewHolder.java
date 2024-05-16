package com.example.cafefront.PedidoRecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.MesasRecyclerView.MesasData;
import com.example.cafefront.R;

public class PedidoViewHolder extends RecyclerView.ViewHolder {
    private TextView nombre;
    private TextView precio;
    private MesasData pedido;

    public PedidoViewHolder(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombre);
        precio = (TextView) itemView.findViewById(R.id.precio);
    }

    public void showData(MesasData data, Activity activity) {
        this.nombre.setText(data.getNombre());
        this.precio.setText(String.valueOf(data.getPrecio())+"€");
        this.pedido = data;
    }

}