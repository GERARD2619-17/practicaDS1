package com.aerolinea.anotaciones;

//<editor-fold defaultstate="collapsed" desc="Imports.">
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//</editor-fold>


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
    
}
