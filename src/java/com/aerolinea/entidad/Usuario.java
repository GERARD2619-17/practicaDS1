package com.aerolinea.entidad;

import com.aerolinea.anotaciones.Entity;
import com.aerolinea.anotaciones.PrimaryKey;

@Entity(table = "Usuario")
public class Usuario {
    @PrimaryKey
    private String idusuario;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String clave;
    private int idpais;
    private int idrol;

    public Usuario() {
    }

    public Usuario(String idusuario, String nombres, String apellidos, String email, String telefono, String clave, int idpais, int idrol) {
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.clave = clave;
        this.idpais = idpais;
        this.idrol = idrol;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }
    
}
