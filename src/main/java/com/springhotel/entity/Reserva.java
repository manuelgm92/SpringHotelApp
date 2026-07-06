package com.springhotel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Entidad que representa una reserva realizada por un huésped en el hotel.
 *
 * <p>
 * Cada instancia de esta clase se corresponde con una fila de la tabla
 * {@code reservas} en la base de datos {@code hoteldb}. Hibernate gestiona
 * automáticamente la traducción entre objetos Java y filas SQL gracias a las
 * anotaciones de mapeo ORM.
 * </p>
 *
 * <p>
 * Estados posibles de una reserva: {@code PENDIENTE}, {@code CONFIRMADA},
 * {@code CANCELADA} y {@code FINALIZADA}.
 * </p>
 *
 * <p>
 * Una reserva siempre está asociada a un huésped y a una habitación.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see Habitacion
 * @see Huesped
 */
@Entity
@Table(name = "reservas")
public class Reserva {

    // -------------------------------------------------------------------------
    // Enums
    // -------------------------------------------------------------------------

    /**
     * Estados posibles de una reserva dentro del sistema.
     */
    public enum EstadoReserva {
        PENDIENTE, CONFIRMADA, CANCELADA, FINALIZADA
    }

    // -------------------------------------------------------------------------
    // Campos
    // -------------------------------------------------------------------------

    /**
     * Identificador único de la reserva, generado automáticamente por la BBDD.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Fecha de entrada del huésped. Debe ser hoy o una fecha futura.
     */
    @NotNull(message = "La fecha de check-in es obligatoria.")
    @FutureOrPresent(message = "La fecha de check-in no puede ser anterior a hoy.")
    @Column(name = "check_in", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkIn;

    /**
     * Fecha de salida del huésped. Debe ser posterior al check-in.
     */
    @NotNull(message = "La fecha de check-out es obligatoria.")
    @Future(message = "La fecha de check-out debe ser futura.")
    @Column(name = "check_out", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOut;

    /**
     * Número de personas incluidas en la reserva. Debe ser mayor que 0.
     */
    @NotNull(message = "Debe indicar el número de personas.")
    @Min(value = 1, message = "El número de personas debe ser mayor que 0.")
    @Column(name = "num_personas", nullable = false)
    private Integer numPersonas;

    /**
     * Estado actual de la reserva. Por defecto se establece como {@code PENDIENTE}.
     */
    @NotNull(message = "Debe seleccionar un estado para la reserva.")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reserva", nullable = false)
    private EstadoReserva estadoReserva = EstadoReserva.PENDIENTE;

    /**
     * Fecha y hora en que se creó la reserva. Se asigna automáticamente.
     */
    @CreationTimestamp
    @Column(name = "fecha_creacion_reserva", updatable = false)
    private LocalDateTime fechaCreacionReserva;

    /**
     * Huésped que realiza la reserva. Relación muchos-a-uno.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_huesped", nullable = false)
    private Huesped huesped;

    /**
     * Habitación asignada a la reserva. Relación muchos-a-uno.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Habitacion habitacion;

    // -------------------------------------------------------------------------
    // Constructores
    // -------------------------------------------------------------------------

    /**
     * Constructor sin argumentos requerido por Hibernate para instanciar entidades
     * al cargarlas desde la base de datos.
     */
    public Reserva() {
    }

    /**
     * Constructor de conveniencia para crear una reserva con sus campos principales.
     *
     * @param checkIn      fecha de entrada.
     * @param checkOut     fecha de salida.
     * @param numPersonas  número de personas.
     * @param huesped      huésped que realiza la reserva.
     * @param habitacion   habitación asignada.
     */
    public Reserva(LocalDate checkIn, LocalDate checkOut, Integer numPersonas,
                   Huesped huesped, Habitacion habitacion) {

        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numPersonas = numPersonas;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.estadoReserva = EstadoReserva.PENDIENTE;
    }

    // -------------------------------------------------------------------------
    // Validaciones adicionales
    // -------------------------------------------------------------------------

    /**
     * Valida que la fecha de salida sea posterior a la fecha de entrada.
     *
     * @return {@code true} si las fechas son válidas.
     */
    @AssertTrue(message = "La fecha de check-out debe ser posterior al check-in.")
    public boolean isFechasValidas() {
        return checkIn != null && checkOut != null && checkOut.isAfter(checkIn);
    }

    // -------------------------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------------------------
    /**
     * Obtiene el identificador único de la reserva.
     *
     * @return el ID de la reserva.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Establece el identificador único de la reserva.
     *
     * @param id el ID a asignar.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Obtiene la fecha de entrada (check-in) del huésped.
     *
     * @return la fecha de check-in.
     */
    public LocalDate getCheckIn() {
        return checkIn;
    }
    /**
     * Establece la fecha de entrada (check-in) del huésped.
     *
     * @param checkIn la fecha de check-in a asignar.
     */
    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }
    /**
     * Obtiene la fecha de salida (check-out) del huésped.
     *
     * @return la fecha de check-out.
     */
    public LocalDate getCheckOut() {
        return checkOut;
    }

    /**
     * Establece la fecha de salida (check-out) del huésped.
     *
     * @param checkOut la fecha de check-out a asignar.
     */
    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
    /**
     * Obtiene el número de personas incluidas en la reserva.
     *
     * @return el número de personas.
     */
    public Integer getNumPersonas() {
        return numPersonas;
    }
    /**
     * Establece el número de personas incluidas en la reserva.
     *
     * @param numPersonas el número de personas a asignar.
     */
    public void setNumPersonas(Integer numPersonas) {
        this.numPersonas = numPersonas;
    }
    /**
     * Obtiene el estado actual de la reserva.
     *
     * @return el estado de la reserva (PENDIENTE, CONFIRMADA, CANCELADA o FINALIZADA).
     */
    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }
    /**
     * Establece el estado actual de la reserva.
     *
     * @param estadoReserva el estado a asignar (PENDIENTE, CONFIRMADA, CANCELADA o FINALIZADA).
     */
    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
    /**
     * Obtiene la fecha y hora en que se creó la reserva.
     *
     * @return la fecha y hora de creación de la reserva.
     */
    public LocalDateTime getFechaCreacionReserva() {
        return fechaCreacionReserva;
    }
    /**
     * Establece la fecha y hora de creación de la reserva.
     *
     * @param fechaCreacionReserva la fecha y hora de creación a asignar.
     */
    public void setFechaCreacionReserva(LocalDateTime fechaCreacionReserva) {
        this.fechaCreacionReserva = fechaCreacionReserva;
    }
    /**
     * Obtiene el huésped asociado a esta reserva.
     *
     * @return el huésped que realiza la reserva.
     */
    public Huesped getHuesped() {
        return huesped;
    }
    /**
     * Establece el huésped asociado a esta reserva.
     *
     * @param huespedDTO el huésped a asignar.
     */
    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }
    /**
     * Obtiene la habitación asignada a esta reserva.
     *
     * @return la habitación reservada.
     */
    public Habitacion getHabitacion() {
        return habitacion;
    }
    /**
     * Establece la habitación asignada a esta reserva.
     *
     * @param habitacion la habitación a asignar.
     */
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    /**
     * Representación legible de la reserva, útil para depuración en logs.
     *
     * @return cadena con los campos principales de la reserva.
     */
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", numPersonas=" + numPersonas +
                ", estado=" + estadoReserva +
                ", fechaCreacion=" + fechaCreacionReserva +
                '}';
    }
}
