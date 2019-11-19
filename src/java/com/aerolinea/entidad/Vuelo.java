package com.aerolinea.entidad;

import com.aerolinea.anotaciones.AutoIncrement;
import com.aerolinea.anotaciones.Entity;
import com.aerolinea.anotaciones.FieldName;
import com.aerolinea.anotaciones.NotNull;
import com.aerolinea.anotaciones.PrimaryKey;
import java.sql.Date;

@Entity(table = "vuelo")
public class Vuelo {
    @PrimaryKey
    @AutoIncrement
    @FieldName(name = "idvuelo")
    private int idvuelo;
    @NotNull
    private int idorigen;
    @NotNull
    private int iddestino;
    @NotNull
    private double precio;
    @NotNull
    private String estado;
    @NotNull
    private int idavion;
    
    public Vuelo() {
    }

    public Vuelo(int idvuelo, int idorigen, int iddestino, double precio, String estado, int idavion) {
        this.idvuelo = idvuelo;
        this.idorigen = idorigen;
        this.iddestino = iddestino;
        this.precio = precio;
        this.estado = estado;
        this.idavion = idavion;
    }

    public int getIdvuelo() {
        return idvuelo;
    }

    public void setIdvuelo(int idvuelo) {
        this.idvuelo = idvuelo;
    }

    public int getIdorigen() {
        return idorigen;
    }

    public void setIdorigen(int idorigen) {
        this.idorigen = idorigen;
    }

    public int getIddestino() {
        return iddestino;
    }

    public void setIddestino(int iddestino) {
        this.iddestino = iddestino;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdavion() {
        return idavion;
    }

    public void setIdavion(int idavion) {
        this.idavion = idavion;
    }
    
    

    
};