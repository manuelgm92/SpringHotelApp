package com.springhotel.dao;

import java.util.List;
import com.springhotel.entity.Incidencia;
import com.springhotel.entity.Incidencia.PrioridadIncidencia;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Interfaz DAO para la gestion de incidencias de mantenimiento del hotel.
 *
 * <p>
 * Define las operaciones CRUD (Create, Read, Update, Delete) y consultas
 * adicionales para gestionar el ciclo de vida de las incidencias asociadas a
 * las habitaciones del hotel.
 * </p>
 *
 * <p>
 * Todas las operaciones pueden lanzar {@link ErrorBaseDatosException} si se
 * produce un fallo en la capa de persistencia. Los metodos que buscan por ID o
 * actualizan/eliminan entidades especificas lanzan ademas
 * {@link EntidadNoEncontradaException} si la incidencia no existe.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @see Incidencia
 * @see com.springhotel.entity.Habitacion
 */
public interface IncidenciaDAO {

	/**
	 * Recupera todas las incidencias registradas en el sistema.
	 *
	 * <p>
	 * La lista devuelta incluye incidencias en cualquier estado: abiertas, en curso
	 * o cerradas. Si no hay incidencias registradas, se devuelve una lista vacia.
	 * </p>
	 *
	 * @return lista con todas las {@link Incidencia incidencias}, o lista vacia si
	 *         no hay ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a la base de
	 *                                 datos.
	 */
	List<Incidencia> mostrarTodasIncidencias() throws ErrorBaseDatosException;

	/**
	 * Busca una incidencia especifica por su identificador unico.
	 *
	 * @param id identificador de la incidencia a buscar (no puede ser
	 *           {@code null}).
	 * @return la {@link Incidencia} correspondiente al ID proporcionado.
	 * @throws EntidadNoEncontradaException si no existe una incidencia con ese ID.
	 * @throws ErrorBaseDatosException      si ocurre un error al acceder a la base
	 *                                      de datos.
	 */
	Incidencia mostrarIncidenciaPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Registra una nueva incidencia en el sistema.
	 *
	 * <p>
	 * La incidencia debe estar correctamente inicializada antes de ser persistida.
	 * </p>
	 *
	 * @param incidencia la {@link Incidencia} a registrar (no puede ser
	 *                   {@code null}).
	 * @throws ErrorBaseDatosException si ocurre un error al persistir la
	 *                                 incidencia.
	 */
	void altaIncidencia(Incidencia incidencia) throws ErrorBaseDatosException;

	/**
	 * Actualiza los datos de una incidencia existente.
	 *
	 * <p>
	 * Permite modificar el estado, prioridad, tecnico asignado, observaciones,
	 * fecha de cierre y cualquier otro campo de la incidencia. La incidencia debe
	 * existir previamente en la base de datos.
	 * </p>
	 *
	 * @param incidencia la {@link Incidencia} con los datos actualizados (debe
	 *                   tener un ID valido).
	 * @throws EntidadNoEncontradaException si la incidencia no existe en la base de
	 *                                      datos.
	 * @throws ErrorBaseDatosException      si ocurre un error al actualizar la
	 *                                      incidencia.
	 */
	void actualizaIncidencia(Incidencia incidencia) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Elimina una incidencia de la base de datos utilizando su identificador.
	 *
	 * <p>
	 * Primero se intenta recuperar la incidencia mediante su ID. Si no existe, se
	 * lanza una {@link EntidadNoEncontradaException}. Si la incidencia se encuentra
	 * correctamente, se elimina usando la sesión actual de Hibernate.
	 * </p>
	 *
	 * <p>
	 * Este método solo elimina la incidencia indicada; no afecta a la habitación
	 * asociada. En caso de que una habitación sea eliminada, sus incidencias
	 * relacionadas deben eliminarse mediante la configuración de la entidad o la
	 * lógica correspondiente en el servicio de habitaciones.
	 * </p>
	 *
	 * @param id el identificador único de la incidencia a eliminar.
	 * @throws EntidadNoEncontradaException si no existe una incidencia con el ID
	 *                                      proporcionado.
	 * @throws ErrorBaseDatosException      si ocurre un error durante la
	 *                                      eliminación en la base de datos.
	 */
	void eliminaIncidencia(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Recupera todas las incidencias que tienen una prioridad especifica.
	 *
	 * <p>
	 * Permite filtrar incidencias por nivel de urgencia: {@code BAJA},
	 * {@code MEDIA} o {@code ALTA}. Util para priorizar el trabajo del equipo de
	 * mantenimiento.
	 * </p>
	 *
	 * @param prioridad la {@link PrioridadIncidencia} por la que filtrar (no puede
	 *                  ser {@code null}).
	 * @return lista de {@link Incidencia incidencias} con esa prioridad, o lista
	 *         vacia si no hay ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a la base de
	 *                                 datos.
	 */
	List<Incidencia> mostrarIncidenciasPorPrioridad(PrioridadIncidencia prioridad) throws ErrorBaseDatosException;

	/**
	 * Recupera todas las incidencias asociadas a una habitacion especifica.
	 *
	 * @param idHabitacion identificador de la habitacion.
	 * @return lista de incidencias de esa habitacion.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
	 */
	List<Incidencia> mostrarIncidenciasPorHabitacion(Integer idHabitacion) throws ErrorBaseDatosException;

}
