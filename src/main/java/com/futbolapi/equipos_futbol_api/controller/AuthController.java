package com.futbolapi.equipos_futbol_api.controller;

import com.futbolapi.equipos_futbol_api.config.util.JwtUtil;
import com.futbolapi.equipos_futbol_api.controller.DTOs.requests.AuthRequestDTO;
import com.futbolapi.equipos_futbol_api.controller.DTOs.AuthResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth/login")
@Tag(name = "authentication-api", description = "Gestión de la autenticacion")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO data) {
        String username = data.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
