package com.springhotel.mapper;

import org.springframework.stereotype.Component;
import com.springhotel.dto.HabitacionDTO;
import com.springhotel.entity.Habitacion;

/**
 * Mapper que convierte entre la entidad {@link Habitacion} y su DTO {@link HabitacionDTO}.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Component
public class HabitacionMapper {

	/**
	 * Convierte un {@link HabitacionDTO} en su entidad {@link Habitacion} correspondiente.
	 *
	 * @param dto objeto DTO con los datos de la habitación
	 * @return entidad {@link Habitacion} con los datos mapeados
	 */
    public Habitacion toEntity(HabitacionDTO dto) {
        Habitacion h = new Habitacion();
        h.setId(dto.getId());
        h.setNumeroHabitacion(dto.getNumeroHabitacion());
        h.setTipoHabitacion(dto.getTipoHabitacion());
        h.setPrecioNoche(dto.getPrecioNoche());
        h.setCapacidad(dto.getCapacidad());
        h.setEstadoHabitacion(dto.getEstadoHabitacion());
        h.setAdaptadaDiscapacidad(dto.getAdaptadaDiscapacidad());
        return h;
    }

	/**
	 * Convierte una entidad {@link Habitacion} en su DTO {@link HabitacionDTO} correspondiente.
	 *
	 * @param h entidad habitación a convertir
	 * @return objeto {@link HabitacionDTO} con los datos mapeados
	 */
    public HabitacionDTO toDTO(Habitacion h) {
        HabitacionDTO dto = new HabitacionDTO();
        dto.setId(h.getId());
        dto.setNumeroHabitacion(h.getNumeroHabitacion());
        dto.setTipoHabitacion(h.getTipoHabitacion());
        dto.setPrecioNoche(h.getPrecioNoche());
        dto.setCapacidad(h.getCapacidad());
        dto.setEstadoHabitacion(h.getEstadoHabitacion());
        dto.setAdaptadaDiscapacidad(h.getAdaptadaDiscapacidad());
        return dto;
    }
}
