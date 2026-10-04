package com.gestion.inventory.controller;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;
import com.gestion.inventory.sevice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private IUserService userService;

    @PostMapping("/login")
    public User login(@RequestBody UserRequest req) {
        List<User> todos = userService.getAll();

        User user = todos.stream()
                .filter(u -> u.getEmail() != null
                        && u.getEmail().equalsIgnoreCase(req.getEmail()))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));

        String stored = user.getPassword();
        String input  = req.getPassword();

        // Comparación directa en texto plano — sin BCrypt
        if (stored == null || !stored.equals(input))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");

        user.setPassword(null);
        return user;
    }
}