<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>


<div class="row row-cols-1 row-cols-md-4 g-4 me-4 ms-4">
    <c:forEach var="ruta" items="${requestScope.rutas}" >

        <div class="col">
            <div class="card me-2 ms-2">
                <img src="${ruta.rutaFoto}" class="card-img-top"></img>
                <div class="card-body">
                    <h6 class="display-6 card-title">${ruta.nombre}</h6>
                    <p class="card-text">${ruta.descripcion}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><b>Distancia: </b>${ruta.distancia} km</li>
                    <li class="list-group-item"><b>Tiempo: </b>${ruta.tiempo} h</li>
                    <li class="list-group-item"><b>Tipo de ruta: </b>${ruta.tipoCarretera}</li>
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

                <div class="card-body d-flex justify-content-between align-items-center">
                    
                        <c:if test="${empty ruta.usuarioid}">
                            <span class="display-6" style="color: #3399ff; font-size: 17px;">Sistema</span>
                        </c:if>
                        <c:if test="${!empty ruta.usuarioid}">
                            <span class="fs-5 display-6">${ruta.usuarioid.nombre}</span>
                        </c:if>

                </div>
            </div>
        </div>

    </c:forEach>
</div>

<%@include file="plantillas/footer.jspf" %>

<script>
    const isLogueado = ${!empty sessionScope.usuarioLogueado};
</script>