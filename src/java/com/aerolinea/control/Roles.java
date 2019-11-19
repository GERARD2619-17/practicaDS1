package com.aerolinea.control;

import com.aerolinea.entidad.Rol;
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

@WebServlet(name = "Roles", urlPatterns = {"/Roles"})
public class Roles extends HttpServlet {

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
                    sql = "select * from rol where rol like ?";
                } else {
                    sql = "select * from rol";
                }
                String[][] roles = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda").toString() + "%");
                    roles = Operaciones.consultar(sql, params);
                } else {
                    roles = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{"ID Rol", "Nombre Rol"};//variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(roles, //array quecontiene los datos
                        "50%", //ancho de la tabla px | % 
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras);
                //array con las cabeceras de la tabla
                //boton eliminar
                tab.setEliminable(true);//boton actualizar
                tab.setModificable(true); //url del proyecto
                tab.setPageContext(request.getContextPath());//pagina encargada de eliminar
                tab.setPaginaEliminable("/Roles?accion=eliminar");//pagina encargada de actualizacion
                tab.setPaginaModificable("/Roles?accion=modificar");//pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Roles?accion=modificar");//icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png"); //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});//pie de tabla
                tab.setPie("Resultado Roles");
                //imprime la tabla enpantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("roles/roles_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if (accion.equals("insertar")) {
            request.getRequestDispatcher("roles/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Rol r = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Rol());
                request.setAttribute("rol", r);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("roles/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Rol r = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Rol());
                if (r.getIdrol() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Roles");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idRol = request.getParameter("txtIdrol");
                String rol = request.getParameter("txtRol");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idRol != null && !idRol.equals("")) {
                        Rol r = new Rol(Integer.parseInt(idRol), rol);
                        r = Operaciones.actualizar(r.getIdrol(), r);
                        if (r.getIdrol() != 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Rol r = new Rol();
                        r.setRol(rol);
                        r = Operaciones.insertar(r);
                        if (r.getIdrol() != 0) {
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
                        Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Roles");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
