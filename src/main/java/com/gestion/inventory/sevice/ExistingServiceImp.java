package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.repository.ExistingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExistingServiceImp implements IExistingService{
    private final ExistingRepository existingRepository;
    public ExistingServiceImp(ExistingRepository existingRepository){this.existingRepository=existingRepository;}
    @Override
    public List<Existing> getAll() {return existingRepository.findAll();}

    @Override
    public Existing getById(Long id) {return existingRepository.findById(id).orElse(null);}

    @Override
    public void deleteById(Long id) {existingRepository.deleteById(id);}

    @Override
    public Existing save(Existing existing) {return existingRepository.save(existing);}
}
