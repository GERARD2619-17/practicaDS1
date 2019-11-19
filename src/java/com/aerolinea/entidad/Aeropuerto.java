package com.aerolinea.entidad;

import com.aerolinea.anotaciones.AutoIncrement;
import com.aerolinea.anotaciones.Entity;
import com.aerolinea.anotaciones.FieldName;
import com.aerolinea.anotaciones.NotNull;
import com.aerolinea.anotaciones.PrimaryKey;


@Entity(table = "aeropuerto")
public class Aeropuerto {
    @PrimaryKey
    @AutoIncrement
    @FieldName(name = "idaeropuerto")
    private int idaeropuerto;
    @NotNull
    private String aeropuerto;
    @NotNull
    private int idpais;
    @NotNull
    private String ciudad;
    
    public Aeropuerto() {
    }

    public Aeropuerto(int idaeropuerto, String aeropuerto, int idpais, String ciudad) {
        this.idaeropuerto = idaeropuerto;
        this.aeropuerto = aeropuerto;
        this.idpais = idpais;
        this.ciudad = ciudad;
    }

    public int getIdaeropuerto() {
        return idaeropuerto;
    }

    public void setIdaeropuerto(int idaeropuerto) {
        this.idaeropuerto = idaeropuerto;
    }

    public String getAeropuerto() {
        return aeropuerto;
    }

    public void setAeropuerto(String aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    

    

}
