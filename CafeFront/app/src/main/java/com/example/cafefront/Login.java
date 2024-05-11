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

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    private Context context;
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
        campo_nombre = findViewById(R.id.campo_nombre);
        campo_contra = findViewById(R.id.campo_contra);
        NombreLayout = findViewById(R.id.NombreLayout);
        ContraLayout = findViewById(R.id.ContraLayout);
        iniciar = findViewById(R.id.iniciar_sesion);

        iniciar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mensaje = "Sesión iniciada";
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

                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }
}