package daw.controllers;

import daw.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class Filtro implements Filter {

    @PersistenceContext(unitName = "ElTumbometroPU")
    private EntityManager em;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false); // Coge la sesión SIN crear una nueva

        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("token_recuerdame")) {
                    
                    try {
                        Long usuarioId = Long.parseLong(c.getValue());
                        
                        Usuario usuario = em.find(Usuario.class, usuarioId);
                        if (usuario != null) {
                            req.getSession().setAttribute("usuarioLogueado", usuario);
                        }
                    } catch (Exception e) {
                        System.out.println("Error en filtro: " + e.getMessage());
                    }
                    
                    break;
                }
            }
        }
        
        chain.doFilter(request, response);
    }
}