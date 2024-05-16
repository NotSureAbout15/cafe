package com.example.cafefront;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.MesasRecyclerView.MesasData;
import com.example.cafefront.PedidoRecyclerView.PedidoAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class VerPedidoCliente extends AppCompatActivity {
    private Context context;
    private PedidoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_pedido_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;

        // Recuperar el array 'pedido' de los extras del Intent
        ArrayList<MesasData> pedido = getIntent().getParcelableArrayListExtra("pedido");

        // Ahora puedes trabajar con el array 'pedido'
        if (pedido.isEmpty()) {
            Toast.makeText(context, "No has pedido nada :D", Toast.LENGTH_SHORT).show();
        } else {
            //si el pedido no llega vacio, creo un recyclerview para poder visualizarlo
            RecyclerView recyclerView = findViewById(R.id.recycler_pedido);
            Activity activity = this;

            adapter = new PedidoAdapter(pedido, activity);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}