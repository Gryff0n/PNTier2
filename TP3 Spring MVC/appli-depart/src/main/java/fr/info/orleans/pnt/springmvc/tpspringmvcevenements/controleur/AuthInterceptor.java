package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.controleur;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (uri.equals("/") || uri.equals("/mesevenements/loginform") || uri.equals("/mesevenements/login")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("laisserPasser") == null) {
            response.sendRedirect("/mesevenements/loginform");
            return false;
        }

        return true;
    }
}