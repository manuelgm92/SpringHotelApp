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
import com.springhotel.dto.ReservaDTO;
import com.springhotel.exception.AccesoNoPermitidoException;
import com.springhotel.exception.ArgumentoNoValidoException;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;
import com.springhotel.service.AutorizacionService;
import com.springhotel.service.HabitacionService;
import com.springhotel.service.HuespedService;
import com.springhotel.service.ReservaService;

/**
 * Controlador MVC para la gestion de reservas del hotel.
 *
 * <p>
 * Gestiona las peticiones HTTP relacionadas con el ciclo de vida de una
 * reserva: listado, alta, modificacion y eliminacion. Todas las rutas parten de
 * {@code /reservas}.
 * </p>
 *
 * <p>
 * Delega la logica de negocio en {@link ReservaService} y comprueba los
 * permisos de acceso mediante {@link AutorizacionService}.
 * </p>
 *
 * @author Ana Laura y Manuel
 * @version 1.0
 * @see ReservaService
 * @see AutorizacionService
 */
//IA:Documentacion JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude). 
@Controller
@RequestMapping("/reservas")
public class ReservaController {

	/**
	 * Logger del sistema para la clase {@link ReservaController}. Se utiliza para
	 * registrar eventos, flujos de datos y excepciones en los diferentes niveles de
	 * depuración (DEBUG, INFO, WARN, ERROR).
	 */
	private static final Logger logger = Logger.getLogger(ReservaController.class);
	/**
	 * Servicio de reservas inyectado por Spring para acceder a la capa de negocio.
	 */
	private final ReservaService reservaService;

	/**
	 * Servicio de autorización para comprobar sesión y roles.
	 */
	private final AutorizacionService autorizacionService;

	/**
	 * Servicio de huéspedes, necesario para cargar el select del formulario.
	 */
	private final HuespedService huespedService;

	/**
	 * Servicio de habitaciones, necesario para cargar el select del formulario.
	 */
	private final HabitacionService habitacionService;

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param reservaService      servicio de gestión de reservas
	 * @param autorizacionService servicio de autenticación y autorización
	 * @param huespedService      servicio de gestión de huéspedes
	 * @param habitacionService   servicio de gestión de habitaciones
	 */
	@Autowired
	public ReservaController(ReservaService reservaService, AutorizacionService autorizacionService,
			HuespedService huespedService, HabitacionService habitacionService) {

		this.reservaService = reservaService;
		this.autorizacionService = autorizacionService;
		this.huespedService = huespedService;
		this.habitacionService = habitacionService;
	}

	/**
	 * Carga en el modelo los datos necesarios para los selects del formulario
	 * (lista de huespedes y lista de habitaciones).
	 *
	 * <p>
	 * Se extrae como metodo privado para evitar duplicar la misma logica en
	 * {@link #mostrarFormularioAlta}, {@link #procesarNuevo},
	 * {@link #mostrarFormularioModificacion} y {@link #procesarModificacion}.
	 * </p>
	 *
	 * @param modelo modelo al que se anaden los atributos.
	 * @throws ErrorBaseDatosException si ocurre un error al consultar la base de
	 *                                 datos.
	 * @author Manuel
	 */
	private void cargarDatosFormulario(Model modelo) throws ErrorBaseDatosException {
		modelo.addAttribute("huespedes", huespedService.mostrarTodosLosHuespedes());
		modelo.addAttribute("habitaciones", habitacionService.mostrarTodasHabitaciones());
	}

	/**
	 * Muestra el listado completo de reservas registradas en el sistema.
	 *
	 * @param session sesión HTTP del usuario autenticado.
	 * @param modelo  modelo para enviar datos a la vista.
	 * @return nombre de la vista que muestra el listado de reservas.
	 * @author Ana Laura
	 */
	@GetMapping
	public String mostrarListadoReservas(HttpSession session, Model modelo) {

		autorizacionService.requiereAutenticacion(session);

		try {
			logger.info("Accediendo a lista de reservas...");
			List<ReservaDTO> reservas = reservaService.listarReservas();

			logger.debug(" Reservas obtenidas: " + reservas.size());
			modelo.addAttribute("reservas", reservas);
			modelo.addAttribute("esRecepcionista", autorizacionService.esRecepcionista(session));

		} catch (ErrorBaseDatosException e) {
			logger.error("[ERROR al obtener reservas: " + e.getMessage());
			modelo.addAttribute("errorMensaje",
					"No se pudieron cargar las reservas. Por favor, intenta de nuevo más tarde.");

		} catch (Exception e) {
			logger.error("ERROR inesperado al cargar reservas: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Ocurrió un error inesperado. Contacta al administrador.");
		}

		return "Reservas";
	}

