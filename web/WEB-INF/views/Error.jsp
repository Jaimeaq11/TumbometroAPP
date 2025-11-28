<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="plantillas/header.jspf" %>

<div class="row mt-5 p-5">
    <div class="m-auto text-center error">
        
        <img src="imagenes/error.png" height="200" width="200" class="mt-5"></img>
        <h1 class="display-3">Error</h1>

        <div class="alert alert-danger mt-4 w-50 mx-auto" role="alert">
            
            <c:if test="${!empty requestScope.msg}">
                ${requestScope.msg}
                <a href="/miapp/iniciar-sesion" class="btn btn-primary mb-5 mt-4" role="button">Iniciar sesión</a>
            </c:if>
            
            <c:if test="${empty requestScope.msg}">
                no hay detalles
            </c:if>
        </div>

        <a href="/miapp/inicio" class="btn btn-outline-light mb-5 mt-4" role="button">Volver</a>
        
    </div>
</div>

<%@include file="plantillas/footer.jspf" %>