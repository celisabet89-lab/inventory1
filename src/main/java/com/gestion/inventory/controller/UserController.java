package com.gestion.inventory.controller;

import java.util.List;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.inventory.useCase.User.CreateUser;
import com.gestion.inventory.useCase.User.DeleteUser;
import com.gestion.inventory.useCase.User.GetAllUser;
import com.gestion.inventory.useCase.User.GetUser;
import com.gestion.inventory.useCase.User.UpdateUser;

@RestController
@RequestMapping("/api/user")
public class UserController { 
    @Autowired
    CreateUser createUser;
    @Autowired
    DeleteUser deleteUser;
    @Autowired
    GetAllUser getAllUser;
    @Autowired
    GetUser getUser;
    @Autowired
    UpdateUser updateUser;

    @GetMapping
    public List<User> getAll(){
        return getAllUser.execute();
    }
    @GetMapping("/{idUser}")
    public User getById(@PathVariable("idUser") Long idUser) {
        return getUser.execute(idUser);
    }

    @PostMapping
    public User save(@RequestBody UserRequest userRequest) {
        return createUser.execute(userRequest);
    }

    @PutMapping("/{idUser}")
    public User update(@PathVariable Long idUser, @RequestBody UserRequest userRequest) {
        return updateUser.execute(idUser, userRequest);
    }

    @DeleteMapping("/{idUser}")
    public void deleteById(@PathVariable Long idUser) {
        deleteUser.execute(idUser);
    }
    
}
