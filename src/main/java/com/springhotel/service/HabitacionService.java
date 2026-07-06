package com.springhotel.service;

import java.util.List;

import com.springhotel.dto.HabitacionDTO;
import com.springhotel.entity.Habitacion;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Interfaz de la capa de servicio para la gestion de entidades {@link Habitacion}.
 * <p>
 * Define el contrato de las operaciones de negocio relacionadas con las habitaciones
 * del hotel. Actua como intermediaria entre la capa de presentacion (controladores)
 * y la capa de acceso a datos ({@link com.springhotel.dao.HabitacionDAO}).
 * </p>
 * <p>
 * Las implementaciones de esta interfaz son responsables de aplicar
 * la logica de negocio antes de delegar las operaciones al DAO correspondiente.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see com.springhotel.dao.HabitacionDAO
 * @see Habitacion
 */
public interface HabitacionService {

	/**
	 * Devuelve todas las habitaciones registradas en el sistema.
	 *
	 * @return lista de habitaciones; vacia si no hay ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
	 */
	List<HabitacionDTO> mostrarTodasHabitaciones() throws ErrorBaseDatosException;

	/**
	 * Busca y devuelve una habitacion por su identificador.
	 *
	 * @param id identificador de la habitacion.
	 * @return la habitacion encontrada.
	 * @throws EntidadNoEncontradaException si no existe ninguna habitacion con ese id.
	 * @throws ErrorBaseDatosException      si ocurre un error al acceder a los datos.
	 */
	HabitacionDTO mostrarHabitacionPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Persiste una nueva habitacion en el sistema.
	 *
	 * @param habitacion objeto habitacion a guardar; el id debe ser {@code null}.
	 * @throws ErrorBaseDatosException si ocurre un error al guardar.
	 */
	void altaHabitacion(HabitacionDTO habitacionDTO) throws ErrorBaseDatosException;

	/**
	 * Actualiza los datos de una habitacion existente.
	 *
	 * @param habitacion objeto habitacion con los datos actualizados; el id debe
	 *                   corresponder a un registro existente.
	 * @throws EntidadNoEncontradaException si no existe la habitacion a actualizar.
	 * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
	 */
	void actualizaHabitacion(HabitacionDTO habitacionDTO) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Elimina la habitacion con el identificador indicado.
	 *
	 * @param id identificador de la habitacion a eliminar.
	 * @throws EntidadNoEncontradaException si no existe ninguna habitacion con ese id.
	 * @throws ErrorBaseDatosException      si ocurre un error al eliminar.
	 */
	void eliminaHabitacion(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;
}