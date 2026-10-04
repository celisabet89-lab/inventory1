package com.gestion.inventory.useCase.User;

import com.gestion.inventory.domain.Profile;
import com.gestion.inventory.domain.Rol;
import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.UserRequest;
import com.gestion.inventory.repository.ProfileRepository;
import com.gestion.inventory.repository.RolRepository;
import com.gestion.inventory.sevice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUser {
    @Autowired IUserService userService;
    @Autowired ProfileRepository profileRepository;
    @Autowired RolRepository rolRepository;

    public User execute(UserRequest r) {
        // Buscar Rol primero
        Rol rol = null;
        if (r.getRolId() != null) {
            rol = rolRepository.findById(r.getRolId()).orElse(null);
        }

        Profile profile = new Profile();
        profile.setName(r.getName());
        profile.setLastName(r.getLastName());
        profile.setTelefono(r.getTelefono());
        profile.setCi(r.getCi());
        profile.setCargo(rol != null ? rol.getCargo() : null);
        profileRepository.save(profile);

        User user = new User();
        user.setEmail(r.getEmail());
        user.setPassword(r.getPassword());
        user.setProfile(profile);
        user.setRol(rol);
        return userService.save(user);
    }
}