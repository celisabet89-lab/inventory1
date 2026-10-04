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
public class UpdateUser {
    @Autowired IUserService userService;
    @Autowired ProfileRepository profileRepository;
    @Autowired RolRepository rolRepository;

    public User execute(Long id, UserRequest r) {
        User user = userService.getById(id);

        // Actualizar credenciales
        if (r.getEmail() != null) user.setEmail(r.getEmail());
        if (r.getPassword() != null && !r.getPassword().isEmpty())
            user.setPassword(r.getPassword());

        // Buscar Rol
        Rol rol = user.getRol();
        if (r.getRolId() != null) {
            rol = rolRepository.findById(r.getRolId()).orElse(rol);
            user.setRol(rol);
        }

        Profile profile = user.getProfile();
        if (profile == null) profile = new Profile();
        if (r.getName() != null)     profile.setName(r.getName());
        if (r.getLastName() != null) profile.setLastName(r.getLastName());
        if (r.getTelefono() != null) profile.setTelefono(r.getTelefono());
        if (r.getCi() != null)       profile.setCi(r.getCi());
        if (rol != null)             profile.setCargo(rol.getCargo());
        profileRepository.save(profile);
        user.setProfile(profile);

        return userService.save(user);
    }
}