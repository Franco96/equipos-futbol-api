package com.futbolapi.equipos_futbol_api.controller;


import com.futbolapi.equipos_futbol_api.entities.Equipo;
import com.futbolapi.equipos_futbol_api.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/equipos")
@Tag(name = "Equipos", description = "Gestión de equipos de fútbol")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService equipoService;

    @GetMapping
    @Operation(summary = "Consulta de Todos los Equipos", description = "Devuelve la lista de todos los equipos de fútbol registrados.")
    public List<Equipo> getAllEquipos() {
        return equipoService.getAllEquipos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de un Equipo por ID", description = "Devuelve los detalles de un equipo específico.")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id) {
        return equipoService.getEquipoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Creación de un Nuevo Equipo", description = "Crea un nuevo equipo de fútbol.")
    public ResponseEntity<Equipo> createEquipo(@RequestBody Equipo equipo) {
        Equipo nuevoEquipo = equipoService.createEquipo(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de un Equipo", description = "Actualiza los detalles de un equipo existente.")
    public ResponseEntity<Equipo> updateEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        try {
            Equipo equipoActualizado = equipoService.updateEquipo(id, equipo);
            return ResponseEntity.ok(equipoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de un Equipo", description = "Elimina un equipo de fútbol por ID.")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        equipoService.deleteEquipo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

