package com.example.sinfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {
    ArrayList<String> listDatos;
    ArrayList<Alumno> listAlumnos;
    public AdapterDatos(ArrayList<Alumno> listaEntrada) {
        this.listAlumnos = listaEntrada;
    }
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //indicando al RV cual es el xml que servira como plantilla
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderDatos(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        //
        holder.asignarDatos(listDatos.get(position));
    }
    @Override
    public int getItemCount() {
        return listDatos.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        //estos textview forman parte de la plantilla (item_list.xml)
        TextView txtApeNom, txtDireccion, txtTelefono; // view xml
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            //de aqui en adelante hacemos un proceso similar al metodo loadUI()
            txtApeNom = itemView.findViewById(R.id.txtApeNom);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
        }
        public void asignarDatos(String dato) {
            txtApeNom.setText(dato);
        }
    }
}
