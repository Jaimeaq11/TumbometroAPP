<%@include file="plantillas/header_plantilla.jspf" %>
<link href="/miapp/css/iniciarsesion.css" rel="stylesheet" type="text/css"/>

<div class="container justify-content-center">
    <main class="form-signin w-100 m-auto">
        <form>
            <img class="mb-4" src="/miapp/imagenes/logo.png" alt="" width="90" height=90">

            <h3 class="h3 mb-3 fw-normal">Inicia sesión con tu cuenta del Tumbometro</h3>

            <div class="form-floating">
                <input 
                    type="email" 
                    class="form-control" 
                    id="floatingInput" 
                    placeholder="name@example.com">
                <label for="floatingInput">Correo</label>
            </div>

            <div class="form-floating">
                <input 
                    type="password" 
                    class="form-control" 
                    id="floatingPassword" 
                    placeholder="Password">
                <label for="floatingPassword">Contraseña</label>
            </div>

            <div class="form-check text-start my-3">
                <input 
                    class="form-check-input" 
                    type="checkbox" 
                    value="remember-me" 
                    id="checkDefault">
                <label class="form-check-label" for="checkDefault">
                    Recuérdame
                </label>
            </div>

            <button class="btn btn-primary w-100 py-2" type="submit">Sign in</button>

            <p class="mt-5 mb-3 text-body-secondary">© 2025</p>
        </form>
    </main>
</div>

<%@include file="plantillas/footer_plantilla.jspf" %>