package com.springhotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Entidad que representa una habitacion del hotel.
 *
 * <p>
 * Cada instancia de esta clase se corresponde con una fila de la tabla
 * {@code habitaciones} en la base de datos {@code hoteldb}. Hibernate gestiona
 * automaticamente la traduccion entre objetos Java y filas SQL gracias a las
 * anotaciones de mapeo ORM.
 * </p>
 *
 * <p>
 * Tipos de habitacion disponibles: {@code INDIVIDUAL}, {@code DOBLE} y
 * {@code SUITE}.
 * </p>
 *
 * <p>
 * Estados posibles de una habitacion: {@code LIBRE}, {@code OCUPADA},
 * {@code MANTENIMIENTO} y {@code LIMPIEZA}.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see com.springhotel.dao.Habitacion
 * @see Incidencia
 */
@Entity
@Table(name = "habitaciones")
public class Habitacion {

	/**
	 * Tipos de habitacion disponibles en el hotel.
	 */
	public enum TipoHabitacion {
		INDIVIDUAL, DOBLE, SUITE
	}

	/**
	 * Estados operativos posibles de una habitacion.
	 */
	public enum EstadoHabitacion {
		LIBRE, OCUPADA, MANTENIMIENTO, LIMPIEZA
	}

// -------------------------------------------------------------------------
// Campos
// -------------------------------------------------------------------------

	/**
	 * Identificador unico de la habitacion, generado automaticamente por la BBDD.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * Numero de habitacion visible para los clientes. Debe ser unico en el hotel.
	 */
	@NotBlank(message = "El número de habitación es obligatorio.")
	@Column(name = "numero_habitacion", nullable = false, unique = true, length = 10)
	private String numeroHabitacion;

	/**
	 * Tipo de habitacion: INDIVIDUAL, DOBLE o SUITE. Se almacena como texto en la
	 * columna para mayor legibilidad en la BBDD.
	 */
	@NotNull(message = "Debe seleccionar un tipo de habitación.")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_habitacion", nullable = false)
	private TipoHabitacion tipoHabitacion;

	/**
	 * Precio por noche de la habitacion en euros. Debe ser mayor o igual a 0.
	 */
	@NotNull(message = "El precio por noche es obligatorio.")
	@Column(name = "precio_noche", nullable = false)
	private Double precioNoche;

	/**
	 * Numero maximo de personas que puede alojar la habitacion. Debe ser mayor que
	 * 0.
	 */
	@NotNull(message = "La capacidad es obligatoria.")
	@Column(name = "capacidad", nullable = false)
	private Integer capacidad;

	/**
	 * Estado actual de la habitacion: LIBRE, OCUPADA, MANTENIMIENTO o LIMPIEZA. El
	 * valor por defecto al crear una habitacion es {@code LIBRE}.
	 */
	@NotNull(message = "Debe seleccionar un estado para la habitación.")
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_habitacion", nullable = false)
	private EstadoHabitacion estadoHabitacion = EstadoHabitacion.LIBRE;

	/**
	 * Indica si la habitacion esta adaptada para personas con discapacidad.
	 * {@code true} si esta adaptada, {@code false} en caso contrario.
	 */
	@NotNull(message = "Debe indicar si la habitación está adaptada o no.")
	@Column(name = "adaptada_discapacidad", nullable = false)
	private Boolean adaptadaDiscapacidad = false;

// -------------------------------------------------------------------------
// Constructores
// -------------------------------------------------------------------------

	/**
	 * Constructor sin argumentos requerido por Hibernate para instanciar entidades
	 * al cargarlas desde la base de datos.
	 */
	public Habitacion() {
	}

