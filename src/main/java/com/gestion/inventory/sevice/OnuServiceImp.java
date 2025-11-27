package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.repository.OnuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OnuServiceImp implements IOnuservice {
    OnuRepository onuRepository;
    public OnuServiceImp(OnuRepository onuRepository) {
        this.onuRepository = onuRepository;}

    @Override
    public List<Onu> getAll() {
        return onuRepository.findAll();}

    @Override
    public Onu getById(Long id) {
        return onuRepository.findById(id).orElse(null);}

    @Override
    public void deleteById(Long id) {
        onuRepository.deleteById(id);}

    @Override
    public Onu save(Onu onu) {
        return onuRepository.save(onu);}
}
