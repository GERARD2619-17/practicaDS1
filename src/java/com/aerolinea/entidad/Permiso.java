
package com.aerolinea.entidad;
import com.aerolinea.anotaciones.AutoIncrement;
import com.aerolinea.anotaciones.Entity;
import com.aerolinea.anotaciones.FieldName;
import com.aerolinea.anotaciones.NotNull;
import com.aerolinea.anotaciones.PrimaryKey;


@Entity(table = "permiso")
public class Permiso {
    @PrimaryKey
    @AutoIncrement
    @FieldName(name = "idpermiso")
    private int idpermiso;
    @NotNull
    private int idmenu;
    @NotNull
    private int idrol;
}
