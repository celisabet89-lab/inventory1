package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Alert;

import java.util.List;

public interface IAlertService {
    List<Alert> getAll();
    Alert getById(Long id);
    void deleteById(Long id);
    Alert save(Alert alert);
}
