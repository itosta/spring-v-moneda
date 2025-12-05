package com.examen.service;

import java.util.List;
import java.util.Optional;

import com.examen.model.HuEmpls;
import com.examen.model.HuEmplsId;

public interface IHuEmplsService {
	
    List<HuEmpls> findAll();
    Optional<HuEmpls> findById(HuEmplsId id);
    HuEmpls save(HuEmpls empleado);
    HuEmpls update(HuEmpls empleado);
    void deleteById(HuEmplsId id);

}
