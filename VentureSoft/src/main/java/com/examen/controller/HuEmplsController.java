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

import com.examen.model.HuEmpls;
import com.examen.model.HuEmplsId;
import com.examen.service.HuEmplsService;

@RestController
@RequestMapping("/api/empleados")
public class HuEmplsController {

    @Autowired
    private HuEmplsService service;

    @GetMapping
    public List<HuEmpls> listar() {
        return service.findAll();
    }

    @GetMapping("/{numCia}/{numEmp}")
    public HuEmpls buscar(@PathVariable Integer numCia, @PathVariable Integer numEmp) {
        HuEmplsId id = new HuEmplsId(numCia, numEmp);
        return service.findById(id).get();
    }

    @PostMapping
    public HuEmpls crear(@RequestBody HuEmpls empleado) {
        return service.save(empleado);
    }

    @PutMapping
    public HuEmpls actualizar(@RequestBody HuEmpls empleado) {
        return service.update(empleado);
    }

    @DeleteMapping("/{numCia}/{numEmp}")
    public String eliminar(@PathVariable Integer numCia, @PathVariable Integer numEmp) {
        HuEmplsId id = new HuEmplsId(numCia, numEmp);
        service.deleteById(id);
        return "Empleado " + numEmp + " con numero de compañia " + numCia + " eliminado";
    }

}
