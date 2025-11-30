<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="container justify-content-center">
    <main class="formulariorutas w-100 m-auto">

        <!-- logica para comprobar si es editar o nueva -->
        <c:set var="accion" value="añadir-ruta" />
        <c:if test="${!empty requestScope.rutaEditada}">
            <c:set var="accion" value="editar-ruta" />
        </c:if>


        <form id="formularioRutas" class="formulariorutas" action="/miapp/mis-rutas/${accion}" method="POST" enctype="multipart/form-data">

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
                <input value="<c:out value='${requestScope.rutaEditada.nombre}' />" type="text" class="form-control" id="camponombre" placeholder="Nombre" name="nombre">
                <label for="camponombre">Nombre de la Ruta</label>
                <div class="invalid-feedback"></div>
            </div>

            <!-- descripcion -->
            <div class="form-floating mb-3">
                <textarea maxlength="100" class="form-control" id="campodescripcion" placeholder="Descripción" name="descripcion" style="height: 100px"><c:out value='${requestScope.rutaEditada.descripcion}' /></textarea>
                <label for="campodescripcion">Descripción</label>
                <div class="position-absolute bottom-0 end-0 p-2 text-muted small" id="contador-bio">
                    0/100
                </div>
                <div class="invalid-feedback"></div>
            </div>

            <!-- tiempo y distancia -->
            <div class="row g-2 mb-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <input value="<c:out value='${requestScope.rutaEditada.distancia}' />" type="number" step="0.1" class="form-control" id="campodistancia" name="distancia">
                        <label for="campodistancia">Distancia (km)</label>
                        <div class="invalid-feedback"></div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-floating">
                        <input value="<c:out value='${requestScope.rutaEditada.tiempo}' />" type="number" step="1" class="form-control" id="campotiempo" name="tiempo">
                        <label for="campotiempo">Tiempo (min)</label>
                        <div class="invalid-feedback"></div>
                    </div>
                </div>
            </div>

            <!-- dificultad  -->

            <div class="row g-2 mb-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <select class="form-select" id="campodificultad" name="dificultad">
                            <option value="" selected>Selecciona</option>
                            <option value="Fácil" ${requestScope.rutaEditada.dificultad == 'Fácil' ? 'selected' : ''}>Fácil</option>
                            <option value="Media" ${requestScope.rutaEditada.dificultad == 'Media' ? 'selected' : ''}>Media</option>
                            <option value="Difícil" ${requestScope.rutaEditada.dificultad == 'Difícil' ? 'selected' : ''}>Difícil</option>
                        </select>
                        <label for="campodificultad">Dificultad</label>
                        <div class="invalid-feedback"></div>
                    </div>
                </div>

                <!-- tipo de carretera -->
                <div class="col-md-6">
                    <div class="form-floating">
                        <select class="form-select" id="campotiporuta" name="tiporuta">
                            <option value="" selected>Selecciona el tipo</option>
                            <option value="Carretera" ${requestScope.rutaEditada.tipoRuta == 'Carretera' ? 'selected' : ''}>Carretera</option>
                            <option value="Montaña" ${requestScope.rutaEditada.tipoRuta == 'Montaña' ? 'selected' : ''}>Montaña</option>
                            <option value="Costa" ${requestScope.rutaEditada.tipoRuta == 'Costa' ? 'selected' : ''}>Costa</option>
                            <option value="Offroad" ${requestScope.rutaEditada.tipoRuta == 'Offroad' ? 'selected' : ''}>Offroad</option>
                            <option value="Urbana" ${requestScope.rutaEditada.tipoRuta == 'Urbana' ? 'selected' : ''}>Urbana</option>
                        </select>
                        <label for="campotiporuta">Tipo de Ruta</label>
                        <div class="invalid-feedback"></div>
                    </div>
                </div>
            </div>

            <!-- foto -->
            <div class="mb-3">
                <label for="campofoto" class="form-label">Foto de la ruta (opcional)</label>
                <input class="form-control" type="file" id="campofoto" name="foto" accept="image/*">
            </div>

            <!-- para pasarselo al controlador (le pasamos el id de la ruta que estamos editando) -->            
            <c:if test="${!empty requestScope.rutaEditada}">
                <input type="hidden" name="idRuta" value="${requestScope.rutaEditada.id}">
            </c:if>

            <button type="submit" class="btn btn-primary w-100 py-2 mb-5 mt-3">
                <c:if test="${!empty requestScope.rutaEditada}">Guardar Cambios</c:if>
                <c:if test="${empty requestScope.rutaEditada}">Crear Ruta</c:if>
                </button>

            </form>

        </main>
    </div>

<%@include file="plantillas/footer.jspf" %>