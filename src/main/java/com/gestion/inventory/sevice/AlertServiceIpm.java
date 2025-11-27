package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.repository.AlertRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AlertServiceIpm implements IAlertService {
    AlertRepository alertRepository;
    public AlertServiceIpm(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;}
    @Override
    public List<Alert> getAll() {
        return alertRepository.findAll();
    }

    @Override
    public Alert getById(Long id) {
        return alertRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        alertRepository.deleteById(id);
    }

    @Override
    public Alert save(Alert alert) {
        return alertRepository.save(alert);
    }
}
