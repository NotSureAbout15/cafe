package com.example.cafefront;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

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
import com.example.cafefront.PedidoRecyclerView.Trabajador.TrabajadorAdapter;
import com.example.cafefront.PedidoRecyclerView.Trabajador.TrabajadorData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerPedidoTrabajador extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private TextView texto;
    private TextView total;
    private TrabajadorAdapter adapter;
    private RecyclerView recyclerView;
    private Float preciototal = (float) 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_pedido_trabajador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        queue = Volley.newRequestQueue(context);
        texto = findViewById(R.id.pedido_mesa);
        total = findViewById(R.id.total);
        recyclerView = findViewById(R.id.recycler_pedido_trabajador);

        //recojo el nombre de la mesa q he enviado desde la otra clase
        String nombreMesa = getIntent().getStringExtra("nombreMesa");
        texto.setText("Mesa: " + nombreMesa);

        mostrarPedido(nombreMesa);
    }

    private void mostrarPedido(String nombreMesa) {
        Activity activity = this;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/verpedido/" + nombreMesa,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //creo una lista para almacenar los items
                        List<TrabajadorData> items = new ArrayList<>();
                        try{
                            //obtengo el array de items del json
                            JSONArray itemsArray = response.getJSONArray("pedido");
                            //recorro el array
                            for (int i=0; i<itemsArray.length(); i++) {
                                //obtengo cada objeto
                                JSONObject item = itemsArray.getJSONObject(i);
                                //creo un objeto MesasData a partir del objeto recogido
                                TrabajadorData data = new TrabajadorData(item.getString("pedido"), (float) item.getDouble("precio"), item.getInt("cantidad"));
                                data.setCantidad(item.getInt("cantidad"));
                                preciototal = preciototal + data.getPrecio();
                                //añado el objeto a la lista
                                items.add(data);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        adapter = new TrabajadorAdapter(items, activity);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        //hago q se muestre el total del pedido
                        total.setText("Total: " + preciototal);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(request);

    }
}