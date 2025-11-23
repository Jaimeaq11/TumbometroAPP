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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "ControladorRutas", urlPatterns = {"/mis-rutas", "/mis-rutas/*"})

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
                vista = "MisRutas"; //asumimos con algun valor para que no de error
                try {
                    Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
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
            }

            case "/nueva-ruta" -> {
                vista = "FormRutas";
            }

            case "/editar-ruta" -> {
                vista = "FormRutas";
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

            /*case "/nueva-ruta" -> {
                //los campos "name" del formUsuario:
                String nombre = request.getParameter("nombre");
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
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
            }
            
            case "/editar" -> {
                vista = "FormRutas";
            }*/
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
