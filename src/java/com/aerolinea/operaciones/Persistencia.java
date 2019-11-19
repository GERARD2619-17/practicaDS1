
package com.aerolinea.operaciones;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persistencia extends ManejadorSentencias{

    Connection conexion;

    public Persistencia(Object entity, Connection cn) throws Exception {
        super(entity);
        this.conexion = cn;
    }

    public final <T> T insertar(T entity) throws Exception {
        try {
            List<Object> valores = super.getValorCampos();
            PreparedStatement stmt = this.conexion.prepareStatement(super.getInsert(), Statement.RETURN_GENERATED_KEYS);
            for (int i = 1; i <= valores.size(); i++) {
                stmt.setObject(i, valores.get(i - 1));
            }
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("Creating file failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Object pk = generatedKeys.getObject(1);
                    return getRegistro(pk, entity.getClass());
                } else {
                    return getRegistro(getPrimaryKeyValue(), entity.getClass());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public final <T> T actualizar(Object id, T entity) throws Exception {
        try {
            List<Object> valores = super.getValorCampos();
            PreparedStatement stmt = this.conexion.prepareStatement(super.getUpdate(), Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < valores.size(); i++) {
                stmt.setObject(i + 1, valores.get(i));
            }
            stmt.setObject(valores.size() + 1, id);
            stmt.executeUpdate();
            return getRegistro(id, entity.getClass());
        } catch (Exception ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public final <T> T getRegistro(Object pk, Class tipo) throws Exception {
        T entity = (T) tipo.newInstance();
        try {
            PreparedStatement stmt = this.conexion.prepareStatement(super.getSelect());
            stmt.setObject(1, pk);
            ResultSet rs = stmt.executeQuery();
            Field[] fs = entity.getClass().getDeclaredFields();
            while (rs.next()) {
                for (int i = 0; i < super.getCampos().size(); i++) {
                    Object valor = rs.getObject(super.getCampos().get(i).getNombre());
                    if (fs[i].getModifiers() != Modifier.PRIVATE) {
                        fs[i].set(entity, valor);
                    } else {
                        Field field = entity.getClass().getDeclaredField(fs[i].getName());
                        field.setAccessible(true);
                        System.out.println(valor);
                        if (valor instanceof Long) {
                            valor = Integer.parseInt(valor.toString());
                        }
                        field.set(entity, valor);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return entity;
    }

    public final <T> T eliminar(Object id, Class entity) throws Exception {
        try {
            Object result = getRegistro(id, entity);
            PreparedStatement stmt = this.conexion.prepareStatement(super.getDelete());
            stmt.setObject(1, id);
            stmt.executeUpdate();
            return (T) result;
        } catch (Exception ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public final <T> ArrayList<T> getTodos(Class entity) throws Exception {
        ArrayList<T> rows = new ArrayList<>();
        try {
            PreparedStatement stmt = this.conexion.prepareStatement(super.getSelectAll());
            ResultSet rs = stmt.executeQuery();
            Field[] fs = entity.getDeclaredFields();
            while (rs.next()) {
                Object obj = entity.newInstance();
                for (int i = 0; i < super.getCampos().size(); i++) {
                    Object valor = rs.getObject(super.getCampos().get(i).getNombre());
                    if (fs[i].getModifiers() != Modifier.PRIVATE) {
                        fs[i].set(obj, valor);
                    } else {
                        Field field = entity.getDeclaredField(fs[i].getName());
                        field.setAccessible(true);
                        if (valor instanceof Long) {
                            valor = Integer.parseInt(valor.toString());
                        }
                        field.set(obj, valor);
                    }
                }
                rows.add((T) obj);
            }
            return rows;
        } catch (Exception ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}

