package com.gestion.inventory.comfing;

import java.util.List;

import com.gestion.inventory.comfing.SesionService.Rol;

/**
 * Reglas de acceso del servidor por cargo (se corresponden con las del frontend).
 *  - ADMIN     : todo.
 *  - ENCARGADO : todo, pero no puede crear/editar/eliminar usuarios ni roles, ni usar las copias de seguridad.
 *  - TECNICO   : consulta productos, stock, ONUs y materiales; registra uso/devolución/confirmación
 *                de sus materiales; recojo y mantenimiento de ONUs; registra clientes ONU.
 *  - OTRO      : solo consulta de productos, categorías y unidades de medida.
 */
public final class AccesoPorRol {

    private AccesoPorRol() {}

    private static final List<String> CONSULTA_TECNICO = List.of(
            "/api/product", "/api/categoria", "/api/unidad_medida", "/api/existing", "/api/movements",
            "/api/onu", "/api/onu-operaciones", "/api/cliente-onu", "/api/tipo-cliente-onu",
            "/api/marca-onu", "/api/materiales-tecnico");

    private static final List<String> CONSULTA_OTRO = List.of(
            "/api/product", "/api/categoria", "/api/unidad_medida");

    private static boolean bajo(String ruta, String base) {
        return ruta.equals(base) || ruta.startsWith(base + "/");
    }

    private static boolean bajoAlguno(String ruta, List<String> bases) {
        for (String b : bases) if (bajo(ruta, b)) return true;
        return false;
    }

    public static boolean permitido(Rol rol, String metodo, String ruta) {
        String m = metodo.toUpperCase();
        boolean lectura = m.equals("GET") || m.equals("HEAD");

        switch (rol) {
            case ADMIN:
                return true;

            case ENCARGADO:
                if (bajo(ruta, "/api/backup")) return false;   // copias de seguridad: solo administrador
                if (bajo(ruta, "/api/user") || bajo(ruta, "/api/rol")) return lectura; // sin gestión de usuarios
                return true;

            case TECNICO:
                if (lectura) return bajoAlguno(ruta, CONSULTA_TECNICO);
                // Sus materiales: confirmar, usar y devolver (PUT). No puede entregar (POST) ni anular (DELETE).
                if (bajo(ruta, "/api/materiales-tecnico")) return m.equals("PUT");
                // Recojo, diagnóstico y asignación a cliente.
                if (bajo(ruta, "/api/onu-operaciones")) return m.equals("POST");
                // Registrar a qué cliente se instaló.
                if (bajo(ruta, "/api/cliente-onu")) return m.equals("POST") || m.equals("PUT") || m.equals("PATCH");
                return false;

            default:
                return lectura && bajoAlguno(ruta, CONSULTA_OTRO);
        }
    }
}