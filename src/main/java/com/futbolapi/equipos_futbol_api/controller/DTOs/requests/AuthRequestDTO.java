package com.futbolapi.equipos_futbol_api.controller.DTOs.requests;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
}
