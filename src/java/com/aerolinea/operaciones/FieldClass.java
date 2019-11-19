
package com.aerolinea.operaciones;

public class FieldClass {

    private String Nombre;
    private Object valor;
    private boolean primary;
    private boolean autoincrement;
    private boolean notnull;
    private Class tipo;

    public Class getTipo() {
        return tipo;
    }

    public void setTipo(Class tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isAutoincrement() {
        return autoincrement;
    }

    public void setAutoincrement(boolean autoincrement) {
        this.autoincrement = autoincrement;
    }

    public boolean isNotnull() {
        return notnull;
    }

    public void setNotnull(boolean notnull) {
        this.notnull = notnull;
    }
}
