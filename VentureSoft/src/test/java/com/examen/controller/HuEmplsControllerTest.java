package com.examen.controller;

import com.examen.model.HuEmpls;
import com.examen.model.HuEmplsId;
import com.examen.service.HuEmplsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HuEmplsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HuEmplsService service;

    @InjectMocks
    private HuEmplsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListar() throws Exception {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(service.findAll()).thenReturn(List.of(empleado));

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.numCia").value(1))
                .andExpect(jsonPath("$[0].id.numEmp").value(101))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(service.findById(id)).thenReturn(Optional.of(empleado));

        mockMvc.perform(get("/api/empleados/1/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.numCia").value(1))
                .andExpect(jsonPath("$.id.numEmp").value(101))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testCrear() throws Exception {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan", "Pérez", "López", "Dev", null);

        when(service.save(any(HuEmpls.class))).thenReturn(empleado);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":{\"numCia\":1,\"numEmp\":101},\"claveMoneda\":\"USD\",\"nombre\":\"Juan\",\"apellidoPaterno\":\"Pérez\",\"apellidoMaterno\":\"López\",\"puesto\":\"Dev\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.numCia").value(1))
                .andExpect(jsonPath("$.id.numEmp").value(101))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testActualizar() throws Exception {
        HuEmplsId id = new HuEmplsId(1, 101);
        HuEmpls empleado = new HuEmpls(id, "USD", "Juan actualizado", "Pérez", "López", "Dev", null);

        when(service.update(any(HuEmpls.class))).thenReturn(empleado);

        mockMvc.perform(put("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":{\"numCia\":1,\"numEmp\":101},\"claveMoneda\":\"USD\",\"nombre\":\"Juan actualizado\",\"apellidoPaterno\":\"Pérez\",\"apellidoMaterno\":\"López\",\"puesto\":\"Dev\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan actualizado"));
    }

    @Test
    void testEliminar() throws Exception {
        HuEmplsId id = new HuEmplsId(1, 101);
        doNothing().when(service).deleteById(id);

        mockMvc.perform(delete("/api/empleados/1/101"))
                .andExpect(status().isOk())
                .andExpect(content().string("Empleado 101 con numero de compañia 1 eliminado"));
    }
}
