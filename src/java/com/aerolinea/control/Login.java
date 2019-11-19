/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolinea.control;

import com.aerolinea.conexion.Conexion;
import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.entidad.Menu;
import com.aerolinea.entidad.Usuario;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.utilerias.Hash;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class Login extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (request.getSession().getAttribute("Usuario") != null) {
            response.sendRedirect("Principal");
        } else {
            if (accion == null) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else if (accion.equals("login")) {
                try {
                    iniciarSesion(request, response);

                } catch (SQLException ex) {

                }
            }
        }
    }
    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
        String usuario = request.getParameter("txtUsuario");
        String clave = request.getParameter("txtClave");
        PrintWriter io = response.getWriter();
        if(usuario==null)
            usuario = "";
        if(clave==null)
            clave = null;
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            if(conn.getConexion()==null){
                request.setAttribute("error", 1);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                
            }else{
                HttpSession sesion = request.getSession();
                Usuario u = Operaciones.get(usuario, new Usuario());
                if(u.getIdusuario()!=null){
                    if(u.getClave().equals(Hash.generarHash(clave, Hash.SHA256))){
                        sesion.setAttribute("Usuario", u.getIdusuario());
                        sesion.setAttribute("Nombre", u.getNombres()+" "+u.getApellidos());
                        sesion.setAttribute("Rol", u.getIdrol());
                        List<Menu> permisos = getPermisos(u.getIdrol());
                        List<Menu> MenuPrincipal = permisos.stream().filter(field -> field.getIdpadre()==0).collect(Collectors.toList());
                        sesion.setAttribute("MenuPrincipal",MenuPrincipal);
                        sesion.setAttribute("Permisos",permisos);
                        response.sendRedirect("Principal");
                    }else{
                        request.setAttribute("error", 2);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }else{
                    request.setAttribute("error", 2);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
            Operaciones.commit();
        }catch(Exception ex){
            Operaciones.rollback();
            io.print(ex);
        }
        
    }
    private List<Menu> getPermisos(Integer idrol) throws SQLException{
        List<Menu> permisos = new ArrayList();
        try{
            String sql = "SELECT * FROM menu WHERE idmenu in (SELECT idmenu FROM permiso WHERE idrol=?) ";
            List<Object> param = new ArrayList();
            param.add(idrol);
            String[][] rs = Operaciones.consultar(sql, param);
            for(int i=0; i<rs[0].length; i++){
                Menu m = new Menu(Integer.parseInt(rs[0][i]),rs[1][i], rs[2][i], rs[3][i], Integer.parseInt(rs[4][i]==null?"0":rs[4][i]) );
                permisos.add(m);
            }
        }catch(Exception ex){
            Operaciones.rollback();
        }
        return permisos;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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

}
