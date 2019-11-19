/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolinea.control;

import com.aerolinea.entidad.Menu;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Principal extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       PrintWriter io = response.getWriter();
       String accion = request.getParameter("accion");
       
       if(request.getSession().getAttribute("Usuario")==null){
           response.sendRedirect("Login");
       }else{
           
       
       if(accion==null){
           HttpSession s = request.getSession();
           List<Menu> per = (List<Menu>)s.getAttribute("Permisos");
           //List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre()==0).collect(Collectors.toList());
           //request.setAttribute("MenuPrincipal", MenuPrincipal);
           String op = request.getParameter("op");
           if(op!=null){
               List<Menu> PermisosAsignados = per.stream().filter(field -> field.getIdpadre()==Integer.parseInt(op)).collect(Collectors.toList());
               request.setAttribute("PermisosAsignados", PermisosAsignados);
           }
           request.getRequestDispatcher("Principal.jsp").forward(request, response);
       }else if(accion.equals("logout"))
           logout(request, response);
       
    }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("Usuario");
        sesion.removeAttribute("Nombre");
        sesion.removeAttribute("Rol");
        sesion.invalidate();
        response.sendRedirect("Login");
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
