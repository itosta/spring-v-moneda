package com.examen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examen.exception.EmpleadoNotFoundException;
import com.examen.exception.MonedaNotFoundException;
import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import com.examen.model.HuEmpls;
import com.examen.model.HuEmplsId;
import com.examen.repository.HuCatMonedaRepository;
import com.examen.repository.HuEmplsRepository;

@Service
public class HuEmplsService implements IHuEmplsService{

    @Autowired
    private HuEmplsRepository empleadoRepository;
    
    @Autowired
    private HuCatMonedaRepository monedaRepository;
    
    @Override
    public List<HuEmpls> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<HuEmpls> findById(HuEmplsId id) {
    	empleadoRepository.findById(id)
        .orElseThrow(() -> new EmpleadoNotFoundException("La clave compuesta no existe"));
        return empleadoRepository.findById(id);
    }

    @Override
    public HuEmpls save(HuEmpls empleado) {
        HuCatMonedaId monedaId = new HuCatMonedaId(
            empleado.getId().getNumCia(),
            empleado.getClaveMoneda()
        );

        HuCatMoneda moneda = monedaRepository.findById(monedaId)
            .orElseThrow(() -> new RuntimeException("La moneda con id " + monedaId + " no existe"));

        empleado.setMoneda(moneda);

        return empleadoRepository.save(empleado);
    }

    @Override
    public HuEmpls update(HuEmpls empleado) {
    	HuEmplsId id = empleado.getId();
    	empleadoRepository.findById(id)
		.orElseThrow(() -> new MonedaNotFoundException("La clave compuesta no existe"));
    	return empleadoRepository.save(empleado);
    	
    }

    @Override
    public void deleteById(HuEmplsId id) {
    	empleadoRepository.findById(id)
    	.orElseThrow(() -> new MonedaNotFoundException("La clave compuesta no existe"));
    	
    	empleadoRepository.deleteById(id);
    }


}
