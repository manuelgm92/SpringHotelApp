package com.springhotel.controller;

import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springhotel.service.HabitacionService;
import com.springhotel.dto.HabitacionDTO;
import com.springhotel.entity.Habitacion;
import com.springhotel.entity.Habitacion.EstadoHabitacion;
import com.springhotel.entity.Habitacion.TipoHabitacion;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;
import com.springhotel.service.AutorizacionService;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA
 * (Claude). Controlador responsable de la gestión completa de habitaciones en
 * el sistema hotelero.
 *
 * <p>
 * Este controlador maneja todas las operaciones CRUD (Crear, Leer, Actualizar,
 * Eliminar) relacionadas con las habitaciones del hotel. Proporciona las
 * siguientes funcionalidades:
 * </p>
 * <ul>
 * <li>Listado completo de todas las habitaciones</li>
 * <li>Visualización de detalles de una habitación específica</li>
 * <li>Creación de nuevas habitaciones (solo recepcionistas)</li>
 * <li>Modificación de habitaciones existentes (solo recepcionistas)</li>
 * <li>Eliminación de habitaciones (solo recepcionistas)</li>
 * </ul>
 *
 * <p>
 * <strong>Control de acceso:</strong>
 * </p>
 * <ul>
 * <li>Las operaciones de lectura (listado y detalle) requieren autenticación
 * básica</li>
 * <li>Las operaciones de escritura (crear, modificar, eliminar) requieren el
 * rol RECEPCIONISTA</li>
 * </ul>
 *
 * <p>
 * Todas las vistas calculan dinámicamente el flag {@code esRecepcionista} para
 * mostrar u ocultar acciones administrativas en la interfaz de usuario según el
 * perfil del usuario.
 * </p>
 *
 * <p>
 * Rutas gestionadas bajo el prefijo {@code /habitaciones}.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 * @since 1.0
 * @see HabitacionService
 * @see AutorizacionService
 * @see Habitacion
 */
@Controller
@RequestMapping("/habitaciones")
public class HabitacionesController {
	/**
	 * Logger del sistema para la clase {@link HabitacionesController}.
	 * Se utiliza para registrar eventos, flujos de datos y excepciones 
	 * en los diferentes niveles de depuración (DEBUG, INFO, WARN, ERROR).
	 */
	 private static final Logger logger = Logger.getLogger(HabitacionesController.class);
	/**
	 * Servicio de habitaciones inyectado por Spring para acceder a la capa de
	 * negocio.
	 */
	private final HabitacionService habitacionService;

	/**
	 * Servicio de autorización para comprobar sesión y roles.
	 */
	private final AutorizacionService autorizacionService;

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param habitacionService   servicio de gestión de habitaciones
	 * @param autorizacionService servicio de autenticación y autorización
	 */
	@Autowired
	public HabitacionesController(HabitacionService habitacionService, AutorizacionService autorizacionService) {
		this.habitacionService = habitacionService;
		this.autorizacionService = autorizacionService;
	}

	/**
	 * Muestra el listado de todas las habitaciones.
	 *
	 * <p>
	 * Comprueba que el usuario esté autenticado mediante
	 * {@link AutorizacionService#requiereAutenticacion(HttpSession)}. Si la carga
	 * de datos falla por un error de base de datos, se añade un mensaje de error al
	 * modelo para que la vista lo muestre.
	 * </p>
	 *
	 * @param session sesión HTTP del usuario; se utiliza para comprobar el perfil y
	 *                la autenticación
	 * @param modelo  modelo MVC donde se añaden los datos para la vista
	 * @return nombre lógico de la vista {@code "Habitaciones"} que renderiza el
	 *         listado
	 */
	@GetMapping
	public String mostrarListadoHabitaciones(HttpSession session, Model modelo) {
		autorizacionService.requiereAutenticacion(session);
		logger.info("Accediendo a GET /habitaciones");
		logger.info("Verificado autenticación...");
		try {
			logger.info("Solicitando listado de habitaciones...");
			List<HabitacionDTO> habitaciones = habitacionService.mostrarTodasHabitaciones();
			logger.debug("Habitaciones obtenidas: " + habitaciones.size());
			modelo.addAttribute("habitaciones", habitaciones);
			modelo.addAttribute("esRecepcionista", autorizacionService.esRecepcionista(session));
		} catch (ErrorBaseDatosException e) {
			logger.error("ERROR al cargar habitaciones: " + e.getMessage());

			modelo.addAttribute("errorMensaje", "No se pudieron cargar las habitaciones.");

		} catch (Exception e) {
			logger.error("ERROR inesperado: " + e.getMessage());

			modelo.addAttribute("errorMensaje", "Ocurrió un error inesperado.");
		}
		return "Habitaciones";
	}

