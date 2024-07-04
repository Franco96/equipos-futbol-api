package com.futbolapi.equipos_futbol_api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipos")
@Data
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String liga;
    private String pais;
}
