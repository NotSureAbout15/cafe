package com.example.cafefront.PedidoRecyclerView.Trabajador;

public class TrabajadorData {
    private String nombre;
    private Float precio;
    private int cantidad;

    public TrabajadorData(String nombre, Float precio, Integer cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad; // la cantidad por defecto es 1
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
