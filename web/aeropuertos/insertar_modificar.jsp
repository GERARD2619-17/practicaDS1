<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../Codigo_top.jsp"%>
<h1>Aeropuertos</h1>
<br/>
<form name="form_paises" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Aeropuertos?accion=insertar_modificar" method="POST">
    <table border="0" id="table">
        <thead>
            <tr><th colspan="">Complete la información<br><br></th>
            </tr>
        </thead>
        <tbody>
            <tr><td>ID Aeropuerto</td><td><input type="text" name="txtIdaeropuerto" value="${aeropuerto.idaeropuerto}" readonly="readonly" /></td></tr>
            <tr><td>Aeropuerto</td><td><input type="text" name="txtAeropuerto" id="txtAeropuerto" value="${aeropuerto.aeropuerto}" /></td></tr>
            <tr><td>ID Pais</td><td><input type="text" name="txtIdpais" id="txtIdpais" value="${aeropuerto.idpais}" /></td></tr>
            <tr><td>Ciudad</td><td><input type="text" name="txtCiudad" id="txtCiudad" value="${aeropuerto.ciudad}" /></td></tr> 
        </tbody></table><br/>    <div class="buttons"><ul><li><input type="submit" value="Guardar" name="guardar"/></li><li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li></ul></div>                     
</form>
        <script>function validar(){var ae = document.getElementById('txtAeropuerto');if (ae.value.length==0){ae.focus();alert("Digite nombre del aeropuerto");return false;}return true;}                
        </script>

