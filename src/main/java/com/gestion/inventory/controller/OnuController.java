package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.dtorecust.OnuRequest;
import com.gestion.inventory.useCase.onu.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController@RequestMapping("/api/onu")
public class OnuController {
    @Autowired
    CreateOnu createOnu;
    @Autowired
    DeleteOnu deleteOnu;
    @Autowired
    GetAllOnu getAllOnu;
    @Autowired
    GetOnu getOnu;
    @Autowired
    UpdateOnu  updateOnu;

    @GetMapping
    public List<Onu> getAll(){
        return getAllOnu.execute();
    }
    @GetMapping("/{idOnu}")
    public Onu getById(@PathVariable("idOnu") Long idOnu) {
        return getOnu.execute(idOnu);
    }
    @PostMapping
    public Onu save(@RequestBody OnuRequest onuRequest) {
        return createOnu.execute(onuRequest);
    }

    @PutMapping("/{idOnu}")
    public Onu update(@PathVariable Long idOnu, @RequestBody OnuRequest onuRequest) {
        return updateOnu.execute(idOnu, onuRequest);
    }

    @DeleteMapping("/{idOnu}")
    public void deleteById(@PathVariable Long idOnu) {
        deleteOnu.execute(idOnu);
    }
}
