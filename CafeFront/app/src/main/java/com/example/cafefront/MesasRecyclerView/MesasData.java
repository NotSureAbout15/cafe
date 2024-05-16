package com.example.cafefront.MesasRecyclerView;

import android.os.Parcel;
import android.os.Parcelable;

public class MesasData implements Parcelable {
    private String nombre;
    private String tipo;
    private Float precio;
    private boolean isChecked;

    // Constructor
    public MesasData(String nombre, String tipo, Float precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.isChecked = false;
    }

    // Métodos de Parcelable
    protected MesasData(Parcel in) {
        nombre = in.readString();
        tipo = in.readString();
        if (in.readByte() == 0) {
            precio = null;
        } else {
            precio = in.readFloat();
        }
        isChecked = in.readByte() != 0;
    }

    public static final Creator<MesasData> CREATOR = new Creator<MesasData>() {
        @Override
        public MesasData createFromParcel(Parcel in) {
            return new MesasData(in);
        }

        @Override
        public MesasData[] newArray(int size) {
            return new MesasData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(tipo);
        if (precio == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(precio);
        }
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}