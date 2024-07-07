package com.futbolapi.equipos_futbol_api.repository;


import com.futbolapi.equipos_futbol_api.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByNombreContainingIgnoreCase(String nombre);
}

