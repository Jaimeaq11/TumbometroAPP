package daw.controllers;

import daw.model.Ruta;
import daw.model.Usuarios;
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
            // Obtener todas las rutas públicas
            List<Ruta> todasRutas = em.createQuery("SELECT r FROM Ruta r", Ruta.class).getResultList();
            request.setAttribute("rutas", todasRutas);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Rutas.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("msg", "Error al cargar las rutas.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/Error.jsp");
            rd.forward(request, response);
        }

        /*String vista = "MisRutas";
        String accion;
        
        //por alguna razon no comprobaba lo de getservletpath pero no me acuerdo de por que lo quité
        if (request.getPathInfo() != null) {
            accion = request.getPathInfo();
        } else {
            accion = "/rutas";
        }

        switch (accion) {
            
             case "/rutas" -> {
                vista = "Rutas";
            }

            default -> {
                vista = "Error";
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
        rd.forward(request, response);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
