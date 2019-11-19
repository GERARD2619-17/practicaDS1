package com.aerolinea.entidad;

import com.aerolinea.anotaciones.AutoIncrement;
import com.aerolinea.anotaciones.Entity;
import com.aerolinea.anotaciones.FieldName;
import com.aerolinea.anotaciones.NotNull;
import com.aerolinea.anotaciones.PrimaryKey;
import java.sql.Date;


@Entity(table = "reservacion")
public class Reservacion {
    @PrimaryKey
    @AutoIncrement
    @FieldName(name = "idreservacion")
    private int idreservacion;
    @NotNull
    private String idusuario;
    @NotNull
    private int idvuelo;
    @NotNull
    private int nboletos;
    @NotNull
    private double precio;
    @NotNull
    private Date fecha;
    
    public Reservacion() {
    }

    public Reservacion(int idreservacion, String idusuario, int idvuelo, int nboletos, double precio, Date fecha) {
        this.idreservacion = idreservacion;
        this.idusuario = idusuario;
        this.idvuelo = idvuelo;
        this.nboletos = nboletos;
        this.precio = precio;
        this.fecha = fecha;
    }

    public int getIdreservacion() {
        return idreservacion;
    }

    public void setIdreservacion(int idreservacion) {
        this.idreservacion = idreservacion;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdvuelo() {
        return idvuelo;
    }

    public void setIdvuelo(int idvuelo) {
        this.idvuelo = idvuelo;
    }

    public int getNboletos() {
        return nboletos;
    }

    public void setNboletos(int nboletos) {
        this.nboletos = nboletos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    

    

    

}
