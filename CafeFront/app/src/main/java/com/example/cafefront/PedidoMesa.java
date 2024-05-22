package com.example.cafefront;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.cafefront.MesasRecyclerView.MesasAdapter;
import com.example.cafefront.MesasRecyclerView.MesasData;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PedidoMesa extends AppCompatActivity {
    private Context context;
    private View rootView;
    private RequestQueue queue;
    private Button comida;
    private Button bebida;
    private Button postres;
    private Button confirmar_comida;
    private Button confirmar_bebida;
    private Button confirmar_postres;
    private Button confirmar_pedido;
    private String tipo;
    private Float total; //servira para ir haciendo la cuenta del coste del pedido del cliente
    private MesasAdapter adapter;
    private List<MesasData> pedido; //esta lista se utilizara para guardar todos los items de las 3 categorias

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
        rootView = findViewById(android.R.id.content);
        queue = Volley.newRequestQueue(context);
        comida = findViewById(R.id.comida);
        bebida = findViewById(R.id.bebida);
        postres = findViewById(R.id.postres);
        confirmar_comida = findViewById(R.id.confirmar_comida);
        confirmar_bebida = findViewById(R.id.confirmar_bebida);
        confirmar_postres = findViewById(R.id.confirmar_postre);
        confirmar_pedido = findViewById(R.id.confirmar_pedido);

        //hago q nada mas abrir esta pantalla se muestre el menu comida y el boton de añadir producto
        mostrarMenu("Comida");

        //inicializo el total a 0
        total = (float) 0.0;

        //inicializo la lista guardar el pedido
        pedido = new ArrayList<>();

        // Recuperar el nombre de la mesa del Intent
        String nombreMesa = getIntent().getStringExtra("nombre_mesa");

        //en un principio los botones de confirmacion van a estar ocultos, al clicar sobre su tipo se haran visibles
        //confirmar_comida.setVisibility(View.INVISIBLE);
        confirmar_bebida.setVisibility(View.INVISIBLE);
        confirmar_postres.setVisibility(View.INVISIBLE);

        //segun el boton q se clique, mostrara un recycler view del menu q tenga ese tipo
        comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo = "Comida";
                mostrarMenu(tipo);
                confirmar_comida.setVisibility(View.VISIBLE);
                confirmar_bebida.setVisibility(View.INVISIBLE);
                confirmar_postres.setVisibility(View.INVISIBLE);
            }
        });
        bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo = "Bebida";
                mostrarMenu(tipo);
                confirmar_comida.setVisibility(View.INVISIBLE);
                confirmar_bebida.setVisibility(View.VISIBLE);
                confirmar_postres.setVisibility(View.INVISIBLE);
            }
        });
        postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo = "Postre";
                mostrarMenu(tipo);
                confirmar_comida.setVisibility(View.INVISIBLE);
                confirmar_bebida.setVisibility(View.INVISIBLE);
                confirmar_postres.setVisibility(View.VISIBLE);
            }
        });

        //al hacer click en confirmar comida
        confirmar_comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtengo la lista de items seleccionados
                List<MesasData> comidaSeleccionada = adapter.getPedido();

                //si la lista esta vacia, le indico que para confirmar hay q seleccionar primero, sino confirmo el pedido
                if (comidaSeleccionada.isEmpty()){
                    Snackbar.make(rootView, "Hay que seleccionar algo antes de pedir :)", Snackbar.LENGTH_LONG).show();
                } else {
                    pedido.addAll(comidaSeleccionada);
                    Toast.makeText(context, "Añadido al pedido", Toast.LENGTH_LONG).show();
                }
            }
        });

        //al hacer click en confirmar bebida
        confirmar_bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtengo la lista de items seleccionados
                List<MesasData> bebidaSeleccionada = adapter.getPedido();

                //si la lista esta vacia, le indico que para confirmar hay q seleccionar primero, sino confirmo el pedido
                if (bebidaSeleccionada.isEmpty()){
                    Snackbar.make(rootView, "Hay que seleccionar algo antes de pedir :)", Snackbar.LENGTH_LONG).show();
                } else {
                    pedido.addAll(bebidaSeleccionada);
                    Toast.makeText(context, "Añadido al pedido", Toast.LENGTH_LONG).show();
                }
            }
        });

        //al hacer click en confirmar postres
        confirmar_postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtengo la lista de items seleccionados
                List<MesasData> postreSeleccionado = adapter.getPedido();

                //si la lista esta vacia, le indico que para confirmar hay q seleccionar primero, sino confirmo el pedido
                if (postreSeleccionado.isEmpty()){
                    Snackbar.make(rootView, "Hay que seleccionar algo antes de pedir :)", Snackbar.LENGTH_LONG).show();
                } else {
                    pedido.addAll(postreSeleccionado);
                    Toast.makeText(context, "Añadido al pedido", Toast.LENGTH_LONG).show();
                }
            }
        });

        //confirmar pedido
        confirmar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verPedido = new Intent(context, VerPedidoCliente.class);
                verPedido.putParcelableArrayListExtra("pedido", new ArrayList<>(pedido));
                verPedido.putExtra("mesa", nombreMesa);
                //verPedido.putExtra("total", total);
                startActivity(verPedido);
            }
        });
    }

    private void mostrarMenu(String tipo) {
        //obtengo el recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_menu);
        Activity activity = this;

        //creo una solicitud para obtener el menu del tipo seleccionado
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/menu/" + tipo,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //creo una lista para almacenar los items
                        List<MesasData> items = new ArrayList<>();
                        try{
                            //obtengo el array de items del json
                            JSONArray itemsArray = response.getJSONArray("items");
                            //recorro el array
                            for (int i=0; i<itemsArray.length(); i++) {
                                //obtengo cada objeto
                                JSONObject item = itemsArray.getJSONObject(i);
                                //creo un objeto MesasData a partir del objeto recogido
                                MesasData data = new MesasData(item.getString("nombre"), tipo, (float) item.getDouble("precio"));
                                //añado el objeto a la lista
                                items.add(data);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        //creo un adaptador
                        adapter = new MesasAdapter(items, activity);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Si hay error, mostramos un mensaje de error
                        System.out.println(error);
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                }
        );

        queue.add(request);
    }
}