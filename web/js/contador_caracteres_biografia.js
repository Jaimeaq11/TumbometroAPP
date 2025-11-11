document.addEventListener('DOMContentLoaded', function () {

    const bioTextarea = document.getElementById('campobiografia');
    const bioContador = document.getElementById('contador-bio');
    const maxLength = 100;

    if (bioTextarea && bioContador) {
        bioTextarea.addEventListener('input', function () { //input escucha cada vez que se da una tecla

            const longitudActual = bioTextarea.value.length;

            bioContador.textContent = longitudActual + '/' + maxLength;

            if (longitudActual >= maxLength) {
                bioContador.classList.add('text-danger');
                bioContador.classList.remove('text-muted');
            } else {
                bioContador.classList.remove('text-danger');
                bioContador.classList.add('text-muted');
            }
        });
    }
});