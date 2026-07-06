package com.springhotel.dao;

import java.util.List;

import com.springhotel.entity.Habitacion;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Interfaz que define las operaciones de acceso a datos para la entidad
 * {@link Habitacion}.
 *
 * <p>
 * Todas las operaciones CRUD (Create, Read, Update, Delete) sobre la tabla
 * {@code habitaciones} se declaran aqui. La implementacion concreta se
 * encuentra en {@link HabitacionDaoImpl}.
 * </p>
 *
 * <p>
 * Usar una interfaz desacopla el controlador de la implementacion, facilitando
 * el mantenimiento y los tests.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see HabitacionDaoImpl
 */
public interface HabitacionDAO {

	/**
	 * Devuelve todas las habitaciones registradas en la base de datos.
	 *
	 * @return lista de habitaciones; vacia si no hay ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a la BBDD.
	 */
	List<Habitacion> mostrarTodasHabitaciones() throws ErrorBaseDatosException;

	/**
	 * Busca y devuelve una habitacion por su identificador.
	 *
	 * @param id identificador de la habitacion.
	 * @return la habitacion encontrada.
	 * @throws EntidadNoEncontradaException si no existe ninguna habitacion con ese
	 *                                      id.
	 * @throws ErrorBaseDatosException      si ocurre un error al acceder a la BBDD.
	 */
	Habitacion mostrarHabitacionPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Persiste una nueva habitacion en la base de datos.
	 *
	 * @param habitacion objeto habitacion a guardar; el id debe ser {@code null}.
	 * @throws ErrorBaseDatosException si ocurre un error al guardar.
	 */
	void altaHabitacion(Habitacion habitacion) throws ErrorBaseDatosException;

	/**
	 * Actualiza los datos de una habitacion existente.
	 *
	 * @param habitacion objeto habitacion con los datos actualizados; el id debe
	 *                   corresponder a un registro existente.
	 * @throws EntidadNoEncontradaException si no existe la habitacion a actualizar.
	 * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
	 */
	void actualizaHabitacion(Habitacion habitacion) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Elimina la habitacion con el identificador indicado.
	 *
	 * @param id identificador de la habitacion a eliminar.
	 * @throws EntidadNoEncontradaException si no existe ninguna habitacion con ese
	 *                                      id.
	 * @throws ErrorBaseDatosException      si ocurre un error al eliminar.
	 */
	void eliminaHabitacion(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;
}
