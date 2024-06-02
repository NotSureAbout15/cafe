package com.example.cafefront;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTrabajador extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private ImageView harry_potter, disney, desdentao;
    private Map<String, ImageView> mesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_trabajador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        queue = Volley.newRequestQueue(context);
        harry_potter = findViewById(R.id.harry_potter);
        disney = findViewById(R.id.disney);
        desdentao = findViewById(R.id.desdentao);

        // Mapeo los nombres de las mesas a sus correspondientes ImageView
        mesas = new HashMap<>();
        mesas.put("Harry Potter", harry_potter);
        mesas.put("Disney", disney);
        mesas.put("Desdentao", desdentao);

        //mando las peticiones para todas las mesas
        consultarUso("Harry Potter");
        consultarUso("Disney");
        consultarUso("Desdentao");
    }

    private void consultarUso(String nombreMesa){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/estadomesa/" + nombreMesa,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String uso = response.getString("estado");
                            if (uso.equalsIgnoreCase("S")){
                                //busco el la lista de mesas la que tiene el mismo nombre q la peticion para q se cambie esa imagen y no el resto
                                ImageView mesaImageView = mesas.get(nombreMesa);
                                if (mesaImageView != null) {
                                    //cambio la imagen de la mesa específica
                                    mesaImageView.setImageResource(R.drawable.mesa_ocupada); // Reemplaza con el ID de tu nueva imagen
                                }
                            }
                        }catch (JSONException e){
                            System.out.println("Error: " + e);
                        }
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