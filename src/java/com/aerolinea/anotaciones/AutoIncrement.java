package com.aerolinea.anotaciones;

//<editor-fold defaultstate="collapsed" desc="Imports.">
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//</editor-fold>

/**
 * Anotación para manejar campos con valores por defecto AutoIncrement o Identity.
 * @author OMAR
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoIncrement {
    
}