	/**
	 * Muestra la vista de detalle para la habitación indicada por {@code id}.
	 *
	 * <p>
	 * Intenta cargar la habitación mediante
	 * {@code habitacionDAO.mostrarHabitacionPorId(id)}. Si la entidad no existe se
	 * añade un mensaje flash {@code "Habitacion no encontrada."} y se redirige al
	 * listado. Si ocurre un error de base de datos se añade un mensaje flash
	 * {@code "Error al obtener la habitacion."} y también se redirige. En caso de
	 * éxito se añade al modelo:
	 * <ul>
	 * <li>{@code "habitacion"} → la entidad {@link Habitacion}</li>
	 * <li>{@code "esRecepcionista"} → boolean indicando si el usuario actual es
	 * recepcionista</li>
	 * </ul>
	 * </p>
	 *
	 * @param id                 identificador de la habitación cuyo detalle se
	 *                           solicita
	 * @param session            sesión HTTP del usuario; usada para determinar el
	 *                           rol
	 * @param modelo             modelo MVC donde se añaden los atributos para la
	 *                           vista
	 * @param redirectAttributes contenedor de atributos flash para redirecciones
	 * @return el nombre lógico de la vista {@code "DetalleHabitacion"} cuando la
	 *         habitación se carga correctamente; {@code "redirect:/habitaciones"}
	 *         en caso de error (habitacion no encontrada o error de BD)
	 */
	@GetMapping("/{id}/detalle")
	public String mostrarDetalleHabitacion(@PathVariable("id") Integer id, HttpSession session, Model modelo,
			RedirectAttributes redirectAttributes) {
		logger.info("Accediendo al detalle de la habitación con ID: " + id);
		logger.info("Comprobado permisos de usuario...");

		try {
			HabitacionDTO h = habitacionService.mostrarHabitacionPorId(id);
			logger.info("Habitación encontrada:");
			logger.debug("  - Número: " + h.getNumeroHabitacion());
			logger.debug("  - Tipo: " + h.getTipoHabitacion());
			logger.debug("  - Estado: " + h.getEstadoHabitacion());
			logger.debug("  - Capacidad: " + h.getCapacidad());
			logger.debug("  - Precio/noche: " + h.getPrecioNoche());
			modelo.addAttribute("habitacion", h);
			modelo.addAttribute("esRecepcionista", autorizacionService.esRecepcionista(session));

		} catch (EntidadNoEncontradaException e) {
			logger.error("ERROR: Habitación no encontrada. ID: " + id);
			redirectAttributes.addFlashAttribute("errorMensaje", "Habitacion no encontrada.");
			return "redirect:/habitaciones";
		} catch (ErrorBaseDatosException e) {
			logger.error("ERROR de base de datos al obtener habitación: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al obtener la habitacion.");
			return "redirect:/habitaciones";
		} catch (Exception e) {
			logger.error("[ERROR inesperado: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Ocurrió un error inesperado.");
			return "redirect:/habitaciones";
		}

		logger.info("Mostrando vista DetalleHabitacion.jsp");
		return "DetalleHabitacion";
	}

	/**
	 * Muestra el formulario vacío para crear una nueva habitación.
	 *
	 * <p>
	 * Comprueba que el usuario tenga el perfil {@code RECEPCIONISTA} mediante
	 * {@link AutorizacionService#requiereRecepcionista(HttpSession)}. Añade al
	 * modelo un objeto {@link Habitacion} vacío y los enumerados necesarios para
	 * los selects del formulario.
	 * </p>
	 *
	 * @param session sesión HTTP del usuario; se utiliza para comprobar el perfil
	 * @param modelo  modelo MVC donde se añaden los atributos para la vista
	 * @return nombre lógico de la vista {@code "FormularioHabitacion"}
	 * @throws com.springhotel.exception.AccesoNoPermitidoException si el usuario no
	 *                                                              tiene permisos
	 *                                                              para acceder a
	 *                                                              este formulario
	 */
	@GetMapping("/nueva")
	public String mostrarFormularioAlta(HttpSession session, Model modelo) {
		autorizacionService.requiereRecepcionista(session);
		logger.info("Accediendo al formulario de nueva habitacion...");
		logger.info("Comprobado permisos de recepcionista...");
		modelo.addAttribute("habitacion", new HabitacionDTO());
		modelo.addAttribute("tipos", TipoHabitacion.values());
		modelo.addAttribute("estados", EstadoHabitacion.values());
		modelo.addAttribute("modoAlta", true);
		logger.info("Formulario preparado correctamente.");
		return "FormularioHabitacion";
	}

