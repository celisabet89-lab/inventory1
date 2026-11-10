package com.gestion.inventory.comfing;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * Protección contra fuerza bruta: tras 5 intentos fallidos seguidos
 * (mismo correo y misma dirección) el acceso se bloquea 15 minutos.
 */
@Component
public class IntentosLoginService {

    private static final int MAXIMO = 5;
    private static final Duration BLOQUEO = Duration.ofMinutes(15);

    private record Registro(int fallos, Instant desde) {}

    private final Map<String, Registro> registros = new ConcurrentHashMap<>();

    private static String clave(String email, String ip) {
        return (email == null ? "" : email.trim().toLowerCase()) + "|" + ip;
    }

    /** Minutos que faltan para poder intentar de nuevo; 0 si no está bloqueado. */
    public long minutosDeBloqueo(String email, String ip) {
        Registro r = registros.get(clave(email, ip));
        if (r == null || r.fallos() < MAXIMO) return 0;
        Duration pasado = Duration.between(r.desde(), Instant.now());
        if (pasado.compareTo(BLOQUEO) >= 0) {
            registros.remove(clave(email, ip));
            return 0;
        }
        return Math.max(1, BLOQUEO.minus(pasado).toMinutes());
    }

    public void fallo(String email, String ip) {
        registros.merge(clave(email, ip), new Registro(1, Instant.now()),
                (a, b) -> new Registro(a.fallos() + 1, a.fallos() + 1 >= MAXIMO ? Instant.now() : a.desde()));
    }

    public void exito(String email, String ip) {
        registros.remove(clave(email, ip));
    }
}