package com.example.cafefront;

import android.app.Activity;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PedidoMesa extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private Button comida;
    private Button bebida;
    private Button postres;
    private String tipo;
    private MesasAdapter adapter;

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
        queue = Volley.newRequestQueue(context);
        comida = findViewById(R.id.comida);
        bebida = findViewById(R.id.bebida);
        postres = findViewById(R.id.postres);

        // Recuperar el nombre de la mesa del Intent
        String nombreMesa = getIntent().getStringExtra("nombre_mesa");

        //segun el boton q se clique, mostrara un recycler view del menu q tenga ese tipo
        comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo = "Comida";
                mostrarMenu(tipo);
            }
        });
        bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo = "Bebida";
                mostrarMenu(tipo);
            }
        });
        postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo = "Postre";
                mostrarMenu(tipo);
            }
        });

        //Toast.makeText(context, "Estas dentro del pedido para la mesa: " + nombreMesa, Toast.LENGTH_LONG).show();
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