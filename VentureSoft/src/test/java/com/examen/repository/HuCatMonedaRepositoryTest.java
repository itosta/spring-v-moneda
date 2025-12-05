package com.examen.repository;

import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HuCatMonedaRepositoryTest {

    @Autowired
    private HuCatMonedaRepository huCatMonedaRepository;

    @Test
    void testGuardarYBuscarMoneda() {
        // Crea
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        // Guarda
        huCatMonedaRepository.save(moneda);

        // Busca
        Optional<HuCatMoneda> resultado = huCatMonedaRepository.findById(id);

        // Valida
        assertTrue(resultado.isPresent(), "La moneda debería existir");
        assertEquals("USD", resultado.get().getId().getClaveMoneda());
        assertEquals("Dólar", resultado.get().getDescripcion());
        assertEquals("$", resultado.get().getSimbolo());
        assertEquals("A", resultado.get().getEstatus());
    }

    @Test
    void testBuscarMonedaInexistente() {
        HuCatMonedaId id = new HuCatMonedaId(99, "XXX");
        Optional<HuCatMoneda> resultado = huCatMonedaRepository.findById(id);

        assertTrue(resultado.isEmpty(), "La moneda no deberia existir");
    }
}