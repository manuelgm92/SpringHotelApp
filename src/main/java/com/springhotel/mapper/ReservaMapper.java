package com.springhotel.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.springhotel.dto.ReservaDTO;
import com.springhotel.entity.Habitacion;
import com.springhotel.entity.Huesped;
import com.springhotel.entity.Reserva;

/**
 * Mapper que convierte entre la entidad {@link Reserva} y su DTO {@link ReservaDTO}.
 *
 * @author Ana Laura
 * @version 1.0
 */
public class ReservaMapper {

	/**
	 * Convierte una entidad {@link Reserva} en su DTO {@link ReservaDTO} correspondiente,
	 * incluyendo los datos del huésped y la habitación asociados.
	 *
	 * @param reserva entidad reserva a convertir
	 * @return objeto {@link ReservaDTO} con los datos mapeados, o {@code null} si el parámetro es nulo
	 */
    public static ReservaDTO toDTO(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setCheckIn(reserva.getCheckIn());
        dto.setCheckOut(reserva.getCheckOut());
        dto.setNumPersonas(reserva.getNumPersonas());
        dto.setEstadoReserva(reserva.getEstadoReserva());
        dto.setFechaCreacionReserva(reserva.getFechaCreacionReserva());

        if (reserva.getHuesped() != null) {
            dto.setIdHuesped(reserva.getHuesped().getId());
            dto.setNombreHuesped(reserva.getHuesped().getNombreHuesped());
            dto.setApellidosHuesped(reserva.getHuesped().getApellidosHuesped());
            dto.setEmailHuesped(reserva.getHuesped().getEmail());
            dto.setTelefonoHuesped(reserva.getHuesped().getTelefonoHuesped());
        }

        if (reserva.getHabitacion() != null) {
            dto.setIdHabitacion(reserva.getHabitacion().getId());
            dto.setNumeroHabitacion(reserva.getHabitacion().getNumeroHabitacion());
            dto.setTipoHabitacion(reserva.getHabitacion().getTipoHabitacion() != null
                    ? reserva.getHabitacion().getTipoHabitacion().name()
                    : null);
            dto.setCapacidadHabitacion(reserva.getHabitacion().getCapacidad());
            dto.setPrecioNoche(reserva.getHabitacion().getPrecioNoche());
        }

        return dto;
    }

	/**
	 * Convierte un {@link ReservaDTO} en su entidad {@link Reserva} correspondiente,
	 * creando referencias a {@link Huesped} y {@link Habitacion} por sus identificadores.
	 *
	 * @param dto objeto DTO con los datos de la reserva
	 * @return entidad {@link Reserva} con los datos mapeados, o {@code null} si el parámetro es nulo
	 */
    public static Reserva toEntity(ReservaDTO dto) {
        if (dto == null) {
            return null;
        }

        Reserva reserva = new Reserva();
        reserva.setId(dto.getId());
        reserva.setCheckIn(dto.getCheckIn());
        reserva.setCheckOut(dto.getCheckOut());
        reserva.setNumPersonas(dto.getNumPersonas());
        reserva.setEstadoReserva(dto.getEstadoReserva());

        if (dto.getIdHuesped() != null) {
            Huesped huesped = new Huesped();
            huesped.setId(dto.getIdHuesped());
            reserva.setHuesped(huesped);
        }

        if (dto.getIdHabitacion() != null) {
            Habitacion habitacion = new Habitacion();
            habitacion.setId(dto.getIdHabitacion());
            reserva.setHabitacion(habitacion);
        }

        return reserva;
    }

	/**
	 * Convierte una lista de entidades {@link Reserva} en una lista de DTOs {@link ReservaDTO}.
	 *
	 * @param reservas lista de entidades a convertir
	 * @return lista de DTOs mapeados, o {@code null} si la lista es nula
	 */
    public static List<ReservaDTO> toDTOList(List<Reserva> reservas) {
        if (reservas == null) {
            return null;
        }
        return reservas.stream().map(ReservaMapper::toDTO).collect(Collectors.toList());
    }
}
