package com.example.cafefront.GerenteRecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.R;

import java.util.List;

public class GerenteAdapter extends RecyclerView.Adapter<GerenteViewHolder> {
    //declaro las variables de instancia
    private List<GerenteData> trabajadores;
    private Activity activity;

    //constructor
    public GerenteAdapter(List<GerenteData> trabajadores, Activity activity){
        this.trabajadores = trabajadores;
        this.activity = activity;
    }
    @NonNull
    @Override
    public GerenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_gerente_view_holder, parent, false);
        return new GerenteViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull GerenteViewHolder holder, int position) {
        GerenteData trabajador = trabajadores.get(position);
        holder.showData(trabajador, activity);
    }

    @Override
    public int getItemCount() {
        return trabajadores.size();
    }
}
