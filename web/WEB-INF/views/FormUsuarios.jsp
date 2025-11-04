<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header_plantilla.jspf" %>

<h6 class="display-6">Registrar un Usuario</h6>
<div class="col-md-8 col-lg-6">

    <form id="formulario" class="form-users" action="/miapp/usuario/guardar" method="POST">

        <h3 class="mt-5">Usuario</h3>

        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="camponombre" placeholder="" name="nombre">
            <label for="camponombre">Nombre</label>
        </div>

        <div class="form-floating mb-3">
            <input type="email" class="form-control" id="campoemail" placeholder="" aria-describedby="emailHelp" name="correo">
            <label for="campoemail">Correo</label>
        </div>

        <c:if test="${!empty requestScope.errorEmail}">
            <div class="alert alert-danger" role="alert">
                ${requestScope.errorEmail}
            </div>
        </c:if>

        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="campodescripcion" placeholder="" name="descripcion">
            <label for="campodescripcion">Descripcion</label>
        </div>

        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="campocontrasena" placeholder="" name="contrasena">
            <label for="campocontrasena"">Contraseña</label>
        </div>


        <h3 class="mt-5">Vehículo</h3>

        <select class="form-select mb-3" aria-label="" id="selectorMarca" name="marca">
            <option selected disabled>Marca de moto</option>
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

        <select class="form-select mb-3" aria-label="" id="selectorModelo" name="modelo" disabled>
            <option selected disabled>Elige un modelo</option>
        </select>

        <button type="submit" class="btn btn-primary">Guardar</button>
    </form>
</div>

<script src="/miapp/js/moto_seleccion.js"></script>

<%@include file="plantillas/footer_plantilla.jspf" %>  