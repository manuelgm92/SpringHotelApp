package com.springhotel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.springhotel.entity.Reserva;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para transferir los datos de una reserva entre capas sin exponer
 * directamente la entidad {@link Reserva} en los controladores y vistas.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Getter
@Setter
public class ReservaDTO {

    private Integer id;

    @NotNull(message = "La fecha de check-in es obligatoria.")
    @FutureOrPresent(message = "La fecha de check-in no puede ser anterior a hoy.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkIn;

    @NotNull(message = "La fecha de check-out es obligatoria.")
    @Future(message = "La fecha de check-out debe ser futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOut;

    @NotNull(message = "Debe indicar el número de personas.")
    @Min(value = 1, message = "El número de personas debe ser mayor que 0.")
    private Integer numPersonas;

    @NotNull(message = "Debe seleccionar un estado para la reserva.")
    private Reserva.EstadoReserva estadoReserva = Reserva.EstadoReserva.PENDIENTE;

    private LocalDateTime fechaCreacionReserva;

    @NotNull(message = "Debe seleccionar un huésped.")
    private Integer idHuesped;

    @NotNull(message = "Debe seleccionar una habitación.")
    private Integer idHabitacion;

    private String nombreHuesped;
    private String apellidosHuesped;
    private String emailHuesped;
    private String telefonoHuesped;

    private String numeroHabitacion;
    private String tipoHabitacion;
    private Integer capacidadHabitacion;
    private Double precioNoche;

	/**
	 * Valida que la fecha de check-out sea posterior a la de check-in.
	 *
	 * @return {@code true} si las fechas son válidas y check-out es posterior a check-in
	 */
    @AssertTrue(message = "La fecha de check-out debe ser posterior al check-in.")
    public boolean isFechasValidas() {
        return checkIn != null && checkOut != null && checkOut.isAfter(checkIn);
    }
}
