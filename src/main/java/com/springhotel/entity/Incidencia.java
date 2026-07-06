package com.springhotel.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Entidad JPA que representa una incidencia de mantenimiento del hotel.
 *
 * <p>
 * Cada incidencia esta asociada a una {@link Habitacion} concreta. La relacion
 * es muchos a uno: una habitacion puede tener varias incidencias, pero cada
 * incidencia pertenece a una sola habitacion.
 * </p>
 *
 * <p>
 * El ciclo de vida tipico de una incidencia es:
 * </p>
 * <ol>
 * <li>{@code ABIERTA}: se acaba de registrar.</li>
 * <li>{@code EN_CURSO}: un tecnico esta trabajando en ella.</li>
 * <li>{@code CERRADA}: el problema ha sido resuelto.</li>
 * </ol>
 *
 * <p>
 * La prioridad indica la urgencia de la incidencia: {@code BAJA}, {@code MEDIA}
 * o {@code ALTA}.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see Habitacion
 * @see com.springhotel.dao.IncidenciaDao
 */
@Entity
@Table(name = "incidencias")
public class Incidencia {

	/**
	 * Estados posibles de una incidencia de mantenimiento.
	 */
	public enum EstadoIncidencia {
		ABIERTA, EN_CURSO, CERRADA
	}

	/**
	 * Niveles de prioridad de una incidencia.
	 */
	public enum PrioridadIncidencia {
		BAJA, MEDIA, ALTA
	}

// -------------------------------------------------------------------------
// Campos
// -------------------------------------------------------------------------

	/**
	 * Identificador unico de la incidencia, generado automaticamente por la BBDD.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * Habitacion a la que pertenece esta incidencia.
	 *
	 * <p>
	 * Se carga de forma lazy ({@link FetchType#LAZY}) para no traer la habitacion
	 * completa en cada consulta de incidencias. Se accede a ella solo cuando se
	 * necesita (por ejemplo, en la vista de detalle).
	 * </p>
	 *
	 * <p>
	 * La columna de clave foranea en la BBDD es {@code id_habitacion}.
	 * </p>
	 */
	@NotNull(message = "Debe seleccionar una habitación")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_habitacion", nullable = false)
	private Habitacion habitacion;

	/**
	 * Estado actual de la incidencia: ABIERTA, EN_CURSO o CERRADA. El valor por
	 * defecto al registrar una incidencia nueva es {@code ABIERTA}.
	 */
	@NotNull(message = "El estado de la incidencia es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_incidencia", nullable = false)
	private EstadoIncidencia estadoIncidencia = EstadoIncidencia.ABIERTA;

	/**
	 * Prioridad de la incidencia: BAJA, MEDIA o ALTA. El valor por defecto es
	 * {@code MEDIA}.
	 */
	@NotNull(message = "La prioridad es obligatoria")
	@Enumerated(EnumType.STRING)
	@Column(name = "prioridad", nullable = false)
	private PrioridadIncidencia prioridad = PrioridadIncidencia.MEDIA;

	/**
	 * Descripcion detallada del problema o averia reportada.
	 */
	@NotBlank(message = "La descripción no puede estar vacía")
	@Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
	private String descripcion;

	/**
	 * Fecha y hora en la que se abrio la incidencia. Se asigna automaticamente al
	 * crear la incidencia.
	 */

	@Column(name = "fecha_apertura", nullable = false)
	private LocalDateTime fechaApertura;

	/**
	 * Fecha y hora en la que se cerro la incidencia. Es {@code null} mientras la
	 * incidencia no haya sido cerrada.
	 */
	@Column(name = "fecha_cierre")
	private LocalDateTime fechaCierre;

	/**
	 * Nombre del tecnico asignado para resolver la incidencia. Puede ser
	 * {@code null} si aun no se ha asignado a nadie.
	 */
	
	@Column(name = "tecnico_asignado", length = 100)
	private String tecnicoAsignado;

	/**
	 * Notas u observaciones adicionales registradas durante la gestion de la
	 * incidencia (materiales usados, pasos realizados, etc.). Puede ser
	 * {@code null}.
	 */
	
	@Column(name = "observaciones", columnDefinition = "TEXT")
	private String observaciones;

// -------------------------------------------------------------------------
// Constructores
// -------------------------------------------------------------------------

	/**
	 * Constructor sin argumentos requerido por Hibernate para instanciar entidades
	 * al cargarlas desde la base de datos.
	 */
	public Incidencia() {
	}

