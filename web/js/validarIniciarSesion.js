const formulario = document.getElementById("formularioRutas");

function validarFormulario() {
    let valido = true;
    
    // Validar nombre
    const nombre = document.getElementById("camponombre");
    if (nombre.value.trim() === "") {
        marcarError(nombre, "Introduce un nombre para la ruta");
        valido = false;
    } else {
        limpiarError(nombre);
    }
    
    // Validar descripción
    const descripcion = document.getElementById("campobiografia");
    if (descripcion.value.trim() === "") {
        marcarError(descripcion, "Introduce una descripción");
        valido = false;
    } else {
        limpiarError(descripcion);
    }
    
    return valido;
}

function marcarError(entrada, mensaje) {
    entrada.classList.add("is-invalid");
    const feedback = entrada.parentElement.querySelector(".invalid-feedback");
    if (feedback) {
        feedback.textContent = mensaje;
    }
}

function limpiarError(entrada) {
    entrada.classList.remove("is-invalid");
    entrada.classList.add("is-valid");
}

function validarNombreUnico() {
    const nombre = document.getElementById("camponombre");
    const valor = nombre.value.trim();
    
    if (valor === "") {
        limpiarError(nombre);
        return;
    }
    
    // Hacer petición como en Ejemplo 2
    const xhr = new XMLHttpRequest();
    const url = "/miapp/mis-rutas/check-nombre";
    xhr.open("POST", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const respuesta = xhr.responseText;
            if (respuesta.includes("Duplicado")) {
                marcarError(nombre, "Ya tienes una ruta con este nombre");
            } else {
                limpiarError(nombre);
            }
        }
    };
    
    const datos = "nombre=" + encodeURIComponent(valor);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(datos);
}

// Event listeners como en Ejemplo 2
if (formulario) {
    // Validar al enviar
    formulario.addEventListener("submit", function(e) {
        if (!validarFormulario()) {
            e.preventDefault();
        }
    });
    
    // Validar nombre único al cambiar (como email en Ejemplo 2)
    const nombreInput = document.getElementById("camponombre");
    if (nombreInput) {
        nombreInput.addEventListener("change", validarNombreUnico);
    }
}