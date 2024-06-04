package com.example.cafefront;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafefront.MesasRecyclerView.MesasData;
import com.example.cafefront.PedidoRecyclerView.Cliente.PedidoAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerPedidoCliente extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private PedidoAdapter adapter;
    private Button pedir;
    private String nombreMesa; //esta variable servira para recoger el nombre de la mesa q paso como intent desde la otra clase
    private Float total;
    private TextView precio;

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
        queue = Volley.newRequestQueue(context);
        pedir = findViewById(R.id.pedir);
        precio = findViewById(R.id.total);

        // Recuperar el array 'pedido' de los extras del Intent
        ArrayList<MesasData> pedido = getIntent().getParcelableArrayListExtra("pedido");
        // recupero tambien el nombre de la mesa para pasarlo al endpoint
        nombreMesa = getIntent().getStringExtra("mesa");
        // recupero el total de la mesa
        //total = getIntent().getFloatExtra("total", 0);
        //inicializo el total a 0
        total = (float) 0.0;
        for (MesasData item : pedido) {
            total += item.getPrecio() * item.getCantidad();
            System.out.println("Pedido" + item.toString());
        }

        // al textview del total le asigno el total pasado desde la otra clase
        String texto_total = new String("Total: " + total + "€");
        precio.setText(texto_total);

        // compruebo si esta vacio (no hay pedido)
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

        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Pedido de la mesa: " + nombreMesa, Toast.LENGTH_LONG).show();

                // para cada item del pedido hago la peticion al servidor
                for (MesasData item : pedido) {

                    try {
                        subirPedido(item);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                Toast.makeText(context, "Total del pedido: " + total, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void subirPedido(MesasData item) throws JSONException {
        //creo el cuerpo de la peticion
        JSONObject pedido = new JSONObject();
        try {
            pedido.put("nombre", item.getNombre());
            pedido.put("mesa", nombreMesa);
            pedido.put("precio", item.getPrecio());
            pedido.put("cantidad", item.getCantidad()); // de momento la cantidad va a estar hardcodeada porq no se le permite tdv al cliente seleccionar la cantidad deseada
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/anadirpedido",
                pedido,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(context, "Su pedido está siendo preparado", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Su pedido está siendo preparado");
                        //uso una variable para el mensaje que se va a mostrar
                        String message = "Se cerrará la aplicación y volverá a la pantalla inicial.\n\n" +
                                "Recuerde que no puede realizar otro pedido hasta haber pagado por éste :)";
                        builder.setMessage(message);

                        builder.setPositiveButton("OK", (dialog, which) -> {
                            // Acción cuando se pulsa OK
                            dialog.dismiss();
                            //esto iniciara la actividad SelccionActivity
                            Intent intent = new Intent(context, SeleccionActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            //esto finalizara la actividad actual
                            if (context instanceof Activity) {
                                ((Activity) context).finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            String errorMessage = new String(error.networkResponse.data);

                            if (statusCode == 405) {
                                Toast.makeText(context, "Faltan parámetros", Toast.LENGTH_LONG).show();
                            } else if (statusCode == 404) {
                                Toast.makeText(context, "Mesa no encontrada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        queue.add(request);
    }
}