	/**
	 * Muestra la vista de detalle de una reserva específica.
	 *
	 * <p>
	 * Este método controla el acceso al detalle de una reserva. Primero valida que
	 * el identificador recibido sea correcto y, posteriormente, delega en el
	 * servicio de reservas la obtención de la entidad {@link Reserva}. Si la
	 * reserva existe, se añade al modelo junto con información adicional sobre el
	 * rol del usuario autenticado, permitiendo que la vista determine qué acciones
	 * están disponibles (por ejemplo, editar o eliminar).
	 * </p>
	 *
	 * <p>
	 * En caso de que el ID sea inválido, la reserva no exista o se produzca un
	 * error en la base de datos, el método redirige al listado de reservas
	 * mostrando un mensaje adecuado mediante {@link RedirectAttributes}.
	 * </p>
	 *
	 * @param id                 identificador único de la reserva cuyo detalle se
	 *                           desea visualizar.
	 * @param session            sesión HTTP utilizada para validar autenticación y
	 *                           roles.
	 * @param modelo             objeto {@link Model} donde se almacenan los datos
	 *                           que serán enviados a la vista.
	 * @param redirectAttributes atributos usados para enviar mensajes en
	 *                           redirecciones.
	 *
	 * @return el nombre de la vista JSP que muestra el detalle de la reserva
	 *         ({@code DetalleReserva}), o una redirección al listado en caso de
	 *         error.
	 *
	 * @throws IllegalStateException si ocurre un error inesperado durante la
	 *                               ejecución.
	 *
	 * @author Ana Laura
	 */
	@GetMapping("/{id}/detalle")
	public String mostrarDetalleReserva(@PathVariable("id") Integer id, HttpSession session, Model modelo,
			RedirectAttributes redirectAttributes) {

		autorizacionService.requiereAutenticacion(session);
		logger.info("Accediendo a mostrar detalle de reserva...");
		try {
			// Validar que el ID sea válido
			if (id == null || id <= 0) {
				logger.error("ID de reserva inválido: " + id);
				redirectAttributes.addFlashAttribute("errorMensaje", "El identificador de reserva no es válido.");
				return "redirect:/reservas";
			}

			ReservaDTO reserva = reservaService.mostrarReservaPorId(id);
			modelo.addAttribute("reserva", reserva);
			modelo.addAttribute("esRecepcionista", autorizacionService.esRecepcionista(session));

			logger.debug("Reserva cargada correctamente - ID: " + id);

		} catch (EntidadNoEncontradaException e) {

			logger.error("Reserva no encontrada - ID: " + id);
			redirectAttributes.addFlashAttribute("errorMensaje", "La reserva solicitada no existe en el sistema.");
			return "redirect:/reservas";

		} catch (ErrorBaseDatosException e) {
			logger.error("Error de base de datos al obtener reserva ID: " + id + " - Causa: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje",
					"Ocurrió un error al obtener la reserva. Por favor, intenta de nuevo.");
			return "redirect:/reservas";

		} catch (Exception e) {
			logger.error("Error inesperado al cargar detalle de reserva ID: " + id + " - Causa: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje",
					"Ocurrió un error inesperado. Por favor, contacta al administrador.");
			return "redirect:/reservas";
		}

		return "DetalleReserva";
	}

