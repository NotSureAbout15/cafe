package com.example.cafefront.GerenteRecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.R;

import org.w3c.dom.Text;

public class GerenteViewHolder extends RecyclerView.ViewHolder {
    private TextView nombre;
    private TextView rol;
    private GerenteData trabajador;

    public GerenteViewHolder(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombre);
        rol = (TextView) itemView.findViewById(R.id.rol);
    }

    public void showData(GerenteData data, Activity activity) {
        this.nombre.setText(data.getNombre());
        this.rol.setText(data.getRol());
        this.trabajador = data;
    }

}