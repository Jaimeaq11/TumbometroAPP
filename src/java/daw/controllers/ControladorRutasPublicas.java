package daw.controllers;

import daw.model.Ruta;
import daw.model.Usuario;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ControladorRutasPublicas", urlPatterns = {"/rutas", "/rutas/*"})

public class ControladorRutasPublicas extends HttpServlet {

    @PersistenceContext(unitName = "ElTumbometroPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(ControladorRutasPublicas.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Ruta> rutasPublicas = em.createNamedQuery("Ruta.findPublicas", Ruta.class).getResultList();
            request.setAttribute("rutas", rutasPublicas);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Rutas.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR DETALLADO: " + e.getMessage());

            request.setAttribute("msg", "Error al cargar las rutas.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getPathInfo();
        String vista;

        switch (accion) {
            case "/eliminar-ruta" -> {

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado != null) {

                    long idRuta = Long.parseLong(request.getParameter("idRuta"));
                    try {
                        eliminarRuta(idRuta);

                    } catch (Exception e) {
                        request.setAttribute("msg", "Error: datos no válidos eliminando la ruta");
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
                        rd.forward(request, response);
                    }

                    response.sendRedirect("/miapp/rutas");
                    return;

                } else {
                    request.setAttribute("msg", "NO TIENES ACCESO.");
                    vista = "Error";
                }
            }
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
