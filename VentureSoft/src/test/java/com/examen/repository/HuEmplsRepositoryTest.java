package com.examen.repository;

import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import com.examen.model.HuEmpls;
import com.examen.model.HuEmplsId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HuEmplsRepositoryTest {

    @Autowired
    private HuEmplsRepository huEmplsRepository;

    @Autowired
    private HuCatMonedaRepository huCatMonedaRepository;

    @Test
    void testGuardarYBuscarPorNumCia() {
        HuCatMonedaId monedaId = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(monedaId, "Dólar", "$", "A");
        huCatMonedaRepository.save(moneda);

        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(
                id,
                "USD",                // claveMoneda
                "Juan",               // nombre
                "Pérez",              // apellidoPaterno
                "López",              // apellidoMaterno
                "Desarrollador",      // puesto
                moneda                // relación con HuCatMoneda
        );
        huEmplsRepository.save(empleado);

        List<HuEmpls> resultado = huEmplsRepository.findByIdNumCia(1);

        assertFalse(resultado.isEmpty(), "Debería encontrar empleados con NUM_CIA = 1");
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("USD", resultado.get(0).getClaveMoneda());
        assertEquals("Dólar", resultado.get(0).getMoneda().getDescripcion());
    }

    @Test
    void testGuardarYBuscarPorClaveMoneda() {
        HuCatMonedaId monedaId = new HuCatMonedaId(2, "EUR");
        HuCatMoneda moneda = new HuCatMoneda(monedaId, "Euro", "€", "A");
        huCatMonedaRepository.save(moneda);

        HuEmplsId id = new HuEmplsId(2, 202);
        HuEmpls empleado = new HuEmpls(
                id,
                "EUR",                // claveMoneda
                "María",              // nombre
                "Gómez",              // apellidoPaterno
                "Ruiz",               // apellidoMaterno
                "Analista",           // puesto
                moneda                // relación con HuCatMoneda
        );
        huEmplsRepository.save(empleado);

        List<HuEmpls> resultado = huEmplsRepository.findByClaveMoneda("EUR");

        assertFalse(resultado.isEmpty(), "Debería encontrar empleados con CLAVE_MONEDA = EUR");
        assertEquals("María", resultado.get(0).getNombre());
        assertEquals("Analista", resultado.get(0).getPuesto());
        assertEquals("Euro", resultado.get(0).getMoneda().getDescripcion());
    }
}