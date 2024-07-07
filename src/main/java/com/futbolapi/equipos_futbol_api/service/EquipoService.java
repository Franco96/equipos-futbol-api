package com.futbolapi.equipos_futbol_api.service;

import com.futbolapi.equipos_futbol_api.config.exceptions.EquipoNotFoundException;
import com.futbolapi.equipos_futbol_api.entities.Equipo;
import com.futbolapi.equipos_futbol_api.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    public Equipo getEquipoById(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo no encontrado"));
    }

    public List<Equipo> buscarEquiposPorNombre(String nombre) {
        return equipoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Equipo createEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo updateEquipo(Long id, Equipo equipo) {
        return equipoRepository.findById(id)
                .map(eq -> {
                    eq.setNombre(equipo.getNombre());
                    eq.setLiga(equipo.getLiga());
                    eq.setPais(equipo.getPais());
                    return equipoRepository.save(eq);
                })
                .orElseThrow(() -> new EquipoNotFoundException("Equipo no encontrado"));
    }

    public void deleteEquipo(Long id) {
        equipoRepository.delete(this.getEquipoById(id));
    }
}
