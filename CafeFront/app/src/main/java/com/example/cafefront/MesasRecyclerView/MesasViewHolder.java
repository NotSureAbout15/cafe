package com.example.cafefront.MesasRecyclerView;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.R;

public class MesasViewHolder extends RecyclerView.ViewHolder {
    public CheckBox checkBox;
    private TextView nombre;
    private TextView precio;
    private EditText cantidad;
    private MesasData pedido;

    public MesasViewHolder(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombre);
        precio = (TextView) itemView.findViewById(R.id.precio);
        checkBox = (CheckBox) itemView.findViewById(R.id.casilla);
        cantidad = (EditText) itemView.findViewById(R.id.cantidad);
    }

    public void showData(MesasData data, Activity activity) {
        this.nombre.setText(data.getNombre());
        this.precio.setText(String.valueOf(data.getPrecio()) + "€");
        this.pedido = data;
        this.cantidad.setText(String.valueOf(data.getCantidad()));

        // Manejar cambios en la cantidad
        cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int qty = Integer.parseInt(s.toString());
                    pedido.setCantidad(qty);
                } catch (NumberFormatException e) {
                    pedido.setCantidad(1); // valor por defecto si la entrada no es un número válido
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
