package com.gestion.inventory.useCase.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Profile;
import com.gestion.inventory.sevice.IProfileService;

@Service
public class GetProfile {
    @Autowired
    IProfileService profileService;
    public Profile execute(long id){
        return profileService.getById(id);
    }
}
