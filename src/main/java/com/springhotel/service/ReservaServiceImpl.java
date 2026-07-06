package com.springhotel.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springhotel.dao.HabitacionDAO;
import com.springhotel.dao.ReservaDAO;
import com.springhotel.dto.HuespedDTO;
import com.springhotel.dto.ReservaDTO;
import com.springhotel.entity.Habitacion;
import com.springhotel.entity.Huesped;
import com.springhotel.entity.Reserva;
import com.springhotel.exception.ArgumentoNoValidoException;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;
import com.springhotel.mapper.HuespedMapper;
import com.springhotel.mapper.ReservaMapper;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA
 * (Claude). Implementación de la interfaz {@link ReservaService} que contiene
 * la lógica de negocio relacionada con la gestión de reservas del hotel.
 *
 * <p>
 * Actúa como capa intermedia entre los controladores y la capa de acceso a
 * datos ({@link ReservaDAO}). Se encarga de validar los datos de entrada antes
 * de delegar las operaciones al DAO correspondiente, garantizando que las
 * reglas del dominio se cumplan correctamente.
 * </p>
 *
 * <p>
 * Todas las operaciones están marcadas con {@link Transactional} a nivel de
 * clase, por lo que Spring gestiona automáticamente el ciclo de vida de las
 * transacciones de base de datos.
 * </p>
 *
 * <p>
 * Entre las validaciones aplicadas se incluyen:
 * </p>
 * <ul>
 * <li>Verificación de que la fecha de salida es posterior a la fecha de
 * entrada.</li>
 * <li>Comprobación de que el número de personas es válido.</li>
 * <li>Validación de que la habitación seleccionada tiene capacidad
 * suficiente.</li>
 * </ul>
 *
 * @author Ana y Manuel
 * @version 1.0
 *
 * @see ReservaService
 * @see ReservaDAO
 * @see Reserva
 */
