package com.example.sinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<Alumno> listAlumnos;

    public AdapterDatos(ArrayList<Alumno> listaEntrada) {
        this.listAlumnos = listaEntrada;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listAlumnos.get(position));
    }

    @Override
    public int getItemCount() {
        return listAlumnos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView txtApeNom, txtDireccion, txtTelefono;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtApeNom = itemView.findViewById(R.id.txtApeNom);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
        }

        public void asignarDatos(Alumno alumno) {
            txtApeNom.setText(alumno.getApellidos() + ", " + alumno.getNombres());
            txtDireccion.setText(alumno.getDireccion());
            txtTelefono.setText(alumno.getTelefono());
        }
    }
}