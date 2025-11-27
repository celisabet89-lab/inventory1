package com.gestion.inventory.useCase.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IUserService;

@Service
public class DeleteUser {
    @Autowired
    IUserService userService;
    public void execute(Long idUser) {
        userService.deleteById(idUser);
    }
}
