<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<c:if test="${!empty requestScope.misRutas}">

    <a href="/miapp/mis-rutas/añadir-ruta" role="button" class="btn btn-primary mt-4 ms-4 px-4">Añadir ruta</a>
    <h3 class="titulo mt-2 mb-3">Tu diario de rutas</h3>

    <div class="row row-cols-1 row-cols-md-4 g-4 me-4 ms-4">
        <c:forEach var="ruta" items="${requestScope.misRutas}" >

            <div class="col">
                <div class="card me-2 ms-2 ${ruta.publica ? 'border-primary' : ''}" style="border-radius: 20px;">
                    <img src="${ruta.rutaFoto}" class="card-img-top"></img>
                    <div class="card-body">

                        <div class="d-flex justify-content-between align-items-center">
                            <h6 class="display-6 card-title">${ruta.nombre}</h6>

                            <c:if test="${!ruta.publica}">
                                <form action="/miapp/mis-rutas/publicar-ruta" class="position-absolute top-0 end-0 mt-3 me-3" method="POST">
                                    <input type="hidden" name="idRuta" value="${ruta.id}">
                                        <button title="Publicar" type="submit" class="btn btn-outline-primary rounded-circle boton-publicar" style="width: 50px; height: 50px;" onclick="return confirm('¿Estás seguro de que quieres publicar esta ruta?');">
                                            <svg class="publicar" xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 24 24"><path fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m7 8l5-5l5 5m-5 7V3m10 12v4a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2v-4"/></svg>
                                        </button>
                                </form>
                            </c:if>
                            <c:if test="${ruta.publica}">
                                <p class="me-2" style=" color: #146ef0; font-size: 18px;">Publicada</p>
                            </c:if>
                        </div>

                        <p class="card-text">${ruta.descripcion}</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><b>Distancia: </b>${ruta.distancia} km</li>
                        <li class="list-group-item"><b>Tiempo: </b>${ruta.tiempo} h</li>
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
                    <div class="card-body d-flex align-items-center">

                        <a href="/miapp/mis-rutas/editar-ruta?idRuta=${ruta.id}" role="button" class="btn btn-outline-warning me-3">Editar ruta</a>

                        <form action="/miapp/mis-rutas/eliminar-ruta" method="POST">
                            <input type="hidden" name="idRuta" value="${ruta.id}">
                                <button type="submit" class="btn btn-danger me-3" onclick="return confirm('¿Estás seguro de que quieres borrar esta ruta?');">Eliminar ruta</button>
                        </form>

                    </div>
                </div>
            </div>

        </c:forEach>
    </div>
</c:if>


<c:if test="${empty requestScope.misRutas}">
    <h3 class="titulo">No tienes ninguna ruta registrada!</h3>
    <div class="text-center">
        <a href="/miapp/mis-rutas/añadir-ruta" role="button" class="btn btn-primary mt-3 me-3">Añadir nueva ruta</a>
        <a href="/miapp/rutas" role="button" class="btn btn-primary mt-3">Buscar rutas en la comunidad</a>
    </div>
</c:if>

<%@include file="plantillas/footer.jspf" %>