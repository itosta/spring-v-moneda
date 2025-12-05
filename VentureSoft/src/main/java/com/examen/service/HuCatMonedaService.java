package com.examen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.exception.MonedaNotFoundException;
import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import com.examen.repository.HuCatMonedaRepository;

@Service
public class HuCatMonedaService implements IHuCatMonedaService{

    @Autowired
    private HuCatMonedaRepository repository;

    @Override
    public List<HuCatMoneda> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<HuCatMoneda> findById(HuCatMonedaId id) {
    	repository.findById(id)
    	.orElseThrow(() -> new MonedaNotFoundException("La clave compuesta no existe"));
        return repository.findById(id);
    }

    @Override
    public HuCatMoneda save(HuCatMoneda moneda) {
        return repository.save(moneda);
    }

    @Override
    public HuCatMoneda update(HuCatMoneda moneda) {
    	HuCatMonedaId id = moneda.getId();
    	repository.findById(id)
    		.orElseThrow(() -> new MonedaNotFoundException("La clave compuesta no existe"));
        return repository.save(moneda);
    }

    @Override
    public void deleteById(HuCatMonedaId id) {
    	repository.findById(id)
    	.orElseThrow(() -> new MonedaNotFoundException("La clave compuesta no existe"));
    	
        repository.deleteById(id);
    }
}
