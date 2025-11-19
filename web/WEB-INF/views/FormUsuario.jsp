<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="plantillas/header.jspf" %>

<div class="container justify-content-center">
    <main class="formulariousuarios w-100 m-auto">

        <!-- logica para comprobar si es editar o registrar -->
        <c:set var="accion" value="registrar" />
        <c:if test="${!empty requestScope.usuarioEditado}">
            <c:set var="accion" value="editar" />
        </c:if>

        
        <form id="formulario" class="formulariousuarios" action="/miapp/usuario/${accion}" method="POST" enctype="multipart/form-data">

            <img class="d-block mx-auto mt-4" src="/miapp/imagenes/logo.png" alt="" width="120" height="120">
            
            <c:if test="${!empty requestScope.usuarioEditado}">
                <h3 class="titulo mt-4 mb-3">Editar perfil</h3>
            </c:if>
            <c:if test="${empty requestScope.usuarioEditado}">
                <h3 class="titulo mt-4 mb-3">Registrar usuario</h3>
            </c:if>

            <div class="row g-3">
                <div class="col-md-6">
                    <div class="form-floating mb-3">
                        <input value="<c:out value='${requestScope.usuarioEditado.nombre}' />" type="text" class="form-control" id="camponombre" placeholder="" name="nombre" required>
                        <label for="camponombre">Nombre</label>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-floating mb-3">
                        <input value="<c:out value='${requestScope.usuarioEditado.correo}' />" type="email" class="form-control" id="campoemail" placeholder="" aria-describedby="emailHelp" name="correo" required>
                        <label for="campoemail">Correo</label>
                    </div>
                </div>
            </div>

            <c:if test="${!empty requestScope.errorEmail}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.errorEmail}
                </div>
            </c:if>


            <!--<div class="mb-3">
                <label for="formFile" class="form-label">Foto de perfil (Opcional)</label>
                <input class="form-control" type="file" id="formFile" name="rutaFoto">
            </div>-->


            <div class="form-floating mb-3">
                <textarea class="form-control" id="campobiografia" placeholder="" name="biografia" required style="height: 120px; resize: none;" maxlength="100"><c:out value='${requestScope.usuarioEditado.biografia}' /></textarea>
                <label for="campobiografia">Biografía</label>

                <div class="position-absolute bottom-0 end-0 p-2 text-muted small" id="contador-bio">
                    0/100
                </div>
            </div>

            <c:if test="${empty requestScope.usuarioEditado}">
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="campocontrasena" placeholder="" name="contrasena" required>
                    <label for="campocontrasena"">Contraseña</label>
                </div>
            </c:if>

            <c:if test="${empty requestScope.usuarioEditado}">
                <h3 class="mt-5 mb-3">Registrar vehículo</h3>

                <c:if test="${!empty requestScope.faltaVehiculo}">
                    <div class="alert alert-danger" role="alert">
                        ${requestScope.faltaVehiculo}
                    </div>
                </c:if>

                <div class="row g-3 mb-3"> 
                    <div class="col-md-6">
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
                    </div>

                    <div class="col-md-6">
                        <select class="form-select mb-3" aria-label="" id="selectorModelo" name="modelo" disabled>
                            <option value="" selected disabled>Elige un modelo</option>
                        </select>
                    </div>
                </div>
            </c:if>
            <c:if test="${!empty requestScope.usuarioEditado}">
                <button type="submit" class="btn btn-primary w-100 py-2 mb-5">Guardar cambios</button>
            </c:if>
            <c:if test="${empty requestScope.usuarioEditado}">
                <button type="submit" class="btn btn-primary w-100 py-2 mb-5">Registrarse</button>
            </c:if> 
        </form>

        <c:if test="${!empty requestScope.usuarioEditado}">
            <h3 class="mt-5 mb-3 titulo text-danger">¡Zona de peligro!</h3>

            <form action="/miapp/usuario/eliminar-cuenta" method="POST">
                <input type="hidden" name="id" value="${requestScope.usuarioEditado.id}">
                <button type="submit" class="btn btn-danger d-block mx-auto w-100 mb-4 mt-4 mt-3 py-2" onclick="return confirm('¿Estás seguro de que quieres borrar este usuario?');">Eliminar cuenta</button>
            </form>
        </c:if>
    </main>
</div>

<%@include file="plantillas/footer.jspf" %>  