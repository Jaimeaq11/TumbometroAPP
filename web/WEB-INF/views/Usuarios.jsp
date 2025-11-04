<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="plantillas/header_plantilla.jspf" %>

<h6 class="display-6">Usuarios</h6>

<c:if test="${!empty requestScope.usuarios}">

    <div> 
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th> 
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Descripcion</th>
                    <th>Marca</th>
                    <th>Modelo</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="usuario" items="${requestScope.usuarios}" >
                    <tr>
                        <td>${usuario.id}</td>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.correo}</td>
                        <td>${usuario.descripcion}</td>
                        
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </c:if>

    <c:if test="${empty requestScope.usuarios}">
        <p>Oops! No hay Usuarios todavía!</p>
    </c:if>
</div>

<%@include file="plantillas/footer_plantilla.jspf" %>