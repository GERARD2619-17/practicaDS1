/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolinea.operaciones;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JOSE
 */
public class ManejadorSentencias {

    private final List<FieldClass> campos;
    private final List<String> nombreCampos;
    private final List<Object> valorCampos;
    private final AuxiliarPersistencia aux;
    private final List<String> paramString;
    private final Object entidad;

    public ManejadorSentencias(Object entidad) throws Exception {
        this.entidad = entidad;
        this.aux = new AuxiliarPersistencia();
        campos = aux.getCampos(entidad);
        nombreCampos = new ArrayList();
        valorCampos = new ArrayList();
        paramString = new ArrayList();
    }

    public List<FieldClass> getCampos() {
        return campos;
    }

    public List<Object> getValorCampos() {
        for (FieldClass campo : campos) {
            if (!campo.isAutoincrement()) {
                valorCampos.add(campo.getValor());
            }
        }
        return valorCampos;
    }

    public List<String> getNombreCampos() {
        for (FieldClass campo : campos) {
            nombreCampos.add(campo.getNombre());
        }
        return nombreCampos;
    }

    private String getListaCampos() {
        for (FieldClass campo : campos) {
            if (!campo.isAutoincrement()) {
                nombreCampos.add(campo.getNombre());
            }
        }
        return String.join(",", nombreCampos);
    }

    private String getTodosCampos() {
        return String.join(",", getNombreCampos());
    }

    private String getListaParametros() {
        for (FieldClass campo : campos) {
            if (!campo.isAutoincrement()) {
                paramString.add("?");
            }
        }
        return String.join(",", paramString);
    }

    private String getPrimaryKey() {
        String pk = "";
        for (FieldClass campo : campos) 
            if (campo.isPrimary()) {
                pk = campo.getNombre();
                break;
            }
            return pk;
        }        
    public Object getPrimaryKeyValue() {
        Object pkvalue = "";
        for (FieldClass campo : campos) {
            if (campo.isPrimary()) {
                pkvalue = campo.getValor();
                break;
            }
        }
        return pkvalue;
    }

    public String getSelect() throws Exception {
        String sqlSelect = String.format("SELECT * FROM %s WHERE %s = ?", aux.getEntityName(entidad.getClass().newInstance()), getPrimaryKey());
        return sqlSelect;
    }

    public String getSelectAll() throws Exception {
        String sqlSelect = String.format("SELECT * FROM %s", aux.getEntityName(entidad.getClass().newInstance()));
        return sqlSelect;
    }

    public String getInsert() throws Exception {
        String sqlInsertar = String.format("INSERT INTO %s(%s) VALUES(%s)", aux.getEntityName(entidad), getListaCampos(), getListaParametros());
        return sqlInsertar;
    }

    public String getUpdate() throws Exception {
        String lista = "";
        for (FieldClass campo : campos) {
            if (!campo.isAutoincrement()) {
                lista += campo.getNombre() + "=?,";
            }
        }
        lista = lista.substring(0, lista.length() - 1);
        String sqlUpdate = String.format("UPDATE %s SET %s WHERE %s = ?", aux.getEntityName(entidad), lista, getPrimaryKey());
        return sqlUpdate;
    }

    public String getDelete() throws Exception {
        String sqlDelete = String.format("DELETE FROM %s WHERE %s = ?", aux.getEntityName(entidad.getClass().newInstance()), getPrimaryKey());
        return sqlDelete;
    }
}
