package com.futbolapi.equipos_futbol_api.controller.DTOs.responses;

import lombok.Data;

@Data
public class EquipoResponseDTO {

    private Long id;
    private String nombre;
    private String liga;
    private String pais;
}
