document.addEventListener('DOMContentLoaded', function () {

    const modelosPorMarca = {
        Yamaha: ["MT-07", "MT-09", "MT-10", "R1", "R6", "R7", "Tracer 7", "Tracer 9 GT", "XSR700", "XSR900", "Ténéré 700", "Ténéré 700 World Raid", "XMAX 125", "XMAX 300", "NMAX 125", "TMAX 560", "YZF-R3"],
        Honda: ["CBR1000RR-R Fireblade", "CBR650R", "CB500F", "CB500X", "CB750 Hornet", "CB650R", "Africa Twin", "NC750X", "Transalp 750", "Rebel 500", "Rebel 1100", "Forza 125", "Forza 350", "X-ADV", "Gold Wing", "PCX 125"],
        Kawasaki: ["Ninja ZX-10R", "Ninja ZX-6R", "Ninja 400", "Z400", "Z650", "Z900", "Z1000", "Versys 650", "Versys 1000", "Vulcan S", "KLX300", "KX450", "W800"],
        Suzuki: ["GSX-R1000", "GSX-S1000", "GSX-8S", "V-Strom 650", "V-Strom 800DE", "V-Strom 1050", "Katana", "Burgman 400", "Burgman 125", "Hayabusa"],
        Ducati: ["Panigale V4", "Panigale V2", "Monster", "Streetfighter V4", "Diavel V4", "Multistrada V4", "Multistrada V2", "Scrambler Icon", "Scrambler Full Throttle", "Hypermotard 950", "DesertX"],
        BMW: ["R 1250 GS", "R 1300 GS", "F 900 R", "F 900 XR", "S 1000 RR", "S 1000 XR", "R nineT", "R 18", "G 310 R", "G 310 GS", "C 400 X", "C 400 GT", "K 1600 GT"],
        KTM: ["390 Duke", "690 Duke", "890 Duke R", "1290 Super Duke R", "390 Adventure", "790 Adventure", "890 Adventure", "1290 Super Adventure", "RC 390", "RC 8C"],
        Triumph: ["Street Triple 765", "Speed Triple 1200 RS", "Bonneville T120", "Bonneville Bobber", "Scrambler 900", "Scrambler 1200 XE", "Tiger 660 Sport", "Tiger 900 GT", "Tiger 1200 Rally Pro", "Thruxton RS"],
        Aprilia: ["RS 660", "Tuono 660", "Tuareg 660", "RSV4 1100 Factory", "Tuono V4", "Shiver 900", "Caponord 1200", "SR GT 125", "SR GT 200", "SX 125"],
        Benelli: ["TRK 502", "TRK 702", "TRK 800", "Leoncino 500", "Leoncino 800", "502C", "BN 302S", "Imperiale 400", "Tornado 252R"],
        Husqvarna: ["Svartpilen 125", "Svartpilen 401", "Svartpilen 801", "Vitpilen 401", "Norden 901", "701 Enduro", "701 Supermoto", "TC 250", "FE 501"],
        CFMoto: ["650MT", "650GT", "800MT", "800NK", "700CL-X", "700CL-X Sport", "300NK", "300SR", "450SR", "450NK"],
        Zontes: ["T-350", "T-310", "R-350", "R-310", "U-350", "U-310", "GK350", "Z2-125", "E350"],
        QJMotor: ["SRT 700", "SRT 800", "SRK 400", "SRK 600", "SRV 550", "SRV 300", "SRV 125", "Fort 350", "Fort 700"],
        Voge: ["300AC", "300R", "300DS", "500AC", "500DS", "525DSX", "650DS", "900DS", "Valico 900", "Trofeo 525"],
        "MV Agusta": ["Brutale 800", "Brutale 1000 RR", "Dragster RR", "F3 800", "F3 Rosso", "Superveloce 800", "Turismo Veloce 800", "Enduro Veloce"]
    };

    const selectMarca = document.getElementById('selectorMarca');
    const selectModelo = document.getElementById('selectorModelo');

    // Lógica para rellenar modelos cuando cambia la marca
    if (selectMarca && selectModelo) {
        selectMarca.addEventListener('change', function () {
            const marcaSeleccionada = this.value;

            // Reiniciar select de modelo
            selectModelo.innerHTML = '<option selected disabled value="">Elige un modelo...</option>';

            if (marcaSeleccionada && modelosPorMarca[marcaSeleccionada]) {
                const modelos = modelosPorMarca[marcaSeleccionada];
                modelos.forEach(modelo => {
                    const opcion = document.createElement('option');
                    opcion.value = modelo;
                    opcion.textContent = modelo;
                    selectModelo.appendChild(opcion);
                });
                selectModelo.disabled = false;
            } else {
                selectModelo.disabled = true;
                selectModelo.value = "";
            }
        });
    }

    async function validarCorreoFetch(entrada) {
        
        let datos = new URLSearchParams();
        datos.append('correo', entrada.value);

        const idOculto = document.getElementById("idUsuarioOculto");
        if (idOculto) {
            datos.append('idOculto', idOculto.value);
        }

        try {
            let response = await fetch("/miapp/usuario/check-email", {
                method: 'POST',
                body: datos
            });

            if (response.ok) {
                const data = await response.text();

                if (data.trim() === "DUPLICADO") {
                    marcar(entrada, false, "Ese correo ya existe!");
                    return false;
                } else {
                    marcar(entrada, true);
                    return true;
                }
            } else {
                console.log("Error en la petición AJAX");
                return false;
            }
        } catch (error) {
            console.log("Error de conexión: " + error);
            return false;
        }
    }

    const formularioUsuarios = document.getElementById("formularioUsuarios");
    if (formularioUsuarios) {
        const mensajesErrorUsuarios = {
            nombre: "El nombre no es valido.",
            correo: "Introduce un correo valido (example@gmail.com)",
            biografia: "Cuentanos algo sobre ti.",
            contrasena: "La contraseña debe ser segura.",
            marca: "Marca no valida.",
            modelo: "Modelo no valido."
        };

        // --- FUNCIONES DE AYUDA ---
        function marcar(entrada, valido, mensaje = "") {
            const feedback = entrada.parentElement.querySelector(".invalid-feedback") || entrada.nextElementSibling;
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

        function validarSimple(entrada, mensajeError) {
            if (!entrada)
                return true;
            // CASO 1: VACÍO
            if (entrada.value.trim() === "") {
                marcar(entrada, false, "Este campo es obligatorio.");
                return false;
            }
            // CASO 2: OK
            marcar(entrada, true);
            return true;
        }

        function validarCorreo(entrada, mensajeFormato) {
            if (!entrada)
                return true;

            if (entrada.value.trim() === "") {
                marcar(entrada, false, "El correo es obligatorio.");
                return false;
            }

            const correoPatron = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!correoPatron.test(entrada.value.trim())) {
                marcar(entrada, false, mensajeFormato);
                return false;
            }

            return true;
        }

        function validarSelect(entrada, mensajeError) {
            if (!entrada)
                return true;

            // Si está disabled, lo ignoramos (es válido temporalmente)
            if (entrada.disabled)
                return true;

            // Si está activo pero vacío -> ERROR
            if (entrada.value === "" || entrada.value === null) {
                marcar(entrada, false, "Selecciona una opcion.");
                return false;
            }
            marcar(entrada, true);
            return true;
        }

        // --- CONTADOR BIOGRAFÍA ---
        const bioInput = document.getElementById("campobiografia");
        const contadorBio = document.getElementById("contador-bio");
        if (bioInput && contadorBio) {
            const actualizarContador = () => {
                const actual = bioInput.value.length;
                contadorBio.textContent = `${actual}/100`;
                if (actual >= 100) {
                    contadorBio.classList.add("text-danger");
                    contadorBio.classList.remove("text-muted");
                } else {
                    contadorBio.classList.add("text-muted");
                    contadorBio.classList.remove("text-danger");
                }
            };
            bioInput.addEventListener("input", actualizarContador);
            actualizarContador();
        }

        // --- EVENTO SUBMIT ---
        formularioUsuarios.addEventListener("submit", (e) => {
            const nombreOk = validarSimple(document.getElementById("camponombre"), mensajesErrorUsuarios.nombre);
            const correoOk = validarCorreo(document.getElementById("campocorreo"), mensajesErrorUsuarios.correo);
            const bioOk = validarSimple(document.getElementById("campobiografia"), mensajesErrorUsuarios.biografia);

            const passInput = document.getElementById("campocontrasena");
            const passOk = passInput ? validarSimple(passInput, mensajesErrorUsuarios.contrasena) : true;

            const marcaInput = document.getElementById("selectorMarca");
            let marcaOk = true;
            let modeloOk = true;

            // Lógica de Vehículos en Submit
            if (marcaInput) {
                marcaOk = validarSelect(marcaInput, mensajesErrorUsuarios.marca);

                const modeloInput = document.getElementById("selectorModelo");
                // Si hay marca seleccionada, OBLIGAMOS a tener modelo
                if (marcaInput.value !== "" && modeloInput) {
                    modeloOk = validarSelect(modeloInput, mensajesErrorUsuarios.modelo);
                }
            }

            if (!(nombreOk && correoOk && bioOk && passOk && marcaOk && modeloOk)) {
                e.preventDefault();
            }
        });

        // --- VALIDACIÓN EN TIEMPO REAL ---
        const campos = ['camponombre', 'campocorreo', 'campobiografia', 'campocontrasena', 'selectorMarca', 'selectorModelo'];
        campos.forEach(campoId => {
            const campo = document.getElementById(campoId);
            if (campo) {
                const evento = (campo.type === 'email' || campo.tagName === 'SELECT') ? 'change' : 'input';
                campo.addEventListener(evento, function () {
                    const claveError = this.id.replace('campo', '').replace('selector', '').toLowerCase();

                    if (this.type === 'email') {

                        const formatoValido = validarCorreo(this, mensajesErrorUsuarios[claveError]);
                        if (formatoValido) {
                            validarCorreoFetch(this);
                        }

                    } else if (this.type === 'select-one') {
                        validarSelect(this, mensajesErrorUsuarios[claveError]);

                    } else {
                        validarSimple(this, mensajesErrorUsuarios[claveError]);
                    }
                });
            }
        });
    }
}); 