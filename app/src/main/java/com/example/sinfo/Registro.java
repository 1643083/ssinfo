package com.example.sinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class Registro extends AppCompatActivity {

    EditText edtApellidos, edtNombres, edtDireccion, edtTelefono, edtEmail;

    Button btnGuardar;

    //1. objeto que sirva como canal de comunicación
    RequestQueue requestQueue;

    //2. endpoint (dirección que apunta ws)
    private final String URL = "http://192.168.101.66:3000/alumnos";

    private void loadUI(){
        edtApellidos = findViewById(R.id.edtApellidos);
        edtNombres = findViewById(R.id.edtNombres);
        edtDireccion = findViewById(R.id.edtDireccion);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtEmail = findViewById(R.id.edtEmail);
        btnGuardar = findViewById(R.id.btnGuardar);
    }

    /**
     * envia los datos ingresados del formulario a la BD a través del webservice
     */
    private void registrarAlumno(){
        //habilitar el canal
        requestQueue = Volley.newRequestQueue(this);

        // 3. ¿qué dato necesita el ws? un JSON
        JSONObject jsonObject = new JSONObject();

        //4. asignar datos al json
        try {
            jsonObject.put("apellidos", edtApellidos.getText().toString());
            jsonObject.put("nombres", edtNombres.getText().toString());
            jsonObject.put("direccion", edtDireccion.getText().toString());
            jsonObject.put("telefono", edtTelefono.getText().toString());
            jsonObject.put("email", edtEmail.getText().toString());
        } catch (JSONException e) {
            Log.e("error_JSON", e.toString());
            throw new RuntimeException(e);
        }

        //5. ¿qué metodo utilizaré para enviar los datos? POST
        // ¿qué objeto obtengo del ws? otro json
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String mensaje = jsonObject.getString("message");
                            int id = jsonObject.getInt("id");

                            Toast.makeText(getApplicationContext(), mensaje + " - id: " +id, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("error_WS", volleyError.toString());
                        Toast.makeText(getApplicationContext(), "no se pudo grabar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //6. enviar datos al ws
        requestQueue.add(jsonObjectRequest);

    } //registar alumno

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        this.loadUI();

        btnGuardar.setOnClickListener(v -> { this.registrarAlumno(); });
    }
}