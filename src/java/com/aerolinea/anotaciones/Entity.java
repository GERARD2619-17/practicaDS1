package com.aerolinea.anotaciones;

//<editor-fold defaultstate="collapsed" desc="Imports.">
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//</editor-fold>

/**
 * Anotación para declarar una clase como Entidad (Entity).
 * @author OMAR
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

    /**
     * Método para definir el nombre real de la tabla que representa.
     * @return Nombre real de la tabla que representa.
     */
    String table () default "";
}
