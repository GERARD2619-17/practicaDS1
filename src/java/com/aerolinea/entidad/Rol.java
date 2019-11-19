package com.aerolinea.entidad;

import com.aerolinea.anotaciones.AutoIncrement;
import com.aerolinea.anotaciones.Entity;
import com.aerolinea.anotaciones.FieldName;
import com.aerolinea.anotaciones.NotNull;
import com.aerolinea.anotaciones.PrimaryKey;


@Entity(table = "rol")
public class Rol {
    @PrimaryKey
    @AutoIncrement
    @FieldName(name = "idrol")
    private int idrol;
    @NotNull
    private String rol;

    public Rol() {
    }

    public Rol(int idrol, String rol) {
        this.idrol = idrol;
        this.rol = rol;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    

}
