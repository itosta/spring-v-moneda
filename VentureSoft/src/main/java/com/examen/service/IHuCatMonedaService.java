package com.examen.service;

import java.util.List;
import java.util.Optional;

import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;

public interface IHuCatMonedaService {
	
    List<HuCatMoneda> findAll();
    Optional<HuCatMoneda> findById(HuCatMonedaId id);
    HuCatMoneda save(HuCatMoneda moneda);
    HuCatMoneda update(HuCatMoneda moneda);
    void deleteById(HuCatMonedaId id);

}
