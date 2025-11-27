package com.gestion.inventory.useCase.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Profile;
import com.gestion.inventory.domain.dtorecust.ProfileRequest;
import com.gestion.inventory.sevice.IProfileService;

@Service
public class UpdateProfile {
    @Autowired
    IProfileService profileService;
    public Profile execute(Long id, ProfileRequest profilerRequest){
        Profile profileUpdate=profileService.getById(id);
        profileUpdate.setName(profilerRequest.getName());
        profileUpdate.setLastName(profilerRequest.getLastName());
        profileUpdate.setTelefono(profilerRequest.getTelefono());
        profileUpdate.setCi(profilerRequest.getCi());
        profileUpdate.setCargo(profilerRequest.getCargo());
        return profileService.save(profileUpdate);
    }
}
