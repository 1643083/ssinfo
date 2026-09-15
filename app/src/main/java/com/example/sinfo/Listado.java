package com.example.sinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    ListView lstAlumnos;

    RequestQueue requestQueue;

    private final String URL = "http://localhost:3000/alumnos";

    private void loadUI(){
        lstAlumnos = findViewById(R.id.lstAlumnos);
    }

    private void obtenerDatosWS(){
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        renderizarListView(jsonArray);
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

    private void renderizarListView(JSONArray jsonArray){

        try {
            ArrayAdapter adapter;
            ArrayList<String> listaAlumnos = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listaAlumnos.add(jsonObject.getString("apellidos") + ", " + jsonObject.getString("nombres"));
            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAlumnos);
            lstAlumnos.setAdapter(adapter);
        } catch (Exception e){
            Log.e("error", e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

        loadUI();
        obtenerDatosWS();
    }
}