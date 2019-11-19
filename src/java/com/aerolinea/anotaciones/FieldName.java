package com.aerolinea.anotaciones;

//<editor-fold defaultstate="collapsed" desc="Imports.">
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//</editor-fold>

/**
 * Anotación utilizada para definir el nombre real de un campo de la tabla.
 * <b>Nota:</b> Se utiliza cuando la variable tiene un nombre diferente al campo de la tabla que representa.
 * @author OMAR
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldName {

    /**
     * Método que representa el nombre de la variable.
     * @return Nombre de la variable.
     */
    String name ();
}