	/**
	 * Constructor de conveniencia para registrar una incidencia nueva.
	 *
	 * <p>
	 * La fecha de apertura se establece automaticamente al momento actual. El
	 * estado inicial es {@code ABIERTA} y la prioridad por defecto {@code MEDIA}.
	 * </p>
	 *
	 * @param habitacion  habitacion a la que pertenece la incidencia.
	 * @param descripcion descripcion del problema.
	 * @param prioridad   nivel de urgencia de la incidencia.
	 */
	public Incidencia(Habitacion habitacion, String descripcion, PrioridadIncidencia prioridad) {
		this.habitacion = habitacion;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.estadoIncidencia = EstadoIncidencia.ABIERTA;
		this.fechaApertura = LocalDateTime.now();
	}

// -------------------------------------------------------------------------
// Getters y Setters
// -------------------------------------------------------------------------

	/**
	 * Devuelve el identificador unico de la incidencia.
	 *
	 * @return id de la incidencia, o {@code null} si aun no se ha persistido.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Establece el identificador de la incidencia. Normalmente lo asigna Hibernate
	 * de forma automatica.
	 *
	 * @param id identificador a asignar.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Devuelve la habitacion a la que esta asociada esta incidencia.
	 *
	 * @return {@link Habitacion} asociada.
	 */
	public Habitacion getHabitacion() {
		return habitacion;
	}

	/**
	 * Establece la habitacion a la que pertenece esta incidencia.
	 *
	 * @param habitacion habitacion con la que se asocia la incidencia.
	 */
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	/**
	 * Devuelve el estado actual de la incidencia.
	 *
	 * @return {@link EstadoIncidencia} actual.
	 */
	public EstadoIncidencia getEstadoIncidencia() {
		return estadoIncidencia;
	}

	/**
	 * Establece el estado de la incidencia.
	 *
	 * @param estadoIncidencia nuevo estado.
	 */
	public void setEstadoIncidencia(EstadoIncidencia estadoIncidencia) {
		this.estadoIncidencia = estadoIncidencia;
	}

	/**
	 * Devuelve la prioridad de la incidencia.
	 *
	 * @return {@link PrioridadIncidencia} asignada.
	 */
	public PrioridadIncidencia getPrioridad() {
		return prioridad;
	}

	/**
	 * Establece la prioridad de la incidencia.
	 *
	 * @param prioridad nivel de urgencia a asignar.
	 */
	public void setPrioridad(PrioridadIncidencia prioridad) {
		this.prioridad = prioridad;
	}

	/**
	 * Devuelve la descripcion del problema.
	 *
	 * @return texto descriptivo de la incidencia.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripcion del problema.
	 *
	 * @param descripcion texto descriptivo de la incidencia.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la fecha y hora de apertura de la incidencia.
	 *
	 * @return {@link LocalDateTime} de apertura.
	 */
	public LocalDateTime getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * Establece la fecha y hora de apertura de la incidencia.
	 *
	 * @param fechaApertura fecha y hora de apertura.
	 */
	public void setFechaApertura(LocalDateTime fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * Devuelve la fecha y hora de cierre de la incidencia.
	 *
	 * @return {@link LocalDateTime} de cierre, o {@code null} si no esta cerrada.
	 */
	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * Establece la fecha y hora de cierre de la incidencia.
	 *
	 * @param fechaCierre fecha y hora de cierre.
	 */
	public void setFechaCierre(LocalDateTime fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	/**
	 * Devuelve el nombre del tecnico asignado a la incidencia.
	 *
	 * @return nombre del tecnico, o {@code null} si no hay ninguno asignado.
	 */
	public String getTecnicoAsignado() {
		return tecnicoAsignado;
	}

	/**
	 * Establece el tecnico responsable de resolver la incidencia.
	 *
	 * @param tecnicoAsignado nombre del tecnico.
	 */
	public void setTecnicoAsignado(String tecnicoAsignado) {
		this.tecnicoAsignado = tecnicoAsignado;
	}

	/**
	 * Devuelve las observaciones adicionales de la incidencia.
	 *
	 * @return texto de observaciones, o {@code null} si no hay ninguna.
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Establece las observaciones adicionales de la incidencia.
	 *
	 * @param observaciones notas sobre la gestion de la incidencia.
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

// -------------------------------------------------------------------------
// toString
// -------------------------------------------------------------------------

	/**
	 * Representacion legible de la incidencia, util para depuracion en logs.
	 *
	 * @return cadena con los campos principales de la incidencia.
	 */
	@Override
	public String toString() {
		return "Incidencia{" + "id=" + id + ", habitacionId=" + (habitacion != null ? habitacion.getId() : "null")
				+ ", estado=" + estadoIncidencia + ", prioridad=" + prioridad + ", fechaApertura=" + fechaApertura
				+ '}';
	}
}
