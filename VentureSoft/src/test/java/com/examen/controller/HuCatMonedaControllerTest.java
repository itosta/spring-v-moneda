package com.examen.controller;

import com.examen.model.HuCatMoneda;
import com.examen.model.HuCatMonedaId;
import com.examen.service.HuCatMonedaService;
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

class HuCatMonedaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HuCatMonedaService service;

    @InjectMocks
    private HuCatMonedaController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListar() throws Exception {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(service.findAll()).thenReturn(List.of(moneda));

        mockMvc.perform(get("/api/monedas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.numCia").value(1))
                .andExpect(jsonPath("$[0].id.claveMoneda").value("USD"))
                .andExpect(jsonPath("$[0].descripcion").value("Dólar"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(service.findById(id)).thenReturn(Optional.of(moneda));

        mockMvc.perform(get("/api/monedas/1/USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.numCia").value(1))
                .andExpect(jsonPath("$.id.claveMoneda").value("USD"))
                .andExpect(jsonPath("$.descripcion").value("Dólar"));
    }

    @Test
    void testCrear() throws Exception {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar", "$", "A");

        when(service.save(any(HuCatMoneda.class))).thenReturn(moneda);

        mockMvc.perform(post("/api/monedas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":{\"numCia\":1,\"claveMoneda\":\"USD\"},\"descripcion\":\"Dólar\",\"simbolo\":\"$\",\"estatus\":\"A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.numCia").value(1))
                .andExpect(jsonPath("$.id.claveMoneda").value("USD"))
                .andExpect(jsonPath("$.descripcion").value("Dólar"));
    }

    @Test
    void testActualizar() throws Exception {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dólar actualizado", "$", "A");

        when(service.findById(id)).thenReturn(Optional.of(moneda));
        when(service.update(any(HuCatMoneda.class))).thenReturn(moneda);

        mockMvc.perform(put("/api/monedas/1/USD")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":{\"numCia\":1,\"claveMoneda\":\"USD\"},\"descripcion\":\"Dólar actualizado\",\"simbolo\":\"$\",\"estatus\":\"A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Dólar actualizado"));
    }

    @Test
    void testEliminar() throws Exception {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        doNothing().when(service).deleteById(id);

        mockMvc.perform(delete("/api/monedas/1/USD"))
                .andExpect(status().isOk())
                .andExpect(content().string("Moneda USD con numero de compañia 1 eliminada."));
    }
}
