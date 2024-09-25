package com.futbolapi.equipos_futbol_api.controller;


import com.futbolapi.equipos_futbol_api.controller.DTOs.responses.EquipoResponseDTO;
import com.futbolapi.equipos_futbol_api.controller.DTOs.responses.ErrorResponseDTO;
import com.futbolapi.equipos_futbol_api.controller.DTOs.requests.EquipoRequestDTO;
import com.futbolapi.equipos_futbol_api.controller.mapper.EquipoMapper;
import com.futbolapi.equipos_futbol_api.entities.Equipo;
import com.futbolapi.equipos_futbol_api.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/equipos")
@Tag(name = "equipos-futbol-api", description = "Gestión de equipos de fútbol")
@RequiredArgsConstructor
public class EquipoController {

    //hola a todos
    private final EquipoService equipoService;

    @GetMapping
    @Operation(summary = "Consulta de Todos los Equipos", description = "Devuelve la lista de todos los equipos de fútbol registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de todos los equipos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Equipo.class))))
    public List<EquipoResponseDTO> getAllEquipos() {
        return EquipoMapper.INSTANCE.toResposeDTOList(equipoService.getAllEquipos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de un Equipo por id", description = "Devuelve los detalles de un equipo por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipos encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Equipo.class))),
            @ApiResponse(responseCode = "404", description = "No se encontro el equipo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public EquipoResponseDTO getEquipoById(@PathVariable Long id) {
        return EquipoMapper.INSTANCE.toResposeDTO(equipoService.getEquipoById(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Consulta de un Equipo por nombre", description = "Devuelve los detalles de un equipo por nombre.")
    @ApiResponse(responseCode = "200", description = "Lista de equipos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Equipo.class))))
    public List<EquipoResponseDTO> buscarEquiposPorNombre(@RequestParam(value = "nombre", required=false) String nombre) {
        return EquipoMapper.INSTANCE.toResposeDTOList(equipoService.buscarEquiposPorNombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Creación de un Nuevo Equipo", description = "Crea un nuevo equipo de fútbol.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Equipo.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))})
    })
    public ResponseEntity<EquipoResponseDTO> createEquipo(@Valid @RequestBody EquipoRequestDTO equipoRequestDTO) {
        Equipo nuevoEquipo = equipoService.createEquipo(EquipoMapper.INSTANCE.toEquipo(equipoRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(EquipoMapper.INSTANCE.toResposeDTO(nuevoEquipo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de un Equipo", description = "Actualiza los detalles de un equipo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Equipo.class))),
            @ApiResponse(responseCode = "404", description = "No se encontro el equipo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public EquipoResponseDTO updateEquipo(@PathVariable Long id, @RequestBody EquipoRequestDTO equipo) {
            return EquipoMapper.INSTANCE.toResposeDTO(equipoService.updateEquipo(id, EquipoMapper.INSTANCE.toEquipo(equipo)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de un Equipo", description = "Elimina un equipo de fútbol por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipo eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontro el equipo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        equipoService.deleteEquipo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

