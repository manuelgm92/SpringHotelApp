package com.springhotel.mapper;

import org.springframework.stereotype.Component;

import com.springhotel.dto.IncidenciaDTO;
import com.springhotel.entity.Habitacion;
import com.springhotel.entity.Incidencia;

/**
 * Mapper que convierte entre la entidad {@link Incidencia} y su DTO {@link IncidenciaDTO}.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Component
public class IncidenciaMapper {

	/**
	 * Convierte un {@link IncidenciaDTO} en su entidad {@link Incidencia} correspondiente.
	 *
	 * @param dto objeto DTO con los datos de la incidencia
	 * @return entidad {@link Incidencia} con los datos mapeados
	 */
    public Incidencia toEntity(IncidenciaDTO dto) {
        Incidencia incidencia = new Incidencia();

        incidencia.setId(dto.getId());

        // Solo asignamos la referencia por ID, sin cargar la entidad completa
        Habitacion h = new Habitacion();
        h.setId(dto.getIdHabitacion());
        incidencia.setHabitacion(h);

        incidencia.setEstadoIncidencia(dto.getEstadoIncidencia());
        incidencia.setPrioridad(dto.getPrioridad());
        incidencia.setDescripcion(dto.getDescripcion());
        incidencia.setFechaApertura(dto.getFechaApertura());
        incidencia.setFechaCierre(dto.getFechaCierre());
        incidencia.setTecnicoAsignado(dto.getTecnicoAsignado());
        incidencia.setObservaciones(dto.getObservaciones());

        return incidencia;
    }

	/**
	 * Convierte una entidad {@link Incidencia} en su DTO {@link IncidenciaDTO} correspondiente.
	 *
	 * @param incidencia entidad incidencia a convertir
	 * @return objeto {@link IncidenciaDTO} con los datos mapeados
	 */
    public IncidenciaDTO toDTO(Incidencia incidencia) {
        IncidenciaDTO dto = new IncidenciaDTO();

        dto.setId(incidencia.getId());
        dto.setIdHabitacion(
            incidencia.getHabitacion() != null ? incidencia.getHabitacion().getId() : null
        );
        dto.setEstadoIncidencia(incidencia.getEstadoIncidencia());
        dto.setPrioridad(incidencia.getPrioridad());
        dto.setDescripcion(incidencia.getDescripcion());
        dto.setFechaApertura(incidencia.getFechaApertura());
        dto.setFechaCierre(incidencia.getFechaCierre());
        dto.setTecnicoAsignado(incidencia.getTecnicoAsignado());
        dto.setObservaciones(incidencia.getObservaciones());

        return dto;
    }
}
