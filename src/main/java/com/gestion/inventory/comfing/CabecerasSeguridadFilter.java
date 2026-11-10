package com.gestion.inventory.comfing;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Cabeceras de seguridad recomendadas por OWASP en todas las respuestas. */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CabecerasSeguridadFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        res.setHeader("X-Content-Type-Options", "nosniff");
        res.setHeader("X-Frame-Options", "DENY");
        res.setHeader("Referrer-Policy", "no-referrer");
        res.setHeader("Permissions-Policy", "camera=(self), microphone=(), geolocation=()");
        res.setHeader("Content-Security-Policy", "default-src 'none'; frame-ancestors 'none'");
        if (req.getRequestURI().startsWith("/api/")) {
            // Las respuestas de la API (usuarios, finanzas) no deben quedar en caché del navegador.
            res.setHeader("Cache-Control", "no-store");
        }
        if (req.isSecure()) {
            res.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        }
        chain.doFilter(req, res);
    }
}