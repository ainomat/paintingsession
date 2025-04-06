package dev.artsupplier.paintingtracker.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

@Component
public class AccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        //redirect to access denied page
        //response.sendRedirect(request.getContextPath() + "/access-denied");
        response.sendRedirect("/access-denied");
    }
}
