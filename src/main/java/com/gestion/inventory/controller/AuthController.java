package com.gestion.inventory.controller;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;
import com.gestion.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = (User) userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        if (!user.getPassword().equals(password)) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Contraseña incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}