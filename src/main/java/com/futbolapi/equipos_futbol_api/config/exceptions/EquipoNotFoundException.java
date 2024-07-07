package com.futbolapi.equipos_futbol_api.config.exceptions;

public class EquipoNotFoundException extends RuntimeException {
    public EquipoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
