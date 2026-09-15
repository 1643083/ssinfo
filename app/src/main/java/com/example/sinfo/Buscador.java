package com.example.sinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Buscador extends AppCompatActivity {

    EditText edtIdB, edtApellidosB, edtNombresB, edtTelefonoB, edtDireccionB, edtEmailB;

    Button btnBuscarB, btnActualizarB, btnEliminarB, btnReiniciarB;

    RequestQueue requestQueue;

    private final String URL = "http://localhost:3000/alumnos";

    private void loadUI(){
        edtIdB = findViewById(R.id.edtIdB);
        edtApellidosB = findViewById(R.id.edtApellidosB);
        edtNombresB = findViewById(R.id.edtNombresB);
        edtDireccionB = findViewById(R.id.edtDireccionB);
        edtTelefonoB = findViewById(R.id.edtTelefonoB);
        edtEmailB = findViewById(R.id.edtEmailB);

        btnBuscarB = findViewById(R.id.btnBuscarB);
        btnActualizarB = findViewById(R.id.btnActualizarB);
        btnEliminarB = findViewById(R.id.btnEliminarB);
        btnReiniciarB = findViewById(R.id.btnReiniciarB);

        btnActualizarB.setEnabled(false);
        btnEliminarB.setEnabled(false);
    }

    private void validarError(int statusCode, String errorJSON){
        if (statusCode == 404){
            try {
                JSONObject jsonObject = new JSONObject(errorJSON);
                String mensajeError = jsonObject.getString("message");
                this.resetUI();
                Toast.makeText(getApplicationContext(), mensajeError, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void buscarAlumno(){
        if (edtIdB.getText().toString().isEmpty()){
            edtIdB.setError("campo requerido");
            edtIdB.requestFocus();
            return;
        }

        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + edtIdB.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            edtApellidosB.setText(jsonObject.getString("apellidos"));
                            edtNombresB.setText(jsonObject.getString("nombres"));
                            edtTelefonoB.setText(jsonObject.getString("telefono"));
                            edtDireccionB.setText(jsonObject.getString("direccion"));
                            edtEmailB.setText(jsonObject.getString("email"));

                            btnActualizarB.setEnabled(true);
                            btnEliminarB.setEnabled(true);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // manejo de errores
                        // si el servidor retorna un error 40X (es un error)
                        NetworkResponse response = volleyError.networkResponse;

                        //validar si existe un codigo de error
                        if (response != null && response.data != null){
                            // mas importante - codigo del error
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);
                        }
                    }//volley error
                }//error listener
        );//jsonobjectrequest

        requestQueue.add(jsonObjectRequest);
    }

    private void actualizarDatos() {
        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + edtIdB.getText().toString();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("apellidos", edtApellidosB.getText().toString());
            jsonObject.put("nombres", edtNombresB.getText().toString());
            jsonObject.put("direccion", edtDireccionB.getText().toString());
            jsonObject.put("telefono", edtTelefonoB.getText().toString());
            jsonObject.put("email", edtEmailB.getText().toString());
        } catch (JSONException e) {
            Log.e("error_JSON", e.toString());
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                endPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String mensaje = jsonObject.getString("message");

                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
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

        requestQueue.add(jsonObjectRequest);
    }

    private void resetUI(){
        edtIdB.setText(null);
        edtNombresB.setText(null);
        edtApellidosB.setText(null);
        edtTelefonoB.setText(null);
        edtDireccionB.setText(null);
        edtEmailB.setText(null);

        btnActualizarB.setEnabled(false);
        btnEliminarB.setEnabled(false);
        edtIdB.requestFocus();
    }

    private void eliminarAlumno(){
        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + edtIdB.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String message = null;
                        try {
                            message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            resetUI();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void actualizarAlumno(){
        requestQueue = Volley.newRequestQueue(this); //canal de comunicacion
        String endPoint = URL + "/" + edtIdB.getText().toString();

        //{"clave": "valor"}
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("apellidos", edtApellidosB.getText().toString());
            jsonObject.put("nombres", edtNombresB.getText().toString());
            jsonObject.put("direccion", edtDireccionB.getText().toString());
            jsonObject.put("telefono", edtTelefonoB.getText().toString());
            jsonObject.put("email", edtEmailB.getText().toString());
        } catch (JSONException e) {
            Log.e("error_JSON", e.toString());
            throw new RuntimeException(e);
        }

        //constructor:
        //metodo, endpoint, JSON, resultado, error
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                endPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String message = jsonObject.getString("message");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

        requestQueue.add(jsonObjectRequest);
    }

    private void validarAccion(String accion){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("weie");

        builder.setMessage("¿seguro de " + accion +"?");
        builder.setPositiveButton("chi",(a,b) ->{
            if (accion.equalsIgnoreCase("eliminar")) this.eliminarAlumno();
            if (accion.equalsIgnoreCase("actualizar")) this.actualizarAlumno();
        });
        builder.setNegativeButton("no", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscador);

        this.loadUI();

        //eventos -> metodo
        btnBuscarB.setOnClickListener(v -> {this.buscarAlumno();});
        btnActualizarB.setOnClickListener(v -> {this.validarAccion("actualizar");});
        btnEliminarB.setOnClickListener(v -> {this.validarAccion("eliminar");});
        btnReiniciarB.setOnClickListener(v -> {this.resetUI();});
    }
}