	/**
	 * Procesa el envío del formulario de alta de una nueva habitación.
	 *
	 * <p>
	 * Valida el objeto {@link Habitacion} recibido. Si hay errores de validación
	 * vuelve a mostrar el formulario con los enumerados y un mensaje de error. Si
	 * la persistencia falla por un problema de base de datos, añade un mensaje de
	 * error al modelo y vuelve a la vista del formulario. En caso de éxito añade un
	 * mensaje flash de confirmación y redirige al listado de habitaciones.
	 * </p>
	 *
	 * @param habitacion         objeto {@link Habitacion} con los datos del
	 *                           formulario
	 * @param resultado          resultado de la validación (binding/constraints)
	 * @param session            sesión HTTP del usuario; se utiliza para comprobar
	 *                           permisos
	 * @param modelo             modelo MVC para volver al formulario en caso de
	 *                           error
	 * @param redirectAttributes contenedor de atributos flash para la redirección
	 * @return redirección a {@code "/habitaciones"} en caso de éxito, o la vista
	 *         {@code "FormularioHabitacion"} si hay errores
	 * @throws com.springhotel.exception.AccesoNoPermitidoException si el usuario no
	 *                                                              tiene permisos
	 *                                                              para realizar la
	 *                                                              operación
	 */
	@PostMapping("/nueva")
	public String procesarAlta(@Valid @ModelAttribute("habitacion") HabitacionDTO habitacionDTO,
			BindingResult resultado, HttpSession session, Model modelo, RedirectAttributes redirectAttributes) {
		autorizacionService.requiereRecepcionista(session);
		logger.info("Procesando alta de nueva habitación...");
		logger.debug("  - Número: " + habitacionDTO.getNumeroHabitacion());
		logger.debug("  - Tipo: " + habitacionDTO.getTipoHabitacion());
		logger.debug("  - Precio: " + habitacionDTO.getPrecioNoche());
		logger.debug("  - Capacidad: " + habitacionDTO.getCapacidad());
		logger.debug("  - Estado: " + habitacionDTO.getEstadoHabitacion());
		logger.debug("  - Adaptada: " + habitacionDTO.getAdaptadaDiscapacidad());

		// Si hay errores de validación, volver al formulario con los enumerados
		if (resultado.hasErrors()) {
			logger.error("Errores de validación detectados.");
			modelo.addAttribute("habitacion", habitacionDTO);
			modelo.addAttribute("tipos", TipoHabitacion.values());
			modelo.addAttribute("estados", EstadoHabitacion.values());
			modelo.addAttribute("modoAlta", true);
			modelo.addAttribute("errorMensaje", "Faltan datos obligatorios o hay errores en el formulario.");
			return "FormularioHabitacion";
		}

		try {
			habitacionService.altaHabitacion(habitacionDTO);
			logger.info("Habitación creada correctamente.");
			redirectAttributes.addFlashAttribute("okMensaje",
					"Habitacion " + habitacionDTO.getNumeroHabitacion() + " creada correctamente.");
			return "redirect:/habitaciones";
		} catch (ErrorBaseDatosException e) {
			logger.error("ERROR al guardar habitación: " + e.getMessage());
			modelo.addAttribute("habitacion", habitacionDTO);
			modelo.addAttribute("tipos", TipoHabitacion.values());
			modelo.addAttribute("estados", EstadoHabitacion.values());
			modelo.addAttribute("modoAlta", true);
			modelo.addAttribute("errorMensaje", "Error al crear la habitacion.");
		} catch (Exception e) {
			logger.error("ERROR inesperado: " + e.getMessage());

			modelo.addAttribute("errorMensaje", "Ocurrió un error inesperado.");
		}
		return "FormularioHabitacion";
	}

