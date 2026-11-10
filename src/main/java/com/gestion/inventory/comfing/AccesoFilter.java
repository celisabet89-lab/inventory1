package com.gestion.inventory.comfing;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Exige un token válido en /api/** y aplica las reglas por cargo. */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class AccesoFilter extends OncePerRequestFilter {

    @Autowired SesionService sesiones;

    private static boolean publica(String ruta) {
        return ruta.equals("/api/auth/login")
                || ruta.equals("/api/alert/order-supplier")
                || ruta.equals("/api/alert/check-stock");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String ruta = req.getRequestURI();

        // Solo se controla la API; las preflight de CORS pasan.
        if (!ruta.startsWith("/api/") || "OPTIONS".equalsIgnoreCase(req.getMethod()) || publica(ruta)) {
            chain.doFilter(req, res);
            return;
        }

        String cab = req.getHeader("Authorization");
        String token = (cab != null && cab.startsWith("Bearer ")) ? cab.substring(7).trim() : null;
        SesionService.Sesion s = sesiones.obtener(token);

        if (s == null) {
            rechazar(res, 401, "Sesión no válida o expirada. Inicia sesión nuevamente.");
            return;
        }
        if (!AccesoPorRol.permitido(s.rol(), req.getMethod(), ruta)) {
            rechazar(res, 403, "Tu cargo no tiene permiso para esta acción.");
            return;
        }
        chain.doFilter(req, res);
    }

    private void rechazar(HttpServletResponse res, int codigo, String mensaje) throws IOException {
        res.setStatus(codigo);
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write("{\"message\":\"" + mensaje + "\"}");
    }
}