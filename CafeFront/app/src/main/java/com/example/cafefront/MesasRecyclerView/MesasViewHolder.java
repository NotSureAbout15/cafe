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
    //private EditText cantidad;
    private TextView restar;
    private TextView cantidad_texto;
    private TextView sumar;
    private int quantity;
    private MesasData pedido;

    public MesasViewHolder(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombre);
        precio = (TextView) itemView.findViewById(R.id.precio);
        checkBox = (CheckBox) itemView.findViewById(R.id.casilla);
        //cantidad = (EditText) itemView.findViewById(R.id.cantidad);
        restar = (TextView) itemView.findViewById(R.id.restar);
        cantidad_texto = (TextView) itemView.findViewById(R.id.cantidad_texto);
        sumar = (TextView) itemView.findViewById(R.id.sumar);

        //hago q al clicar sobre -, la cantidad disminuya
        restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity --;
                    cantidad_texto.setText(String.valueOf(quantity));
                    pedido.setCantidad(quantity);
                }
            }
        });

        //hago lo propio con +
        sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity ++;
                cantidad_texto.setText(String.valueOf(quantity));
                pedido.setCantidad(quantity);
            }
        });
    }

    public void showData(MesasData data, Activity activity) {
        this.nombre.setText(data.getNombre());
        this.precio.setText(String.valueOf(data.getPrecio()) + "€");
        this.pedido = data;
        this.cantidad_texto.setText(String.valueOf(data.getCantidad()));
    }
}
