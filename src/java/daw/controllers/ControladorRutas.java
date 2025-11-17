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
                    Usuarios usuarioLogueado = (Usuarios) request.getSession().getAttribute("usuarioLogueado");
                    Usuarios usuario = em.find(Usuarios.class, usuarioLogueado.getId());
                    List<Ruta> misRutas = usuario.getRutas();
                    request.setAttribute("misRutas", misRutas);

                } catch (Exception e) {
                    request.setAttribute("msg", "Error al cargar tus rutas.");
                }
            }
            
            case "/nueva" -> {
                vista = "FormRutas";
            }
            
            case "/editar" -> {
                
            }
            
            case "/eliminar" -> {
                
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

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
