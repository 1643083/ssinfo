package com.example.sinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    Button btnListaSimple, btnListaDetallada, btnRegistrar, btnBuscador;

    private void loadUI(){
        btnListaSimple = findViewById(R.id.btnListaSimple);
        btnListaDetallada = findViewById(R.id.btnListaDetallada);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBuscador = findViewById(R.id.btnBuscador);
    }

    private void loadEvents(){
        btnListaSimple.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, Listado.class));
        });

        btnListaDetallada.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, ListaDetallada.class));
        });

        btnRegistrar.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, Registro.class));
        });

        btnBuscador.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, Buscador.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        this.loadUI();
        this.loadEvents();
    }
}