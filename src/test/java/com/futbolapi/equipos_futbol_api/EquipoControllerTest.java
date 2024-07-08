package com.futbolapi.equipos_futbol_api;

import com.futbolapi.equipos_futbol_api.config.exceptions.EquipoNotFoundException;
import com.futbolapi.equipos_futbol_api.config.util.JwtUtil;
import com.futbolapi.equipos_futbol_api.entities.Equipo;
import com.futbolapi.equipos_futbol_api.service.EquipoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipoControllerTest {

    private final String URL_BASE = "http://localhost:8080/api";

    @Autowired
    private JwtUtil jwtUtil;
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipoService equipoService;

    @BeforeEach
    public void setUp() {
        token = jwtUtil.generateToken("test");
    }
    @Test
    void testGetAllEquipos() throws Exception {
        List<Equipo> equipos = Arrays.asList(
                new Equipo(1L, "FC Barcelona", "La Liga", "España"),
                new Equipo(2L, "Real Madrid", "La Liga", "España")
        );
        when(equipoService.getAllEquipos()).thenReturn(equipos);

        mockMvc.perform(get(URL_BASE + "/equipos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("FC Barcelona"))
                .andExpect(jsonPath("$[1].nombre").value("Real Madrid"));

    }

    @Test
    void testGetEquipoById() throws Exception {
        Equipo equipo = new Equipo(1L, "FC Barcelona", "La Liga", "España");
        when(equipoService.getEquipoById(1L)).thenReturn(equipo);

        mockMvc.perform(get(URL_BASE + "/equipos/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("FC Barcelona"));
    }

    @Test
    void testGetEquipoByIdNotFound() throws Exception {
        when(equipoService.getEquipoById(1L)).thenThrow(new EquipoNotFoundException("Equipo no encontrado"));

        mockMvc.perform(get(URL_BASE + "/equipos/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Equipo no encontrado"))
                .andExpect(jsonPath("$.codigo").value(404));

    }

    @Test
    void testBuscarEquiposPorNombre() throws Exception {
        List<Equipo> equipos = Arrays.asList(
                new Equipo(1L, "FC Barcelona", "La Liga", "España"),
                new Equipo (2L,"Celtic FC", "Scottish Premiership", "Escocia")
        );
        when(equipoService.buscarEquiposPorNombre("CE")).thenReturn(equipos);

        mockMvc.perform(get(URL_BASE + "/equipos/buscar?nombre=CE")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("FC Barcelona"))
                .andExpect(jsonPath("$[1].nombre").value("Celtic FC"));
    }

    @Test
    void testCreateEquipo() throws Exception {
        Equipo equipo = new Equipo(1L, "Nuevo Equipo FC", "Nueva Liga", "Nuevo País");
        when(equipoService.createEquipo(any(Equipo.class))).thenReturn(equipo);

        mockMvc.perform(post(URL_BASE + "/equipos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Nuevo Equipo FC\", \"liga\": \"Nueva Liga\", \"pais\": \"Nuevo País\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nuevo Equipo FC"));
    }

    @Test
    void testUpdateEquipo() throws Exception {
        Equipo updatedEquipo = new Equipo(1L, "Nuevo Nombre", "Nueva Liga", "Nuevo País");
        when(equipoService.updateEquipo(eq(1L), any(Equipo.class))).thenReturn(updatedEquipo);

        mockMvc.perform(put(URL_BASE + "/equipos/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Nuevo Nombre\", \"liga\": \"Nueva Liga\", \"pais\": \"Nuevo País\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo Nombre"));
    }

    @Test
    void testDeleteEquipo() throws Exception {
        mockMvc.perform(delete(URL_BASE + "/equipos/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
