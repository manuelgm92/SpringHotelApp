package com.springhotel.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springhotel.dao.HabitacionDAO;
import com.springhotel.dto.HabitacionDTO;
import com.springhotel.entity.Habitacion;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;
import com.springhotel.mapper.HabitacionMapper;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA
 * (Claude). Implementacion de la interfaz {@link HabitacionService} que
 * contiene la logica de negocio relacionada con la gestion de habitaciones del
 * hotel.
 * <p>
 * Actua como capa intermedia entre los controladores y la capa de acceso a
 * datos ({@link HabitacionDAO}). Se encarga de validar los datos de entrada
 * antes de delegar las operaciones al DAO correspondiente.
 * </p>
 * <p>
 * Todas las operaciones estan marcadas con {@link Transactional} a nivel de
 * clase, por lo que Spring gestiona automaticamente el ciclo de vida de las
 * transacciones de base de datos.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 *
 * @see HabitacionService
 * @see HabitacionDAO
 * @see Habitacion
 */
@Service
@Transactional
public class HabitacionServiceImpl implements HabitacionService {

	/**
	 * Objeto de acceso a datos para habitaciones, inyectado automáticamente por
	 * Spring.
	 */
	private final HabitacionDAO habitacionDAO;

	/**
	 * Mapper para convertir entre entidades y DTOs de habitaciones.
	 */
	private final HabitacionMapper mapper;

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param habitacionDAO DAO de habitaciones
	 * @param mapper        mapper para conversión entre entidad y DTO
	 */
	@Autowired
	public HabitacionServiceImpl(HabitacionDAO habitacionDAO, HabitacionMapper mapper) {
		this.habitacionDAO = habitacionDAO;
		this.mapper = mapper;
	}

	/**
	 * Devuelve todas las habitaciones registradas en el sistema.
	 * <p>
	 * Delega directamente la consulta al DAO sin aplicar logica de negocio
	 * adicional.
	 * </p>
	 *
	 * @return lista de habitaciones; vacia si no hay ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
	 */
	@Override

	public List<HabitacionDTO> mostrarTodasHabitaciones() throws ErrorBaseDatosException {
		return habitacionDAO.mostrarTodasHabitaciones().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	/**
	 * Busca y devuelve una habitacion por su identificador.
	 * <p>
	 * Delega directamente la busqueda al DAO. Si no existe una habitacion con el
	 * identificador indicado, el DAO lanzara
	 * {@link com.springhotel.exception.EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id identificador de la habitacion.
	 * @return la habitacion encontrada.
	 * @throws EntidadNoEncontradaException si no existe ninguna habitacion con ese
	 *                                      id.
	 * @throws ErrorBaseDatosException      si ocurre un error al acceder a los
	 *                                      datos.
	 */
	@Override
	public HabitacionDTO mostrarHabitacionPorId(Integer id)
			throws EntidadNoEncontradaException, ErrorBaseDatosException {
		Habitacion h = habitacionDAO.mostrarHabitacionPorId(id);
		return mapper.toDTO(h);
	}

	/**
	 * Persiste una nueva habitacion en el sistema.
	 * <p>
	 * Delega directamente la persistencia al DAO sin aplicar logica de negocio
	 * adicional.
	 * </p>
	 *
	 * @param habitacion objeto habitacion a guardar; el id debe ser {@code null}.
	 * @throws ErrorBaseDatosException si ocurre un error al guardar.
	 */
	@Override
	public void altaHabitacion(HabitacionDTO dto) throws ErrorBaseDatosException {
		Habitacion h = mapper.toEntity(dto);
		habitacionDAO.altaHabitacion(h);
	}

	/**
	 * Actualiza los datos de una habitacion existente.
	 * <p>
	 * Delega directamente la actualizacion al DAO. Si no existe una habitacion con
	 * el identificador indicado, el DAO lanzara
	 * {@link com.springhotel.exception.EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param habitacion objeto habitacion con los datos actualizados; el id debe
	 *                   corresponder a un registro existente.
	 * @throws EntidadNoEncontradaException si no existe la habitacion a actualizar.
	 * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
	 */
	@Override
	public void actualizaHabitacion(HabitacionDTO habitacionDTO)
			throws EntidadNoEncontradaException, ErrorBaseDatosException {
		Habitacion h = mapper.toEntity(habitacionDTO);
		habitacionDAO.actualizaHabitacion(h);
	}

	/**
	 * Elimina la habitacion con el identificador indicado.
	 * <p>
	 * Delega directamente la eliminacion al DAO. Si no existe una habitacion con el
	 * identificador indicado, el DAO lanzara
	 * {@link com.springhotel.exception.EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id identificador de la habitacion a eliminar.
	 * @throws EntidadNoEncontradaException si no existe ninguna habitacion con ese
	 *                                      id.
	 * @throws ErrorBaseDatosException      si ocurre un error al eliminar.
	 */
	@Override
	public void eliminaHabitacion(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		habitacionDAO.eliminaHabitacion(id);
	}
}