package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SessionIntercepteur implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        // Pages publiques qui ne nécessitent pas de session
        List<String> publicPages = Arrays.asList("/mesevenements", "/mesevenements/login", "/mesevenements/loginform");

        if (publicPages.contains(requestURI)) {
            return true;
        }

        // Vérification de la session pour les pages protégées
        if (session == null || session.getAttribute("laisserPasser") == null) {
            response.sendRedirect("/mesevenements/loginform");
            return false;
        }

        // Mise à jour de l'activité
        session.setAttribute("lastAccess", new Date());

        return true;
    }


}