<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="plantillas/header_plantilla.jspf" %>

<div class="row">
    <div class="col-md-8 offset-md-2 text-center">
        
        <h1 class="display-4 text-danger">Bro... Ha petao</h1>
        <p class="lead">Ha ocurrido un problema al procesar tu solicitud.</p>

        <div class="alert alert-danger mt-4" role="alert">
            <strong>La causa de la explosión:</strong>
            <br>
            
            <c:if test="${!empty requestScope.msg}">
                ${requestScope.msg}
            </c:if>
            
            <c:if test="${empty requestScope.msg}">
                No se proporcionaron detalles del error.
            </c:if>
        </div>

        <a href="/miapp/inicio" class="btn btn-primary mt-3">Volver</a>
        
    </div>
</div>

<%@include file="plantillas/footer_plantilla.jspf" %>