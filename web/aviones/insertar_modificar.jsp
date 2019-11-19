<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../Codigo_top.jsp"%>
<h1>Aviones</h1>
<br/>
<form name="form_paises" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Aviones?accion=insertar_modificar" method="POST">
    <table border="0" id="table">
        <thead>
            <tr><th colspan="">Complete la información<br><br></th>
            </tr>
        </thead>
        <tbody>
            <tr><td>ID País</td><td><input type="text" name="txtIdavion" value="${avion.idavion}" readonly="readonly" /></td></tr>
            <tr><td>Capacidad</td><td><input type="text" name="txtCapacidad" id="txtCapacidad" value="${avion.capacidad}" /></td></tr>
            <tr><td>Descripcion</td><td><input type="text" name="txtDescripcion" id="txtDescripcion" value="${avion.capacidad}" /></td></tr> 
        </tbody></table><br/>    <div class="buttons"><ul><li><input type="submit" value="Guardar" name="guardar"/></li><li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li></ul></div>                     
</form>
<script>function validar() {
        var a = document.getElementById('txtCapacidad');
        if (a.value.length == 0) {
            a.focus();
            alert("Digite nombre del avion");
            return false;
        }
        return true;
    }
</script>

