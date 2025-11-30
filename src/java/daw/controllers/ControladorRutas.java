package daw.controllers;

import daw.model.Moto;
import daw.model.Ruta;
import daw.model.Usuario;
import daw.util.Encriptar;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ControladorRutas", urlPatterns = {"/mis-rutas", "/mis-rutas/*"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)

public class ControladorRutas extends HttpServlet {

    @PersistenceContext(unitName = "ElTumbometroPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(ControladorRutas.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String vista = "MisRutas";
        String accion;

        //por alguna razon no comprobaba lo de getservletpath pero no me acuerdo de por que lo quité
        if (request.getPathInfo() != null) {
            accion = request.getPathInfo();
        } else {
            accion = "/mis-rutas";
        }

        switch (accion) {

            case "/mis-rutas" -> {

                //solo accedemos si tenemos el login (proteccion de rutas)
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {
                    vista = "MisRutas"; //asumimos con algun valor para que no de error
                    try {
                        Usuario usuario = em.find(Usuario.class, usuarioLogueado.getId());
                        List<Ruta> misRutas = usuario.getRutas();

                        if (misRutas.isEmpty()) {
                            request.setAttribute("msg", "No hay rutas.");
                        } else {
                            request.setAttribute("misRutas", misRutas);
                        }

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error al cargar tus rutas.");
                    }
                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/añadir-ruta" -> {
                //solo accedemos si tenemos el login (proteccion de rutas)
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {
                    vista = "FormRutas";
                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/editar-ruta" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {
                    try {
                        String idParam = request.getParameter("idRuta");
                        Long id = Long.parseLong(idParam);

                        Ruta rutaEditada = em.find(Ruta.class, id);

                        request.setAttribute("rutaEditada", rutaEditada);
                        vista = "FormRutas";

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error al cargar la ruta para editar.");
                        vista = "Error";
                    }
                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }

            }

            default -> {
                vista = "Error";
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String vista;
        String accion = request.getPathInfo();

        switch (accion) {

            case "/añadir-ruta" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    try {
                        //los campos "name" del formRutas:
                        String nombre = request.getParameter("nombre");
                        String descripcion = request.getParameter("descripcion");
                        double tiempo = Double.parseDouble(request.getParameter("tiempo"));
                        double distancia = Double.parseDouble(request.getParameter("distancia"));
                        String dificultad = request.getParameter("dificultad");
                        String tipoRuta = request.getParameter("tiporuta");

                        Part filePart = request.getPart("foto");
                        String nombreFoto = subirImagen(filePart, "rutas"); // <--- CARPETA RUTAS

                        if (nombreFoto == null) {
                            nombreFoto = "sin_fondo.png"; // Ten una imagen por defecto para rutas
                        }

                        Ruta ruta = new Ruta(nombre, descripcion, distancia, tiempo, dificultad, tipoRuta, nombreFoto, false, usuarioLogueado);
                        guardarRuta(ruta);
                        response.sendRedirect("/miapp/mis-rutas");

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error: datos no válidos");
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                        rd.forward(request, response);
                    }

                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/editar-ruta" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    try {
                        String nombre = request.getParameter("nombre");
                        String descripcion = request.getParameter("descripcion");
                        double tiempo = Double.parseDouble(request.getParameter("tiempo"));
                        double distancia = Double.parseDouble(request.getParameter("distancia"));
                        String dificultad = request.getParameter("dificultad");
                        String tipoRuta = request.getParameter("tiporuta");

                        long idRuta = Long.parseLong(request.getParameter("idRuta"));
                        Ruta rutaEditada = em.find(Ruta.class, idRuta);

                        rutaEditada.setNombre(nombre);
                        rutaEditada.setDescripcion(descripcion);
                        rutaEditada.setTiempo(tiempo);
                        rutaEditada.setDistancia(distancia);
                        rutaEditada.setDificultad(dificultad);
                        rutaEditada.setTipoRuta(tipoRuta);

                        Part filePart = request.getPart("foto");
                        String nombreFotoNueva = subirImagen(filePart, "rutas");
                        
                        // 3. SOLO CAMBIAR FOTO SI HAY UNA NUEVA
                        if (nombreFotoNueva != null) {
                            rutaEditada.setNombreFoto(nombreFotoNueva);
                        }

                        guardarRuta(rutaEditada);

                        response.sendRedirect("/miapp/mis-rutas");
                        return;

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error: datos no válidos en editar");
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                        rd.forward(request, response);
                    }

                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/eliminar-ruta" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    long idRuta = Long.parseLong(request.getParameter("idRuta"));
                    try {
                        eliminarRuta(idRuta);

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error: datos no válidos");
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                        rd.forward(request, response);
                    }

                    response.sendRedirect("/miapp/mis-rutas");
                    return;

                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/publicar-ruta" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    try {
                        Long idRuta = Long.parseLong(request.getParameter("idRuta"));
                        Ruta ruta = em.find(Ruta.class, idRuta);
                        ruta.setPublica(true);
                        guardarRuta(ruta);

                        response.sendRedirect("/miapp/mis-rutas");
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }

                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/retirar-ruta" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    try {
                        Long idRuta = Long.parseLong(request.getParameter("idRuta"));
                        Ruta ruta = em.find(Ruta.class, idRuta);
                        ruta.setPublica(false);
                        guardarRuta(ruta);

                        response.sendRedirect("/miapp/mis-rutas");
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }

                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void guardarRuta(Ruta r) {

        Long id = r.getId();
        try {
            utx.begin();
            if (id == null) {
                em.persist(r);
                Log.log(Level.INFO, "Nueva Ruta Registrado");
            } else {
                Log.log(Level.INFO, "Ruta {0} actualizado", id);
                em.merge(r);
            }
            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "excepción capturada", e);
            throw new RuntimeException(e);
        }
    }

    public void eliminarRuta(Long id) {

        try {
            utx.begin();

            Ruta r = em.find(Ruta.class, id);

            if (r != null) {
                em.remove(r); // La operación de borrado de JPA
                Log.log(Level.INFO, "Ruta {0} eliminado", id);
            } else {
                Log.log(Level.WARNING, "Intento de eliminar ID {0} que no existe.", id);
            }

            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "Excepción capturada al eliminar", e);
            throw new RuntimeException(e);
        }
    }

    private String subirImagen(Part part, String nombreCarpeta) {
        try {
            // 1. Validar que hay archivo
            if (part == null || part.getSize() == 0) {
                return null; // No subió nada
            }

            // 2. Obtener nombre original y extensión
            String nombreOriginal = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String extension = "";
            int i = nombreOriginal.lastIndexOf('.');
            if (i > 0) {
                extension = nombreOriginal.substring(i); // Ejemplo: .jpg, .png
            }

            // 3. Generar nombre ÚNICO (Evita que Pepe borre la foto de Juan)
            String nombreUnico = UUID.randomUUID().toString() + extension;

            // 4. Determinar ruta absoluta en el servidor
            // OJO: Esto guarda en la carpeta de despliegue. Al recompilar se borran.
            String rutaReal = getServletContext().getRealPath("/") + File.separator + "imagenes" + File.separator + nombreCarpeta;

            System.out.println(">>> ESTOY GUARDANDO LA FOTO AQUÍ: " + rutaReal);

            // Crear directorios si no existen
            File directorio = new File(rutaReal);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            // 5. Guardar el archivo
            Path rutaDestino = Paths.get(rutaReal, nombreUnico);
            try (java.io.InputStream input = part.getInputStream()) {
                Files.copy(input, rutaDestino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            return nombreUnico; // Devolvemos el nombre para guardarlo en la BBDD

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}