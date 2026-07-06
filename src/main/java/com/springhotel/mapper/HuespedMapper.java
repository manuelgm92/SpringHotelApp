package com.springhotel.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.springhotel.dto.HuespedDTO;
import com.springhotel.entity.Huesped;

/**
 * Mapper que convierte entre la entidad {@link Huesped} y su DTO {@link HuespedDTO}.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Component
public class HuespedMapper {

	/**
	 * Convierte una entidad {@link Huesped} en su DTO {@link HuespedDTO} correspondiente.
	 *
	 * @param huesped entidad huésped a convertir
	 * @return objeto {@link HuespedDTO} con los datos mapeados, o {@code null} si el parámetro es nulo
	 */
    public HuespedDTO toDTO(Huesped huesped) {
        if (huesped == null) {
            return null;
        }

        HuespedDTO dto = new HuespedDTO();
        dto.setId(huesped.getId());
        dto.setNombreHuesped(huesped.getNombreHuesped());
        dto.setApellidosHuesped(huesped.getApellidosHuesped());
        dto.setDniPasaporte(huesped.getDniPasaporte());
        dto.setTelefonoHuesped(huesped.getTelefonoHuesped());
        dto.setEmail(huesped.getEmail());
        dto.setDireccion(huesped.getDireccion());

        return dto;
    }

	/**
	 * Convierte un {@link HuespedDTO} en su entidad {@link Huesped} correspondiente.
	 *
	 * @param dto objeto DTO con los datos del huésped
	 * @return entidad {@link Huesped} con los datos mapeados, o {@code null} si el parámetro es nulo
	 */
    public Huesped toEntity(HuespedDTO dto) {
        if (dto == null) {
            return null;
        }

        Huesped huesped = new Huesped();
        huesped.setId(dto.getId());
        huesped.setNombreHuesped(dto.getNombreHuesped());
        huesped.setApellidosHuesped(dto.getApellidosHuesped());
        huesped.setDniPasaporte(dto.getDniPasaporte());
        huesped.setTelefonoHuesped(dto.getTelefonoHuesped());
        huesped.setEmail(dto.getEmail());
        huesped.setDireccion(dto.getDireccion());

        return huesped;
    }

	/**
	 * Convierte una lista de entidades {@link Huesped} en una lista de DTOs {@link HuespedDTO}.
	 *
	 * @param huespedes lista de entidades a convertir
	 * @return lista de DTOs mapeados, o {@code null} si la lista es nula
	 */
    public List<HuespedDTO> toDTOList(List<Huesped> huespedes) {
        if (huespedes == null) {
            return null;
        }
        return huespedes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}