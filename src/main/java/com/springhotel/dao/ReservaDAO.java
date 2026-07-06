package com.springhotel.dao;

import java.util.List;

import com.springhotel.entity.Reserva;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Interfaz que define las operaciones de acceso a datos para la entidad
 * {@link Reserva}.
 *
 * <p>
 * Todas las operaciones CRUD (Create, Read, Update, Delete) sobre la tabla
 * {@code reservas} se declaran aquí. La implementación concreta se encuentra en
 * {@link ReservaDaoImpl}.
 * </p>
 *
 * <p>
 * Usar una interfaz desacopla el controlador de la implementación, facilitando
 * el mantenimiento, la escalabilidad y las pruebas unitarias.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see ReservaDaoImpl
 */
public interface ReservaDAO {

    /**
     * Devuelve todas las reservas registradas en la base de datos.
     *
     * @return lista de reservas; vacía si no hay ninguna.
     * @throws ErrorBaseDatosException si ocurre un error al acceder a la BBDD.
     */
    List<Reserva> mostrarTodasReservas() throws ErrorBaseDatosException;

    /**
     * Busca y devuelve una reserva por su identificador.
     *
     * @param id identificador de la reserva.
     * @return la reserva encontrada.
     * @throws EntidadNoEncontradaException si no existe ninguna reserva con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al acceder a la BBDD.
     */
    Reserva mostrarReservaPorId(Integer id)
            throws EntidadNoEncontradaException, ErrorBaseDatosException;

    /**
     * Persiste una nueva reserva en la base de datos.
     *
     * @param reserva objeto reserva a guardar; el id debe ser {@code null}.
     * @throws ErrorBaseDatosException si ocurre un error al guardar.
     */
    void altaReserva(Reserva reserva) throws ErrorBaseDatosException;

    /**
     * Actualiza los datos de una reserva existente.
     *
     * @param reserva objeto reserva con los datos actualizados; el id debe
     *                corresponder a un registro existente.
     * @throws EntidadNoEncontradaException si no existe la reserva a actualizar.
     * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
     */
    void actualizaReserva(Reserva reserva)
            throws EntidadNoEncontradaException, ErrorBaseDatosException;

    /**
     * Elimina la reserva con el identificador indicado.
     *
     * @param id identificador de la reserva a eliminar.
     * @throws EntidadNoEncontradaException si no existe ninguna reserva con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al eliminar.
     */
    void eliminaReserva(Integer id)
            throws EntidadNoEncontradaException, ErrorBaseDatosException;
}
