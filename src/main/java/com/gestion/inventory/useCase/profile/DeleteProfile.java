package com.gestion.inventory.useCase.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IProfileService;

@Service
public class DeleteProfile {
    @Autowired
    IProfileService profileService;
    public void execute (Long idProfile){
        profileService.deleteById(idProfile);
    }
}
