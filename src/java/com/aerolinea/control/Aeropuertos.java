package com.aerolinea.control;

import com.aerolinea.entidad.Aeropuerto;
import com.aerolinea.conexion.Conexion;
import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.entidad.Menu;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Aeropuertos", urlPatterns = {"/Aeropuertos"})
public class Aeropuertos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession s = request.getSession();
//        List<Menu> per = (List<Menu>)s.getAttribute("Permisos");
//        List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre()==0).collect(Collectors.toList());
//        request.setAttribute("MenuPrincipal", MenuPrincipal);
//        String op = request.getParameter("op");
//        if (op!=null){
//      List<Menu> PermisosAsignados = per.stream().filter(field -> field.getIdpadre()==Integer.parseInt(op)).collect(Collectors.toList());//            request.setAttribute("PermisosAsignados", PermisosAsignados);
//        }
        String accion = request.getParameter("accion");
        if (accion == null) {
            if (request.getSession().getAttribute("resultado") != null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "";
                if (request.getParameter("txtBusqueda") != null) {
                    sql = "select * from aeropuerto where aeropuerto like ?";
                } else {
                    sql = "select * from aeropuerto";
                }
                String[][] aeropuertos = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda").toString() + "%");
                    aeropuertos = Operaciones.consultar(sql, params);
                } else {
                    aeropuertos = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{"ID Aeropuertos", "Nombre Aeropuertos","ID Pais","Ciudad"};//variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(aeropuertos, //array quecontiene los datos
                        "50%", //ancho de la tabla px | % 
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras);
                //array con las cabeceras de la tabla 
                //boton eliminar
                tab.setEliminable(true);//boton actualizar
                tab.setModificable(true); //url del proyecto
                tab.setPageContext(request.getContextPath());//pagina encargada de eliminar
                tab.setPaginaEliminable("/Aeropuertos?accion=eliminar");//pagina encargada de actualizacion
                tab.setPaginaModificable("/Aeropuertos?accion=modificar");//pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Aeropuertos?accion=modificar");//icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png"); //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});//pie de tabla
                tab.setPie("Resultado Aeropuertos");
                //imprime la tabla enpantalla
                String tabla01 = tab.getTabla(); 
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("aeropuertos/aeropuertos_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Aeropuertos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Aeropuertos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if (accion.equals("insertar")) {
            request.getRequestDispatcher("aeropuertos/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Aeropuerto ae = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Aeropuerto());
                request.setAttribute("aeropuerto", ae);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Aeropuertos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("aeropuertos/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Aeropuerto ae = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Aeropuerto());
                if (ae.getIdaeropuerto() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Aeropuertos.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Aeropuertos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Aeropuertos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idAeropuerto = request.getParameter("txtIdaeropuerto");
                String aeropuerto = request.getParameter("txtAeropuerto");
                String idPais = request.getParameter("txtIdpais");
                String ciudad = request.getParameter("txtCiudad");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idAeropuerto != null && !idAeropuerto.equals("")) {
                        Aeropuerto ae = new Aeropuerto(Integer.parseInt(idAeropuerto),aeropuerto,Integer.parseInt(idPais),ciudad);
                        ae = Operaciones.actualizar(ae.getIdaeropuerto(), ae);
                        if (ae.getIdaeropuerto() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Aeropuerto ae = new Aeropuerto();
                        ae.setAeropuerto(aeropuerto);
                        ae.setIdpais(Integer.parseInt(idPais));
                        ae.setCiudad(ciudad);
                        ae = Operaciones.insertar(ae);
                        if (ae.getIdaeropuerto() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Aeropuertos.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Aeropuertos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Aeropuertos");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
