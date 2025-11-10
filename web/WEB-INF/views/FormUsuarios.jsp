<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="container justify-content-center">
    <main class="formulariousuarios w-100 m-auto">
        <form id="formulario" class="formulariousuarios" action="/miapp/usuario/registrar" method="POST">
            <img class="" src="/miapp/imagenes/logo.png" alt="" width="90" height="90">
            
            <h3 class="mt-5 mb-3">Registrar usuario</h3>

            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="camponombre" placeholder="" name="nombre" required>
                <label for="camponombre">Nombre</label>
            </div>

            <div class="form-floating mb-3">
                <input type="email" class="form-control" id="campoemail" placeholder="" aria-describedby="emailHelp" name="correo" required>
                <label for="campoemail">Correo</label>
            </div>

            <c:if test="${!empty requestScope.errorEmail}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.errorEmail}
                </div>
            </c:if>

            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="campobiografia" placeholder="" name="biografia" required>
                <label for="campobiografia">Biografía</label>
            </div>

            <div class="form-floating mb-3">
                <input type="password" class="form-control" id="campocontrasena" placeholder="" name="contrasena" required>
                <label for="campocontrasena"">Contraseña</label>
            </div>


            <h3 class="mt-5 mb-3">Registrar vehículo</h3>

            <c:if test="${!empty requestScope.faltaVehiculo}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.faltaVehiculo}
                </div>
            </c:if>

            <select class="form-select mb-3" aria-label="" id="selectorMarca" name="marca">
                <option value="" selected disabled>Marca de moto</option>
                <option value="Yamaha">Yamaha</option>
                <option value="Honda">Honda</option>
                <option value="Kawasaki">Kawasaki</option>
                <option value="Suzuki">Suzuki</option>
                <option value="Ducati">Ducati</option>
                <option value="BMW">BMW</option>
                <option value="KTM">KTM</option>
                <option value="Triumph">Triumph</option>
                <option value="Aprilia">Aprilia</option>
                <option value="Benelli">Benelli</option>
                <option value="Husqvarna">Husqvarna</option>
                <option value="CFMoto">CFMoto</option>
                <option value="Zontes">Zontes</option>
                <option value="QJMotor">QJMotor</option>
                <option value="Voge">Voge</option>
                <option value="MV Agusta">MV Agusta</option>
            </select>

            <select class="form-select mb-5" aria-label="" id="selectorModelo" name="modelo" disabled>
                <option value="" selected disabled>Elige un modelo</option>
            </select>

            <button type="submit" class="btn btn-primary w-100 py-2 mb-5">Registrarse</button>
        </form>
    </main>
</div>

<%@include file="plantillas/footer.jspf" %>  