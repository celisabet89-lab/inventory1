package com.gestion.inventory.comfing;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.gestion.inventory.domain.User;

/**
 * Sesiones abiertas en memoria: token -> cargo del usuario.
 * Si se reinicia el servidor las sesiones se pierden y hay que volver a iniciar sesión.
 */
@Component
public class SesionService {

    public record Sesion(Long userId, String email, Rol rol) {}

    public enum Rol { ADMIN, ENCARGADO, TECNICO, OTRO }

    private final Map<String, Sesion> sesiones = new ConcurrentHashMap<>();

    public String abrir(User user) {
        String token = UUID.randomUUID().toString();
        sesiones.put(token, new Sesion(user.getIdUser(), user.getEmail(), rolDe(user)));
        return token;
    }

    public Sesion obtener(String token) {
        return token == null ? null : sesiones.get(token);
    }

    public void cerrar(String token) {
        if (token != null) sesiones.remove(token);
    }

    public static Rol rolDe(User u) {
        String cargo = "";
        if (u.getRol() != null && u.getRol().getCargo() != null) cargo = u.getRol().getCargo();
        else if (u.getProfile() != null && u.getProfile().getCargo() != null) cargo = u.getProfile().getCargo();
        String c = java.text.Normalizer.normalize(cargo.toLowerCase(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        if (c.contains("admin")) return Rol.ADMIN;
        if (c.contains("encargad")) return Rol.ENCARGADO;
        if (c.contains("tecnic")) return Rol.TECNICO;
        return Rol.OTRO;
    }
}