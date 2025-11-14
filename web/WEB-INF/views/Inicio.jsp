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

                <img src="/miapp/imagenes/negro.jpg" class="d-block w-100 arreglar1" height="1500" width="2250"></img>

                <div class="container">
                    <div class="carousel-caption text-start">
                        <h1 style="color: white;">¿Listo para rodar?</h1>
                        <p class="opacity-100" style="color: #cccccc; font-size: 23px;">Regístrate gratis, añade tu moto y empieza a construir tu historial de rutas hoy mismo.</p>
                        <p><a class="btn btn-lg btn-primary" href="/miapp/usuario/nuevo">Únete ahora</a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item">

                <img src="/miapp/imagenes/viaje.jpg" class="d-block w-100 arreglar2" height="100" width="150"></img>

                <div class="container">
                    <div class="carousel-caption">
                        <h1 style="color: white;">Tu diario de rutas.</h1>
                        <p class="opacity-100" style="color: #cccccc; font-size: 23px;">Con tu moto hasta el último kilómetro, guarda todos los detalles de tus aventuras.</p>
                        <p><a class="btn btn-lg btn-primary" href="">Learn more</a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item">

                <img src="/miapp/imagenes/grupo.jpg" class="d-block w-100 arreglar3" height="1500" width="2250"></img>

                <div class="container">
                    <div class="carousel-caption text-end">
                        <h1 style="color: white;">Mejor con amigos.</h1>
                        <p class="opacity-100" style="color: #cccccc; font-size: 23px;">Descubre las rutas de otros usuarios y comparte tus experiencias con ellos.</p>
                        <p><a class="btn btn-lg btn-primary" href="/miapp/usuarios">Ver usuarios activos</a></p>
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
                <svg aria-label="Placeholder" class="bd-placeholder-img rounded-circle" width="140" height="140" preserveAspectRatio="xMidYMid slice" xmlns="http://www.w3.org/2000/svg">
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                </svg>
                <h2 class="fw-normal">Heading</h2>
                <p>Some representative placeholder content for the three columns of text below the carousel. This is the first column.</p>
                <p><a class="btn btn-secondary" href="#">View details »</a></p>
            </div>
            <div class="col-lg-4">
                <svg aria-label="Placeholder" class="bd-placeholder-img rounded-circle" width="140" height="140" preserveAspectRatio="xMidYMid slice" xmlns="http://www.w3.org/2000/svg">
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                </svg>
                <h2 class="fw-normal">Heading</h2>
                <p>Another exciting bit of representative placeholder content. This time, we've moved on to the second column.</p>
                <p><a class="btn btn-secondary" href="#">View details »</a></p>
            </div>
            <div class="col-lg-4">
                <svg aria-label="Placeholder" class="bd-placeholder-img rounded-circle" width="140" height="140" preserveAspectRatio="xMidYMid slice" xmlns="http://www.w3.org/2000/svg">
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                </svg>
                <h2 class="fw-normal">Heading</h2>
                <p>And lastly this, the third column of representative placeholder content.</p>
                <p><a class="btn btn-secondary" href="#">View details »</a></p>
            </div>
        </div>

        <hr class="featurette-divider">

         <!--
        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading fw-normal lh-1">First featurette heading. <span class="text-body-secondary">It?ll blow your mind.</span></h2>
                <p class="lead">Some great placeholder content for the first featurette here. Imagine some exciting prose here.</p>
            </div>
            <div class="col-md-5">
                <svg aria-label="Placeholder: 500x500" class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" preserveAspectRatio="xMidYMid slice" xmlns="http://www.w3.org/2000/svg">
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-bg)"></rect>
                <text x="50%" y="50%" fill="var(--bs-secondary-color)" dy=".3em">500x500</text>
                </svg>
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading fw-normal lh-1">Oh yeah, it?s that good. <span class="text-body-secondary">See for yourself.</span></h2>
                <p class="lead">Another featurette? Of course. More placeholder content here to give you an idea of how this layout would work with some actual real-world content in place.</p>
            </div>
            <div class="col-md-5 order-md-1">
                <svg aria-label="Placeholder: 500x500" class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" preserveAspectRatio="xMidYMid slice" xmlns="http://www.w3.org/2000/svg">
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-bg)"></rect>
                <text x="50%" y="50%" fill="var(--bs-secondary-color)" dy=".3em">500x500</text>
                </svg>
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading fw-normal lh-1">And lastly, this one. <span class="text-body-secondary">Checkmate.</span></h2>
                <p class="lead">And yes, this is the last block of representative placeholder content. Again, not really intended to be actually read, simply here to give you a better view of what this would look like with some actual content. Your content.</p>
            </div>
            <div class="col-md-5">
                <svg aria-label="Placeholder: 500x500" class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" preserveAspectRatio="xMidYMid slice" xmlns="http://www.w3.org/2000/svg">
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-bg)"></rect>
                <text x="50%" y="50%" fill="var(--bs-secondary-color)" dy=".3em">500x500</text>
                </svg>
            </div>
        </div>

        <hr class="featurette-divider">
    </div>
    -->
</main>


<%@include file="plantillas/footer.jspf" %>