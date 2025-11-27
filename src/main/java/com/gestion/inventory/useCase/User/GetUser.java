package com.gestion.inventory.useCase.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.sevice.IUserService;

@Service
public class GetUser {
    @Autowired
    IUserService userService;
    public User execute(Long idUser) {
        return userService.getById(idUser);
    }
}