	/**
	 * Constructor de conveniencia para crear una habitacion con sus campos
	 * principales.
	 *
	 * @param numeroHabitacion     codigo unico de la habitacion.
	 * @param tipoHabitacion       tipo de habitacion (INDIVIDUAL, DOBLE, SUITE).
	 * @param precioNoche          precio por noche en euros.
	 * @param capacidad            numero maximo de personas.
	 * @param adaptadaDiscapacidad {@code true} si esta adaptada para
	 *                             discapacitados.
	 */
	public Habitacion(String numeroHabitacion, TipoHabitacion tipoHabitacion, Double precioNoche, Integer capacidad,
			Boolean adaptadaDiscapacidad) {
		this.numeroHabitacion = numeroHabitacion;
		this.tipoHabitacion = tipoHabitacion;
		this.precioNoche = precioNoche;
		this.capacidad = capacidad;
		this.adaptadaDiscapacidad = adaptadaDiscapacidad;
		this.estadoHabitacion = EstadoHabitacion.LIBRE;
	}

// -------------------------------------------------------------------------
// Getters y Setters
// -------------------------------------------------------------------------

	/**
	 * Devuelve el identificador unico de la habitacion.
	 *
	 * @return id de la habitacion, o {@code null} si aun no se ha persistido.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Establece el identificador de la habitacion. Normalmente lo asigna Hibernate
	 * de forma automatica.
	 *
	 * @param id identificador a asignar.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Devuelve el numero de habitacion visible para los clientes.
	 *
	 * @return numero de habitacion (por ejemplo, "101").
	 */
	public String getNumeroHabitacion() {
		return numeroHabitacion;
	}

	/**
	 * Establece el numero de habitacion.
	 *
	 * @param numeroHabitacion codigo unico de la habitacion.
	 */
	public void setNumeroHabitacion(String numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	/**
	 * Devuelve el tipo de habitacion.
	 *
	 * @return {@link TipoHabitacion} (INDIVIDUAL, DOBLE o SUITE).
	 */
	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	/**
	 * Establece el tipo de habitacion.
	 *
	 * @param tipoHabitacion tipo a asignar.
	 */
	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	/**
	 * Devuelve el precio por noche de la habitacion.
	 *
	 * @return precio en euros.
	 */
	public Double getPrecioNoche() {
		return precioNoche;
	}

	/**
	 * Establece el precio por noche de la habitacion.
	 *
	 * @param precioNoche precio en euros (debe ser >= 0).
	 */
	public void setPrecioNoche(Double precioNoche) {
		this.precioNoche = precioNoche;
	}

	/**
	 * Devuelve la capacidad maxima de la habitacion.
	 *
	 * @return numero maximo de personas.
	 */
	public Integer getCapacidad() {
		return capacidad;
	}

	/**
	 * Establece la capacidad maxima de la habitacion.
	 *
	 * @param capacidad numero maximo de personas (debe ser > 0).
	 */
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * Devuelve el estado operativo actual de la habitacion.
	 *
	 * @return {@link EstadoHabitacion} actual.
	 */
	public EstadoHabitacion getEstadoHabitacion() {
		return estadoHabitacion;
	}

	/**
	 * Establece el estado operativo de la habitacion.
	 *
	 * @param estadoHabitacion nuevo estado.
	 */
	public void setEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
		this.estadoHabitacion = estadoHabitacion;
	}

	/**
	 * Indica si la habitacion esta adaptada para personas con discapacidad.
	 *
	 * @return {@code true} si esta adaptada, {@code false} en caso contrario.
	 */
	public Boolean getAdaptadaDiscapacidad() {
		return adaptadaDiscapacidad;
	}

	/**
	 * Establece si la habitacion esta adaptada para personas con discapacidad.
	 *
	 * @param adaptadaDiscapacidad {@code true} para marcarla como adaptada.
	 */
	public void setAdaptadaDiscapacidad(Boolean adaptadaDiscapacidad) {
		this.adaptadaDiscapacidad = adaptadaDiscapacidad;
	}

// -------------------------------------------------------------------------
// toString
// -------------------------------------------------------------------------

	/**
	 * Representacion legible de la habitacion, util para depuracion en logs.
	 *
	 * @return cadena con los campos principales de la habitacion.
	 */
	@Override
	public String toString() {
		return "Habitacion{" + "id=" + id + ", numero='" + numeroHabitacion + '\'' + ", tipo=" + tipoHabitacion
				+ ", precio=" + precioNoche + ", capacidad=" + capacidad + ", estado=" + estadoHabitacion
				+ ", adaptada=" + adaptadaDiscapacidad + '}';
	}
}