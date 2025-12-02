package daw.controllers;

import daw.model.Usuario;
import daw.model.Moto;
import daw.model.Ruta;
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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

//no puedo poner usuario/* si quiero controlar mis-rutas en el otro controlador
@WebServlet(name = "ControladorUsuario", urlPatterns = {"/usuarios", "/usuario/*"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)

public class ControladorUsuario extends HttpServlet {

    @PersistenceContext(unitName = "ElTumbometroPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(ControladorUsuario.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String vista;
        String accion;

        if (request.getServletPath().equals("/usuario") && request.getPathInfo() != null) {
            accion = request.getPathInfo();
        } else {
            accion = "/usuarios";
        }

        switch (accion) {
            case "/usuarios" -> {
                //solo el admin puede acceder aquí
                Boolean esAdmin = (Boolean) request.getSession().getAttribute("admin");
                
                if (esAdmin != null && esAdmin) {
                    List<Usuario> lu;
                    TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findAll", Usuario.class);
                    lu = q.getResultList();
                    request.setAttribute("listausuarios", lu);
                    vista = "Usuarios";

                } else {
                    request.setAttribute("msg", "NO ERES ADMINISTRADOR, FUERA.");
                    vista = "Error";
                }
            }

            case "/nuevo" -> {
                vista = "FormUsuario";
            }

            case "/editar-perfil" -> {
                //proteger las rutas
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {
                    request.setAttribute("usuarioEditado", usuarioLogueado);
                    vista = "FormUsuario";
                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/logout" -> {
                //protegemos las rutas
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {
                    request.getSession().invalidate();
                    response.sendRedirect("/miapp/inicio");
                    return;
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

            case "/registrar-usuario" -> {

                //los campos "name" del formUsuario:
                String nombre = request.getParameter("nombre");
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
                String contraseñaEncriptada = Encriptar.encriptar(contrasena);
                String biografia = request.getParameter("biografia");
                String marca = request.getParameter("marca");
                String modelo = request.getParameter("modelo");

                // esto evita el acceso desde fuera con valores nulos
                if (nombre == null || nombre.trim().isEmpty() || correo == null || correo.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty() || marca == null || marca.trim().isEmpty() || modelo == null || modelo.trim().isEmpty()) {
                    request.setAttribute("msg", "Error: No puedes enviar el formulario vacío.");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/FormUsuario.jsp");
                    rd.forward(request, response);
                    return;
                }

                Part filePart = request.getPart("foto"); // Coincide con name="foto" del HTML
                String nombreFoto = subirImagen(filePart, "perfiles"); // Guardará en /imagenes/perfiles/

                // Si no subió foto, pon una por defecto o null
                if (nombreFoto == null) {
                    nombreFoto = "sin_foto.jpg";
                }
                try {

                    TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                    q.setParameter("correo", correo);
                    List<Usuario> lu = q.getResultList();

                    if (!lu.isEmpty()) {
                        request.setAttribute("errorEmail", "Ese correo ya está registrado. Por favor, usa otro.");
                        vista = "FormUsuario";
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
                        rd.forward(request, response);
                    } else {
                        Moto moto = new Moto(marca, modelo);
                        ArrayList<Ruta> rutas = new ArrayList<Ruta>(); //vacio

                        Usuario u = new Usuario(nombre, correo, biografia, contraseñaEncriptada, moto, rutas, "usuario");
                        u.setNombreFoto(nombreFoto);

                        guardarUsuario(u);
                        response.sendRedirect("/miapp/inicio");
                    }

                } catch (Exception e) {
                    request.setAttribute("msg", "Error: datos no válidos");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                    rd.forward(request, response);
                }
            }

            case "/editar-usuario" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    //los campos "name" del formUsuario:
                    String nombre = request.getParameter("nombre");
                    String correo = request.getParameter("correo");
                    String biografia = request.getParameter("biografia");

                    try {
                        TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                        q.setParameter("correo", correo);
                        List<Usuario> lu = q.getResultList();

                        if (!lu.isEmpty() && !lu.get(0).getCorreo().equals(usuarioLogueado.getCorreo())) {
                            request.setAttribute("errorEmail", "Ese correo ya está registrado. Por favor, usa otro.");
                            vista = "FormUsuario";
                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
                            rd.forward(request, response);

                        } else {
                            usuarioLogueado.setNombre(nombre);
                            usuarioLogueado.setCorreo(correo);
                            usuarioLogueado.setBiografia(biografia);

                            Part filePart = request.getPart("foto");
                            String nombreFotoNueva = subirImagen(filePart, "perfiles");

                            if (nombreFotoNueva != null) {
                                usuarioLogueado.setNombreFoto(nombreFotoNueva);
                            }

                            guardarUsuario(usuarioLogueado);

                            //actualiza la sesion
                            request.getSession().setAttribute("usuarioLogueado", usuarioLogueado);
                            response.sendRedirect("/miapp/inicio");
                        }

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

            case "/login" -> {

                //aqui la proteccion es al reves (si no esta logueado, entonces dejamos que se loguee)
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado == null) {

                    //los campos "name" del formUsuario:
                    String correo = request.getParameter("correo");
                    String contrasena = request.getParameter("contrasena");

                    //esto es por si intentan acceder desde fuera (al no tener proteccion de rutas pueden escribir miapp/usuario/login
                    //y acceder, pero enviarán datos nulos, esto es para que si algún listo quiere acceder desde fuera lo redireccione
                    //al iniciar-sesion)
                    if (correo == null || contrasena == null) {
                        response.sendRedirect("/miapp/iniciar-sesion");
                    }

                    String contraseñaEncriptada = Encriptar.encriptar(contrasena);

                    try {
                        TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                        q.setParameter("correo", correo);
                        List<Usuario> lu = q.getResultList();

                        //comprobar si no esta vacio y si la contraseña coincide (no compruebo el correo porque ya lo hago arriba)
                        if (!lu.isEmpty() && lu.get(0).getContrasena().equals(contraseñaEncriptada)) {

                            usuarioLogueado = lu.get(0);
                            request.getSession().setAttribute("usuarioLogueado", usuarioLogueado);

                            //compruebo si es admin
                            if (usuarioLogueado.getRol().equals("admin")) {
                                request.getSession().setAttribute("admin", true);
                            }

                            response.sendRedirect("/miapp/inicio");

                        } else {
                            request.setAttribute("loginIncorrecto", "Correo o contraseña incorrectos.");
                            vista = "IniciarSesion";
                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
                            rd.forward(request, response);
                        }

                    } catch (Exception e) {
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                        rd.forward(request, response);
                    }

                } else {
                    response.sendRedirect("/miapp/inicio");
                }
            }

            case "/eliminar-usuario" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    String idParam = request.getParameter("idUsuario");
                    if (idParam != null) {

                        try {
                            Long id = Long.parseLong(idParam);

                            eliminarUsuario(id);

                            //si soy yo mismo me invalido la sesion
                            if (usuarioLogueado.getId().equals(id)) {

                                request.getSession().invalidate();
                                response.sendRedirect("/miapp/inicio");
                            } else {
                                response.sendRedirect("/miapp/usuarios");
                            }

                        } catch (Exception e) {
                            request.setAttribute("msg", "Error: datos no válidos");
                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                            rd.forward(request, response);
                        }
                    }

                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }

            case "/check-email" -> {
                response.setContentType("text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");

                String correo = request.getParameter("correo");
                String idParam = request.getParameter("idOculto");
                String respuesta;

                try {
                    TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                    q.setParameter("correo", correo);
                    List<Usuario> lu = q.getResultList();

                    if (!lu.isEmpty()) {

                        // si no hay id, estamos registrando
                        if (idParam == null || idParam.isEmpty()) {
                            respuesta = "DUPLICADO";
                        } else {
                            //estamos editando, comprobamos que no soy yo mismo
                            Long miId = Long.parseLong(idParam);

                            if (lu.get(0).getId().equals(miId)) {
                                //es mi propio correo, ok!
                                respuesta = "OK";
                            } else {
                                respuesta = "DUPLICADO";
                            }
                        }

                    } else {
                        respuesta = "OK";
                    }
                } catch (Exception e) {
                    Log.severe("Error comprobando email: " + e.getMessage());
                    respuesta = "Error en el servidor";
                }

                try (PrintWriter out = response.getWriter()) {
                    out.print(respuesta);
                }
            }

            default -> {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                rd.forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void guardarUsuario(Usuario u) {
        Long id = u.getId();
        try {
            utx.begin();
            if (id == null) {
                em.persist(u);
                Log.log(Level.INFO, "Nuevo Usuario Registrado");
            } else {
                Log.log(Level.INFO, "Usuario {0} actualizado", id);
                em.merge(u);
            }
            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "excepción capturada", e);
            throw new RuntimeException(e);
        }
    }

    public void eliminarUsuario(Long id) {

        try {
            utx.begin();

            Usuario u = em.find(Usuario.class, id);

            //borramos todas sus rutas en el caso de que tenga
            if (u != null) {
                List<Ruta> susRutas = u.getRutas();

                if (susRutas != null && !susRutas.isEmpty()) {
                    // Usamos una copia de la lista para evitar errores al borrar mientras recorremos
                    for (Ruta r : susRutas) {
                        em.remove(r);
                    }
                }

                em.remove(u);

                Log.log(Level.INFO, "Usuario {0} y todas sus rutas eliminados", id);
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
