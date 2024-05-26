package com.example.cafefront.MesasRecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafefront.R;

import java.util.ArrayList;
import java.util.List;

public class MesasAdapter extends RecyclerView.Adapter<MesasViewHolder> {
    // Declaro las variables de instancia
    private List<MesasData> pedido;
    private Activity activity;

    // Constructor
    public MesasAdapter(List<MesasData> pedido, Activity activity){
        this.pedido = pedido;
        this.activity = activity;
    }

    // Se llama al crear un nuevo ViewHolder
    @NonNull
    @Override
    public MesasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_mesas_view_holder, parent, false);
        return new MesasViewHolder(view);
    }

    // Se llama para vincular los datos con el ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MesasViewHolder holder, int position) {
        // Obtenemos los datos que queremos ver
        MesasData dataToBeRendered = pedido.get(position);
        // Llamamos al método showData del ViewHolder para mostrar los datos
        holder.showData(dataToBeRendered, activity);
        // Establecemos el estado del CheckBox según el valor de isChecked
        holder.checkBox.setChecked(pedido.get(holder.getAdapterPosition()).isChecked());
        // Establecemos un escuchador de cambios para el CheckBox
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Si el estado del CheckBox cambia, actualizamos su valor
                pedido.get(holder.getAdapterPosition()).setChecked(isChecked);
            }
        });
    }

    // Devuelve el número de elementos de la lista
    @Override
    public int getItemCount() {
        return pedido.size();
    }

    // Método que retorna el pedido seleccionado
    public List<MesasData> getPedido() {
        List<MesasData> selectedPedido = new ArrayList<>();
        for (MesasData item : pedido) {
            if (item.isChecked()) {
                selectedPedido.add(item);
            }
        }
        return selectedPedido;
    }
}
