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
    //declaro las varibles de instancia
    private List<MesasData> pedido;
    private Activity activity;

    //constructor
    public MesasAdapter(List<MesasData> pedido, Activity activity){
        this.pedido = pedido;
        this.activity = activity;
    }

    //se llama al crear un nuevo viewHolder
    @NonNull
    @Override
    public MesasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_mesas_view_holder, parent, false);
        return new MesasViewHolder(view);
    }

    //se llama para vincular los datos con el viewHolder
    @Override
    public void onBindViewHolder(@NonNull MesasViewHolder holder, int position) {
        //obtenemos los datos q queremos ver
        MesasData dataToBeRendered = pedido.get(position);
        //llamamos al metodo showData del viewHolder para mostrar los datos
        holder.showData(dataToBeRendered, activity);
        //establecemos el estado del checkBox segun el valor de isChecked
        holder.checkBox.setChecked(pedido.get(holder.getAdapterPosition()).isChecked());
        //establecemos un escuchador de cambios para el checkedBox
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //si el estado del checkedBox cambia, actualizamos su valor
                pedido.get(holder.getAdapterPosition()).setChecked(isChecked);
            }
        });
    }

    //devuelve el numero de elementos de la lista
    @Override
    public int getItemCount() {
        return pedido.size();
    }

    //metodo que retorna el pedido seleccionado
    public List<MesasData> getPedido() {
        //creamos una lista para seleccionar el pedido
        List<MesasData> pedidoSeleccionado = new ArrayList<>();
        //recorremos la lista
        for (MesasData item : pedido) {
            //si el item esta seleccionado lo añadimos a la lista
            if (item.isChecked()){
                pedidoSeleccionado.add(item);
            }
        }
        //devolvemos la lista de items seleccionados
        return pedidoSeleccionado;
    }
}
