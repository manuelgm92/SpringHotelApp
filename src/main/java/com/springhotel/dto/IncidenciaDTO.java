package com.springhotel.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.springhotel.entity.Incidencia.EstadoIncidencia;
import com.springhotel.entity.Incidencia.PrioridadIncidencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para transferencia de datos de incidencias entre la capa de
 * presentacion y la capa de servicio, sin exponer la entidad JPA.
 *
 * IA: Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaDTO {

    private Integer id;

    @NotNull(message = "Debe seleccionar una habitación")
    private Integer idHabitacion;

    @NotNull(message = "El estado de la incidencia es obligatorio")
    private EstadoIncidencia estadoIncidencia;

    @NotNull(message = "La prioridad es obligatoria")
    private PrioridadIncidencia prioridad;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

    private String tecnicoAsignado;

    private String observaciones;
}
