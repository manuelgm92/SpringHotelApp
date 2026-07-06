package com.springhotel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springhotel.dto.HabitacionDTO;
import com.springhotel.dto.IncidenciaDTO;

import com.springhotel.entity.Incidencia;

import com.springhotel.entity.Incidencia.PrioridadIncidencia;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;
import com.springhotel.service.AutorizacionService;
import com.springhotel.service.IncidenciaService;
import com.springhotel.service.HabitacionService;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA
 * (Claude). Controlador responsable de la gestión completa de incidencias en el
 * sistema hotelero.
 *
 * <p>
 * Este controlador maneja todas las operaciones CRUD (Crear, Leer, Actualizar,
 * Eliminar) relacionadas con las incidencias reportadas en las habitaciones del
 * hotel. Las incidencias son problemas, averías o situaciones que requieren
 * atención del personal, como reparaciones, limpieza especial, o mantenimiento.
 * </p>
 *
 * <p>
 * <strong>Funcionalidades principales:</strong>
 * </p>
 * <ul>
 * <li>Listado completo de todas las incidencias registradas</li>
 * <li>Filtrado de incidencias por prioridad (BAJA, MEDIA, ALTA)</li>
 * <li>Filtrado de incidencias por habitación específica</li>
 * <li>Visualización de detalles de una incidencia</li>
 * <li>Registro de nuevas incidencias (solo recepcionistas)</li>
 * <li>Modificación de incidencias existentes (solo recepcionistas)</li>
 * <li>Eliminación de incidencias (solo recepcionistas)</li>
 * <li>Vista especializada de incidencias agrupadas por habitación</li>
 * </ul>
 *
 * <p>
 * <strong>Control de acceso:</strong>
 * </p>
 * <ul>
 * <li>Las operaciones de lectura (listado, detalle, filtrado) requieren
 * autenticación básica</li>
 * <li>Las operaciones de escritura (crear, modificar, eliminar) requieren el
 * rol RECEPCIONISTA</li>
 * </ul>
 *
 * <p>
 * <strong>Rutas gestionadas bajo el prefijo {@code /incidencias}:</strong>
 * </p>
 * <ul>
 * <li>GET /incidencias → mostrar listado completo</li>
 * <li>GET /incidencias?prioridad=ALTA → filtrar por prioridad</li>
 * <li>GET /incidencias?habitacion=101 → filtrar por habitación</li>
 * <li>GET /incidencias/{id}/detalle → ver detalles de una incidencia</li>
 * <li>GET /incidencias/nueva → formulario de alta</li>
 * <li>POST /incidencias/nueva → procesar alta</li>
 * <li>GET /incidencias/{id}/editar → formulario de edición</li>
 * <li>POST /incidencias/{id}/editar → procesar edición</li>
 * <li>POST /incidencias/{id}/eliminar → eliminar incidencia</li>
 * <li>GET /incidencias/habitacion/{idHabitacion} → incidencias de una
 * habitación</li>
 * </ul>
 *
 * @author Ana Laura
 * @version 1.1
 * @since 1.0
 * @see IncidenciaService
 * @see HabitacionService
 * @see AutorizacionService
 * @see Incidencia
 */
@Controller
@RequestMapping("/incidencias")
public class IncidenciaController {
	/**
	 * Logger del sistema para la clase {@link IncidenciaController}.
	 * Se utiliza para registrar eventos, flujos de datos y excepciones 
	 * en los diferentes niveles de depuración (DEBUG, INFO, WARN, ERROR).
	 */
	private static final Logger logger = Logger.getLogger(IncidenciaController.class);

	/**
	 * Servicio de incidencias inyectado por Spring para acceder a la capa de
	 * negocio.
	 */
	private final IncidenciaService incidenciaService;

	/**
	 * Servicio de autorización para comprobar sesión y roles.
	 */
	private final AutorizacionService autorizacionService;

	/**
	 * Servicio de habitaciones inyectado por Spring para operaciones de negocio.
	 */
	private final HabitacionService habitacionService;

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param incidenciaService   servicio de gestión de incidencias
	 * @param autorizacionService servicio de autenticación y autorización
	 * @param habitacionService   servicio de gestión de habitaciones
	 */
	@Autowired
	public IncidenciaController(IncidenciaService incidenciaService, AutorizacionService autorizacionService,
			HabitacionService habitacionService) {
		this.incidenciaService = incidenciaService;
		this.autorizacionService = autorizacionService;
		this.habitacionService = habitacionService;
	}

