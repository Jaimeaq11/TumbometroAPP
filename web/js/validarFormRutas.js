//const contextPath = "/miapp";
const formularioRutas = document.getElementById("formularioRutas");
const idEntrada = document.getElementById("idRuta");

let idRuta = null;
if (idEntrada) {
    idRuta = idEntrada.value;
}

const mensajesErrorRutas = {
    nombre: "El nombre de la ruta es obligatorio.",
    descripcion: "Anade una breve descripcion.",
    distancia: "Introduce una distancia valida (km).",
    tiempo: "Introduce un tiempo valido (horas).",
    dificultad: "Selecciona la dificultad.",
    tiporuta: "Selecciona el tipo de terreno."
};

// --- 2. INTEGRACIÓN DEL CONTADOR (Adaptado a Descripción) ---
const descTextarea = document.getElementById('campodescripcion');
const descContador = document.getElementById('contador-bio'); // En tu JSP dejaste este ID, así que lo mantenemos
const maxLength = 100;

if (descTextarea && descContador) {
    // Creamos la función para usarla al escribir Y al cargar
    const actualizarContador = () => {
        const longitudActual = descTextarea.value.length;
        descContador.textContent = longitudActual + '/' + maxLength;

        if (longitudActual >= maxLength) {
            descContador.classList.add('text-danger');
            descContador.classList.remove('text-muted');
        } else {
            descContador.classList.remove('text-danger');
            descContador.classList.add('text-muted');
        }
    };

    // Escuchar cada tecla
    descTextarea.addEventListener('input', actualizarContador);

    // ¡IMPORTANTE! Ejecutar una vez al inicio por si estamos en "Editar Ruta"
    actualizarContador();
}

// Función visual (Rojo/Verde)
function marcar(entrada, valido, mensaje = "") {
    // Busca el div de feedback inmediatamente después del input
    const feedback = entrada.parentElement.querySelector(".invalid-feedback");

    if (valido) {
        entrada.classList.add("is-valid");
        entrada.classList.remove("is-invalid");
    } else {
        entrada.classList.add("is-invalid");
        entrada.classList.remove("is-valid");
        if (feedback)
            feedback.textContent = mensaje;
}
}

// Validar texto simple (Nombre, Descripción)
function validarSimple(entrada, mensaje) {
    if (!entrada || entrada.value.trim() === "") {
        marcar(entrada, false, mensaje);
        return false;
    }
    marcar(entrada, true);
    return true;
}

// Validar números (Distancia, Tiempo)
function validarNumero(entrada, mensaje) {
    if (!entrada || entrada.value.trim() === "") {
        marcar(entrada, false, mensaje);
        return false;
    }
    const valor = parseFloat(entrada.value);
    if (isNaN(valor) || valor <= 0) {
        marcar(entrada, false, "Debe ser un numero mayor que 0.");
        return false;
    }
    marcar(entrada, true);
    return true;
}

// Validar Selectores (Dificultad, Tipo)
function validarSelect(entrada, mensaje) {
    if (!entrada || entrada.value === "" || entrada.disabled) {
        marcar(entrada, false, mensaje);
        return false;
    }
    marcar(entrada, true);
    return true;
}

// el "main"
if (formularioRutas) {
    formularioRutas.addEventListener("submit", (e) => {
        e.preventDefault(); // Paramos el envío para validar

        // Validaciones SÍNCRONAS (todas locales, sin AJAX)
        const nombreOk = validarSimple(document.getElementById("camponombre"), mensajesErrorRutas.nombre);
        const descripcionOk = validarSimple(document.getElementById("campodescripcion"), mensajesErrorRutas.descripcion);
        const distanciaOk = validarNumero(document.getElementById("campodistancia"), mensajesErrorRutas.distancia);
        const tiempoOk = validarNumero(document.getElementById("campotiempo"), mensajesErrorRutas.tiempo);
        const dificultadOk = validarSelect(document.getElementById("campodificultad"), mensajesErrorRutas.dificultad);
        const tipoOk = validarSelect(document.getElementById("campotiporuta"), mensajesErrorRutas.tiporuta);

        // Si todo OK, enviamos
        if (nombreOk && descripcionOk && distanciaOk && tiempoOk && dificultadOk && tipoOk) {
            formularioRutas.submit();
        }
    });

    // Validación en tiempo real para todos los campos
    const campos = ['camponombre', 'campodescripcion', 'campodistancia', 'campotiempo', 'campodificultad', 'campotiporuta'];
    campos.forEach(campoId => {
        const campo = document.getElementById(campoId);
        if (campo) {
            campo.addEventListener('change', function () {

                const claveError = this.id.replace('campo', '').toLowerCase();

                if (this.type === 'number') {
                    validarNumero(this, mensajesErrorRutas[claveError]);
                } else if (this.type === 'select-one') {
                    validarSelect(this, mensajesErrorRutas[claveError]);
                } else {
                    validarSimple(this, mensajesErrorRutas[claveError]);
                }
            });
        }
    });
}