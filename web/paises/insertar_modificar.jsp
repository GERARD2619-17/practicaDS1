<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../Codigo_top.jsp"%>
<h1>Paises</h1>
<br/>
<form name="form_paises" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Paises?accion=insertar_modificar" method="POST">
    <table border="0" id="table">
        <thead>
            <tr><th colspan="">Complete la informaci�n<br><br></th>
            </tr>
        </thead>
        <tbody>
            <tr><td>ID Pa�s</td><td><input type="text" name="txtIdpais" value="${pais.idpais}" readonly="readonly" /></td></tr>
            <tr><td>Nombre Pa�s</td><td><input type="text" name="txtPais" id="txtPais" value="${pais.pais}" /></td></tr>            
        </tbody></table><br/>    <div class="buttons"><ul><li><input type="submit" value="Guardar" name="guardar"/></li><li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li></ul></div>                     
</form>
        <script>function validar(){var pais = document.getElementById('txtPais');if (pais.value.length==0){pais.focus();alert("Digite nombre del pa�s");return false;}return true;}                
        </script>

