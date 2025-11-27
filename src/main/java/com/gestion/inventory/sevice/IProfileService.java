package com.gestion.inventory.sevice;

import java.util.List;

import com.gestion.inventory.domain.Profile;

public interface IProfileService {
    List<Profile> getAll();
    Profile getById(Long id);
    void deleteById(Long id);
    Profile save(Profile profile);
}
