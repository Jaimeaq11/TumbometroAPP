<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<c:if test="${!empty requestScope.listausuarios}">

    <h3 class="texto mt-5 mb-3">Usuarios registrados</h3>
    <div> 
        <table class="table">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Biografia</th>
                    <th>Marca</th>
                    <th>Modelo</th>

                </tr>
            </thead>
            <tbody class="table-group-divider">
                <c:forEach var="usuario" items="${requestScope.listausuarios}" >
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

    <c:if test="${empty requestScope.listausuarios}">
        <h3 class="texto">Oops! No hay Usuarios todavía!</h3>
    </c:if>
</div>

<%@include file="plantillas/footer.jspf" %>