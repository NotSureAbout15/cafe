package com.example.cafefront;

import android.content.Context;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetalleTrabajador extends AppCompatActivity {
    private Context context;
    private RequestQueue queue;
    private TextView nombre, telefono, email, turno, rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_trabajador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        queue = Volley.newRequestQueue(context);
        nombre = findViewById(R.id.nombre_trabajador);
        telefono = findViewById(R.id.telefono_trabajador);
        email = findViewById(R.id.email_trabajador);
        turno = findViewById(R.id.turno_trabajador);
        rol = findViewById(R.id.rol_trabajador);

        // recojo los valores q me lo paso desde la otra clase y hago q se muestren
        String nombreTrabajador = getIntent().getStringExtra("nombre");
        nombre.setText(nombreTrabajador);

        String emailTrabajador = getIntent().getStringExtra("email");
        email.setText("Email: " + emailTrabajador);

        String telefonoTrabajador = getIntent().getStringExtra("telefono");
        telefono.setText("Teléfono: " + telefonoTrabajador);

        String turnoTrabajador = getIntent().getStringExtra("turno");
        turno.setText("Turno: " + turnoTrabajador);

        String rolTrabajador = getIntent().getStringExtra("rol");
        rol.setText("Rol: " + rolTrabajador);
    }

}