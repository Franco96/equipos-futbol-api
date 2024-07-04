package com.futbolapi.equipos_futbol_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Equipos de Fútbol")
                        .version("1.0")
                        .description("API para la gestión de equipos de fútbol, desarrollada por Lautaro Carranza.")
                        .contact(new Contact().name("Lautaro Carranza")
                                .email("francolautarocarranza@hotmail.com")
                                .url("https://www.linkedin.com/in/lautaro-carranza-7667821b9/")));
    }
}
