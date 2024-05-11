package com.example.cafefront;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private TextView campo_nombre;
    private TextView campo_contra;
    private Button iniciar;
    private TextInputLayout NombreLayout;
    private TextInputLayout ContraLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        queue = Volley.newRequestQueue(context);
        campo_nombre = findViewById(R.id.campo_nombre);
        campo_contra = findViewById(R.id.campo_contra);
        NombreLayout = findViewById(R.id.NombreLayout);
        ContraLayout = findViewById(R.id.ContraLayout);
        iniciar = findViewById(R.id.iniciar_sesion);

        iniciar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mensaje = "";
                String nombre = campo_nombre.getText().toString();
                String contra = campo_contra.getText().toString();

                if (nombre.isEmpty()) {
                    mensaje = "El nombre es obligatorio";
                    NombreLayout.setBoxStrokeColor(Color.RED);
                    NombreLayout.requestFocus(); //hace q se actualice la interfaz inmediatamente
                } else {
                    // reestablece el color del borde si ya no da fallo
                    NombreLayout.setError(null);
                    NombreLayout.setBoxStrokeColor(Color.BLACK);
                }

                if (contra.isEmpty()) {
                    mensaje = "La contraseña es obligatoria";
                    ContraLayout.setBoxStrokeColor(Color.RED);
                    ContraLayout.requestFocus();
                } else {
                    ContraLayout.setError(null);
                    ContraLayout.setBoxStrokeColor(Color.BLACK);
                }

                if(!nombre.isEmpty() && !contra.isEmpty()) {
                    try {
                        iniciarSesion(nombre, contra);
                    } catch (JSONException e) {
                        Toast.makeText(context, "Esto da un error: " + e, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void iniciarSesion(String nombre, String contra) throws JSONException {
        // creo un JSONObject q va a servir como json_body de la peticion
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", nombre);
        jsonObject.put("contra", contra);

        //creo la peticion
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/login",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            String rol = response.getString("rol");
                            Toast.makeText(context, "El usuario es: " + rol, Toast.LENGTH_LONG).show();
                        }catch (JSONException e) {
                            System.out.println("Error: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            String errorMessage = new String(error.networkResponse.data);

                            if (statusCode == 404) {
                                Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            } else if (statusCode == 401) {
                                Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
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