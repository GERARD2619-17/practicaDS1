<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../Codigo_top.jsp"%>
<h1>Usuarios</h1>
<br/>
<form name="form_paises" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Usuarios2?accion=insertar_modificar" method="POST">
    <table border="0" id="table">
        <thead>
            <tr><th colspan="">Complete la información<br><br></th>
            </tr>
        </thead>
        <tbody>
            <tr><td>Usuario</td></tr>
            <tr><td><input type="text" name="txtUsuario" id="txtidUsuario" value="${usuario.idusuario}" readonly="readonly" /></td></tr>
            <tr><td>Contrasena</td></tr>
            <tr><td><input type="password" name="txtClave" id="txtClave" value="${usuario.clave}" readonly="readonly" /></td></tr>

            <tr><td>Nombres</td></tr>
            <tr><td><input type="text" name="txtNom" id="txtNom" value="${usuario.nombres}" readonly="readonly" /></td></tr>
            <tr><td>Apellidos</td></tr>
            <tr><td><input type="text" name="txtApe" id="txtApe" value="${usuario.apellidos}" readonly="readonly" /></td></tr>
            <tr><td>Correo</td></tr>
            <tr><td><input type="email" name="txtEmail" id="txtEmail" value="${usuario.email}" readonly="readonly" /></td></tr>
            <tr><td>Telefono</td></tr>
            <tr><td><input type="text" name="txtTel" di="txtTel" value="${usuario.telefono}" readonly="readonly" /></td></tr>

            <tr><td>Pais</td></tr>
            <tr><td>
                <select name="txtPais" id="txtPais" value="${usuario.idpais}" readonly="readonly"/>

                <c:forEach var="opcion" items="${Paises}">
                <option value="${opcion.idpais}">${opcion.pais}</option>
            </c:forEach> 
            </select>
            </td>
            </tr>
            <tr><td>Rol</td></tr>
            <tr><td>
                <select name="txtRol" id="txtRol" value="${usuario.idrol}" readonly="readonly"/>

                <c:forEach var="opcion" items="${Roles}">
                <option value="${opcion.idrol}">${opcion.rol}</option>
            </c:forEach> 
            </select>
            </td>
            </tr>    
        </tbody></table><br/>    <div class="buttons"><ul><li><input type="submit" value="Guardar" name="guardar"/></li><li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li></ul></div>                     
</form>
<script>function validar() {
        var u = document.getElementById('txtidUsuario');
        if (u.value.length == 0) {
            u.focus();
            alert("Digite un nombre de usuario");
            return false;
        }
        return true;
    }
</script>

