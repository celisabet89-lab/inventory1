package com.gestion.inventory.useCase.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Profile;
import com.gestion.inventory.sevice.IProfileService;

@Service
public class GetAllProfile {
    @Autowired
    IProfileService profileService;
    public List<Profile> execute(){
        return profileService.getAll();
    }
}
