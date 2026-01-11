package com.gestion.inventory.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {

    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Backend funcionando correctamente");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return response;
    }

    @GetMapping("/hello")
    public Map<String, String> hello(@RequestParam(defaultValue = "Usuario") String name) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hola " + name);
        return response;
    }
}
