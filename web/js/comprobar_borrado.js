document.addEventListener('DOMContentLoaded', function () {

    const botonesEliminar = document.querySelectorAll('.boton_eliminar');

    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', function (e) {

            const confirmar = confirm('¿Estás seguro de que quieres borrar a este usuario?');

            if (!confirmar) {
                e.preventDefault(); // Cancelar
            }
        });
    });
});