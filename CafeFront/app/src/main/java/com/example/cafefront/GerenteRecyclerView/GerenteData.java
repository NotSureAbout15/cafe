package com.example.cafefront.GerenteRecyclerView;

public class GerenteData {
    private String nombre;
    private String email;
    private int telefono;
    private String turno;
    private String rol;

    public GerenteData(String nombre, String email, int telefono, String turno, String rol){
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.turno = turno;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getRol() {
        if (!rol.equalsIgnoreCase("gerente") && !rol.equalsIgnoreCase("trabajador")) {
            rol = "No hay rol asignado";
        }

        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
