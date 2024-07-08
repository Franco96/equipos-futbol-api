package com.futbolapi.equipos_futbol_api.controller.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private String mensaje;
    private Integer codigo;
}
