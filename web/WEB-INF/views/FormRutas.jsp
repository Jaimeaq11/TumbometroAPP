<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="container justify-content-center">
    <main class="formulariorutas w-100 m-auto">

        <!-- logica para comprobar si es editar o nueva -->
        <c:set var="accion" value="nueva-ruta" />
        <c:if test="${!empty requestScope.rutaEditada}">
            <c:set var="accion" value="editar-ruta" />
        </c:if>


        <form id="formularioRutas" class="formulariorutas" action="/miapp/usuario/${accion}" method="POST">

            <img class="d-block mx-auto mt-4" src="/miapp/imagenes/logo.png" alt="" width="120" height="120">

            <!-- titulo -->
            <c:if test="${!empty requestScope.rutaEditada}">
                <h3 class="titulo mt-4 mb-3">Editar Ruta</h3>
            </c:if>
            <c:if test="${empty requestScope.rutaEditada}">
                <h3 class="titulo mt-4 mb-3">Nueva Ruta</h3>
            </c:if>

            <!-- nombre -->
            <div class="form-floating mb-3">
                <input value="<c:out value='${requestScope.rutaEditada.nombre}' />" type="text" class="form-control" id="camponombre" placeholder="Nombre" name="nombre" required>
                <label for="camponombre">Nombre de la Ruta</label>
            </div>

            <!-- descripcion -->
            <div class="form-floating mb-3">
                <textarea class="form-control" id="campobiografia" placeholder="Descripción" name="descripcion" required style="height: 100px"><c:out value='${requestScope.rutaEditada.descripcion}' /></textarea>
                <label for="campobiografia">Descripción</label>
                <div class="position-absolute bottom-0 end-0 p-2 text-muted small" id="contador-bio">
                    0/100
                </div>
            </div>

            <!-- tiempo y distancia -->
            <div class="row g-2 mb-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <input value="<c:out value='${requestScope.rutaEditada.distancia}' />" type="number" step="0.1" class="form-control" id="campodistancia" name="distancia" required>
                        <label for="campodistancia">Distancia (km)</label>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-floating">
                        <input value="<c:out value='${requestScope.rutaEditada.tiempo}' />" type="number" step="1" class="form-control" id="campotiempo" name="tiempo" required>
                        <label for="campotiempo">Tiempo (min)</label>
                    </div>
                </div>
            </div>

            <!-- dificultad  -->

            <div class="row g-2 mb-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <select class="form-select" id="campodificultad" name="dificultad" required>
                            <option value="" disabled selected>Selecciona</option>
                            <option value="Fácil" ${requestScope.rutaEditada.dificultad == 'Fácil' ? 'selected' : ''}>Fácil</option>
                            <option value="Media" ${requestScope.rutaEditada.dificultad == 'Media' ? 'selected' : ''}>Media</option>
                            <option value="Difícil" ${requestScope.rutaEditada.dificultad == 'Difícil' ? 'selected' : ''}>Difícil</option>
                        </select>
                        <label for="campodificultad">Dificultad</label>
                    </div>
                </div>

                <!-- tipo de carretera -->
                <div class="col-md-6">
                    <div class="form-floating mb-3">
                        <select class="form-select" id="campoTipoCarretera" name="tipoCarretera" required>
                            <option value="" disabled selected>Selecciona el tipo</option>
                            <option value="Carretera" ${requestScope.rutaEditada.tipoCarretera == 'Carretera' ? 'selected' : ''}>Carretera</option>
                            <option value="Montaña" ${requestScope.rutaEditada.tipoCarretera == 'Montaña' ? 'selected' : ''}>Montaña</option>
                            <option value="Costa" ${requestScope.rutaEditada.tipoCarretera == 'Costa' ? 'selected' : ''}>Costa</option>
                            <option value="Offroad" ${requestScope.rutaEditada.tipoCarretera == 'Offroad' ? 'selected' : ''}>Offroad</option>
                            <option value="Urbana" ${requestScope.rutaEditada.tipoCarretera == 'Urbana' ? 'selected' : ''}>Urbana</option>
                        </select>
                        <label for="campoTipoCarretera">Tipo de Ruta</label>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-primary w-100 py-2 mb-5">
                <c:if test="${!empty requestScope.rutaEditada}">Guardar Cambios</c:if>
                <c:if test="${empty requestScope.rutaEditada}">Crear Ruta</c:if>
                </button>

            </form>


        <c:if test="${!empty requestScope.rutaEditada}">
            <h3 class="mt-5 mb-3 titulo text-danger">¡Zona de peligro!</h3>
            <a href="miapp/rutas/eliminar?id=${requestScope.rutaEditada.id}' />" class="btn btn-danger d-block mx-auto w-100 mb-4 mt-3 py-2" onclick="return confirm('¿Estás seguro de que quieres borrar esta ruta?');">Eliminar Ruta</a>
        </c:if>

    </main>
</div>

<%@include file="plantillas/footer.jspf" %>