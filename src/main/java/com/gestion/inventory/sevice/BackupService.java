package com.gestion.inventory.sevice;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Copias de seguridad de la base de datos (mysqldump) y pruebas de recuperación.
 *  - Copia automática programada (por defecto cada día a las 02:00) y manual.
 *  - Conserva los últimos N días.
 *  - Prueba de recuperación: restaura la copia en una base temporal, compara y la elimina.
 */
@Service
public class BackupService {

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final Pattern NOMBRE_VALIDO = Pattern.compile("^inventory_\\d{8}_\\d{6}\\.sql$");
    private static final ZoneId ZONA = ZoneId.of("America/La_Paz");

    @Value("${backup.directorio:./backups}")            private String directorio;
    @Value("${backup.retencion-dias:14}")               private int retencionDias;
    @Value("${backup.mysqldump:mysqldump}")             private String mysqldump;
    @Value("${backup.mysql:mysql}")                     private String mysql;
    @Value("${backup.db-host:localhost}")               private String host;
    @Value("${backup.db-nombre:inventory}")             private String baseDatos;
    @Value("${backup.db-usuario:${spring.datasource.username}}") private String usuario;
    @Value("${backup.db-clave:${spring.datasource.password}}")   private String clave;

    @Autowired private JdbcTemplate jdbc;

    public record Copia(String nombre, long bytes, String fecha) {}

    // ───────────────────────── Crear ─────────────────────────

    public Copia crear() throws IOException, InterruptedException {
        Path dir = Paths.get(directorio).toAbsolutePath();
        Files.createDirectories(dir);
        String nombre = "inventory_" + LocalDateTime.now(ZONA).format(FMT) + ".sql";
        Path destino = dir.resolve(nombre);

        ProcessBuilder pb = new ProcessBuilder(mysqldump,
                "-h", host, "-u", usuario,
                "--single-transaction", "--routines", "--triggers", "--default-character-set=utf8mb4",
                baseDatos);
        pb.environment().put("MYSQL_PWD", clave);          // la clave no va en la línea de comandos
        pb.redirectOutput(destino.toFile());
        Path errores = dir.resolve(nombre + ".err");
        pb.redirectError(errores.toFile());

        Process p = pb.start();
        if (!p.waitFor(10, TimeUnit.MINUTES)) {
            p.destroyForcibly();
            Files.deleteIfExists(destino);
            throw new IOException("La copia tardó demasiado y se canceló.");
        }
        if (p.exitValue() != 0 || Files.size(destino) == 0) {
            String detalle = Files.exists(errores) ? Files.readString(errores, StandardCharsets.UTF_8).trim() : "";
            Files.deleteIfExists(destino);
            Files.deleteIfExists(errores);
            throw new IOException("mysqldump falló" + (detalle.isEmpty() ? "." : ": " + detalle));
        }
        Files.deleteIfExists(errores);
        log.info("Copia de seguridad creada: {}", destino);
        limpiarAntiguas();
        return aCopia(destino);
    }

    @Scheduled(cron = "${backup.cron:0 0 2 * * *}", zone = "America/La_Paz")
    public void copiaAutomatica() {
        try {
            crear();
        } catch (Exception e) {
            log.error("Falló la copia automática: {}", e.getMessage());
        }
    }

    /** Cada domingo a las 03:30 se prueba que la última copia realmente se pueda restaurar. */
    @Scheduled(cron = "${backup.cron-prueba:0 30 3 * * SUN}", zone = "America/La_Paz")
    public void pruebaAutomatica() {
        try {
            List<Copia> copias = listar();
            if (!copias.isEmpty()) verificar(copias.get(0).nombre());
        } catch (Exception e) {
            log.error("Falló la prueba automática de recuperación: {}", e.getMessage());
        }
    }

    // ───────────────────────── Listar / descargar ─────────────────────────

    public List<Copia> listar() throws IOException {
        Path dir = Paths.get(directorio).toAbsolutePath();
        if (!Files.isDirectory(dir)) return List.of();
        try (Stream<Path> s = Files.list(dir)) {
            return s.filter(f -> NOMBRE_VALIDO.matcher(f.getFileName().toString()).matches())
                    .sorted(Comparator.comparing((Path f) -> f.getFileName().toString()).reversed())
                    .map(this::aCopia)
                    .toList();
        }
    }

    public Path ruta(String nombre) {
        if (nombre == null || !NOMBRE_VALIDO.matcher(nombre).matches())
            throw new IllegalArgumentException("Nombre de copia no válido");
        Path p = Paths.get(directorio).toAbsolutePath().resolve(nombre);
        if (!Files.isRegularFile(p)) throw new IllegalArgumentException("La copia no existe");
        return p;
    }

    private Copia aCopia(Path f) {
        try {
            String fecha = Instant.ofEpochMilli(Files.getLastModifiedTime(f).toMillis())
                    .atZone(ZONA).toLocalDateTime().toString();
            return new Copia(f.getFileName().toString(), Files.size(f), fecha);
        } catch (IOException e) {
            return new Copia(f.getFileName().toString(), 0, "");
        }
    }

