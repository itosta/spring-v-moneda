package com.examen.service;

import com.examen.exception.MonedaNotFoundException;
import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import com.examen.repository.HuCatMonedaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HuCatMonedaServiceTest {

    private HuCatMonedaRepository repository;
    private HuCatMonedaService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(HuCatMonedaRepository.class);
        service = new HuCatMonedaService();
        // Inyección manual del mock (simulando @Autowired)
        service = new HuCatMonedaService();
        // Usamos reflexión para asignar el mock al campo privado
        try {
            var field = HuCatMonedaService.class.getDeclaredField("repository");
            field.setAccessible(true);
            field.set(service, repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindAll() {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(repository.findAll()).thenReturn(List.of(moneda));

        List<HuCatMoneda> resultado = service.findAll();

        assertEquals(1, resultado.size());
        assertEquals("USD", resultado.get(0).getId().getClaveMoneda());
    }

    @Test
    void testFindByIdExistente() {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(repository.findById(id)).thenReturn(Optional.of(moneda));

        Optional<HuCatMoneda> resultado = service.findById(id);

        assertTrue(resultado.isPresent());
        assertEquals("Dólar", resultado.get().getDescripcion());
    }

    @Test
    void testFindByIdNoExistente() {
        HuCatMonedaId id = new HuCatMonedaId(99, "XXX");

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MonedaNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void testSave() {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(repository.save(moneda)).thenReturn(moneda);

        HuCatMoneda resultado = service.save(moneda);

        assertEquals("USD", resultado.getId().getClaveMoneda());
        verify(repository, times(1)).save(moneda);
    }

    @Test
    void testUpdateExistente() {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(repository.findById(id)).thenReturn(Optional.of(moneda));
        when(repository.save(moneda)).thenReturn(moneda);

        HuCatMoneda resultado = service.update(moneda);

        assertEquals("Dólar", resultado.getDescripcion());
        verify(repository, times(1)).save(moneda);
    }

    @Test
    void testUpdateNoExistente() {
        HuCatMonedaId id = new HuCatMonedaId(99, "XXX");
        HuCatMoneda moneda = new HuCatMoneda(id, "Fake", "?", "I");

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MonedaNotFoundException.class, () -> service.update(moneda));
    }

    @Test
    void testDeleteExistente() {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(repository.findById(id)).thenReturn(Optional.of(moneda));
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteNoExistente() {
        HuCatMonedaId id = new HuCatMonedaId(99, "XXX");

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MonedaNotFoundException.class, () -> service.deleteById(id));
    }
}
