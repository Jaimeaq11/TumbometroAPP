<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="row">
    <div class="col-8">
        <h6 class="display-6">Mis Rutas</h6>
    </div>
    <div class="col-4 d-flex align-items-center justify-content-end">
        <%-- Botón para ir al formulario de crear ruta (que harás después) --%>
        <a href="<c:url value='/usuario/crear-ruta' />" role="button" class="btn btn-primary">
            + Añadir Ruta
        </a>
    </div>
</div>


<%-- Comprobamos si la lista NO está vacía --%>
<c:if test="${!empty requestScope.misRutas}">

    <div>    
        <table class="table table-dark mt-4">
            <thead>
                <tr>
                    <th>Título</th>
                    <th>Descripción</th>
                    <th>Punto de Inicio</th>
                    <th>Punto de Fin</th>
                </tr>
            </thead>
            <tbody>
                <%-- Recorremos la lista de rutas --%>
                <c:forEach var="ruta" items="${requestScope.misRutas}" >
                    <tr>
                        <td>${ruta.titulo}</td>
                        <td>${ruta.descripcion}</td>
                        <td>${ruta.puntoInicio}</td>
                        <td>${ruta.puntoFin}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<%-- Comprobamos si la lista SÍ está vacía --%>
<c:if test="${empty requestScope.misRutas}">
    <div class="alert alert-info mt-4" role="alert">
        ¡Oops! Todavía no has guardado ninguna ruta.
    </div>
</c:if>

<%@include file="plantillas/footer.jspf" %>