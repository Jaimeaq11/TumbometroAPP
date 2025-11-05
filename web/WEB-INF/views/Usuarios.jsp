<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="plantillas/header_plantilla.jspf" %>

<h3 class="texto mt-5 mb-3">Usuarios</h3>

<c:if test="${!empty requestScope.usuarios}">

    <div> 
        <table class="table table-dark m-5">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Biografia</th>
                    <th>Marca</th>
                    <th>Modelo</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="usuario" items="${requestScope.usuarios}" >
                    <tr>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.correo}</td>
                        <td>${usuario.biografia}</td>
                        <td>${usuario.moto.marca}</td>                        
                        <td>${usuario.moto.modelo}</td>                        
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