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
                List<Usuario> lu;
                TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findAll", Usuario.class);
                lu = q.getResultList();
                request.setAttribute("listausuarios", lu);
                vista = "Usuarios";
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

                //protegemos las rutas
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    //los campos "name" del formUsuario:
                    String nombre = request.getParameter("nombre");
                    String correo = request.getParameter("correo");
                    String contrasena = request.getParameter("contrasena");
                    String contraseñaEncriptada = Encriptar.encriptar(contrasena);
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
                                guardarUsuario(u);
                                response.sendRedirect("/miapp/inicio");
                            }
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