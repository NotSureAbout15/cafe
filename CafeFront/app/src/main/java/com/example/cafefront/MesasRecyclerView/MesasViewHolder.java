package com.example.cafefront.MesasRecyclerView;

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

import com.example.cafefront.R;

public class MesasViewHolder extends RecyclerView.ViewHolder {
    public CheckBox checkBox;
    private TextView nombre;
    private TextView precio;
    private MesasData pedido;

    public MesasViewHolder(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombre);
        precio = (TextView) itemView.findViewById(R.id.precio);
        checkBox = (CheckBox) itemView.findViewById(R.id.casilla);
    }

    public void showData(MesasData data, Activity activity) {
        this.nombre.setText(data.getNombre());
        this.precio.setText(String.valueOf(data.getPrecio())+"€");
        this.pedido = data;
    }
}