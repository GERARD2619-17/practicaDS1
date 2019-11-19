<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session='true' %>
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
      
    </head>
    <body>
               
        
        <%@include file="Codigo_top.jsp" %>
         <c:if test="${error!=null}">
            <c:if test="${error=='1'}">
                <p style="color:#FF0000"><strong>No se pudo ingresar</strong></p>
            </c:if>
         </c:if>
         <c:if test="${exito!=null}">
                <p style="color:darkgreen"><strong>Ingresado Correctamente</strong></p>
         </c:if>
                <br>
        <form name="main" action="Usuarios?accion=insertar" method="POST">
            <table class="login">
                <tr><td>Usuario</td></tr>
                <tr><td><input type="text" name="txtUsuario" value="${usuario.idusuario}" readonly="readonly" /></td></tr>
                <tr><td>Contrasena</td></tr>
                <tr><td><input type="password" name="txtClave" value="${usuario.clave}" readonly="readonly" /></td></tr>

                <tr><td>Nombres</td></tr>
                <tr><td><input type="text" name="txtNom" value="${usuario.nombres}" readonly="readonly" /></td></tr>
                <tr><td>Apellidos</td></tr>
                <tr><td><input type="text" name="txtApe" value="${usuario.apellidos}" readonly="readonly" /></td></tr>
                <tr><td>Correo</td></tr>
                <tr><td><input type="email" name="txtEmail" value="${usuario.email}" readonly="readonly" /></td></tr>
                <tr><td>Telefono</td></tr>
                <tr><td><input type="text" name="txtTel" value="${usuario.telefono}" readonly="readonly" /></td></tr>
                
                <tr><td>Pais</td></tr>
                <tr><td>
                        <select name="pais" id="txtPais" value="${usuario.idpais}" readonly="readonly"/>

                            <c:forEach var="opcion" items="${Paises}">
                                <option value="${opcion.idpais}">${opcion.pais}</option>
                            </c:forEach> 
                        </select>
                    </td>
                </tr>
                <tr><td>Rol</td></tr>
                <tr><td>
                        <select name="rol" id="txtRol" value="${usuario.idrol}" readonly="readonly"/>

                            <c:forEach var="opcion" items="${Roles}">
                                <option value="${opcion.idrol}">${opcion.rol}</option>
                            </c:forEach> 
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="buttons">
                            <ul>
                                <li>
                                    <input type="submit" value="Registrar" name="btnEntrar"/>
                                </li>
                            </ul>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    
</body>
</html>

