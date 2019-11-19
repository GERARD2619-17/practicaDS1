<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
    
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/reset.css" rel="stylesheet" type="text/css"/>
        <link href="css/main.css" rel="stylesheet" type="text/css"/>
        <style type="text/css">
            .login{margin: 0 auto}
            .login td{padding: 5px 0px}
            #header > h1 {margin-left: 0px; padding-top: 110px;}
            #content > h1{text-align: center}
        </style>
    
    </head>
    <body>
        <div id="header">
            <h1>Aerolinea DS1</h1>
        </div>
        <div id="content">
            <hr>
            <h1>Inicio de Sesion</h1><br>
            <center>
                <c:if test="${error!=null}">
                    <c:if test="${error==2}">
                        <p>
                            <strong style="color: red">Usuario y/o contrasena incorrectos</strong>
                        </p>
                        
                    </c:if>
                </c:if>
            </center>
            <form name="main" action="Login?accion=login" method="POST">
                <table class="login">
                    <tr><td>Usuario</td></tr>
                    <tr><td><input type="text" name="txtUsuario" size="30px"/></td></tr>
                    <tr><td>Contrasena</td></tr>
                    <tr><td><input type="password" name="txtClave" size="30px"/></td></tr>
                
                    <tr>
                        <td>
                            <div class="buttons">
                                <ul>
                                    <li>
                                        <input type="submit" value="Entrar" name="btnEntrar"/>
                                    </li>
                                </ul>
                                <%--</div>
                                <ul>
                                
                                </ul>
                            </div > --%>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
