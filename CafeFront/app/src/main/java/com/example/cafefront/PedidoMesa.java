package com.example.cafefront;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;

public class PedidoMesa extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedido_mesa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;

        // Recuperar el nombre de la mesa del Intent
        String nombreMesa = getIntent().getStringExtra("nombre_mesa");

        // Ahora puedes usar el nombre de la mesa como desees en esta actividad
        // Por ejemplo, mostrarlo en un TextView
        Toast.makeText(context, "Estas dentro del pedido para la mesa: " + nombreMesa, Toast.LENGTH_LONG).show();
    }
}