	/**
	 * Elimina una reserva del sistema según su identificador.
	 *
	 * <p>
	 * Antes de proceder con la eliminación, se verifica que el usuario tenga
	 * permisos de recepcionista. Si la eliminación es exitosa, se redirige al
	 * listado de reservas con un mensaje de confirmación. En caso de error, se
	 * muestra un mensaje apropiado según el tipo de excepción capturada.
	 * </p>
	 *
	 * @param id                 identificador de la reserva a eliminar.
	 * @param session            sesión HTTP del usuario autenticado.
	 * @param redirectAttributes atributos para enviar mensajes flash tras la
	 *                           redirección.
	 * @return redirección al listado de reservas.
	 * @author Ana Laura
	 */
	@PostMapping("/{id}/eliminar")
	public String eliminarReserva(@PathVariable("id") Integer id, HttpSession session,
			RedirectAttributes redirectAttributes) {

		autorizacionService.requiereRecepcionista(session);

		logger.info("Solicitando eliminación de reserva ID: " + id);

		try {
			reservaService.eliminaReserva(id);
			redirectAttributes.addFlashAttribute("okMensaje", "Reserva eliminada correctamente.");
			logger.debug("Reserva eliminada correctamente");

		} catch (EntidadNoEncontradaException e) {
			logger.error("Reserva no encontrada: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "La reserva no existe o ya fue eliminada.");

		} catch (ErrorBaseDatosException e) {
			logger.error("Error al eliminar reserva: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al eliminar la reserva. Intenta nuevamente.");
		}

		return "redirect:/reservas";
	}

	/**
	 * Muestra el formulario para registrar una nueva reserva.
	 *
	 * <p>
	 * Requiere autenticacion. Inicializa un objeto {@link Reserva} vacio y carga
	 * las listas de huespedes y habitaciones necesarias para los selects.
	 * </p>
	 *
	 * @param session sesion HTTP activa del usuario.
	 * @param modelo  modelo de datos que se pasa a la vista.
	 * @return nombre de la vista {@code FormularioReserva}, o redireccion a
	 *         {@code /login} si el usuario no esta autenticado.
	 * @author Manuel
	 */
	@GetMapping("/nueva")
	public String mostrarFormularioAlta(HttpSession session, Model modelo) {

		logger.info("Controller: petición GET /reservas/nueva - mostrar formulario de alta.");
		autorizacionService.requiereRecepcionista(session);

		try {
			modelo.addAttribute("reserva", new ReservaDTO());
			cargarDatosFormulario(modelo);

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			return "redirect:/login";

		} catch (ErrorBaseDatosException e) {
			logger.error("Controller: error al cargar datos del formulario: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Error al cargar los datos del formulario.");
		}

		return "FormularioReserva";

	}

	/**
	 * Procesa el envio del formulario de alta de una nueva reserva.
	 *
	 * <p>
	 * Si hay errores de validacion Bean Validation se recarga el formulario
	 * conservando los datos introducidos por el usuario. En caso contrario se
	 * persiste la reserva y se redirige al listado.
	 * </p>
	 *
	 * @param reserva   objeto {@link Reserva} enlazado al formulario.
	 * @param resultado resultado de la validacion Bean Validation.
	 * @param session   sesion HTTP activa del usuario.
	 * @param modelo    modelo de datos que se pasa a la vista.
	 * @return redireccion a {@code /reservas} si todo va bien, o nombre de la vista
	 *         {@code FormularioReserva} si hay errores.
	 * @author Manuel
	 */
	@PostMapping("/nueva")
	public String procesarNuevo(@Valid @ModelAttribute("reserva") ReservaDTO reserva, BindingResult resultado,
			HttpSession session, Model modelo) {

		logger.info("Controller: petición POST /reservas/nueva - procesando alta de reserva.");

		// Si hay errores de validacion, volver al formulario con los enumerados
		if (resultado.hasErrors()) {
			logger.warn("Controller: errores de validación en el formulario de alta.");
			try {
				cargarDatosFormulario(modelo);
			} catch (ErrorBaseDatosException e) {
				logger.error("Controller: error al cargar datos del formulario de reservas: " + e.getMessage());
				modelo.addAttribute("errorMensaje", "Error al recargar los datos del formulario.");
			}
			return "FormularioReserva";
		}

		try {
			autorizacionService.requiereAutenticacion(session);
			reservaService.altaReserva(reserva);
			logger.info("Controller: reserva dada de alta correctamente.");

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			return "redirect:/login";
			
		} catch (ArgumentoNoValidoException e) {
			logger.warn("Controller: validación de negocio fallida al crear reserva: " + e.getMessage());
			modelo.addAttribute("errorMensaje", e.getMessage());
			modelo.addAttribute("reserva", reserva);
			try {
				cargarDatosFormulario(modelo);
			} catch (ErrorBaseDatosException ex) {
				modelo.addAttribute("errorMensaje", "Error al recargar los datos del formulario.");
			}
			return "FormularioReserva";
			
		} catch (EntidadNoEncontradaException | ErrorBaseDatosException e) {
			logger.error("Controller: error al guardar la Reserva: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Error al guardar la Reserva.");
			modelo.addAttribute("reserva", reserva);
			try {
				cargarDatosFormulario(modelo);
			} catch (ErrorBaseDatosException ex) {
				logger.error("Controller: error crítico al restablecer el formulario de alta: " + ex.getMessage());
				modelo.addAttribute("errorMensaje", "Error al recargar los datos del formulario.");
			}
			return "FormularioReserva";
		}

		return "redirect:/reservas";
	}

	/**
	 * Muestra el formulario de edicion de una reserva existente.
	 *
	 * <p>
	 * Carga la reserva por su identificador y las listas de huespedes y
	 * habitaciones para los selects. Requiere autenticacion.
	 * </p>
	 *
	 * @param id      identificador de la reserva a editar.
	 * @param session sesion HTTP activa del usuario.
	 * @param modelo  modelo de datos que se pasa a la vista.
	 * @return nombre de la vista {@code FormularioReserva}, o redireccion/vista de
	 *         error segun la excepcion capturada.
	 * @author Manuel
	 */
	@GetMapping("/{id}/editar")
	public String mostrarFormularioModificacion(@PathVariable("id") Integer id, HttpSession session, Model modelo) {

		logger.info("Controller: petición GET /reservas/" + id + "/editar.");

		try {
			autorizacionService.requiereAutenticacion(session);
			ReservaDTO r = reservaService.mostrarReservaPorId(id);
			modelo.addAttribute("reserva", r);
			cargarDatosFormulario(modelo);
			logger.debug("Controller: formulario de edición cargado para reserva con id " + id + ".");

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			return "redirect:/login";
		} catch (EntidadNoEncontradaException e) {
			logger.error("Controller: reserva con id " + id + " no encontrada para editar.");
			modelo.addAttribute("errorMensaje", "Reserva no encontrada.");
			return "Reservas";
		} catch (ErrorBaseDatosException e) {
			logger.error("Controller: error al cargar reserva con id " + id + " para editar: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Error al obtener la reserva.");
			return "Reservas";
		}

		return "FormularioReserva";
	}

	/**
	 * Procesa el envio del formulario de modificacion de una reserva existente.
	 *
	 * <p>
	 * Asigna al objeto recibido el identificador extraido de la URL para evitar
	 * manipulaciones. Si hay errores de validacion se recarga el formulario; en
	 * caso contrario se actualiza la reserva y se redirige al listado.
	 * </p>
	 *
	 * @param id        identificador de la reserva, extraido de la URL.
	 * @param reserva   objeto {@link Reserva} enlazado al formulario.
	 * @param resultado resultado de la validacion Bean Validation.
	 * @param session   sesion HTTP activa del usuario.
	 * @param modelo    modelo de datos que se pasa a la vista.
	 * @return redireccion a {@code /reservas} si todo va bien, o nombre de la vista
	 *         {@code FormularioReserva} si hay errores.
	 * @author Manuel
	 */
	@PostMapping("/{id}/editar")
	public String procesarModificacion(@PathVariable("id") Integer id,
			@Valid @ModelAttribute("reserva") ReservaDTO reserva, BindingResult resultado, HttpSession session,
			Model modelo) {
		logger.info("Controller: petición POST /reservas/" + id + "/editar - procesando modificación.");

		try {
			autorizacionService.requiereAutenticacion(session);
		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			return "redirect:/login";
		}

		if (resultado.hasErrors()) {
			logger.warn("Controller: errores de validación en el formulario de edición.");
			try {
				cargarDatosFormulario(modelo);
			} catch (ErrorBaseDatosException e) {
				logger.error("Controller: error al recargar combos tras fallos de validación en ID " + id + ": "
						+ e.getMessage());
				modelo.addAttribute("errorMensaje", "Error al recargar los datos del formulario.");
			}
			return "FormularioReserva";
		}

		try {
			// aseguramos que el id el de la URL
			reserva.setId(id); // IA: optimizado con Claude.
			reservaService.actualizaReserva(reserva);
			logger.info("Controller: reserva con id " + id + " actualizado correctamente.");
		
		} catch (ArgumentoNoValidoException e) {
			logger.warn("Controller: validación de negocio fallida al actualizar reserva con id " + id + ": " + e.getMessage());
			modelo.addAttribute("errorMensaje", e.getMessage());
			try {
				cargarDatosFormulario(modelo);
			} catch (ErrorBaseDatosException ex) {
				modelo.addAttribute("errorMensaje", "Error al recargar los datos del formulario.");
			}
			return "FormularioReserva";
		} catch (EntidadNoEncontradaException e) {
			logger.error("Controller: reserva con id " + id + " no encontrado para actualizar.");
			modelo.addAttribute("errorMensaje", "Reserva no encontrado.");
			return "FormularioReserva";
		} catch (ErrorBaseDatosException e) {
			logger.error("Controller: error al actualizar reserva con id " + id + ": " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Error al actualizar la reserva.");
			try {
				cargarDatosFormulario(modelo);
			} catch (ErrorBaseDatosException ex) {
				modelo.addAttribute("errorMensaje", "Error al recargar los datos del formulario.");
			}
			return "FormularioReserva";

		}
		return "redirect:/reservas";
	}
}
