/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolinea.operaciones;

import com.aerolinea.conexion.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JOSE
 */
public class Operaciones {

    private static Conexion conexion;

    public static void abrirConexion(Conexion conexion) {
        Operaciones.conexion = conexion;
    }

    public static void iniciarTransaccion() throws SQLException, Exception {
        conexion.getConexion().setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        conexion.getConexion().commit();
    }

    public static void rollback() throws SQLException {
        conexion.getConexion().rollback();
    }

    public static void cerrarConexion() throws SQLException {
        if (!conexion.getConexion().isClosed()) {
            conexion.getConexion().close();
        }
    }

    public static <T> T insertar(T entity) throws SQLException, Exception {
        try {
            Persistencia accion = new Persistencia(entity, conexion.getConexion());
            return accion.insertar(entity);
        } catch (Exception ex) {
            conexion.getConexion().rollback();
            throw ex;
        }
    }

    public static <T> T get(Object id, T entity) throws SQLException, Exception {
        try {
            Persistencia accion = new Persistencia(entity, conexion.getConexion());
            return accion.getRegistro(id, entity.getClass());
        } catch (Exception ex) {
            conexion.getConexion().rollback();
            throw ex;
        }
    }

    public static <T> T actualizar(Object id, T entity) throws SQLException, Exception {
        try {
            Persistencia accion = new Persistencia(entity, conexion.getConexion());
            return accion.actualizar(id, entity);
        } catch (Exception ex) {
            conexion.getConexion().rollback();
            throw ex;
        }
    }

    public static <T> T eliminar(Object id, T entity) throws SQLException, Exception {
        try {
            Persistencia accion = new Persistencia(entity, conexion.getConexion());
            return accion.eliminar(id, entity.getClass());
        } catch (Exception ex) {
            conexion.getConexion().rollback();
            throw ex;
        }
    }

    public static <T> ArrayList<T> getTodos(T entity) throws Exception {
        try {
            Persistencia accion = new Persistencia(entity, conexion.getConexion());
            return accion.getTodos(entity.getClass());
        } catch (Exception ex) {
            conexion.getConexion().rollback();
            throw ex;
        }
    }

    public static String[][] consultar(String sqlQuery, List<Object> params) throws Exception {
        String[][] resultados = null;
        try {
            PreparedStatement stmt = conexion.getConexion().prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if (params != null && params.size() > 0) {
                List<Object> cleanParams = params;
                for (int i = 1; i <= params.size(); i++) {
                    Object valor = cleanParams.get(i - 1);
                    if (valor == null) {
                        stmt.setNull(i, 0);
                    } else if (valor instanceof Integer) {
                        stmt.setInt(i, (int) valor);
                    } else if (valor instanceof Double) {
                        stmt.setDouble(i, (double) valor);
                    } else if (valor instanceof BigDecimal) {
                        stmt.setBigDecimal(i, (BigDecimal) valor);
                    } else if (valor instanceof Float) {
                        stmt.setFloat(i, (float) valor);
                    } else if (valor instanceof Long) {
                        stmt.setLong(i, (long) valor);
                    } else if (valor instanceof Boolean) {
                        stmt.setBoolean(i, (boolean) valor);
                    } else if (valor instanceof Byte) {
                        stmt.setByte(i, (byte) valor);
                    } else if (valor instanceof Short) {
                        stmt.setShort(i, (short) valor);
                    } else if (valor instanceof String) {
                        stmt.setString(i, valor.toString());
                    } else if (valor instanceof Date) {
                        stmt.setDate(i, (Date) valor);
                    } else if (valor instanceof Time) {
                        stmt.setTime(i, (Time) valor);
                    } else if (valor instanceof Timestamp) {
                        stmt.setTimestamp(i, (Timestamp) valor);
                    }
                }
            }
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                rs.last();
                ResultSetMetaData rsmd = rs.getMetaData();
                int numCols = rsmd.getColumnCount();
                int numFils = rs.getRow();
                resultados = new String[numCols][numFils];
                int j = 0;
                rs.beforeFirst();
                while (rs.next()) {
                    for (int i = 0; i < numCols; i++) {
                        resultados[i][j] = rs.getString(i + 1);
                    }
                    j++;
                }
                return resultados;
            }
            
            return resultados;
        } catch (Exception ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
