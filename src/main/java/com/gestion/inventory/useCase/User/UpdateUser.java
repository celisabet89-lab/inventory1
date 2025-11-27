package com.gestion.inventory.useCase.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;
import com.gestion.inventory.sevice.IUserService;

@Service
public class UpdateUser {
    @Autowired
    IUserService userService;
    public User execute(Long id, UserRequest userRequest){
        User usertUpdete =userService.getById(id);
        usertUpdete.setEmail(userRequest.getEmail());
        usertUpdete.setPassword(userRequest.getPassword());
        return userService.save(usertUpdete);
}
}
