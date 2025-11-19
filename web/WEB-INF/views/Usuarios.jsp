<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<c:if test="${!empty requestScope.listausuarios}">

    <h3 class="titulo mt-5 mb-3">Usuarios registrados</h3>

    <div class="row justify-content-center"> 
        <div class="col-lg-10 text-center">
            <table class="table table-dark">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Biografia</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th></th>

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
                            <td>

                                <c:if test="${!empty sessionScope.admin}">
                                    <form action="/miapp/usuario/eliminar-usuario" method="POST" style="display: inline;">
                                        <input type="hidden" name="id" value="${usuario.id}">
                                            <button type="submit" class="btn btn-outline-danger btn-sm" onclick="return confirm('¿Estás seguro de que quieres borrar este usuario?');">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 24 24">
                                                    <path fill="none" stroke="#e60000" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 7h16m-10 4v6m4-6v6M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2l1-12M9 7V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v3"/>
                                                </svg>
                                            </button>
                                    </form>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>  

    </c:if>

    <c:if test="${empty requestScope.listausuarios}">
        <h3 class="titulo">Oops! No hay Usuarios activos!</h3>
    </c:if>
</div>

<%@include file="plantillas/footer.jspf" %>