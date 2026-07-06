package com.springhotel.dto;

import com.springhotel.entity.Habitacion.TipoHabitacion;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.springhotel.entity.Habitacion.EstadoHabitacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para transferir datos de habitaciones
 * entre la capa de presentacion y la capa de servicio,
 * sin exponer la entidad JPA directamente.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO {
	 private Integer id;
	@NotBlank(message = "El número de habitación es obligatorio.")
	private String numeroHabitacion;

	@NotNull(message = "Debe seleccionar un tipo de habitación.")
	private TipoHabitacion tipoHabitacion;

	@NotNull(message = "El precio por noche es obligatorio.")
	private Double precioNoche;

	@NotNull(message = "La capacidad es obligatoria.")
	private Integer capacidad;

	@NotNull(message = "Debe seleccionar un estado para la habitación.")
	private EstadoHabitacion estadoHabitacion;

	@NotNull(message = "Debe indicar si la habitación está adaptada o no.")
	private Boolean adaptadaDiscapacidad;
}