package com.examen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;

public interface HuCatMonedaRepository extends JpaRepository<HuCatMoneda, HuCatMonedaId>{
	
}
