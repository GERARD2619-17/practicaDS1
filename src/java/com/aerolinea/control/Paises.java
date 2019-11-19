package com.aerolinea.control;

import com.aerolinea.entidad.Pais;
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

@WebServlet(name = "Paises", urlPatterns = {"/Paises"})
public class Paises extends HttpServlet {

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
                    sql = "select * from pais where pais like ?";
                } else {
                    sql = "select * from pais";
                }
                String[][] paises = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda").toString() + "%");
                    paises = Operaciones.consultar(sql, params);
                } else {
                    paises = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{"ID País", "Nombre País"};//variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(paises, //array quecontiene los datos
                        "50%", //ancho de la tabla px | % 
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras);
                //array con las cabeceras de la tabla
                //boton eliminar
                tab.setEliminable(true);//boton actualizar
                tab.setModificable(true); //url del proyecto
                tab.setPageContext(request.getContextPath());//pagina encargada de eliminar
                tab.setPaginaEliminable("/Paises?accion=eliminar");//pagina encargada de actualizacion
                tab.setPaginaModificable("/Paises?accion=modificar");//pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Paises?accion=modificar");//icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png"); //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});//pie de tabla
                tab.setPie("Resultado paises");
                //imprime la tabla enpantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if (accion.equals("insertar")) {
            request.getRequestDispatcher("paises/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Pais p = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Pais());
                request.setAttribute("pais", p);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("paises/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Pais p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Pais());
                if (p.getIdpais() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Paises");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idPais = request.getParameter("txtIdpais");
                String pais = request.getParameter("txtPais");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idPais != null && !idPais.equals("")) {
                        Pais p = new Pais(Integer.parseInt(idPais), pais);
                        p = Operaciones.actualizar(p.getIdpais(), p);
                        if (p.getIdpais() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Pais p = new Pais();
                        p.setPais(pais);
                        p = Operaciones.insertar(p);
                        if (p.getIdpais() != 0) {
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
                        Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Paises");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
