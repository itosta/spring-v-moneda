package com.examen.service;

import com.examen.exception.EmpleadoNotFoundException;
import com.examen.exception.MonedaNotFoundException;
import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import com.examen.model.HuEmpls;
import com.examen.model.HuEmplsId;
import com.examen.repository.HuCatMonedaRepository;
import com.examen.repository.HuEmplsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HuEmplsServiceTest {

    private HuEmplsRepository empleadoRepository;
    private HuCatMonedaRepository monedaRepository;
    private HuEmplsService service;

    @BeforeEach
    void setUp() throws Exception {
        empleadoRepository = Mockito.mock(HuEmplsRepository.class);
        monedaRepository = Mockito.mock(HuCatMonedaRepository.class);
        service = new HuEmplsService();

        // Inyectamos los mocks en los campos privados usando reflexión
        var fieldEmpls = HuEmplsService.class.getDeclaredField("empleadoRepository");
        fieldEmpls.setAccessible(true);
        fieldEmpls.set(service, empleadoRepository);

        var fieldMoneda = HuEmplsService.class.getDeclaredField("monedaRepository");
        fieldMoneda.setAccessible(true);
        fieldMoneda.set(service, monedaRepository);
    }

    @Test
    void testFindAll() {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));

        List<HuEmpls> resultado = service.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void testFindByIdExistente() {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));

        Optional<HuEmpls> resultado = service.findById(id);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void testFindByIdNoExistente() {
        HuEmplsId id = new HuEmplsId(99, 999);

        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmpleadoNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void testSaveConMonedaExistente() {
        HuCatMonedaId monedaId = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(monedaId, "Dólar", "$", "A");

        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(monedaRepository.findById(monedaId)).thenReturn(Optional.of(moneda));
        when(empleadoRepository.save(any(HuEmpls.class))).thenReturn(empleado);

        HuEmpls resultado = service.save(empleado);

        assertEquals("USD", resultado.getClaveMoneda());
        assertEquals("Juan", resultado.getNombre());
        assertEquals(moneda, resultado.getMoneda());
    }

    @Test
    void testSaveConMonedaNoExistente() {
        HuCatMonedaId monedaId = new HuCatMonedaId(1, "XXX");
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "XXX", "Juan", "Pérez", "López", "Dev", null);

        when(monedaRepository.findById(monedaId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.save(empleado));
    }

    @Test
    void testUpdateExistente() {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        HuEmpls resultado = service.update(empleado);

        assertEquals("Juan", resultado.getNombre());
        verify(empleadoRepository, times(1)).save(empleado);
    }

    @Test
    void testUpdateNoExistente() {
        HuEmplsId id = new HuEmplsId(99, 999);
        HuEmpls empleado = new HuEmpls(id, "USD", "Fake", "X", "Y", "Dev", null);

        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MonedaNotFoundException.class, () -> service.update(empleado));
    }

    @Test
    void testDeleteExistente() {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));
        doNothing().when(empleadoRepository).deleteById(id);

        service.deleteById(id);

        verify(empleadoRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteNoExistente() {
        HuEmplsId id = new HuEmplsId(99, 999);

        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MonedaNotFoundException.class, () -> service.deleteById(id));
    }
}
