package com.futbolapi.equipos_futbol_api.controller.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private String mensaje;
    private Integer codigo;
}