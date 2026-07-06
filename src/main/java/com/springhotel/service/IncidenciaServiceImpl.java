package com.springhotel.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springhotel.dao.IncidenciaDAO;
import com.springhotel.dto.IncidenciaDTO;
import com.springhotel.entity.Incidencia;
import com.springhotel.entity.Incidencia.PrioridadIncidencia;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;
import com.springhotel.mapper.IncidenciaMapper;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA
 * (Claude). Implementacion de {@link IncidenciaService} que gestiona la logica
 * de negocio relacionada con las incidencias de mantenimiento del hotel.
 *
 * <p>
 * Actua como capa intermedia entre los controladores y la capa de acceso a
 * datos ({@link IncidenciaDAO}). Se encarga de validar los datos de entrada
 * cuando es necesario y delega las operaciones CRUD al DAO correspondiente.
 * </p>
 *
 * <p>
 * La clase esta anotada con {@link Transactional}, por lo que Spring gestiona
 * automaticamente las transacciones de base de datos.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 *
 * @see IncidenciaService
 * @see IncidenciaDAO
 * @see Incidencia
 */
@Service
@Transactional
public class IncidenciaServiceImpl implements IncidenciaService {

	/**
	 * Objeto de acceso a datos para incidencias, inyectado automáticamente por
	 * Spring a través del constructor.
	 */
	private final IncidenciaDAO incidenciaDAO;

	/**
	 * Mapper para convertir entre entidades y DTOs de incidencias.
	 */
	private final IncidenciaMapper mapper;

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param incidenciaDAO DAO de incidencias inyectado por Spring
	 * @param mapper        mapper para conversión entre entidad y DTO
	 */
	@Autowired
	public IncidenciaServiceImpl(IncidenciaDAO incidenciaDAO, IncidenciaMapper mapper) {
		this.incidenciaDAO = incidenciaDAO;
		this.mapper = mapper;
	}

	/**
	 * Devuelve todas las incidencias registradas en el sistema.
	 *
	 * <p>
	 * Delega directamente la consulta al DAO sin aplicar logica de negocio
	 * adicional.
	 * </p>
	 *
	 * @return lista de incidencias; vacia si no hay ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
	 */
	@Override
	public List<IncidenciaDTO> mostrarTodasIncidencias() throws ErrorBaseDatosException {
		return incidenciaDAO.mostrarTodasIncidencias().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	/**
	 * Busca y devuelve una incidencia por su identificador.
	 *
	 * <p>
	 * Delega directamente la busqueda al DAO. Si no existe una incidencia con el
	 * identificador indicado, el DAO lanzara {@link EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id identificador de la incidencia.
	 * @return la incidencia encontrada.
	 * @throws EntidadNoEncontradaException si no existe ninguna incidencia con ese
	 *                                      id.
	 * @throws ErrorBaseDatosException      si ocurre un error al acceder a los
	 *                                      datos.
	 */
	@Override
	public IncidenciaDTO mostrarIncidenciaPorId(Integer id)
			throws EntidadNoEncontradaException, ErrorBaseDatosException {

		Incidencia incidencia = incidenciaDAO.mostrarIncidenciaPorId(id);
		return mapper.toDTO(incidencia);
	}

	/**
	 * Registra una nueva incidencia en el sistema.
	 *
	 * <p>
	 * Delega directamente la persistencia al DAO. La incidencia debe tener asociada
	 * una habitacion valida y una descripcion.
	 * </p>
	 *
	 * @param incidencia incidencia a registrar.
	 * @throws ErrorBaseDatosException si ocurre un error al guardar.
	 */
	@Override
	public void altaIncidencia(IncidenciaDTO incidenciaDTO) throws ErrorBaseDatosException {
		Incidencia incidencia = mapper.toEntity(incidenciaDTO);
		incidenciaDAO.altaIncidencia(incidencia);
	}

	/**
	 * Actualiza los datos de una incidencia existente.
	 *
	 * <p>
	 * Delega directamente la actualizacion al DAO. Si no existe una incidencia con
	 * el identificador indicado, el DAO lanzara
	 * {@link EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param incidencia incidencia con los datos actualizados.
	 * @throws EntidadNoEncontradaException si no existe la incidencia a actualizar.
	 * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
	 */
	@Override
	public void actualizaIncidencia(IncidenciaDTO incidenciaDTO)
			throws EntidadNoEncontradaException, ErrorBaseDatosException {

		Incidencia incidencia = mapper.toEntity(incidenciaDTO);
		incidenciaDAO.actualizaIncidencia(incidencia);
	}

	/**
	 * Elimina una incidencia del sistema.
	 *
	 * <p>
	 * Delega directamente la eliminacion al DAO. Si no existe una incidencia con el
	 * identificador indicado, el DAO lanzara {@link EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id identificador de la incidencia a eliminar.
	 * @throws EntidadNoEncontradaException si no existe la incidencia.
	 * @throws ErrorBaseDatosException      si ocurre un error al eliminar.
	 */
	@Override
	public void eliminaIncidencia(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		incidenciaDAO.eliminaIncidencia(id);
	}

	/**
	 * Recupera todas las incidencias que tienen una prioridad específica.
	 *
	 * <p>
	 * Este método delega directamente la consulta al DAO correspondiente. Si no
	 * existen incidencias con la prioridad indicada, se devolverá una lista vacía
	 * sin generar errores.
	 * </p>
	 *
	 * @param prioridad la prioridad por la que se desea filtrar las incidencias (no
	 *                  debe ser {@code null}).
	 * @return lista de incidencias que coinciden con la prioridad indicada, o una
	 *         lista vacía si no se encuentra ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
	 */
	@Override
	public List<IncidenciaDTO> mostrarIncidenciasPorPrioridad(PrioridadIncidencia prioridad)
			throws ErrorBaseDatosException {

		return incidenciaDAO.mostrarIncidenciasPorPrioridad(prioridad).stream().map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Recupera todas las incidencias asociadas a una habitación específica.
	 *
	 * <p>
	 * Este método delega la consulta al DAO, permitiendo obtener todas las
	 * incidencias registradas para la habitación indicada. Si la habitación no
	 * tiene incidencias asociadas, se devolverá una lista vacía.
	 * </p>
	 *
	 * @param idHabitacion el identificador único de la habitación cuyas incidencias
	 *                     se desean consultar (no debe ser {@code null}).
	 * @return lista de incidencias asociadas a la habitación indicada, o una lista
	 *         vacía si no existen incidencias registradas.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
	 */
	@Override
	public List<IncidenciaDTO> mostrarIncidenciasPorHabitacion(Integer idHabitacion) throws ErrorBaseDatosException {

		return incidenciaDAO.mostrarIncidenciasPorHabitacion(idHabitacion).stream().map(mapper::toDTO)
				.collect(Collectors.toList());
	}
}