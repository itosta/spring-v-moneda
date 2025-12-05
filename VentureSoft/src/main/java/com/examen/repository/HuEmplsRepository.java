package com.examen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examen.model.HuEmpls;
import com.examen.model.HuEmplsId;

public interface HuEmplsRepository extends JpaRepository<HuEmpls, HuEmplsId>{
	
    List<HuEmpls> findByIdNumCia(Integer numCia);

    List<HuEmpls> findByClaveMoneda(String claveMoneda);

}
