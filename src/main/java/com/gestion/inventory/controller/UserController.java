package com.gestion.inventory.controller;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;
import com.gestion.inventory.useCase.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")   // FIX: faltaba CrossOrigin
public class UserController {

    @Autowired CreateUser createUser;
    @Autowired DeleteUser deleteUser;
    @Autowired GetAllUser getAllUser;
    @Autowired GetUser getUser;
    @Autowired UpdateUser updateUser;

    @GetMapping
    public List<User> getAll() { return getAllUser.execute(); }

    @GetMapping("/{idUser}")
    public User getById(@PathVariable Long idUser) { return getUser.execute(idUser); }

    @PostMapping
    public User save(@RequestBody UserRequest userRequest) {
        return createUser.execute(userRequest);
    }

    @PutMapping("/{idUser}")
    public User update(@PathVariable Long idUser, @RequestBody UserRequest userRequest) {
        return updateUser.execute(idUser, userRequest);
    }

    @DeleteMapping("/{idUser}")
    public void deleteById(@PathVariable Long idUser) { deleteUser.execute(idUser); }
}