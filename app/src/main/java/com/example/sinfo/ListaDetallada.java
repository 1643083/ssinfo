package com.example.sinfo;

import android.os.Bundle;
import android.util.Log;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaDetallada extends AppCompatActivity {

    //contenedor temporal
    ArrayList<String> listaAlumnos = new ArrayList<>();
    //adaptador (transferir informacion obtenida del WS > lista > RV
    ArrayList<Alumno> lstAlumnos = new ArrayList<>();
    AdapterDatos adapterDatos;
    //view donde se renderizara
    RecyclerView RVAlumnos;
    //canal de comunicacion
    RequestQueue requestQueue;
    //endpoint del WS
    private final String URL = "http://192.168.101.66:3000/alumnos";

    private void loadUI(){ RVAlumnos = findViewById(R.id.RVAlumnos); }

    //acceso al WS
    private void obtenerDatosWS(){
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        //Log.i("Datos_obtenidos", jsonArray.toString());
                        String apellidos, nombres, telefono, direccion;
                        for (int i = 0; i < jsonArray.length(); i++){
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                apellidos = jsonObject.getString("apellidos");
                                nombres = jsonObject.getString("nombres");
                                direccion = jsonObject.getString("direccion");
                                telefono = jsonObject.getString("telefono");

                                lstAlumnos.add(new Alumno(apellidos, nombres, direccion, telefono));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            adapterDatos.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("error", volleyError.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_detallada);

        this.loadUI();


        //configuraciones generales
        RVAlumnos.setLayoutManager(new LinearLayoutManager(this));
        adapterDatos = new AdapterDatos(lstAlumnos);
        RVAlumnos.setAdapter(adapterDatos);


        this.obtenerDatosWS(); //comunica ws > jsonarray > recorrer con for > arraylist
    }
}

