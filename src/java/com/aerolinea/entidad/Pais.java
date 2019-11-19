package com.aerolinea.entidad;

import com.aerolinea.anotaciones.AutoIncrement;
import com.aerolinea.anotaciones.Entity;
import com.aerolinea.anotaciones.FieldName;
import com.aerolinea.anotaciones.NotNull;
import com.aerolinea.anotaciones.PrimaryKey;


@Entity(table = "pais")
public class Pais {
    @PrimaryKey
    @AutoIncrement
    @FieldName(name = "idpais")
    private int idpais;
    @NotNull
    private String pais;

    public Pais() {
    }

    public Pais(int idpais, String pais) {
        this.idpais = idpais;
        this.pais = pais;
    }

    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
};