    private void limpiarAntiguas() throws IOException {
        long limite = System.currentTimeMillis() - retencionDias * 86_400_000L;
        for (Copia c : listar()) {
            Path f = Paths.get(directorio).toAbsolutePath().resolve(c.nombre());
            if (Files.getLastModifiedTime(f).toMillis() < limite) Files.deleteIfExists(f);
        }
    }

    // ───────────────────────── Prueba de recuperación ─────────────────────────

    public Map<String, Object> verificar(String nombre) throws IOException, InterruptedException {
        Path copia = ruta(nombre);
        String temporal = baseDatos + "_prueba_" + LocalDateTime.now(ZONA).format(FMT);
        long t0 = System.currentTimeMillis();

        Map<String, Object> r = new LinkedHashMap<>();
        r.put("archivo", nombre);
        r.put("fecha", LocalDateTime.now(ZONA).toString());
        r.put("baseTemporal", temporal);

        boolean ok = false;
        String mensaje;
        long tablasCopia = 0, filasCopia = 0, filasActuales = 0;
        List<String> tablasFaltantes = new ArrayList<>();

        try {
            jdbc.execute("CREATE DATABASE `" + temporal + "` CHARACTER SET utf8mb4");

            ProcessBuilder pb = new ProcessBuilder(mysql, "-h", host, "-u", usuario, "--default-character-set=utf8mb4", temporal);
            pb.environment().put("MYSQL_PWD", clave);
            pb.redirectInput(copia.toFile());
            File err = File.createTempFile("restauracion", ".err");
            pb.redirectError(err);
            Process p = pb.start();
            boolean termino = p.waitFor(10, TimeUnit.MINUTES);
            if (!termino) p.destroyForcibly();

            if (!termino || p.exitValue() != 0) {
                String d = Files.readString(err.toPath(), StandardCharsets.UTF_8).trim();
                mensaje = "No se pudo restaurar la copia" + (d.isEmpty() ? "." : ": " + d);
            } else {
                List<String> tablasOrigen = nombresTablas(baseDatos);
                List<String> tablasRestauradas = nombresTablas(temporal);
                for (String t : tablasOrigen) if (!tablasRestauradas.contains(t)) tablasFaltantes.add(t);
                tablasCopia = tablasRestauradas.size();
                for (String t : tablasRestauradas) filasCopia += contar(temporal, t);
                for (String t : tablasOrigen) filasActuales += contar(baseDatos, t);
                ok = tablasCopia > 0 && tablasFaltantes.isEmpty();
                mensaje = ok
                        ? "La copia se restauró correctamente: " + tablasCopia + " tablas y " + filasCopia + " registros."
                        : "La restauración terminó, pero faltan tablas: " + tablasFaltantes;
            }
            err.delete();
        } catch (Exception e) {
            mensaje = "Error durante la prueba: " + e.getMessage();
        } finally {
            try { jdbc.execute("DROP DATABASE IF EXISTS `" + temporal + "`"); } catch (Exception ignore) {}
        }

        r.put("exitoso", ok);
        r.put("mensaje", mensaje);
        r.put("tablasRestauradas", tablasCopia);
        r.put("registrosRestaurados", filasCopia);
        r.put("registrosActuales", filasActuales);
        r.put("tablasFaltantes", tablasFaltantes);
        r.put("duracionSegundos", (System.currentTimeMillis() - t0) / 1000.0);
        guardarResultado(r);
        return r;
    }

    private List<String> nombresTablas(String esquema) {
        return jdbc.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = ? AND table_type = 'BASE TABLE'",
                String.class, esquema);
    }

    private long contar(String esquema, String tabla) {
        Long n = jdbc.queryForObject("SELECT COUNT(*) FROM `" + esquema + "`.`" + tabla + "`", Long.class);
        return n == null ? 0 : n;
    }

    // ───────────────────────── Historial de pruebas ─────────────────────────

    private Path archivoPruebas() throws IOException {
        Path dir = Paths.get(directorio).toAbsolutePath();
        Files.createDirectories(dir);
        return dir.resolve("pruebas_recuperacion.log");
    }

    private void guardarResultado(Map<String, Object> r) {
        try {
            String linea = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(r) + System.lineSeparator();
            Files.writeString(archivoPruebas(), linea, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            log.warn("No se pudo guardar el resultado de la prueba: {}", e.getMessage());
        }
    }

    public List<Map<String, Object>> pruebas() throws IOException {
        Path f = archivoPruebas();
        if (!Files.exists(f)) return List.of();
        com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
        List<Map<String, Object>> lista = new ArrayList<>();
        for (String linea : Files.readAllLines(f, StandardCharsets.UTF_8)) {
            if (linea.isBlank()) continue;
            try {
                lista.add(om.readValue(linea, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {}));
            } catch (Exception ignore) {}
        }
        java.util.Collections.reverse(lista);
        return lista;
    }
}