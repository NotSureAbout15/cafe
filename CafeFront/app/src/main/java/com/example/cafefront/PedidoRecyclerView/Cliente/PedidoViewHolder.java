package com.example.cafefront.PedidoRecyclerView.Cliente;

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

import org.w3c.dom.Text;

public class PedidoViewHolder extends RecyclerView.ViewHolder {
    private TextView nombre;
    private TextView precio;
    private TextView cantidad;
    private MesasData pedido;

    public PedidoViewHolder(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombre);
        precio = (TextView) itemView.findViewById(R.id.precio);
        cantidad = (TextView) itemView.findViewById(R.id.cantidad);
    }

    public void showData(MesasData data, Activity activity) {
        this.nombre.setText(data.getNombre());
        this.precio.setText(String.valueOf(data.getPrecio())+"€");
        this.cantidad.setText(String.valueOf(data.getCantidad()));
        this.pedido = data;
    }

}