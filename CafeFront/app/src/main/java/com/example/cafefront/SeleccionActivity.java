package com.example.cafefront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SeleccionActivity extends AppCompatActivity {
    private Context context;
    private Button trabajador;
    private Button cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);

        context = this;
        trabajador = findViewById(R.id.boton_trabajador);
        cliente = findViewById(R.id.boton_cliente);

        //al clicar en el boton trabajador, me mostrara un mensaje
        trabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Has clicado en trabajador", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Login.class);
                startActivity(intent);
            }
        });

        //al clicar en el boton cliente, me mostrará un mensaje
        cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Has clicado en cliente", Toast.LENGTH_LONG).show();
                Intent mesas = new Intent(context, IniciarMesa.class);
                startActivity(mesas);
            }
        });


    }
}