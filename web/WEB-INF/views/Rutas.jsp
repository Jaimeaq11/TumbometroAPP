<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<h3 class="titulo mt-4 mb-3">Rutas de la comunidad</h3>

<!--<form class="buscador mb-4" role="search">
    <input type="search" class="form-control" placeholder="Buscar por título..." aria-label="Search">
</form>-->

<div class="row row-cols-1 row-cols-md-4 g-4 me-4 ms-4">

    <c:forEach var="ruta" items="${requestScope.rutas}" >

        <div class="col">
            <div class="card me-2 ms-2" style="border-radius: 20px;">

                <c:if test="${empty ruta.nombreFoto}">
                    <img src="imagenes/rutas/sin_fondo.png" class="card-img-top lafoto">
                </c:if>
                <c:if test="${!empty ruta.nombreFoto}">
                    <img src="/miapp/imagenes/rutas/${ruta.nombreFoto}" class="card-img-top lafoto">
                </c:if>

                <div class="card-body">
                    <h6 class="display-6 card-title">${ruta.nombre}</h6>
                    <p class="card-text">${ruta.descripcion}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><b>Distancia: </b>${ruta.distancia} km</li>
                    <li class="list-group-item"><b>Tiempo: </b>${ruta.tiempoFormateado} h</li>
                    <li class="list-group-item"><b>Tipo de ruta: </b>${ruta.tipoRuta}</li>
                    <li class="list-group-item"><b>Dificultad: </b>
                        <c:choose>
                            <c:when test="${ruta.dificultad == 'Fácil'}">
                                <span class="dificultad-facil">${ruta.dificultad}</span>
                            </c:when>
                            <c:when test="${ruta.dificultad == 'Media'}">
                                <span class="dificultad-media">${ruta.dificultad}</span>
                            </c:when>
                            <c:when test="${ruta.dificultad == 'Difícil'}">
                                <span class="dificultad-dificil">${ruta.dificultad}</span>
                            </c:when>
                        </c:choose>
                    </li>
                </ul>

                <div class="card-body">


                    <div class="row">

                        <div class="col-md-6">
                            <div class="d-inline-flex align-items-center">
                                <img src="/miapp/imagenes/perfiles/${ruta.usuarioid.nombreFoto}" height="35" width="35" class="rounded-circle"></img>
                                <span class="ms-1 fs-5 display-6" style="color: #3399ff;">${ruta.usuarioid.nombre}</span>
                            </div>
                        </div>

                        <div class="col-md-6 text-end">
                            <c:if test="${!empty sessionScope.admin and !empty sessionScope.usuarioLogueado}">
                                <form action="/miapp/rutas/eliminar-ruta" method="POST" style="display: inline;">
                                    <input type="hidden" name="idRuta" value="${ruta.id}">
                                    <button type="submit" class="boton-basura btn btn-outline-danger btn-sm" onclick="return confirm('¿Estás seguro de que quieres borrar esta ruta?');">
                                        <svg class="basura" xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 24 24"><path fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 7h16m-10 4v6m4-6v6M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2l1-12M9 7V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v3"/></svg>
                                    </button>
                                </form>
                            </c:if>

                            <c:if test="${empty sessionScope.admin and !empty sessionScope.usuarioLogueado}">
                                <!--<div class="d-inline-flex align-items-center">
                                    <button type="submit" class="btn btn-link p-0 border-0 bg-transparent" onclick="darLike(this, ${ruta.id})">
                                        <svg class="like" xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24"><path id="icono-like-${ruta.id}" fill="${ruta.yaLeDiLike ? '#0d6efd' : '#cccccc'}" d="M20 8h-5.612l1.123-3.367c.202-.608.1-1.282-.275-1.802S14.253 2 13.612 2H12c-.297 0-.578.132-.769.36L6.531 8H4c-1.103 0-2 .897-2 2v9c0 1.103.897 2 2 2h13.307a2.01 2.01 0 0 0 1.873-1.298l2.757-7.351A1 1 0 0 0 22 12v-2c0-1.103-.897-2-2-2M4 10h2v9H4zm16 1.819L17.307 19H8V9.362L12.468 4h1.146l-1.562 4.683A.998.998 0 0 0 13 10h7z"/></svg>
                                    </button>
                                    <span id="contador-likes-${ruta.id}" class="ms-2 fs-5">${ruta.likes}</span>
                                </div>-->

                                <div class="like-button">
                                    <input class="on" id="heart-${ruta.id}" type="checkbox" onchange="darLike(this, ${ruta.id})" ${ruta.yaLeDiLike ? 'checked' : ''} />
                                    <label class="like" for="heart-${ruta.id}">
                                        <svg class="like-icon" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M20 8h-5.612l1.123-3.367c.202-.608.1-1.282-.275-1.802S14.253 2 13.612 2H12c-.297 0-.578.132-.769.36L6.531 8H4c-1.103 0-2 .897-2 2v9c0 1.103.897 2 2 2h13.307a2.01 2.01 0 0 0 1.873-1.298l2.757-7.351A1 1 0 0 0 22 12v-2c0-1.103-.897-2-2-2M4 10h2v9H4zm16 1.819L17.307 19H8V9.362L12.468 4h1.146l-1.562 4.683A.998.998 0 0 0 13 10h7z"/>
                                        </svg>
                                    </label>

                                    <div class="like-count-container" id="contador-container-${ruta.id}">
                                        <c:set var="likesBase" value="${ruta.yaLeDiLike ? ruta.likes - 1 : ruta.likes}" />
                                        <span class="like-count one">${likesBase}</span>
                                        <span class="like-count two">${likesBase + 1}</span>
                                    </div>
                                </div>

                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </c:forEach>
</div>

<%@include file="plantillas/footer.jspf" %>

<script>
    const isLogueado = ${!empty sessionScope.usuarioLogueado};
</script>