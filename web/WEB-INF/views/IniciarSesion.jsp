<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="container justify-content-center">
    <main class="iniciodesesion w-100 m-auto">
        <form action="/miapp/usuario/login" class="mt-5" method="POST">
            <img class="mb-4" src="/miapp/imagenes/logo.png" alt="" width="120" height=120">

            <h3 class="h3 mb-3 fw-normal">Inicia sesión con tu cuenta</h3>

            <c:if test="${!empty requestScope.loginIncorrecto}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.loginIncorrecto}
                </div>
            </c:if>

            <div class="form-floating mb-3">
                <input type="email" class="form-control" id="floatingInput" placeholder="" name="correo" required>
                <label for="floatingInput">Correo</label>
            </div>

            <div class="form-floating mb-2">
                <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="contrasena" required>
                <label for="floatingPassword">Contraseña</label>
            </div>

            <div class="mb-4">
                <a href="/miapp/inicio"
                   <p class="text-body-secondary">¿Ha olvidado su contraseña?</p>
                </a>
            </div>

            <div class="form-check text-start my-3 mb-4">
                <input class="form-check-input" type="checkbox" value="remember-me" id="checkDefault">
                <label class="form-check-label" for="checkDefault" name="recuerdame" value="on">Recuérdame</label>
            </div>

            <div class="buttons">
                <button class="btn btn-primary w-100 py-2 mb-5" type="submit">Entrar</button>
            </div>
            
            <div class="mb-4">
                <p class="text-body-secondary">¿No tiene cuenta?
                    <a href="/miapp/usuario/nuevo"
                       <p class="text-body-primary">Registrarse</p>
                    </a>
            </div>

        </form>
    </main>
</div>

<%@include file="plantillas/footer.jspf" %>