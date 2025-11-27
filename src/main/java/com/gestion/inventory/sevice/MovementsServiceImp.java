package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.repository.MovementsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementsServiceImp implements IMovementsService {

    private final MovementsRepository movementsRepository;

    public MovementsServiceImp(MovementsRepository movementsRepository) {
        this.movementsRepository = movementsRepository;
    }

    @Override
    public List<Movements> getAll() {
        return movementsRepository.findAll();
    }

    @Override
    public Movements getById(Long id) {
        return movementsRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        movementsRepository.deleteById(id);
    }

    @Override
    public Movements save(Movements movements) {
        return movementsRepository.save(movements);
    }
}