	/**
	 * Muestra el formulario precargado con los datos de una habitación para
	 * modificarla.
	 *
	 * <p>
	 * Comprueba que el usuario tenga el perfil {@code RECEPCIONISTA} mediante
	 * {@link AutorizacionService#requiereRecepcionista(HttpSession)}. Si la
	 * habitación no existe o hay un error de base de datos, añade un mensaje flash
	 * y redirige al listado.
	 * </p>
	 *
	 * @param id                 identificador de la habitación a modificar
	 * @param session            sesión HTTP del usuario; se utiliza para comprobar
	 *                           permisos
	 * @param modelo             modelo MVC donde se añaden los atributos para la
	 *                           vista
	 * @param redirectAttributes contenedor de atributos flash para la redirección
	 * @return la vista {@code "FormularioHabitacion"} con los datos de la
	 *         habitación, o {@code "redirect:/habitaciones"} si ocurre un error
	 * @throws com.springhotel.exception.AccesoNoPermitidoException si el usuario no
	 *                                                              tiene permisos
	 */
	@GetMapping("/{id}/editar")
	public String mostrarFormularioModificacion(@PathVariable("id") Integer id, HttpSession session, Model modelo,
			RedirectAttributes redirectAttributes) {
		autorizacionService.requiereRecepcionista(session);
		logger.info("Accediendo a GET /habitaciones/" + id + "/editar");
		logger.info("Comprobado permisos de recepcionista...");
		try {
			logger.info("Cargando habitación con ID: " + id);
			HabitacionDTO h = habitacionService.mostrarHabitacionPorId(id);
			logger.debug("Habitación encontrada:");
			logger.debug("  - Número: " + h.getNumeroHabitacion());
			logger.debug("  - Tipo: " + h.getTipoHabitacion());
			logger.debug("  - Estado: " + h.getEstadoHabitacion());
			logger.debug("  - Capacidad: " + h.getCapacidad());
			logger.debug("  - Precio/noche: " + h.getPrecioNoche());
			modelo.addAttribute("habitacion", h);
			modelo.addAttribute("tipos", TipoHabitacion.values());
			modelo.addAttribute("estados", EstadoHabitacion.values());
			modelo.addAttribute("modoAlta", false);
			logger.info(" Formulario de edición preparado correctamente.");
			return "FormularioHabitacion";

		} catch (EntidadNoEncontradaException e) {
			logger.error("ERROR: Habitación no encontrada. ID: " + id);
			redirectAttributes.addFlashAttribute("errorMensaje", "Habitacion no encontrada.");
		} catch (ErrorBaseDatosException e) {
			logger.error("ERROR de base de datos: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al actualizar la habitacion.");
		}
		return "redirect:/habitaciones";
	}

