package com.springhotel.service;

import java.util.List;

import com.springhotel.dto.ReservaDTO;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Interfaz de la capa de servicio para la gestión de entidades {@link Reserva}.
 *
 * <p>
 * Define el contrato de las operaciones de negocio relacionadas con las reservas
 * realizadas por los huéspedes del hotel. Actúa como intermediaria entre la capa
 * de presentación (controladores) y la capa de acceso a datos
 * ({@link com.springhotel.dao.ReservaDAO}).
 * </p>
 *
 * <p>
 * Las implementaciones de esta interfaz son responsables de aplicar la lógica
 * de negocio antes de delegar las operaciones al DAO correspondiente, tales como
 * validación de fechas, capacidad de la habitación o estado de la reserva.
 * </p>
 *
 * <p>
 * Esta capa garantiza que las reglas del dominio se cumplan de forma consistente
 * en toda la aplicación.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see com.springhotel.dao.ReservaDAO
 * @see Reserva
 */
public interface ReservaService {

    /**
     * Devuelve todas las reservas registradas en el sistema.
     *
     * @return lista de reservas; vacía si no hay ninguna.
     * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
     */
    List<ReservaDTO> listarReservas() throws ErrorBaseDatosException;

    /**
     * Busca y devuelve una reserva por su identificador.
     *
     * @param id identificador de la reserva.
     * @return la reserva encontrada.
     * @throws EntidadNoEncontradaException si no existe ninguna reserva con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al acceder a los datos.
     */
    ReservaDTO mostrarReservaPorId(Integer id)
            throws EntidadNoEncontradaException, ErrorBaseDatosException;

    /**
     * Persiste una nueva reserva en el sistema.
     *
     * <p>
     * Antes de guardar, la implementación debe validar que las fechas sean correctas,
     * que la habitación tenga capacidad suficiente y que los datos sean coherentes.
     * </p>
     *
     * @param reserva objeto reserva a guardar; el id debe ser {@code null}.
     * @throws ErrorBaseDatosException si ocurre un error al guardar.
     */
    void altaReserva(ReservaDTO reserva) throws ErrorBaseDatosException;

    /**
     * Actualiza los datos de una reserva existente.
     *
     * <p>
     * La implementación debe verificar que la reserva exista y que los datos
     * actualizados cumplan las reglas de negocio.
     * </p>
     *
     * @param reserva objeto reserva con los datos actualizados; el id debe
     *                corresponder a un registro existente.
     * @throws EntidadNoEncontradaException si no existe la reserva a actualizar.
     * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
     */
    void actualizaReserva(ReservaDTO reserva)
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
