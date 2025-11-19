<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="container justify-content-center">
    <main class="formulariousuarios w-100 m-auto">

        <!-- Lógica para comprobar si es editar o crear -->
        <c:set var="accion" value="crear" />
        <c:if test="${!empty requestScope.rutaEditada}">
            <c:set var="accion" value="actualizar" />
        </c:if>

        <form id="formulario" class="formulariousuarios" action="/miapp/usuario/mis-rutas/${accion}" method="POST">
            <img class="" src="/miapp/imagenes/logo.png" alt="" width="120" height="120">

            <c:if test="${!empty requestScope.rutaEditada}">
                <h3 class="mt-5 mb-3">Editar ruta</h3>
                <input type="hidden" name="id" value="${requestScope.rutaEditada.id}">
            </c:if>
            <c:if test="${empty requestScope.rutaEditada}">
                <h3 class="mt-5 mb-3">Registrar nueva ruta</h3>
            </c:if>

            <!-- Solo campos esenciales -->
            <div class="form-floating mb-3">
                <input value="<c:out value='${requestScope.rutaEditada.nombre}' />" 
                       type="text" class="form-control" id="camponombre" 
                       placeholder="Mi ruta por la sierra" name="nombre" required>
                <label for="camponombre">Nombre de la ruta</label>
            </div>

            <div class="form-floating mb-3">
                <input value="<c:out value='${requestScope.rutaEditada.distanciaRecorrida}' />" 
                       type="number" class="form-control" id="campoDistancia" 
                       placeholder="50" name="distanciaRecorrida" 
                       step="0.1" min="0" required>
                <label for="campoDistancia">Distancia aproximada (km)</label>
            </div>

            <div class="form-floating mb-3">
                <input value="<c:out value='${requestScope.rutaEditada.tiempo}' />" 
                       type="number" class="form-control" id="campoTiempo" 
                       placeholder="120" name="tiempo" 
                       step="1" min="0" required>
                <label for="campoTiempo">Duración aproximada (minutos)</label>
            </div>

            <div class="form-floating mb-3">
                <input type="date" class="form-control" id="campoFecha" 
                       name="fecha" required
                       value="<c:if test='${!empty requestScope.rutaEditada.fecha}'>${requestScope.rutaEditada.fecha}</c:if>">
                <label for="campoFecha">Fecha de la ruta</label>
            </div>

            <!-- Sección de métricas automáticas (solo informativa en edición) -->
            <c:if test="${!empty requestScope.rutaEditada}">
                <div class="card mb-4">
                    <div class="card-header">
                        <h6 class="mb-0">Métricas registradas por el dispositivo</h6>
                    </div>
                    <div class="card-body">
                        <div class="row text-center">
                            <div class="col-md-4">
                                <small class="text-muted">Velocidad máxima</small>
                                <h5>${requestScope.rutaEditada.velocidadMaxima} km/h</h5>
                            </div>
                            <div class="col-md-4">
                                <small class="text-muted">Velocidad media</small>
                                <h5>${requestScope.rutaEditada.velocidadMedia} km/h</h5>
                            </div>
                            <div class="col-md-4">
                                <small class="text-muted">Inclinación máxima</small>
                                <h5>${requestScope.rutaEditada.inclinacionMaxima}°</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${!empty requestScope.errorRuta}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.errorRuta}
                </div>
            </c:if>

            <div class="d-flex gap-2">
                <c:if test="${!empty requestScope.rutaEditada}">
                    <button type="submit" class="btn btn-primary w-50 py-2 mb-5">Actualizar ruta</button>
                    <a href="/miapp/usuario/mis-rutas" class="btn btn-secondary w-50 py-2 mb-5">Cancelar</a>
                </c:if>
                <c:if test="${empty requestScope.rutaEditada}">
                    <button type="submit" class="btn btn-primary w-100 py-2 mb-5">
                        <i class="bi bi-plus-circle"></i> Registrar ruta
                    </button>
                </c:if> 
            </div>
        </form>
    </main>
</div>

<script>
    // Establecer fecha actual por defecto
    document.addEventListener('DOMContentLoaded', function() {
        const fechaInput = document.getElementById('campoFecha');
        if (fechaInput && !fechaInput.value) {
            const today = new Date().toISOString().split('T')[0];
            fechaInput.value = today;
        }
    });
</script>

<%@include file="plantillas/footer.jspf" %>