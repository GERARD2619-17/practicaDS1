<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<% HttpSession sesion = request.getSession();
 response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aerolinea Project</title>
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />
        <link href="css/tabla.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="header">
            <h1>Aerolínea DSi</h1>
            <br/>
            <h2>A Example Project for Learning</h2>
            <img src="iconos/logo.png" />
        </div>
        <div id="sesion">            
                <h2>Usuario: <%= sesion.getAttribute("Nombre") %>
                    <strong>[<%= sesion.getAttribute("Usuario") %>]</strong> | 
                    <a href="Principal?accion=logout">Cerrar Sesión</a>
                </h2>
        </div>
        <div id="menu">
            <ul>
                <c:forEach var="menu" items="${MenuPrincipal}">
                    <li><a href="${pageContext.servletContext.contextPath}${menu.url}?op=${menu.idmenu}">${menu.menu}</a></li>    
                </c:forEach>
            </ul>
        </div>
        <div id="content">