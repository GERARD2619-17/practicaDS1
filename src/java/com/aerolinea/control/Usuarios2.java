package com.aerolinea.control;

import com.aerolinea.entidad.Usuario;
import com.aerolinea.conexion.Conexion;
import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.entidad.Menu;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.utilerias.Tabla;
import java.io.IOException;
import static java.sql.JDBCType.NULL;
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

@WebServlet(name = "Usuarios2", urlPatterns = {"/Usuarios2"})
public class Usuarios2 extends HttpServlet {

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
                    sql = "select * from usuario where usuario like ?";
                } else {
                    sql = "select * from usuario";
                }
                String[][] usuarios = null;
                if (request.getParameter("txtBusqueda") != null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%" + request.getParameter("txtBusqueda").toString() + "%");
                    usuarios = Operaciones.consultar(sql, params);
                } else {
                    usuarios = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{"ID Usuario", "Nombres","Apellidos","Email","Telefono","Clave","ID Pais","ID Rol"};//variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(usuarios, //array quecontiene los datos
                        "50%", //ancho de la tabla px | % 
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras);
                //array con las cabeceras de la tabla
                //boton eliminar
                tab.setEliminable(true);//boton actualizar
                tab.setModificable(true); //url del proyecto
                tab.setPageContext(request.getContextPath());//pagina encargada de eliminar
                tab.setPaginaEliminable("/Usuarios2?accion=eliminar");//pagina encargada de actualizacion
                tab.setPaginaModificable("/Usuarios2?accion=modificar");//pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Usuarios2?accion=modificar");//icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png"); //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});//pie de tabla
                tab.setPie("Resultado usuarios");
                //imprime la tabla enpantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("usuarios/usuarios_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Usuarios2.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if (accion.equals("insertar")) {
            request.getRequestDispatcher("usuarios/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Usuario u = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Usuario());
                request.setAttribute("usuario", u);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Usuarios2.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("Usuarios2/insertar_modificar.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Usuario u = Operaciones.eliminar(request.getParameter("id"), new Usuario());
                if (u.getIdusuario() != null) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Usuarios2.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Usuarios2");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String idUsuario = request.getParameter("txtIdpais");
                String clave = request.getParameter("txtClave");
                String nom = request.getParameter("txtNom");
                String ape = request.getParameter("txtApe");
                String email = request.getParameter("txtEmail");
                String tel = request.getParameter("txtTel");
                String idPais = request.getParameter("txtPais");
                String idRol = request.getParameter("txtRol");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if (idUsuario != null && !idUsuario.equals("")) {
                        Usuario u = new Usuario(idUsuario,nom,ape,email,tel,clave,Integer.parseInt(idPais),Integer.parseInt(idRol) );
                        u = Operaciones.actualizar(u.getIdusuario(), u);
                        if (u.getIdusuario() != null) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Usuario u = new Usuario();
                        u.setNombres(nom);
                        u.setApellidos(ape);
                        u.setEmail(email);
                        u.setTelefono(tel);
                        u.setClave(clave);
                        u.setIdpais(Integer.parseInt(idPais));
                        u.setIdrol(Integer.parseInt(idRol));
                        u = Operaciones.insertar(u);
                        if (u.getIdusuario() != null) {
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
                        Logger.getLogger(Usuarios2.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Usuarios2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/Usuarios2");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }
}