	/**
	 * Muestra el listado de incidencias registradas en el sistema, con opciones de
	 * filtrado por prioridad.
	 *
	 * <p>
	 * Antes de cargar los datos, se verifica que el usuario esté autenticado. En
	 * caso de que el usuario no tenga sesión válida, el servicio de autorización se
	 * encargará de redirigir o lanzar la excepción correspondiente.
	 * </p>
	 *
	 * <p>
	 * El método soporta los siguientes parámetros de consulta opcionales:
	 * <ul>
	 * <li>{@code prioridad} - Filtra incidencias por prioridad: BAJA, MEDIA o
	 * ALTA</li>
	 * </ul>
	 * </p>
	 *
	 * <p>
	 * Si la carga de incidencias falla por un error en la base de datos, se
	 * registra la excepción mediante el logger y se añade un mensaje de error
	 * específico al modelo para ser mostrado en la vista.
	 * </p>
	 *
	 * @param session    sesión HTTP del usuario actual, utilizada para validar
	 *                   autenticación y roles.
	 * @param prioridad  prioridad opcional para filtrar incidencias (BAJA, MEDIA,
	 *                   ALTA).
	 * @param habitacion identificador numérico de la habitación para filtrar
	 *                   incidencias.
	 * @param modelo     modelo utilizado para enviar datos a la vista.
	 * @return el nombre de la vista "Incidencias" encargada de mostrar el listado
	 *         de incidencias con los filtros aplicados.
	 * @throws ErrorBaseDatosException si se produce un error al acceder a la base
	 *                                 de datos (se captura y maneja internamente)
	 */
	@GetMapping
	public String mostrarListadoIncidencias(HttpSession session, @RequestParam(required = false) String prioridad,
			@RequestParam(required = false) Integer habitacion, Model modelo) {
		autorizacionService.requiereAutenticacion(session);
		logger.info("Accediendo a GET /incidencias");
		logger.info("Verificado autenticación...");

		try {
			logger.info("Solicitando listado de habitaciones...");
			List<IncidenciaDTO> incidencias;

			// Filtrar por habitación si viene el parámetro
			if (habitacion != null) {
				incidencias = incidenciaService.mostrarIncidenciasPorHabitacion(habitacion);
				HabitacionDTO hab = habitacionService.mostrarHabitacionPorId(habitacion);
				modelo.addAttribute("filtroAplicado", "Habitación: " + hab.getNumeroHabitacion());
				modelo.addAttribute("habitacionSeleccionada", habitacion);
			}
			// Filtrar por prioridad
			else if (prioridad != null && !prioridad.isEmpty()) {
				PrioridadIncidencia prioridadEnum = PrioridadIncidencia.valueOf(prioridad.toUpperCase());
				incidencias = incidenciaService.mostrarIncidenciasPorPrioridad(prioridadEnum);
				modelo.addAttribute("filtroAplicado", "Prioridad: " + prioridad);
			}
			// Sin filtros
			else {
				incidencias = incidenciaService.mostrarTodasIncidencias();
			}
			List<HabitacionDTO> habitaciones = habitacionService.mostrarTodasHabitaciones();
			Map<Integer, String> habitacionPorId = new HashMap<>();
			for (HabitacionDTO hab : habitaciones) {
				habitacionPorId.put(hab.getId(), hab.getNumeroHabitacion());
			}

			modelo.addAttribute("incidencias", incidencias);
			modelo.addAttribute("habitacionPorId", habitacionPorId);
			modelo.addAttribute("esRecepcionista", autorizacionService.esRecepcionista(session));

		} catch (IllegalArgumentException e) {
			logger.error("Parámetro de filtro inválido: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Filtro inválido. Por favor, selecciona un filtro válido.");

		} catch (ErrorBaseDatosException e) {
			logger.error("Error al obtener incidencias: " + e.getMessage());
			modelo.addAttribute("errorMensaje",
					"No se pudieron cargar las incidencias. Por favor, intenta de nuevo más tarde.");

		} catch (Exception e) {
			logger.error("Error inesperado al cargar incidencias: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Ocurrió un error inesperado. Por favor, contacta al administrador.");
		}

		return "Incidencias";
	}

