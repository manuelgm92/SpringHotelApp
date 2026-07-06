package com.springhotel.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.springhotel.dto.HuespedDTO;
import com.springhotel.entity.Huesped;
import com.springhotel.exception.AccesoNoPermitidoException;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;
import com.springhotel.service.AutorizacionService;
import com.springhotel.service.HuespedService;
import org.apache.log4j.Logger;

/**
 * Controlador Spring MVC encargado de gestionar las operaciones relacionadas
 * con los huespedes del hotel.
 * <p>
 * Maneja las peticiones HTTP bajo la ruta base {@code /huespedes} y delega la
 * logica de negocio en {@link HuespedService}. La autenticacion y autorizacion
 * de cada operacion se gestiona a traves de {@link AutorizacionService}.
 * </p>
 * <p>
 * Las vistas JSP utilizadas son:
 * <ul>
 * <li>{@code Huespedes.jsp} - listado de todos los huespedes</li>
 * <li>{@code FormularioHuesped.jsp} - formulario de alta y edicion</li>
 * <li>{@code DetalleHuesped.jsp} - vista de detalle de un huesped</li>
 * </ul>
 * </p>
 *
 * @author Manuel
 * @version 1.0
 *
 * @see HuespedService
 * @see AutorizacionService
 * @see Huesped
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
// IA: javadoc generado con ayuda de IA(Claude)
@Controller
@RequestMapping("/huespedes")
public class HuespedController {

	/**
	 * Logger del sistema para la clase {@link HuespedController}. Se utiliza para
	 * registrar eventos, flujos de datos y excepciones en los diferentes niveles de
	 * depuración (DEBUG, INFO, WARN, ERROR).
	 */
	private static final Logger logger = Logger.getLogger(HuespedController.class);

	/**
	 * Servicio que contiene la logica de negocio para la gestion de huespedes.
	 * Inyectado automaticamente por Spring a traves del constructor.
	 */
	private final HuespedService huespedService;

	/**
	 * Servicio que gestiona la autenticacion y autorizacion de los usuarios de la
	 * sesion activa. Inyectado automaticamente por Spring a traves del constructor.
	 */
	private final AutorizacionService autorizacionService;

	/**
	 * Constructor con inyeccion de dependencias.
	 *
	 * @param huespedService      servicio de gestion de huespedes
	 * @param autorizacionService servicio de autenticacion y autorizacion
	 */
	@Autowired
	public HuespedController(HuespedService huespedService, AutorizacionService autorizacionService) {
		this.huespedService = huespedService;
		this.autorizacionService = autorizacionService;
	}

	/**
     * Muestra el listado completo de huespedes registrados en el sistema, 
     * permitiendo aplicar un filtro de búsqueda opcional por coincidencia de texto.
     * <p>
     * Requiere que el usuario este autenticado. Si no lo esta, redirige
     * al login. Si ocurre un error al cargar los datos, muestra un mensaje
     * de error en la vista sin interrumpir la navegacion.
     * </p>
     * <p>
     * Ademas comprueba si el usuario tiene perfil de recepcionista para
     * mostrar u ocultar las opciones de edicion y eliminacion en la vista.
     * </p>
     *
     * @param textoBusqueda cadena opcional para filtrar huespedes por coincidencia 
     * 			en nombre o apellidos; puede ser {@code null} o vacía.
     * @param session sesion HTTP activa del usuario
     * @param model   modelo de datos que se pasa a la vista
     * @param redirectAttributes atributos de redirección para propagar mensajes flash entre peticiones.
     * @return nombre de la vista {@code Huespedes}, o redireccion a
     *         {@code /login} si el usuario no esta autenticado
     */
	@GetMapping
	public String mostrarTodosHuespedes(@RequestParam(required = false) String textoBusqueda,
										HttpSession session, Model model,
										RedirectAttributes redirectAttributes) {
		
	    logger.info("Controller: petición GET /huespedes - mostrar todos los huéspedes.");
	    
		try {
			autorizacionService.requiereAutenticacion(session);
			
			List<HuespedDTO> listaHuespedes = huespedService.buscarHuespedes(textoBusqueda);
			model.addAttribute("huespedes", listaHuespedes);
			model.addAttribute("textoBusqueda", textoBusqueda);
			model.addAttribute("esRecepcionista", autorizacionService.esRecepcionista(session));
			logger.info("Controller: lista de huéspedes cargada correctamente.");

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
	        redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
	        return "redirect:/login";
		}   catch (ErrorBaseDatosException e) {
			logger.error("Controller: error de base de datos al listar huéspedes: " + e.getMessage(), e); 
            model.addAttribute("errorMensaje", "No se pudieron cargar los huéspedes.");
        }
		
		return "Huespedes";
	}

	/**
	 * Muestra el detalle completo de un huesped a partir de su identificador.
	 * <p>
	 * Requiere autenticacion. Si el huesped no existe o ocurre un error de base de
	 * datos, redirige al listado con un mensaje de error.
	 * </p>
	 *
	 * @param id      identificador unico del huesped a visualizar
	 * @param session sesion HTTP activa del usuario
	 * @param modelo  modelo de datos que se pasa a la vista
	 * @return nombre de la vista {@code DetalleHuesped} si todo va bien;
	 *         {@code Huespedes} con mensaje de error si el huesped no existe o hay
	 *         un error de base de datos; redireccion a {@code /login} si el usuario
	 *         no esta autenticado
	 */
	@GetMapping("/{id}/detalle")
	public String mostrarDetalleHuesped(@PathVariable Integer id, HttpSession session, Model modelo,
			RedirectAttributes redirectAttributes) {

		logger.debug("Controller: petición GET /huespedes/" + id + "/detalle.");

		try {
			autorizacionService.requiereAutenticacion(session);
			HuespedDTO h = huespedService.mostrarHuespedPorId(id);
			modelo.addAttribute("huesped", h);
			logger.debug("Controller: detalle del huésped con id " + id + " cargado.");

			return "DetalleHuesped";

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/login";
		} catch (EntidadNoEncontradaException e) {
			logger.warn("Controller: huésped con id " + id + " no encontrado.");
			redirectAttributes.addFlashAttribute("errorMensaje", "Huésped no encontrado.");
			return "redirect:/huespedes";
		} catch (ErrorBaseDatosException e) {
			logger.error("Controller: error al obtener huésped con id " + id + ": " + e);
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al obtener el Huésped.");
			return "redirect:/huespedes";
		}

	}

	/**
	 * Muestra el formulario para registrar un nuevo huesped.
	 * <p>
	 * Requiere autenticacion. Inicializa un objeto {@link Huesped} vacio y lo añade
	 * al modelo para que el formulario pueda vincularse a el mediante Spring Form
	 * tags.
	 * </p>
	 *
	 * @param session sesion HTTP activa del usuario
	 * @param modelo  modelo de datos que se pasa a la vista
	 * @return nombre de la vista {@code FormularioHuesped}; o redireccion a
	 *         {@code /login} si el usuario no esta autenticado
	 */
	@GetMapping("/nuevo")
	public String mostrarFormularioAlta(HttpSession session, Model modelo, RedirectAttributes redirectAttributes) {

		logger.info("Controller: petición GET /huespedes/nuevo - mostrar formulario de alta.");
		autorizacionService.requiereRecepcionista(session);

		try {
			modelo.addAttribute("huesped", new HuespedDTO());
		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/login";
		}

		return "FormularioHuesped";

	}

	/**
	 * Procesa el formulario de alta de un nuevo huesped.
	 * <p>
	 * Primero valida los datos del formulario con Bean Validation. Si hay errores
	 * de validacion, devuelve el formulario con los mensajes de error sin realizar
	 * ninguna operacion en base de datos.
	 * </p>
	 * <p>
	 * Si los datos son correctos, intenta persistir el huesped. En caso de
	 * violacion de restriccion UNIQUE (DNI o email duplicados), informa al usuario
	 * y mantiene el formulario. Si la operacion tiene exito, redirige al listado de
	 * huespedes.
	 * </p>
	 *
	 * @param huesped   objeto {@link Huesped} construido a partir de los datos del
	 *                  formulario y validado con {@code @Valid}
	 * @param resultado resultado de la validacion de Bean Validation; contiene los
	 *                  errores de campo si los hay
	 * @param session   sesion HTTP activa del usuario
	 * @param modelo    modelo de datos que se pasa a la vista en caso de error
	 * @return redireccion a {@code /huespedes} si el alta tiene exito;
	 *         {@code FormularioHuesped} con mensaje de error en caso contrario;
	 *         redireccion a {@code /login} si el usuario no esta autenticado
	 */
	@PostMapping("/nuevo")
	public String procesarNuevo(@Valid @ModelAttribute("huesped") HuespedDTO huespedDTO, BindingResult resultado,
			HttpSession session, Model modelo, RedirectAttributes redirectAttributes) {

		logger.info("Controller: petición POST /huespedes/nuevo - procesando alta de huésped.");

		// Si hay errores de validación, volver al formulario con los enumerados
		if (resultado.hasErrors()) {
			logger.warn("Controller: errores de validación en el formulario de alta.");
			modelo.addAttribute("huesped", huespedDTO);
			return "FormularioHuesped";
		}

		try {
			autorizacionService.requiereRecepcionista(session);
			huespedService.altaHuesped(huespedDTO);
			logger.info("Controller: huésped dado de alta correctamente.");

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/login";
		} catch (PersistenceException e) {
			logger.warn("Controller: DNI/Pasaporte o email duplicado al dar de alta.");
			// IA: error al poner datos ya registrados en la BBDD que son unicos,
			// solucionado con ayuda de Claude.
			modelo.addAttribute("errorMensaje", "El DNI/Pasaporte o el email ya existe en el sistema.");
			modelo.addAttribute("huesped", huespedDTO);
			return "FormularioHuesped";
		} catch (EntidadNoEncontradaException | ErrorBaseDatosException e) {
			logger.error("Controller: error al guardar el huésped: " + e.getMessage());
			modelo.addAttribute("errorMensaje", "Error al guardar el huésped.");
			modelo.addAttribute("huesped", huespedDTO);
			return "FormularioHuesped";
		}

		return "redirect:/huespedes";
	}

	/**
	 * Muestra el formulario de edicion de un huesped existente.
	 * <p>
	 * Carga los datos actuales del huesped desde la base de datos y los pre-rellena
	 * en el formulario. Si el huesped no existe o hay un error, redirige al listado
	 * con un mensaje de error.
	 * </p>
	 *
	 * @param id      identificador unico del huesped a editar, extraido de la URL
	 * @param session sesion HTTP activa del usuario
	 * @param modelo  modelo de datos que se pasa a la vista
	 * @return nombre de la vista {@code FormularioHuesped} con los datos del
	 *         huesped pre-cargados; {@code Huespedes} con mensaje de error si no se
	 *         encuentra el huesped o hay un error de base de datos; redireccion a
	 *         {@code /login} si el usuario no esta autenticado
	 */
	@GetMapping("/{id}/editar")
	public String mostrarFormularioModificacion(@PathVariable("id") Integer id, HttpSession session, Model modelo,
			RedirectAttributes redirectAttributes) {

		logger.info("Controller: petición GET /huespedes/" + id + "/editar.");

		try {
			autorizacionService.requiereAutenticacion(session);
			HuespedDTO h = huespedService.mostrarHuespedPorId(id);
			modelo.addAttribute("huesped", h);
			logger.info("Controller: formulario de edición cargado para huésped con id " + id + ".");

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/login";
		} catch (EntidadNoEncontradaException e) {
			logger.warn("Controller: huésped con id " + id + " no encontrado para editar.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/huespedes";
		} catch (ErrorBaseDatosException e) {
			logger.error("Controller: error al cargar huésped con id " + id + " para editar: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/huespedes";
		}

		return "FormularioHuesped";
	}

	/**
	 * Procesa el formulario de modificacion de un huesped existente.
	 * <p>
	 * Primero verifica la autenticacion del usuario. A continuacion valida los
	 * datos del formulario. Si la validacion es correcta, actualiza el huesped en
	 * la base de datos asegurandose de que el ID usado es el de la URL y no el del
	 * formulario, para evitar manipulaciones.
	 * </p>
	 * <p>
	 * Si se detecta una violacion de restriccion UNIQUE (DNI o email ya existentes
	 * en otro registro), informa al usuario y mantiene el formulario con los datos
	 * introducidos.
	 * </p>
	 *
	 * @param id        identificador unico del huesped a modificar, extraido de la
	 *                  URL
	 * @param huesped   objeto {@link Huesped} construido a partir de los datos del
	 *                  formulario y validado con {@code @Valid}
	 * @param resultado resultado de la validacion de Bean Validation
	 * @param session   sesion HTTP activa del usuario
	 * @param modelo    modelo de datos que se pasa a la vista en caso de error
	 * @return redireccion a {@code /huespedes} si la modificacion tiene exito;
	 *         {@code FormularioHuesped} con mensaje de error en caso contrario;
	 *         redireccion a {@code /login} si el usuario no esta autenticado
	 */
	@PostMapping("/{id}/editar")
	public String procesarModificacion(@PathVariable("id") Integer id,
			@Valid @ModelAttribute("huesped") HuespedDTO huespedDTO, BindingResult resultado, HttpSession session,
			Model modelo, RedirectAttributes redirectAttributes) {

		logger.info("Controller: petición POST /huespedes/" + id + "/editar - procesando modificación.");

		try {
			autorizacionService.requiereAutenticacion(session);
		} catch (AccesoNoPermitidoException e) {
			logger.info("Controller: acceso no permitido, redirigiendo a /login.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/login";
		}

		if (resultado.hasErrors()) {
			logger.warn("Controller: errores de validación en el formulario de edición.");
			modelo.addAttribute("huesped", huespedDTO);
			return "FormularioHuesped";
		}

		try {
			// aseguramos que el id el de la URL
			huespedDTO.setId(id); // IA: optimizado con Claude.
			huespedService.actualizaHuesped(huespedDTO);
			logger.info("Controller: huésped con id " + id + " actualizado correctamente.");

		} catch (PersistenceException e) {
			logger.error("Controller: DNI/Pasaporte o email duplicado al actualizar huésped con id " + id + ".");
			modelo.addAttribute("errorMensaje", "El DNI/Pasaporte o el email ya existe en el sistema.");
			modelo.addAttribute("huesped", huespedDTO);

			return "FormularioHuesped";
		} catch (EntidadNoEncontradaException e) {
			logger.warn("Controller: huésped con id " + id + " no encontrado para actualizar.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "FormularioHuesped";
		} catch (ErrorBaseDatosException e) {
			logger.error("Controller: error al actualizar huésped con id " + id + ": " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "FormularioHuesped";
		}
		return "redirect:/huespedes";
	}

	/**
	 * Elimina un huesped del sistema a partir de su identificador.
	 * <p>
	 * Requiere autenticacion. Si el huesped no existe o hay un error de base de
	 * datos, añade un mensaje de error como atributo flash para que sea visible
	 * tras la redireccion. Si la eliminacion tiene exito, añade un mensaje de
	 * confirmacion flash y redirige al listado.
	 * </p>
	 * <p>
	 * Se usan {@link RedirectAttributes} con {@code addFlashAttribute} para que los
	 * mensajes persistan a traves de la redireccion y sean visibles en la vista del
	 * listado.
	 * </p>
	 *
	 * @param id                 identificador unico del huesped a eliminar,
	 *                           extraido de la URL
	 * @param session            sesion HTTP activa del usuario
	 * @param redirectAttributes atributos que persisten tras la redireccion, usados
	 *                           para mostrar mensajes de exito o error
	 * @return redireccion a {@code /huespedes} siempre, con mensaje de exito o
	 *         error segun el resultado de la operacion; redireccion a
	 *         {@code /login} si el usuario no esta autenticado
	 */
	@PostMapping("/{id}/eliminar")
	public String eliminarHuesped(@PathVariable("id") Integer id, HttpSession session,
			RedirectAttributes redirectAttributes) {

		logger.info("Controller: petición POST /huespedes/" + id + "/eliminar.");

		try {
			autorizacionService.requiereAutenticacion(session);

		} catch (AccesoNoPermitidoException e) {
			logger.warn("Controller: acceso no permitido, redirigiendo a /login.");
			redirectAttributes.addFlashAttribute("errorMensaje", e.getMessage());
			return "redirect:/login";
		}

		try {
			huespedService.eliminaHuesped(id);
			logger.info("Controller: huésped con id " + id + " eliminado correctamente.");
			redirectAttributes.addFlashAttribute("okMensaje", "Huésped eliminado correctamente.");

		} catch (EntidadNoEncontradaException e) {
			logger.warn("Controller: huésped con id " + id + " no encontrado para eliminar.");
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al eliminar el huésped.");

		} catch (ErrorBaseDatosException e) {
			logger.error("Controller: error de base de datos al eliminar huésped con id " + id + ": " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMensaje", "Error al eliminar el huésped.");
		}

		return "redirect:/huespedes";

	}
}
