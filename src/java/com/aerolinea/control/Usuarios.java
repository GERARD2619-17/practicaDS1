/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolinea.control;

import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.entidad.Menu;
import com.aerolinea.entidad.Pais;
import com.aerolinea.entidad.Rol;
import com.aerolinea.entidad.Usuario;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.utilerias.Hash;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rober
 */
public class Usuarios extends HttpServlet {

    
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("opc");
        if (request.getSession().getAttribute("Usuario") == null) {
            response.sendRedirect("Login");
        } else {
            HttpSession s = request.getSession();
            List<Menu> per = (List<Menu>) s.getAttribute("Permisos");
            List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre() == 0).collect(Collectors.toList());
            request.setAttribute("MenuPrincipal", MenuPrincipal);
            
            List<Pais> permisos;
                List<Rol> roles;
                
                try {
                    permisos = getPaises();
                    roles = getRoles();
                    request.setAttribute("Paises",permisos);
                    request.setAttribute("Roles", roles);
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            
            if (accion == null) {

                request.getRequestDispatcher("usuarios.jsp").forward(request, response);
            }else if(accion.equals("10")){
                
                
                request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                
            }
        }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion.equals("insertar")){
            HttpSession s = request.getSession();
            List<Menu> per = (List<Menu>) s.getAttribute("Permisos");
            List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre() == 0).collect(Collectors.toList());
            request.setAttribute("MenuPrincipal", MenuPrincipal);
            
            List<Pais> permisos;
                List<Rol> roles;
                
                try {
                    permisos = getPaises();
                    roles = getRoles();
                    request.setAttribute("Paises",permisos);
                    request.setAttribute("Roles", roles);
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            registrar(request, response);
            request.getRequestDispatcher("usuarios.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    private List<Pais> getPaises() throws SQLException{
        List<Pais> paises = new ArrayList();
        try{
            ConexionPool con = new ConexionPool();
            con.conectar();
          
            
            Operaciones.abrirConexion(con);
            paises = Operaciones.getTodos(new Pais());
        }catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return paises;
    }
    private List<Rol> getRoles() throws SQLException{
        List<Rol> roles = new ArrayList();
        try{
            ConexionPool con = new ConexionPool();
            con.conectar();
          
            
            Operaciones.abrirConexion(con);
            roles = Operaciones.getTodos(new Rol());
        }catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return roles;
    }
    
    private void registrar(HttpServletRequest request, HttpServletResponse response) {
        
        String user = request.getParameter("txtUsuario");
        String pass = request.getParameter("txtClave");
        String nom = request.getParameter("txtNom");
        String ape = request.getParameter("txtApe");
        String email = request.getParameter("txtEmail");
        String tel = request.getParameter("txtTel");
        int pais = Integer.parseInt(request.getParameter("pais"));
        int rol = Integer.parseInt(request.getParameter("rol"));
        try {
            ConexionPool con = new ConexionPool();
            con.conectar();
            
            Usuario usuario = new Usuario();
            usuario.setIdusuario(user);
            usuario.setEmail(email);
            usuario.setNombres(nom);
            usuario.setApellidos(ape);
            usuario.setTelefono(tel);
            usuario.setClave(Hash.generarHash(pass, Hash.SHA256));
            usuario.setIdrol(rol);
            usuario.setIdpais(pais);
            Operaciones.abrirConexion(con);
            
            Operaciones.iniciarTransaccion();
            usuario = Operaciones.insertar(usuario);
            Operaciones.commit();
            request.setAttribute("exito", 1);
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
                request.setAttribute("error", "1");
            } catch (SQLException ex1) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

  
}


