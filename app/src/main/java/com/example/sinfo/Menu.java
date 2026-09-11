package com.example.sinfo;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu extends AppCompatActivity {

    Button btnListaSimple, btnListaDetallada, btnRegistrar, btnBuscador;

    private void loadUI(){
        btnListaSimple = findViewById(R.id.btnListaSimple);
        btnListaDetallada = findViewById(R.id.btnListaDetallada);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBuscador = findViewById(R.id.btnBuscador);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        this.loadUI();
    }
}