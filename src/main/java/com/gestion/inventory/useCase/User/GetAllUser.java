package com.gestion.inventory.useCase.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.sevice.IUserService;

@Service
public class GetAllUser {
    @Autowired
    IUserService userService;
    public List<User> execute(){
        return userService.getAll();
    }
}
