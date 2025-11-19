<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="row row-cols-1 row-cols-md-3 g-4">
    <div class="col">
        <div class="card">
            <img src="imagenes/imagen.png" class="card-img-top">
            <div class="card-body">
                <h5 class="card-title">Nombre Ruta</h5>
                <p class="card-text">Descripcion de la ruta. Descripcion de la ruta Descripcion de la ruta Descripcion de la ruta Descripcion de la ruta</p>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><b>Distancia: </b>${ruta.distancia}</li>
                <li class="list-group-item"><b>Tiempo: </b>${ruta.tiempo}</li>
                <li class="list-group-item"><b>Inclinación máxima: </b>${ruta.inclinacionMaxima}</li>
                <li class="list-group-item"><b>Dificultad ? </b>${ruta.dificultad}</li>
                <li class="list-group-item"><b>Tipo de ruta: </b>${ruta.tipoCarretera}</li>
            </ul>
            <div class="card-body">
                <a href="#" class="card-link">Card link</a>
                <a href="#" class="card-link">Another link</a>
            </div>
        </div>
    </div>
</div>
<%@include file="plantillas/footer.jspf" %>