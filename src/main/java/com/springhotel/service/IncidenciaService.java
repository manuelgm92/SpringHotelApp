package com.springhotel.service;

import java.util.List;

import com.springhotel.dto.IncidenciaDTO;
import com.springhotel.entity.Incidencia;
import com.springhotel.entity.Incidencia.PrioridadIncidencia;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Servicio de gestion de incidencias de mantenimiento del hotel.
 *
 * <p>
 * Proporciona operaciones de consulta, registro, actualizacion y eliminacion de
 * incidencias. Todas las operaciones delegan en la capa DAO y aplican
 * validaciones de negocio cuando es necesario.
 * </p>
 *
 * <p>
 * Las incidencias estan asociadas a una habitacion concreta y pueden tener
 * distintos estados y niveles de prioridad.
 * </p>
 *
 * @author Ana Laura
 * @version 1.1
 * @see Incidencia
 */
public interface IncidenciaService {

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
	List<IncidenciaDTO> mostrarTodasIncidencias() throws ErrorBaseDatosException;

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
	IncidenciaDTO mostrarIncidenciaPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Registra una nueva incidencia en el sistema.
	 *
	 * <p>
	 * La incidencia debe tener asignada una habitacion valida y una descripcion. El
	 * estado inicial sera {@code ABIERTA} y la fecha de apertura se establece
	 * automaticamente.
	 * </p>
	 *
	 * @param incidencia la {@link Incidencia} a registrar (no puede ser
	 *                   {@code null}).
	 * @throws ErrorBaseDatosException si ocurre un error al persistir la
	 *                                 incidencia.
	 */
	void altaIncidencia(IncidenciaDTO incidenciaDTO) throws ErrorBaseDatosException;

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
	void actualizaIncidencia(IncidenciaDTO incidenciaDTO) throws EntidadNoEncontradaException, ErrorBaseDatosException;

	/**
	 * Elimina una incidencia del sistema.
	 *
	 * <p>
	 * <strong>Advertencia:</strong> Esta operacion es destructiva y elimina
	 * permanentemente el registro de la base de datos. En entornos de produccion,
	 * se recomienda considerar un borrado logico o archivar las incidencias en
	 * lugar de eliminarlas.
	 * </p>
	 *
	 * @param incidencia la {@link Incidencia} a eliminar (debe tener un ID valido).
	 * @throws EntidadNoEncontradaException si la incidencia no existe en la base de
	 *                                      datos.
	 * @throws ErrorBaseDatosException      si ocurre un error al eliminar la
	 *                                      incidencia.
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
	List<IncidenciaDTO> mostrarIncidenciasPorPrioridad(PrioridadIncidencia prioridad) throws ErrorBaseDatosException;

	/**
	 * Recupera todas las incidencias asociadas a una habitación específica.
	 *
	 * <p>
	 * Este método permite filtrar las incidencias registradas en el sistema según
	 * el identificador de una habitación. Es útil para que el personal de
	 * mantenimiento o recepción pueda revisar rápidamente los problemas reportados
	 * en una habitación concreta.
	 * </p>
	 *
	 * @param idHabitacion el identificador único de la habitación cuyas incidencias
	 *                     se desean consultar (no puede ser {@code null}).
	 * @return lista de {@link Incidencia incidencias} asociadas a la habitación, o
	 *         una lista vacía si no existen incidencias registradas.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a la base de
	 *                                 datos.
	 */
	List<IncidenciaDTO> mostrarIncidenciasPorHabitacion(Integer idHabitacion) throws ErrorBaseDatosException;
}
