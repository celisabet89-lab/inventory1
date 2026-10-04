package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.User;
import com.gestion.inventory.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImp implements IUserService {
    

    UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;}
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
