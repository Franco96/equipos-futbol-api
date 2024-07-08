package com.futbolapi.equipos_futbol_api;

import com.futbolapi.equipos_futbol_api.config.exceptions.EquipoNotFoundException;
import com.futbolapi.equipos_futbol_api.entities.Equipo;
import com.futbolapi.equipos_futbol_api.repository.EquipoRepository;
import com.futbolapi.equipos_futbol_api.service.EquipoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class EquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoService equipoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEquipos() {
        List<Equipo> equipos = Arrays.asList(
                new Equipo(1L, "FC Barcelona", "La Liga", "España"),
                new Equipo(2L, "Real Madrid", "La Liga", "España")
        );
        when(equipoRepository.findAll()).thenReturn(equipos);

        List<Equipo> result = equipoService.getAllEquipos();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetEquipoById() {
        Equipo equipo = new Equipo(1L, "Nuevo Equipo FC", "Nueva Liga", "Nuevo País");
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));

        Equipo result = equipoService.getEquipoById(1L);

        assertNotNull(result);
        assertEquals(equipo.getNombre(), result.getNombre());
    }

    @Test
    public void testGetEquipoByIdNotFound() {

        when(equipoRepository.findById(any())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(EquipoNotFoundException.class, () -> {
            equipoService.getEquipoById(any());
        });
        assertEquals("Equipo no encontrado", exception.getMessage());
    }

    @Test
    void testBuscarEquiposPorNombre() {
        List<Equipo> equipos = Arrays.asList(
                new Equipo(1L, "FC Barcelona", "La Liga", "España"),
                new Equipo(2L, "Real Madrid", "La Liga", "España")
        );
        when(equipoRepository.findByNombreContainingIgnoreCase("FC")).thenReturn(equipos);

        List<Equipo> result = equipoService.buscarEquiposPorNombre("FC");

        assertEquals(2, result.size());

    }

    @Test
    void testCreateEquipo() {
        Equipo equipo = new Equipo(1L, "Nuevo Equipo FC", "Nueva Liga", "Nuevo País");
        when(equipoRepository.save(equipo)).thenReturn(equipo);

        Equipo result = equipoService.createEquipo(equipo);

        assertEquals("Nuevo Equipo FC", result.getNombre());
    }

    @Test
    void testUpdateEquipo() {
        Equipo existingEquipo = new Equipo(1L, "FC Barcelona", "La Liga", "España");
        Equipo updatedEquipo = new Equipo(1L, "Nuevo Nombre", "Nueva Liga", "Nuevo País");
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(existingEquipo));
        when(equipoRepository.save(existingEquipo)).thenReturn(existingEquipo);

        Equipo result = equipoService.updateEquipo(1L, updatedEquipo);

        assertEquals("Nuevo Nombre", result.getNombre());
    }

    @Test
    void testDeleteEquipo() {
        Equipo equipo = new Equipo(1L, "FC Barcelona", "La Liga", "España");
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));
        equipoService.deleteEquipo(1L);
    }
}

