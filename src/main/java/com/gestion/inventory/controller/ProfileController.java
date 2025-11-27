package com.gestion.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gestion.inventory.domain.Profile;
import com.gestion.inventory.domain.dtorecust.ProfileRequest;
import com.gestion.inventory.useCase.profile.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    CreateProfile createProfile;
    @Autowired
    DeleteProfile deleteProfile;
    @Autowired
    GetAllProfile getAllProfile;
    @Autowired
    GetProfile getProfile;
    @Autowired
    UpdateProfile updateProfile;

    @GetMapping
    public List<Profile> getAll(){
        return getAllProfile.execute();
    }
    @GetMapping("/{idProfile}")
    public Profile getById(@PathVariable Long idProfile){
        return getProfile.execute(idProfile);
    }
    @PostMapping
    public Profile save(@RequestBody ProfileRequest profileRequest){
        return createProfile.execute(profileRequest);
    }
    @PutMapping("/{idProfile}")
    public Profile update(@PathVariable Long idProfile, @RequestBody ProfileRequest profileRequest){
        return updateProfile.execute(idProfile, profileRequest);
    }
    @DeleteMapping("/{idProfile}")
    public void deleteById(@PathVariable Long idProfile){
        deleteProfile.execute(idProfile);
    }
}
