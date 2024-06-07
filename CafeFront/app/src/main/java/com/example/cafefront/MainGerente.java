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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafefront.GerenteRecyclerView.GerenteAdapter;
import com.example.cafefront.GerenteRecyclerView.GerenteData;
import com.example.cafefront.MesasRecyclerView.MesasAdapter;
import com.example.cafefront.MesasRecyclerView.MesasData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainGerente extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private GerenteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_gerente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        queue = Volley.newRequestQueue(context);

        listadoEmpleados();
    }

    private void listadoEmpleados() {
        //obtengo el recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_gerente);
        Activity activity = this;

        //creo una solicitud para obtener el menu del tipo seleccionado
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/trabajadores",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //creo una lista para almacenar los trabajadores
                        List<GerenteData> items = new ArrayList<>();
                        try{
                            //obtengo el array de items del json
                            JSONArray trabajadoresArray = response.getJSONArray("trabajadores");
                            //recorro el array
                            for (int i=0; i<trabajadoresArray.length(); i++) {
                                //obtengo cada objeto
                                JSONObject trabajador = trabajadoresArray.getJSONObject(i);
                                //creo un objeto GerenteData a partir del objeto recogido
                                GerenteData data = new GerenteData(trabajador.getString("nombre"), trabajador.getString("email"), trabajador.getInt("telefono"), trabajador.getString("turno"), trabajador.getString("rol"));
                                //añado el data a la lista de trabajadores (items) q le voy a pasar al adapter
                                items.add(data);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        //creo un adaptador
                        adapter = new GerenteAdapter(items, activity);
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