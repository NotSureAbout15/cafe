package com.example.cafefront.GerenteRecyclerView;

import android.app.Activity;
import android.content.Intent;
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

import com.example.cafefront.DetalleTrabajador;
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

        // Agregar un OnClickListener al itemView
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetalleTrabajador.class);
                intent.putExtra("nombre", trabajador.getNombre());
                intent.putExtra("email", trabajador.getEmail());
                intent.putExtra("telefono", trabajador.getTelefono());
                intent.putExtra("turno", trabajador.getTurno());
                intent.putExtra("rol", trabajador.getRol());
                v.getContext().startActivity(intent);
            }
        });
    }

    public void showData(GerenteData data, Activity activity) {
        this.nombre.setText(data.getNombre());
        this.rol.setText(data.getRol());
        this.trabajador = data;
    }

}