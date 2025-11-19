package daw.controllers;

import daw.model.Usuarios;
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
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

//no puedo poner usuario/* si quiero controlar mis-rutas en el otro controlador
@WebServlet(name = "ControladorUsuario", urlPatterns = {"/usuarios", "/usuario/*"})

@MultipartConfig
public class ControladorUsuario extends HttpServlet {

    @PersistenceContext(unitName = "ElTumbometroPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(ControladorUsuario.class.getName());

    //private static final String directorio_url = "imagenes" + File.separator + "perfiles";
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
                List<Usuarios> lu;
                TypedQuery<Usuarios> q = em.createNamedQuery("Usuarios.findAll", Usuarios.class);
                lu = q.getResultList();
                request.setAttribute("listausuarios", lu);
                vista = "Usuarios";
            }

            case "/nuevo" -> {
                vista = "FormUsuario";
            }

            case "/editar" -> {
                Usuarios usuarioLogueado = (Usuarios) request.getSession().getAttribute("usuarioLogueado");
                request.setAttribute("usuarioEditado", usuarioLogueado);
                vista = "FormUsuario";
            }

            case "/logout" -> {
                request.getSession().invalidate();

                //borramos la cookie de "recuerdame"
                /*Cookie cookieRecordarme = new Cookie("token_recuerdame", null);
                cookieRecordarme.setMaxAge(0);
                response.addCookie(cookieRecordarme);*/
                //tenemos que hacerlo asi para que no se quede la ruta .../logout en la url
                response.sendRedirect("/miapp/inicio");
                return;
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

            case "/registrar" -> {

                //los campos "name" del formUsuario:
                String nombre = request.getParameter("nombre");
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
                String contraseñaEncriptada = Encriptar.encriptar(contrasena);
                //String rutaFoto = request.getParameter("rutaFoto");
                String biografia = request.getParameter("biografia");
                String marca = request.getParameter("marca");
                String modelo = request.getParameter("modelo");

                try {
                    if (marca == null || modelo == null || marca.isEmpty() || modelo.isEmpty()) {
                        request.setAttribute("faltaVehiculo", "Debes registrar un vehículo");
                        vista = "FormUsuario";
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
                        rd.forward(request, response);
                    } else {

                        TypedQuery<Usuarios> q = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
                        q.setParameter("correo", correo);
                        List<Usuarios> lu = q.getResultList();

                        if (!lu.isEmpty()) {
                            request.setAttribute("errorEmail", "Ese correo ya está registrado. Por favor, usa otro.");
                            vista = "FormUsuario";
                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
                            rd.forward(request, response);
                        } else {
                            Moto moto = new Moto(marca, modelo);
                            ArrayList<Ruta> rutas = new ArrayList<Ruta>(); //vacio

                            //como no se lo que contiene lo pongo a ""
                            /*if (rutaFoto.isEmpty()) {
                            rutaFoto = "";
                        }*/
                            Usuarios u = new Usuarios(nombre, correo, biografia, contraseñaEncriptada, moto, rutas, "usuario");
                            guardarUsuario(u);
                            response.sendRedirect("/miapp/inicio");
                        }
                    }

                } catch (Exception e) {
                    request.setAttribute("msg", "Error: datos no válidos");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                    rd.forward(request, response);
                }
            }

            case "/editar" -> {

                //los campos "name" del formUsuario:
                String nombre = request.getParameter("nombre");
                String correo = request.getParameter("correo");
                //String rutaFoto = request.getParameter("rutaFoto");
                String biografia = request.getParameter("biografia");

                try {
                    Usuarios usuarioLogueado = (Usuarios) request.getSession().getAttribute("usuarioLogueado");

                    TypedQuery<Usuarios> q = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
                    q.setParameter("correo", correo);
                    List<Usuarios> lu = q.getResultList();

                    if (!lu.isEmpty() && !lu.get(0).getCorreo().equals(usuarioLogueado.getCorreo())) {
                        request.setAttribute("errorEmail", "Ese correo ya está registrado. Por favor, usa otro.");
                        vista = "FormUsuario";
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
                        rd.forward(request, response);

                    } else {
                        usuarioLogueado.setNombre(nombre);
                        usuarioLogueado.setCorreo(correo);
                        usuarioLogueado.setBiografia(biografia);
                        //usuarioLogueado.setRutaFoto(rutaFoto);

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
            }

            case "/login" -> {
                //los campos "name" del formUsuario:
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
                String contraseñaEncriptada = Encriptar.encriptar(contrasena);

                try {
                    TypedQuery<Usuarios> q = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
                    q.setParameter("correo", correo);
                    List<Usuarios> lu = q.getResultList();

                    //comprobar si no esta vacio y si la contraseña coincide (no compruebo el correo porque ya lo hago arriba)
                    if (!lu.isEmpty() && lu.get(0).getContrasena().equals(contraseñaEncriptada)) {

                        Usuarios usuarioLogueado = lu.get(0);
                        request.getSession().setAttribute("usuarioLogueado", usuarioLogueado);

                        //compruebo si es admin
                        if (usuarioLogueado.getRol().equals("admin")) {
                            request.getSession().setAttribute("admin", true);
                        }

                        //logica del check box de "recuerdame"
                        /*String recuerdame = request.getParameter("recuerdame");
                    if (recuerdame != null && recuerdame.equals("on")) {
                        //creamos el token persistente
                        String token = usuarioLogueado.getId().toString();
                        Cookie cookieRecordarme = new Cookie("token_recuerdame", token);

                        //dura 30 dias (en segundos)
                        cookieRecordarme.setMaxAge(30 * 24 * 60 * 60);
                        response.addCookie(cookieRecordarme);
                    }*/
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
            }

            case "/eliminar-usuario" -> {
                String idParam = request.getParameter("id");

                if (idParam != null) {
                    try {
                        Long id = Long.parseLong(idParam);

                        eliminarUsuario(id);

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error: datos no válidos");
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                        rd.forward(request, response);
                    }
                }

                response.sendRedirect("/miapp/usuarios");
                
                //esto se hace para que no haga el forward
                //return;
            }
            
            case "/eliminar-cuenta" -> {
                String idParam = request.getParameter("id");

                if (idParam != null) {
                    try {
                        Long id = Long.parseLong(idParam);

                        eliminarUsuario(id);
                        request.getSession().invalidate();

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error: datos no válidos");
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                        rd.forward(request, response);
                    }
                }

                response.sendRedirect("/miapp/usuarios");
                
                //esto se hace para que no haga el forward
                //return;
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

    public void guardarUsuario(Usuarios u) {
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

            Usuarios u = em.find(Usuarios.class, id);

            if (u != null) {
                em.remove(u); // La operación de borrado de JPA
                Log.log(Level.INFO, "Usuario {0} eliminado", id);
            } else {
                Log.log(Level.WARNING, "Intento de eliminar ID {0} que no existe.", id);
            }

            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "Excepción capturada al eliminar", e);
            throw new RuntimeException(e);
        }
    }
}
