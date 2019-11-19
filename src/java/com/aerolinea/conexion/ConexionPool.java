package com.aerolinea.conexion;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConexionPool implements Conexion {

    private Connection conn;

    public ConexionPool() {
        conn = null;
    }

    @Override
    public synchronized void conectar() {
        try {
            Context initCtx;
            initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("jdbc/aerolinea");
            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            System.out.println("db: " + e.getMessage());
        }
    }

    @Override
    public Connection getConexion() {
        return conn;
    }

    @Override
    public synchronized void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