	/**
	 * Muestra la vista de detalle de una incidencia identificada por su {@code id}.
	 *
	 * <p>
	 * El método:
	 * </p>
	 * <ul>
	 * <li>Valida que el identificador recibido sea positivo.</li>
	 * <li>Obtiene la incidencia mediante
	 * {@link IncidenciaService#mostrarIncidenciaPorId(Integer)}.</li>
	 * <li>Añade al modelo la incidencia y un indicador del rol del usuario.</li>
	 * </ul>
	 *
	 * <p>
	 * En caso de error:
	 * </p>
	 * <ul>
	 * <li>Si el ID es inválido, se redirige al listado con un mensaje
	 * descriptivo.</li>
	 * <li>Si la incidencia no existe, se informa mediante un mensaje flash.</li>
	 * <li>Si ocurre un error de base de datos o un error inesperado, se notifica y
	 * se redirige.</li>
	 * </ul>
	 *
	 * @param id                 identificador de la incidencia a consultar.
	 * @param session            sesión HTTP utilizada para validar autenticación y
	 *                           rol.
	 * @param modelo             modelo MVC donde se cargan los datos para la vista.
	 * @param redirectAttributes atributos flash para mensajes tras redirección.
	 * @return la vista {@code "DetalleIncidencia"} si la carga es exitosa;
	 *         {@code "redirect:/incidencias"} en caso de error.
	 */
	@GetMapping("/{id}/detalle")
	public String mostrarDetalleIncidencia(@PathVariable("id") Integer id, HttpSession session, Model modelo,
			RedirectAttributes redirectAttributes) {

		autorizacionService.requiereAutenticacion(session);
		logger.info("Accediendo a GET /incidencias/{id}/detalle");
		logger.info("Verificado autenticación...");

		try {
			// Validar que el ID sea válido
			if (id == null || id <= 0) {
				logger.error("ID de incidencia inválido: " + id);
				redirectAttributes.addFlashAttribute("errorMensaje", "El identificador de incidencia no es válido.");
				return "redirect:/incidencias";
			}

			IncidenciaDTO incidencia = incidenciaService.mostrarIncidenciaPorId(id);
			modelo.addAttribute("incidencia", incidencia);
			HabitacionDTO habitacion = habitacionService.mostrarHabitacionPorId(incidencia.getIdHabitacion());
			modelo.addAttribute("habitacion", habitacion);
			modelo.addAttribute("esRecepcionista", autorizacionService.esRecepcionista(session));

			logger.info("Incidencia cargada correctamente - ID: " + id);

		} catch (EntidadNoEncontradaException e) {

			logger.error("Incidencia no encontrada - ID: " + id);
			redirectAttributes.addFlashAttribute("errorMensaje", "La incidencia solicitada no existe en el sistema.");
			return "redirect:/incidencias";

		} catch (ErrorBaseDatosException e) {
			logger.error("Error de base de datos al obtener incidencia ID: " + id + " - Causa: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje",
					"Ocurrió un error al obtener la incidencia. Por favor, intenta de nuevo.");
			return "redirect:/incidencias";

		} catch (Exception e) {
			logger.error("Error inesperado al cargar detalle de incidencia ID: " + id + " - Causa: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje",
					"Ocurrió un error inesperado. Por favor, contacta al administrador.");
			return "redirect:/incidencias";
		}

		return "DetalleIncidencia";
	}

	/**
	 * Muestra el formulario para registrar una nueva incidencia.
	 *
	 * <p>
	 * Antes de cargar la vista, se valida que el usuario esté autenticado como
	 * recepcionista. El método obtiene la lista de habitaciones disponibles
	 * mediante el servicio correspondiente y la añade al modelo para permitir la
	 * selección en el formulario.
	 * </p>
	 *
	 * <p>
	 * Si ocurre un error al acceder a la base de datos, se añade un mensaje de
	 * error al modelo para ser mostrado en la vista.
	 * </p>
	 *
	 * @param session sesión HTTP del usuario actual, utilizada para validar
	 *                autenticación.
	 * @param modelo  modelo utilizado para enviar datos a la vista.
	 * @return la vista {@code "AltaIncidencia"} encargada de mostrar el formulario.
	 */
	@GetMapping("/nueva")
	public String mostrarFormularioNuevaIncidencia(HttpSession session, Model modelo) {

		autorizacionService.requiereRecepcionista(session);

		logger.info("Accediendo al formulario de nueva incidencia...");

		try {
			List<HabitacionDTO> habitacionesDAO = habitacionService.mostrarTodasHabitaciones();
			modelo.addAttribute("habitaciones", habitacionesDAO);

			IncidenciaDTO incidencia = new IncidenciaDTO();

			modelo.addAttribute("incidencia", incidencia);

			logger.info("Habitaciones cargadas correctamente: " + habitacionesDAO.size());

		} catch (ErrorBaseDatosException e) {
			logger.error("Error al cargar habitaciones: " + e.getMessage());
			modelo.addAttribute("errorMensaje",
					"No se pudieron cargar las habitaciones. Intenta nuevamente más tarde.");
		}

		return "FormularioIncidencia";
	}

