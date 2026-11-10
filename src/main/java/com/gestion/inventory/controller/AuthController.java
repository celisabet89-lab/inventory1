package com.gestion.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.gestion.inventory.comfing.IntentosLoginService;
import com.gestion.inventory.comfing.SesionService;
import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;
import com.gestion.inventory.sevice.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final String CREDENCIALES_INVALIDAS = "Correo o contraseña incorrectos";

    @Autowired private IUserService userService;
    @Autowired private SesionService sesiones;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private IntentosLoginService intentos;

    @PostMapping("/login")
    public User login(@RequestBody UserRequest req, HttpServletRequest http) {
        String ip = http.getRemoteAddr();

        long espera = intentos.minutosDeBloqueo(req.getEmail(), ip);
        if (espera > 0)
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Demasiados intentos fallidos. Espera " + espera + " minuto(s) e inténtalo de nuevo.");

        List<User> todos = userService.getAll();
        User user = todos.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(req.getEmail()))
                .findFirst()
                .orElse(null);

        String guardada = user == null ? null : user.getPassword();
        String ingresada = req.getPassword();
        boolean cifrada = guardada != null && guardada.startsWith("$2");

        boolean correcta = guardada != null && ingresada != null
                && (cifrada ? passwordEncoder.matches(ingresada, guardada) : guardada.equals(ingresada));

        if (!correcta) {
            intentos.fallo(req.getEmail(), ip);
            // Mismo mensaje exista o no el correo, para no revelar qué usuarios hay.
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, CREDENCIALES_INVALIDAS);
        }
        intentos.exito(req.getEmail(), ip);

        // Contraseñas antiguas en texto plano: se cifran en el primer inicio de sesión correcto.
        if (!cifrada) {
            user.setPassword(passwordEncoder.encode(ingresada));
            userService.save(user);
        }

        // La contraseña no se envía al navegador (el campo es solo de escritura).
        user.setToken(sesiones.abrir(user));
        return user;
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(value = "Authorization", required = false) String cab) {
        if (cab != null && cab.startsWith("Bearer ")) sesiones.cerrar(cab.substring(7).trim());
    }
}