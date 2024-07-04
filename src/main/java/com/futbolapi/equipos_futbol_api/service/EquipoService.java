package com.futbolapi.equipos_futbol_api.service;

import com.futbolapi.equipos_futbol_api.entities.Equipo;
import com.futbolapi.equipos_futbol_api.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipoService {


    private final EquipoRepository equipoRepository;

    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> getEquipoById(Long id) {
        return equipoRepository.findById(id);
    }

    public Equipo createEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo updateEquipo(Long id, Equipo equipo) {
        return equipoRepository.findById(id)
                .map(existingEquipo -> {
                    existingEquipo.setNombre(equipo.getNombre());
                    existingEquipo.setLiga(equipo.getLiga());
                    existingEquipo.setPais(equipo.getPais());
                    return equipoRepository.save(existingEquipo);
                })
                .orElseThrow(() -> new RuntimeException("Equipo not found"));
    }

    public void deleteEquipo(Long id) {
        equipoRepository.deleteById(id);
    }
}