	/**
	 * Procesa el formulario de creación de una nueva incidencia.
	 *
	 * <p>
	 * Valida los datos recibidos y delega el registro de la incidencia al servicio
	 * correspondiente. Si la operación es exitosa, se añade un mensaje flash de
	 * éxito y se redirige al listado de incidencias.
	 * </p>
	 *
	 * <p>
	 * Si ocurre un error de validación, si la habitación no existe o si se produce
	 * un error de base de datos, se añade un mensaje flash descriptivo y se
	 * redirige nuevamente al formulario de alta.
	 * </p>
	 *
	 * @param incidencia         objeto {@link Incidencia} con los datos enviados
	 *                           desde el formulario.
	 * @param session            sesión HTTP del usuario actual, utilizada para
	 *                           validar autenticación.
	 * @param redirectAttributes atributos flash utilizados para mostrar mensajes
	 *                           tras la redirección.
	 * @return redirección al listado de incidencias o al formulario en caso de
	 *         error.
	 */
	@PostMapping("/nueva")
	public String procesarAltaIncidencia(@Valid @ModelAttribute("incidencia") IncidenciaDTO incidenciaDTO,
			BindingResult resultado, HttpSession session, Model modelo, RedirectAttributes redirectAttributes) {

		autorizacionService.requiereRecepcionista(session);

		logger.info("Procesando alta de nueva incidencia...");

		// Si hay errores de validación, volver al formulario.
		if (resultado.hasErrors()) {
			logger.error("Errores de validación detectados:");
			resultado.getAllErrors().forEach(error -> logger.error("  - " + error.getDefaultMessage()));

			try {
				List<HabitacionDTO> habitaciones = habitacionService.mostrarTodasHabitaciones();
				modelo.addAttribute("habitaciones", habitaciones);
				modelo.addAttribute("incidencia", incidenciaDTO);
				modelo.addAttribute("errorMensaje", "Faltan datos obligatorios o hay errores en el formulario.");
			} catch (ErrorBaseDatosException e) {
				logger.error("Error al cargar habitaciones: " + e.getMessage());
				modelo.addAttribute("errorMensaje", "Error al cargar el formulario. Intenta nuevamente.");
			}

			return "FormularioIncidencia";
		}

		try {

			// Guardar incidencia
			incidenciaService.altaIncidencia(incidenciaDTO);

			logger.info("Incidencia registrada correctamente.");

			redirectAttributes.addFlashAttribute("okMensaje", "La incidencia fue registrada correctamente.");

		} catch (EntidadNoEncontradaException e) {
			logger.error("Habitación no encontrada: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "La habitación seleccionada no existe.");
			return "redirect:/incidencias/nueva";

		} catch (ErrorBaseDatosException e) {
			logger.error("Error de base de datos al guardar incidencia: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al guardar la incidencia. Intenta nuevamente.");
			return "redirect:/incidencias/nueva";

		} catch (Exception e) {
			logger.error("Error inesperado al registrar incidencia: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje",
					"Ocurrió un error inesperado. Contacta al administrador.");
			return "redirect:/incidencias/nueva";
		}

		return "redirect:/incidencias";
	}

	/**
	 * Muestra el formulario de edición para la incidencia indicada por su
	 * identificador.
	 *
	 * <p>
	 * El método:
	 * </p>
	 * <ul>
	 * <li>Verifica que el usuario tenga rol de recepcionista.</li>
	 * <li>Carga la incidencia existente mediante {@link IncidenciaService}.</li>
	 * <li>Obtiene todas las habitaciones disponibles para mostrarlas en un
	 * desplegable dentro del formulario.</li>
	 * <li>Añade al modelo los datos necesarios para la vista
	 * {@code FormularioIncidencia}.</li>
	 * </ul>
	 *
	 * <p>
	 * Si la incidencia no existe o ocurre un error de acceso a datos, se añade un
	 * mensaje de error y se redirige al listado de incidencias.
	 * </p>
	 *
	 * @param id      identificador de la incidencia a editar.
	 * @param session sesión HTTP del usuario actual.
	 * @param modelo  modelo MVC donde se cargan los datos para la vista.
	 * @return el nombre de la vista {@code FormularioIncidencia} o una redirección
	 *         a {@code /incidencias} en caso de error.
	 */
	@GetMapping("/{id}/editar")
	public String mostrarFormularioModificacionIncidencia(@PathVariable("id") Integer id, HttpSession session,
			Model modelo) {

		autorizacionService.requiereRecepcionista(session);
		logger.info("Accediendo al formulario de edición - Incidencia ID: " + id);

		try {
			// Cargar la incidencia existente
			IncidenciaDTO incidencia = incidenciaService.mostrarIncidenciaPorId(id);

			// Cargar todas las habitaciones para el dropdown
			List<HabitacionDTO> habitaciones = habitacionService.mostrarTodasHabitaciones();

			// Añadir al modelo
			modelo.addAttribute("incidencia", incidencia);
			modelo.addAttribute("habitaciones", habitaciones);

			logger.info("Datos cargados correctamente");
			logger.debug("   - Incidencia ID: " + incidencia.getId());
			logger.debug("   - Total habitaciones disponibles: " + habitaciones.size());

		} catch (EntidadNoEncontradaException e) {
			logger.error("Incidencia no encontrada: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "La incidencia solicitada no existe.");
			return "redirect:/incidencias";

		} catch (ErrorBaseDatosException e) {
			logger.error("Error al cargar datos: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Error al cargar los datos. Intenta nuevamente más tarde.");
		}

		return "FormularioIncidencia";
	}

	/**
	 * Procesa la actualización de una incidencia previamente existente.
	 *
	 * <p>
	 * El método:
	 * </p>
	 * <ul>
	 * <li>Verifica que el usuario tenga rol de recepcionista.</li>
	 * <li>Asigna el ID recibido por URL al objeto {@link Incidencia} para evitar
	 * inconsistencias.</li>
	 * <li>Actualiza la incidencia mediante el servicio correspondiente.</li>
	 * <li>Redirige al listado mostrando un mensaje de éxito.</li>
	 * </ul>
	 *
	 * <p>
	 * Si la incidencia no existe o ocurre un error de base de datos, se añade un
	 * mensaje de error mediante {@link RedirectAttributes} y se redirige al
	 * listado.
	 * </p>
	 *
	 * @param id                 identificador de la incidencia a actualizar.
	 * @param incidencia         objeto con los datos modificados enviados desde el
	 *                           formulario.
	 * @param session            sesión HTTP del usuario actual.
	 * @param redirectAttributes atributos flash para mensajes de éxito o error.
	 * @return redirección a {@code /incidencias}.
	 */
	@PostMapping("/{id}/editar")
	public String procesarModificacionIncidencia(@PathVariable("id") Integer id,
			@Valid @ModelAttribute("incidencia") IncidenciaDTO incidenciaDTO, BindingResult resultado,
			HttpSession session, Model modelo, RedirectAttributes redirectAttributes) {

		autorizacionService.requiereRecepcionista(session);
		logger.info("Procesando actualización - Incidencia ID: " + id);

		// Si hay errores de validación, volver al formulario
		if (resultado.hasErrors()) {
			logger.error("Errores de validación detectados:");
			resultado.getAllErrors().forEach(error -> logger.debug("  - " + error.getDefaultMessage()));

			incidenciaDTO.setId(id);

			try {
				List<HabitacionDTO> habitaciones = habitacionService.mostrarTodasHabitaciones();
				modelo.addAttribute("habitaciones", habitaciones);
				modelo.addAttribute("incidencia", incidenciaDTO);
				modelo.addAttribute("errorMensaje", "Faltan datos obligatorios o hay errores en el formulario.");
			} catch (ErrorBaseDatosException e) {
				logger.error("Error al cargar habitaciones: " + e.getMessage());
				modelo.addAttribute("errorMensaje", "Error al cargar el formulario. Intenta nuevamente.");
			}

			return "FormularioIncidencia";
		}

		try {
			// Asegurar que el ID no se pierda
			incidenciaDTO.setId(id);

			// Actualizar la incidencia
			incidenciaService.actualizaIncidencia(incidenciaDTO);

			logger.debug("Incidencia actualizada correctamente");
			redirectAttributes.addFlashAttribute("okMensaje", "Incidencia actualizada correctamente.");

		} catch (EntidadNoEncontradaException e) {
			logger.error("Incidencia no encontrada: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Incidencia no encontrada.");

		} catch (ErrorBaseDatosException e) {
			logger.error("Error al actualizar: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje",
					"Error al actualizar la incidencia. Intenta nuevamente.");
		}

		return "redirect:/incidencias";
	}

	/**
	 * Elimina una incidencia del sistema identificada por su ID.
	 * 
	 * <p>
	 * Este método realiza las siguientes operaciones:
	 * </p>
	 * <ul>
	 * <li>Verifica que el usuario tenga permisos de recepcionista</li>
	 * <li>Intenta eliminar la incidencia con el ID proporcionado</li>
	 * <li>Añade mensajes de éxito o error según el resultado</li>
	 * <li>Redirige a la lista de incidencias</li>
	 * </ul>
	 * 
	 * @param id                 el identificador único de la incidencia a eliminar
	 * @param session            la sesión HTTP actual para verificar autorizaciones
	 * @param redirectAttributes atributos para añadir mensajes flash de éxito o
	 *                           error
	 * @return una redirección a la vista de listado de incidencias ("/incidencias")
	 * @throws EntidadNoEncontradaException si la incidencia no existe (capturada
	 *                                      internamente)
	 * @throws ErrorBaseDatosException      si ocurre un error en la base de datos
	 *                                      (capturada internamente)
	 */
	@PostMapping("/{id}/eliminar")
	public String eliminarIncidencia(@PathVariable("id") Integer id, HttpSession session,
			RedirectAttributes redirectAttributes) {

		autorizacionService.requiereRecepcionista(session);
		logger.info("Solicitando eliminación de incidencia ID: " + id);

		try {
			incidenciaService.eliminaIncidencia(id);

			redirectAttributes.addFlashAttribute("okMensaje", "Incidencia eliminada correctamente.");

			logger.debug("Incidencia eliminada correctamente");

		} catch (EntidadNoEncontradaException e) {

			logger.error("Incidencia no encontrada: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "La incidencia no existe o ya fue eliminada.");

		} catch (ErrorBaseDatosException e) {

			logger.error("Error al eliminar incidencia: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje",
					"Error al eliminar la incidencia. Intenta nuevamente.");

		}

		return "redirect:/incidencias";
	}

	/**
	 * Muestra todas las incidencias asociadas a una habitación concreta.
	 *
	 * <p>
	 * Este método:
	 * </p>
	 * <ul>
	 * <li>Verifica que el usuario esté autenticado.</li>
	 * <li>Obtiene la habitación mediante su ID.</li>
	 * <li>Recupera todas las incidencias vinculadas a dicha habitación.</li>
	 * <li>Envía los datos a la vista {@code IncidenciasPorHabitacion.jsp}.</li>
	 * </ul>
	 *
	 * <p>
	 * Si la habitación no existe o ocurre un error al consultar las incidencias, se
	 * redirige al listado de habitaciones mostrando un mensaje de error.
	 * </p>
	 *
	 * @param idHabitacion identificador único de la habitación.
	 * @param modelo       objeto {@link Model} para enviar datos a la vista.
	 * @param session      sesión HTTP del usuario autenticado.
	 * @return nombre de la vista JSP a renderizar.
	 */
	@GetMapping("/habitacion/{idHabitacion}")
	public String mostrarIncidenciasPorHabitacion(@PathVariable Integer idHabitacion, Model modelo,
			HttpSession session) {

		autorizacionService.requiereAutenticacion(session);
		logger.info("Accediendo a incidencias de la habitación ID: " + idHabitacion);

		try {
			HabitacionDTO habitacion = habitacionService.mostrarHabitacionPorId(idHabitacion);
			modelo.addAttribute("habitacion", habitacion);
			logger.debug("Habitación encontrada: " + habitacion.getNumeroHabitacion());
			List<IncidenciaDTO> incidencias = incidenciaService.mostrarIncidenciasPorHabitacion(idHabitacion);
			modelo.addAttribute("incidencias", incidencias);
			logger.debug("Incidencias encontradas: " + incidencias.size());
		} catch (Exception e) {
			modelo.addAttribute("errorMensaje", "No se pudieron cargar las incidencias.");
			logger.error("Error al cargar incidencias de la habitación: " + e.getMessage());
			return "redirect:/habitaciones";
		}

		return "IncidenciasPorHabitacion";
	}
}