	/**
	 * Procesa el envío del formulario de modificación de una habitación existente.
	 *
	 * <p>
	 * Valida el objeto {@link Habitacion} recibido. Si hay errores de validación
	 * vuelve a mostrar el formulario con los enumerados y un mensaje de error. Si
	 * la actualización falla por no encontrar la entidad o por un error de base de
	 * datos, añade un mensaje flash y redirige al listado.
	 * </p>
	 *
	 * @param id                 identificador de la habitación a modificar
	 * @param habitacion         objeto {@link Habitacion} con los datos
	 *                           actualizados
	 * @param resultado          resultado de la validación (binding/constraints)
	 * @param session            sesión HTTP del usuario; se utiliza para comprobar
	 *                           permisos
	 * @param modelo             modelo MVC para volver al formulario en caso de
	 *                           error
	 * @param redirectAttributes contenedor de atributos flash para la redirección
	 * @return {@code "redirect:/habitaciones"} tras procesar la modificación, o
	 *         {@code "FormularioHabitacion"} si hay errores de validación
	 * @throws com.springhotel.exception.AccesoNoPermitidoException si el usuario no
	 *                                                              tiene permisos
	 */
	@PostMapping("/{id}/editar")
	public String procesarModificacion(@PathVariable("id") Integer id,
			@Valid @ModelAttribute("habitacion") HabitacionDTO habitacionDTO, BindingResult resultado,
			HttpSession session, Model modelo, RedirectAttributes redirectAttributes) {
		autorizacionService.requiereRecepcionista(session);
		logger.info("POST /habitaciones/" + id + "/editar recibido.");
		logger.info("Comprobado permisos de recepcionista...");
		logger.debug("Datos recibidos para actualización:");
		logger.debug("  - Número: " + habitacionDTO.getNumeroHabitacion());
		logger.debug("  - Tipo: " + habitacionDTO.getTipoHabitacion());
		logger.debug("  - Precio: " + habitacionDTO.getPrecioNoche());
		logger.debug("  - Capacidad: " + habitacionDTO.getCapacidad());
		logger.debug("  - Estado: " + habitacionDTO.getEstadoHabitacion());
		logger.debug("  - Adaptada: " + habitacionDTO.getAdaptadaDiscapacidad());
		// Si hay errores de validación, volver al formulario con los enumerados
		if (resultado.hasErrors()) {
			habitacionDTO.setId(id);
			logger.error("Errores de validación detectados:");
			modelo.addAttribute("habitacion", habitacionDTO);
			modelo.addAttribute("tipos", TipoHabitacion.values());
			modelo.addAttribute("estados", EstadoHabitacion.values());
			modelo.addAttribute("modoAlta", false);
			modelo.addAttribute("errorMensaje", "Faltan datos obligatorios o hay errores en el formulario.");
			return "FormularioHabitacion";
		}

		try {
			// Asegurar que el ID no se pierda
			logger.debug("Guardando cambios en la base de datos...");
			habitacionDTO.setId(id);
			habitacionService.actualizaHabitacion(habitacionDTO);
			logger.debug("Habitación actualizada correctamente.");
			redirectAttributes.addFlashAttribute("okMensaje",
					"Habitacion " + habitacionDTO.getNumeroHabitacion() + " actualizada correctamente.");
		} catch (EntidadNoEncontradaException e) {
			logger.error("ERROR: Habitación no encontrada. ID: " + id);
			redirectAttributes.addFlashAttribute("errorMensaje", "Habitacion no encontrada.");
		} catch (ErrorBaseDatosException e) {
			logger.error("ERROR de base de datos al actualizar: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al actualizar la habitacion.");
		}
		return "redirect:/habitaciones";
	}

	/**
	 * Elimina la habitación con el identificador indicado y redirige al listado.
	 *
	 * <p>
	 * Comprueba que el usuario tenga el perfil {@code RECEPCIONISTA}. Si la
	 * eliminación se realiza correctamente se añade al {@code RedirectAttributes}
	 * un atributo flash {@code "okMensaje"} con la confirmación. Si la habitación
	 * no existe se añade {@code "errorMensaje"} con el texto
	 * {@code "Habitacion no encontrada."}. Si ocurre un error de base de datos se
	 * añade {@code "errorMensaje"} con el texto
	 * {@code "Error al eliminar la habitacion."}.
	 * </p>
	 *
	 * @param id                 identificador de la habitación a eliminar
	 * @param session            sesión HTTP del usuario; se utiliza para comprobar
	 *                           permisos
	 * @param redirectAttributes contenedor de atributos flash para la redirección
	 * @return redirección a {@code "/habitaciones"} tras procesar la petición
	 * @throws com.springhotel.exception.AccesoNoPermitidoException si el usuario no
	 *                                                              tiene el perfil
	 *                                                              {@code RECEPCIONISTA}
	 *                                                              o no está
	 *                                                              autenticado
	 */
	@PostMapping("/{id}/eliminar")
	public String eliminarHabitacion(@PathVariable("id") Integer id, HttpSession session,
			RedirectAttributes redirectAttributes) {
		autorizacionService.requiereRecepcionista(session);
		logger.info("POST /habitaciones/" + id + "/eliminar recibido.");
		logger.info("Comprobado permisos de recepcionista...");
		try {
			logger.info("Eliminando habitación con ID: " + id);
			habitacionService.eliminaHabitacion(id);
			redirectAttributes.addFlashAttribute("okMensaje", "Habitacion eliminada correctamente.");
		} catch (EntidadNoEncontradaException e) {
			logger.error("ERROR: Habitación no encontrada. ID: " + id);
			redirectAttributes.addFlashAttribute("errorMensaje", "Habitacion no encontrada.");
		} catch (ErrorBaseDatosException e) {
			logger.error("ERROR de base de datos al eliminar: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al eliminar la habitacion.");
		} catch (Exception e) {
			logger.error("ERROR inesperado: " + e.getMessage());

			redirectAttributes.addFlashAttribute("errorMensaje", "Ocurrió un error inesperado.");
		}
		return "redirect:/habitaciones";
	}

}
