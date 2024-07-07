package com.futbolapi.equipos_futbol_api.controller.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipoRequestDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String liga;
    @NotBlank
    private String pais;
}
