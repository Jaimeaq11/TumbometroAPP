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
import java.util.logging.Logger;

@WebServlet(name = "ControladorRutasPublicas", urlPatterns = {"/rutas"})

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

        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
