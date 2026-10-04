package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Rol;
import com.gestion.inventory.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RolServiceIpm implements IRolService{
    RolRepository rolRepository;
    public RolServiceIpm(RolRepository rolRepository) {
        this.rolRepository = rolRepository;}
    @Override
    public List<Rol> getAll() {
        return rolRepository.findAll();}

    @Override
    public Rol getById(Long id) {
        return rolRepository.findById(id).orElse(null);}

    @Override
    public void deleteById(Long id) {
        rolRepository.deleteById(id);}

    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);}
}
