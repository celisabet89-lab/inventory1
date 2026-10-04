package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Profile;
import com.gestion.inventory.repository.ProfileRepository;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class ProfileServiceImp implements IProfileService{
    ProfileRepository profileRepository;
    public ProfileServiceImp(ProfileRepository profileRepository){
        this.profileRepository=profileRepository;
    }
    @Override
    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        profileRepository.deleteById(id);
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }
}
