package com.gestion.inventory.useCase.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;
import com.gestion.inventory.sevice.IUserService;

@Service
public class CreateUser {
    @Autowired
    IUserService userService;
    public User execute(UserRequest userRequest){
        User user= new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return userService.save(user);
    }
}
