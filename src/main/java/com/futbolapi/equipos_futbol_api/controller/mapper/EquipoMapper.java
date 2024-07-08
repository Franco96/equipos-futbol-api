package com.futbolapi.equipos_futbol_api.controller.mapper;

import com.futbolapi.equipos_futbol_api.controller.DTOs.requests.EquipoRequestDTO;
import com.futbolapi.equipos_futbol_api.controller.DTOs.responses.EquipoResponseDTO;
import com.futbolapi.equipos_futbol_api.entities.Equipo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EquipoMapper {

    EquipoMapper INSTANCE = Mappers.getMapper(EquipoMapper.class);

    @Mapping(target = "id", ignore = true)
    Equipo toEquipo(EquipoRequestDTO equipoRequestDTO);

    EquipoResponseDTO toResposeDTO(Equipo equipo);

    List<EquipoResponseDTO> toResposeDTOList(List<Equipo> equipo);
}
