package com.examen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import com.examen.service.HuCatMonedaService;

@RestController
@RequestMapping("/api/monedas")
public class HuCatMonedaController {
	
    @Autowired
    private HuCatMonedaService service;

    @GetMapping
    public List<HuCatMoneda> listar() {
        return service.findAll();
    }

    @GetMapping("/{numCia}/{claveMoneda}")
    public HuCatMoneda buscar(@PathVariable Integer numCia, @PathVariable String claveMoneda) {
        HuCatMonedaId id = new HuCatMonedaId(numCia, claveMoneda);
        return service.findById(id).get();
    }

    @PostMapping
    public HuCatMoneda crear(@RequestBody HuCatMoneda moneda) {
        return service.save(moneda);
    }

    @PutMapping("/{numCia}/{claveMoneda}")
    public HuCatMoneda actualizar(@PathVariable Integer numCia, @PathVariable String claveMoneda, @RequestBody HuCatMoneda moneda) {
        HuCatMonedaId id = new HuCatMonedaId(numCia, claveMoneda);
        service.findById(id).get();
        return service.update(moneda);
    }

    @DeleteMapping("/{numCia}/{claveMoneda}")
    public String eliminar(@PathVariable Integer numCia, @PathVariable String claveMoneda) {
        HuCatMonedaId id = new HuCatMonedaId(numCia, claveMoneda);
        service.deleteById(id);
        return "Moneda " + claveMoneda + " con numero de compañia " + numCia + " eliminada.";
    }


}
