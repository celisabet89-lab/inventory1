package com.gestion.inventory.useCase.sevice;

import com.gestion.inventory.domain.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User getById(Long id);
    void deleteById(Long id);
    User save(User user);
}
