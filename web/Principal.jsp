<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session='true' %>
<%
    
    
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
%>
<%@include file="Codigo_top.jsp" %>
<style>
    #opciones li a{
        text-decoration: none;
        font-size: 14px;
    }
</style>
<ul id="opciones">
    <c:forEach var="opcion" items="${PermisosAsignados}">
        <li><a href="${pageContext.servletContext.contextPath}${opcion.url}?opc=${opcion.idmenu}">${opcion.menu}</a></li>
    </c:forEach>    
</ul>