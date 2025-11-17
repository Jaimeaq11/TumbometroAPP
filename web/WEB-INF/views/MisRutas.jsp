<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<c:if test="${!empty requestScope.misRutas}">

    <h3 class="titulo">Tu diario de rutas</h3>

    <c:forEach var="ruta" items="${requestScope.misRutas}" >

        <div class="card mb-3" style="max-width: 540px;">
            <div class="row g-0">
                <div class="col-md-4">
                    <img src="..." class="img-fluid rounded-start" alt="...">
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                        <p class="card-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                    </div>
                </div>
            </div>
        </div>

    </c:forEach>

</c:if>


<c:if test="${empty requestScope.misRutas}">
    <h3 class="titulo">No tienes ninguna ruta registrada!</h3>
    <div class="text-center">
        <a href="/miapp/mis-rutas/nueva" role="button" class="btn btn-primary mt-3">Añadir nueva ruta</a>
    </div>
</c:if>

<%@include file="plantillas/footer.jspf" %>