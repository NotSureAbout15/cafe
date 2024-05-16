package com.example.cafefront.MesasRecyclerView;

public class MesasData {
    private String nombre;
    private String tipo;
    private Float precio;
    private boolean isChecked;

    //meto cada uno de los parámetros en una variable
    public MesasData(String nombre, String tipo, Float precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        //inicializo el isChecked a falso
        this.isChecked = false;

    }

    //obtener el valor de isChecked
    public boolean isChecked() {
        return isChecked;
    }

    //establecer el valor de isChecked
    public void setChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    //creo el get para los datos q quiero q se muestren
    public String getNombre() {
        return nombre;
    }

    public Float getPrecio() {
        return precio;
    }
}
