package com.example.cafefront;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class IniciarMesa extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private Button harry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_mesa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        queue = Volley.newRequestQueue(context);
        harry = findViewById(R.id.harry_potter);

        harry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicaste en: " + harry.getText().toString(), Toast.LENGTH_LONG).show();
                try {
                    String mesa = harry.getText().toString();
                    mesaenuso(mesa);
                }catch (Exception e){
                    System.out.println("Error: " + e);
                }
            }
        });
    }

    private void mesaenuso(String nombreMesa) throws JSONException {
        //creo el cuerpo de la peticion
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mesa", nombreMesa);

        //creo la peticion
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/iniciomesa",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Bienvenido a la cafeteria", Toast.LENGTH_LONG).show();
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
                            }else if (statusCode == 404) {
                                Toast.makeText(context, "Mesa no encontrada", Toast.LENGTH_SHORT).show();
                            } else if (statusCode == 409) {
                                Toast.makeText(context, "La mesa ya está en uso", Toast.LENGTH_SHORT).show();
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