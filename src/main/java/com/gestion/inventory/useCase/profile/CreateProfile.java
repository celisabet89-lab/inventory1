package com.gestion.inventory.useCase.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Profile;
import com.gestion.inventory.domain.dtorecust.ProfileRequest;
import com.gestion.inventory.sevice.IProfileService;

@Service
public class CreateProfile {
    @Autowired
    IProfileService profileService;
    public Profile execute(ProfileRequest profileRequest){
        Profile profile=new Profile();
        profile.setName(profileRequest.getName());
        profile.setLastName(profileRequest.getLastName());
        profile.setTelefono(profileRequest.getTelefono());
        profile.setCi(profileRequest.getCi());
        profile.setCargo(profileRequest.getCargo());
        return profileService.save(profile);
    }
}
