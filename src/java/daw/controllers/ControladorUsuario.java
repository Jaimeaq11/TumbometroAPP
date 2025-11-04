package daw.controllers;

import daw.model.Usuarios;
import daw.model.Moto;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/usuarios", "/usuario/*"})
public class ControladorUsuario extends HttpServlet {

    @PersistenceContext(unitName = "ElTumbometroPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(ControladorUsuario.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String vista;
        String accion = "/usuarios";
        if (request.getServletPath().equals("/usuario")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            } else {
                accion = "error";
            }
        }
        switch (accion) {
            case "/usuarios" -> {
                List<Usuarios> lu;
                TypedQuery<Usuarios> q = em.createNamedQuery("Usuarios.findAll", Usuarios.class);
                lu = q.getResultList();
                request.setAttribute("usuarios", lu);
                vista = "Usuarios";
            }
            case "/nuevo" -> {
                vista = "FormUsuarios";
            }
            case "/iniciarsesion" -> {
                vista = "IniciarSesión";
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
        if (accion.equals("/guardar")) {
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String contrasena = request.getParameter("contrasena");
            String descripcion = request.getParameter("descripcion");
            String marca = request.getParameter("marca");
            String modelo = request.getParameter("modelo");

            try {
                if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || descripcion.isEmpty()) {
                    throw new NullPointerException();
                }

                TypedQuery<Usuarios> q = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
                q.setParameter("correo", correo);
                List<Usuarios> lu = q.getResultList();

                if (!lu.isEmpty()) {
                    request.setAttribute("errorEmail", "Ese correo ya está registrado. Por favor, usa otro.");
                    vista = "formUsuarios";
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
                    rd.forward(request, response);
                } 
                else {
                    Moto m = new Moto(marca, modelo); 
                    
                    LocalDateTime fechaRegistro = LocalDateTime.now();
                    
                    Usuarios u = new Usuarios(nombre, descripcion,  correo, contrasena, fechaRegistro, m);
                    guardarUsuario(u);
                    response.sendRedirect("/miapp/usuarios");
                }

            } catch (Exception e) {
                request.setAttribute("msg", "Error: datos no válidos");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
                rd.forward(request, response);
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
            rd.forward(request, response);
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
                Log.log(Level.INFO, "Nuevo Usuario Guardado");
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
}