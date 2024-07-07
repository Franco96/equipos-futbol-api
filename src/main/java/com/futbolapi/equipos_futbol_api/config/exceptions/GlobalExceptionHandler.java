package com.futbolapi.equipos_futbol_api.config.exceptions;

import com.futbolapi.equipos_futbol_api.controller.DTOs.ErrorDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO("La solicitud es inválida", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EquipoNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEquipoNotFoundException(EquipoNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        ErrorDTO errorDTO = new ErrorDTO("Autenticación fallida", HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorDTO errorDTO = new ErrorDTO("Recurso no encontrado", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDTO> handleDataAccessException(DataAccessException ex) {
        ErrorDTO errorDTO = new ErrorDTO("Error al acceder a la base de datos", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorDTO> handleSQLException(SQLException ex) {
        ErrorDTO errorDTO = new ErrorDTO("Error de conexión a la base de datos", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex) {
        ErrorDTO errorDTO = new ErrorDTO("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