@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

	/**
	 * Logger del sistema para la clase {@link ReservaServiceImpl}. Se utiliza para
	 * registrar eventos, flujos de datos y excepciones en los diferentes niveles de
	 * depuración (DEBUG, INFO, WARN, ERROR).
	 */
	private static final Logger logger = Logger.getLogger(ReservaServiceImpl.class);

	/**
	 * Objeto de acceso a datos para reservas, inyectado automáticamente por Spring
	 * a través del constructor.
	 */
	private final ReservaDAO reservaDao;

	/**
	 * Objeto de acceso a datos para habitaciones, inyectado automáticamente por
	 * Spring a través del constructor.
	 */
	private final HabitacionDAO habitacionDao;

	/**
	 * Servicio de huéspedes necesario para validaciones y carga de datos.
	 */
	private final HuespedService huespedService;

	/**
	 * Mapper para conversión entre entidad y DTO de huéspedes.
	 */
	private final HuespedMapper huespedMapper;

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param reservaDao     DAO de reservas
	 * @param habitacionDao  DAO de habitaciones
	 * @param huespedService servicio de gestión de huéspedes
	 * @param huespedMapper  mapper para conversión entre entidad y DTO
	 */
	@Autowired
	public ReservaServiceImpl(ReservaDAO reservaDao, HabitacionDAO habitacionDao, HuespedService huespedService,
			HuespedMapper huespedMapper) {
		this.reservaDao = reservaDao;
		this.habitacionDao = habitacionDao;
		this.huespedService = huespedService;
		this.huespedMapper = huespedMapper;
	}
	// -------------------------------------------------------------------------
	// Métodos de servicio
	// -------------------------------------------------------------------------

	/**
	 * Devuelve todas las reservas registradas en el sistema.
	 *
	 * <p>
	 * Delega directamente la consulta al DAO sin aplicar lógica de negocio
	 * adicional.
	 * </p>
	 *
	 * @return lista de reservas; vacía si no hay ninguna.
	 * @throws ErrorBaseDatosException si ocurre un error al acceder a los datos.
	 */
	@Override
	public List<ReservaDTO> listarReservas() throws ErrorBaseDatosException {
		return ReservaMapper.toDTOList(reservaDao.mostrarTodasReservas());
	}

	/**
	 * Busca y devuelve una reserva por su identificador.
	 *
	 * <p>
	 * Delega directamente la búsqueda al DAO. Si no existe una reserva con el
	 * identificador indicado, el DAO lanzará
	 * {@link com.springhotel.exception.EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id identificador de la reserva.
	 * @return la reserva encontrada.
	 * @throws EntidadNoEncontradaException si no existe ninguna reserva con ese id.
	 * @throws ErrorBaseDatosException      si ocurre un error al acceder a los
	 *                                      datos.
	 */
	@Override
	public ReservaDTO mostrarReservaPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		return ReservaMapper.toDTO(reservaDao.mostrarReservaPorId(id));
	}

	/**
	 * Persiste una nueva reserva en el sistema.
	 *
	 * <p>
	 * Antes de delegar la operación al DAO, se valida que los datos de la reserva
	 * cumplan las reglas de negocio (fechas válidas, capacidad adecuada, etc.).
	 * </p>
	 *
	 * @param reserva objeto reserva a guardar; el id debe ser {@code null}.
	 * @throws ErrorBaseDatosException si ocurre un error al guardar.
	 */
	@Override
	public void altaReserva(ReservaDTO reservaDTO) throws ErrorBaseDatosException {
		if (reservaDTO == null) {
			logger.warn("Service: intento de alta con reserva DTO nula.");
			throw new ArgumentoNoValidoException("No se puede dar de alta una reserva nula.");
		}

		if (reservaDTO.getId() != null) {
			logger.warn("Service: intento de alta con ID ya asignado: " + reservaDTO.getId());
			throw new ArgumentoNoValidoException("Una nueva reserva no debe tener un ID asignado.");
		}

		Reserva reserva = ReservaMapper.toEntity(reservaDTO);

		if (reserva.getHuesped() == null || reserva.getHuesped().getId() == null) {
			throw new ArgumentoNoValidoException("Debe seleccionar un huésped válido.");
		}

		try {
			HuespedDTO huespedDTO = huespedService.mostrarHuespedPorId(reserva.getHuesped().getId());
			Huesped huespedEntidad = huespedMapper.toEntity(huespedDTO);
			reserva.setHuesped(huespedEntidad);
		} catch (EntidadNoEncontradaException e) {
			throw new ArgumentoNoValidoException("El huésped seleccionado no existe.");
		}

		if (reserva.getHabitacion() == null || reserva.getHabitacion().getId() == null) {
			throw new ArgumentoNoValidoException("Debe seleccionar una habitación válida.");
		}

		try {
			Habitacion habReal = habitacionDao.mostrarHabitacionPorId(reserva.getHabitacion().getId());
			reserva.setHabitacion(habReal);
		} catch (EntidadNoEncontradaException e) {
			throw new ArgumentoNoValidoException("La habitación seleccionada no existe.");
		}

		validarReserva(reserva);
		logger.debug("Service: dando de alta a la reserva: " + reserva.getId());
		reservaDao.altaReserva(reserva);
	}

	/**
	 * Actualiza los datos de una reserva existente.
	 *
	 * <p>
	 * Antes de delegar la operacion al DAO, rehidrata la habitacion desde la base
	 * de datos y valida que los datos actualizados cumplan las reglas de negocio.
	 * Si no existe una reserva con el identificador indicado, el DAO lanzara
	 * {@link EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param reserva objeto reserva con los datos actualizados; el id debe
	 *                corresponder a un registro existente.
	 * @throws EntidadNoEncontradaException si no existe la reserva a actualizar o
	 *                                      la habitacion seleccionada no existe.
	 * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
	 */
	@Override
	public void actualizaReserva(ReservaDTO reservaDTO) throws EntidadNoEncontradaException, ErrorBaseDatosException {

		if (reservaDTO == null || reservaDTO.getId() == null) {
			logger.warn("Service: intento de actualización con reserva nula o sin ID.");
			throw new ArgumentoNoValidoException("Reserva no encontrada.");
		}

		Reserva reserva = ReservaMapper.toEntity(reservaDTO);

		if (reserva.getHuesped() == null || reserva.getHuesped().getId() == null) {
			throw new ArgumentoNoValidoException("Debe seleccionar un huésped válido.");
		}

		try {
			HuespedDTO huespedDTO = huespedService.mostrarHuespedPorId(reserva.getHuesped().getId());
			Huesped huespedEntidad = huespedMapper.toEntity(huespedDTO);
			reserva.setHuesped(huespedEntidad);
		} catch (EntidadNoEncontradaException e) {
			throw new ArgumentoNoValidoException("El huésped seleccionado no existe.");
		}

		if (reserva.getHabitacion() == null || reserva.getHabitacion().getId() == null) {
			throw new ArgumentoNoValidoException("Debe seleccionar una habitación válida.");
		}

		Habitacion habReal = habitacionDao.mostrarHabitacionPorId(reserva.getHabitacion().getId());
		reserva.setHabitacion(habReal);

		validarReserva(reserva);
		logger.warn("Service: actualizando reserva con id: " + reserva.getId());
		reservaDao.actualizaReserva(reserva);
	}

	/**
	 * Elimina la reserva con el identificador indicado.
	 *
	 * <p>
	 * Delega directamente la eliminación al DAO. Si no existe una reserva con el
	 * identificador indicado, el DAO lanzará
	 * {@link com.springhotel.exception.EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id identificador de la reserva a eliminar.
	 * @throws EntidadNoEncontradaException si no existe ninguna reserva con ese id.
	 * @throws ErrorBaseDatosException      si ocurre un error al eliminar.
	 */
	@Override
	public void eliminaReserva(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {

		reservaDao.eliminaReserva(id);
	}

	// -------------------------------------------------------------------------
	// Reglas de negocio
	// -------------------------------------------------------------------------

	/**
	 * Valida los datos de una reserva antes de ser persistida o actualizada.
	 *
	 * <p>
	 * Comprueba que:
	 * </p>
	 * <ul>
	 * <li>La fecha de salida es posterior a la fecha de entrada.</li>
	 * <li>El número de personas es mayor que cero.</li>
	 * <li>La habitación seleccionada tiene capacidad suficiente.</li>
	 * </ul>
	 *
	 * @param reserva la reserva a validar.
	 * @throws IllegalArgumentException si alguna regla de negocio no se cumple.
	 */
	private void validarReserva(Reserva reserva) {

		if (reserva.getCheckIn() == null || reserva.getCheckOut() == null) {
			throw new ArgumentoNoValidoException("Las fechas de check-in y check-out son obligatorias.");
		}

		if (reserva.getCheckOut().isBefore(reserva.getCheckIn())) {
			throw new ArgumentoNoValidoException("La fecha de check-out debe ser posterior al check-in.");
		}

		if (reserva.getNumPersonas() <= 0) {
			throw new ArgumentoNoValidoException("El número de personas debe ser mayor que 0.");
		}

		if (reserva.getHabitacion() != null && reserva.getHabitacion().getCapacidad() != null) {
			if (reserva.getNumPersonas() > reserva.getHabitacion().getCapacidad()) {
				throw new ArgumentoNoValidoException(
						"La habitación no tiene capacidad suficiente para " + reserva.getNumPersonas()
								+ " personas (Capacidad máxima: " + reserva.getHabitacion().getCapacidad() + ").");
			}
		}
	}
}
