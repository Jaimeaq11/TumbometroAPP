<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<!--<img src="/miapp/imagenes/imagen.png" class="rounded mx-auto d-block" height="600" width="600">-->

<main>
    <div id="myCarousel" class="carousel slide mb-6" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" aria-label="Slide 1" class="botones active" aria-current="true"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2" class="botones"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3" class="botones"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">

                <img src="/miapp/imagenes/carrousel/negro.jpg" class="d-block w-100 arreglar1" height="1500" width="2250"></img>

                <div class="container">
                    <div class="carousel-caption text-start">
                        <h1 style="color: white;">¿Listo para rodar?</h1>
                        <p class="opacity-100" style="color: #cccccc; font-size: 23px;">Regístrate gratis, añade tu moto y empieza a construir tu historial de rutas hoy mismo.</p>
                        <p><a class="btn btn-lg btn-primary" href="/miapp/usuario/nuevo">Únete ahora</a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item">

                <img src="/miapp/imagenes/carrousel/viaje.jpg" class="d-block w-100 arreglar2" height="100" width="150"></img>

                <div class="container">
                    <div class="carousel-caption">
                        <h1 style="color: white;">Tu diario de rutas.</h1>
                        <p class="opacity-100" style="color: #cccccc; font-size: 23px;">Con tu moto hasta el último kilómetro, guarda todos los detalles de tus aventuras.</p>
                        <c:if test="${!empty sessionScope.usuarioLogueado}">
                            <p><a class="btn btn-lg btn-primary" href="/miapp/mis-rutas">Añadir nuevas rutas</a></p>
                        </c:if>
                        <c:if test="${empty sessionScope.usuarioLogueado}">
                            <p><a class="btn btn-lg btn-primary" href="/miapp/iniciar-sesion">Añadir nuevas rutas</a></p>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="carousel-item">

                <img src="/miapp/imagenes/carrousel/grupo.jpg" class="d-block w-100 arreglar3" height="1500" width="2250"></img>

                <div class="container">
                    <div class="carousel-caption text-end">
                        <h1 style="color: white;">Mejor con amigos.</h1>
                        <p class="opacity-100" style="color: #cccccc; font-size: 23px;">Descubre las rutas de otros usuarios y comparte tus experiencias con ellos.</p>
                        <p><a class="btn btn-lg btn-primary" href="/miapp/rutas">Ver rutas comunitarias</a></p>
                    </div>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <div class="container marketing">
        <div class="row">
            <div class="col-lg-4">
                <img src="imagenes/carrousel/casco.png" class="bd-placeholder-img rounded-circle" width="140" height="140">
                <h2 class="fw-normal mt-3 mb-3">Tu perfil de motero</h2>
                <p class="mb-4">Sube tu foto de perfil, escribe tu biografía y deja que la comunidad conozca al motero detrás del casco.</p>
            </div>
            <div class="col-lg-4">
                <img src="imagenes/carrousel/moto.png" class="bd-placeholder-img rounded-circle" width="140" height="140">
                <h2 class="fw-normal mt-3 mb-3">El garaje virtual</h2>
                <p class="mb-4">Añade los detalles de tu moto a tu perfil. Desde el modelo hasta el color, tu máquina es la protagonista</p>
            </div>
            <div class="col-lg-4">
                <img src="imagenes/carrousel/carretera.png" class="bd-placeholder-img rounded-circle" width="140" height="140">
                <h2 class="fw-normal mt-3 mb-3">Explora y descubre</h2>
                <p class="mb-4">Filtra las rutas de la comunidad por marca de moto, dificultad o kilómetros para planear tu próxima salida.</p>
            </div>
        </div>
</main>


<%@include file="plantillas/footer.jspf" %>