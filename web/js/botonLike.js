async function darLike(boton, idRuta) {
    // Evitar doble click
    boton.style.pointerEvents = 'none';

    try {
        let params = new URLSearchParams();
        params.append("idRuta", idRuta);

        let response = await fetch("/miapp/rutas/like", {
            method: "POST",
            body: params
        });

        if (response.ok) {
            let texto = await response.text(); // "5:true"
            let partes = texto.split(":");
            let nuevosLikes = partes[0];
           
            let isLiked = partes[1];
            if (isLiked === "true") { //cuando lo pasas es texto
                isLiked = true;
            }
            else {
                isLiked = false;
            }
            

            // Actualizar DOM
            document.getElementById("contador-likes-" + idRuta).textContent = nuevosLikes;
            let icono = document.getElementById("icono-like-" + idRuta);

            if (isLiked) {
                icono.setAttribute("fill", "#0d6efd"); // AZUL
            } else {
                icono.setAttribute("fill", "#cccccc"); // GRIS
            }
        }
    } catch (error) {
        console.error(error);
    } finally {
        boton.style.pointerEvents = 'auto';
    }
}