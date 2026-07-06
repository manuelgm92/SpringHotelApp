package com.springhotel.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springhotel.dto.UsuarioDTO;
import com.springhotel.entity.Usuario;
import com.springhotel.exception.ArgumentoNoValidoException;
import com.springhotel.service.UsuarioService;

/**
 * Controlador MVC para la gestion de usuarios del sistema hotelero.
 *
 * <p>Gestiona las operaciones CRUD sobre la entidad {@link Usuario}, asi como
 * la visualizacion y actualizacion del perfil del usuario autenticado en sesion.</p>
 *
 * <p>Todas las rutas se mapean bajo el prefijo {@code /usuarios}.</p>
 *
 * @author Manuel
 * @version 1.0
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Controller
@RequestMapping("/usuarios")
public class UserController {
	
	/**
	 * Logger del sistema para la clase {@link UserController}.
	 * Se utiliza para registrar eventos, flujos de datos y excepciones 
	 * en los diferentes niveles de depuración (DEBUG, INFO, WARN, ERROR).
	 */
    private static final Logger logger = Logger.getLogger(UserController.class);
    
    /**
     * Servicio de usuarios inyectado automáticamente por Spring.
     * Proporciona la lógica de negocio para autenticación y registro.
     */
    private final UsuarioService usuarioService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param usuarioService servicio de gestión de usuarios
     */
    @Autowired
    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
	
	/**
     * Muestra el listado completo de usuarios registrados en el sistema.
     *
     * @param model modelo MVC al que se aniade el atributo {@code usuarios}
     *              con la lista obtenida del servicio
     * @return nombre logico de la vista {@code usuarios}
     */
	@GetMapping
	public String mostrarUsuarios(Model model) {
	    logger.info("Controller: petición GET /usuarios - mostrando lista de usuarios.");
		model.addAttribute("usuarios", usuarioService.mostrarTodosLosUsuariosDTO());
		return "usuarios";
	}

	/**
     * Muestra el formulario para registrar un nuevo usuario.
     *
     * @return nombre logico de la vista {@code nuevoUsuario}
     */
	@GetMapping("/nuevo")
	public String nuevoUsuario() {
		logger.info("Controller: petición GET /usuarios/nuevoUsuario - mostrando formulario de alta.");
		return "formAltaUsuario";
	}
	
	
	/**
     * Procesa el formulario de alta y persiste el nuevo usuario en la base de datos.
     * <p>
     * Si los datos son válidos, se delega el alta al servicio y se redirige al listado.
     * En caso de que el nombre de usuario ya exista o los argumentos no sean válidos,
     * se captura la excepción y se vuelve a mostrar el formulario con un mensaje de error.
     * </p>
     *
     * @param usuario objeto {@link Usuario} construido automáticamente por Spring MVC 
     *                mediante data binding a partir de los parámetros del formulario.
     * @param model   objeto {@link Model} utilizado para pasar el mensaje de error 
     *                y los datos del formulario de vuelta a la vista en caso de fallo.
     * @return una cadena con la redirección a {@code /usuarios} si el alta es exitosa, 
     *         o el nombre de la vista {@code nuevoUsuario} si ocurre un error de validación.
     */
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute("nuevoUsuario") UsuarioDTO usuarioDTO, Model model) {
		logger.debug("Controller: petición POST /usuarios/guardar - guardando nuevo usuario: " + usuarioDTO.getNombreUsuario());
		try {
			usuarioService.altaUsuario(usuarioDTO);
			logger.info("Controller: usuario guardado correctamente.");
			return "redirect:/usuarios";
		} catch (ArgumentoNoValidoException e){
			logger.warn("Controller: argumento no válido al guardar usuario: " + e.getMessage());
			model.addAttribute("error", e.getMessage());
	        model.addAttribute("nuevoUsuario", usuarioDTO);
	        return "formAltaUsuario";
		}
	}

	/**
     * Muestra el formulario de edicion precargado con los datos del usuario indicado.
     *
     * @param id    identificador del usuario a editar, recibido como parametro de consulta
     * @param model modelo MVC al que se aniade el atributo {@code usuario}
     * @return nombre logico de la vista {@code editarUsuario}
     */
	@GetMapping("/editar")
	public String mostrarEditar(@RequestParam int id, Model model) {
		logger.info("Controller: petición GET /usuarios/editar - cargando usuario con id: " + id);
		UsuarioDTO usuario = usuarioService.mostrarUsuarioDTOPorId(id);
		model.addAttribute("usuario", usuario);
		return "editarUsuario";
	}

	/**
     * Procesa el formulario de edicion y actualiza los datos del usuario,
     * excluyendo la contrasenia (gestionada por separado en
     * {@link #actualizarPassword}).
     *
     * @param usuario objeto {@link UsuarioDTO} con los datos actualizados del formulario
     * @return redireccion a {@code /usuarios} tras la actualizacion
     */
	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("usuario") UsuarioDTO usuarioDTO, Model model) {
		logger.debug("Controller: petición POST /usuarios/actualizar - actualizando usuario con id: " + usuarioDTO.getId());
		try {
			usuarioService.actualizaUsuarioMenosPassword(usuarioDTO);
			logger.info("Controller: usuario actualizado correctamente.");
			return "redirect:/usuarios";
		} catch (ArgumentoNoValidoException e) {
			logger.warn("Controller: argumento no válido al actualizar usuario: " + e.getMessage());
			model.addAttribute("error", e.getMessage());
			model.addAttribute("usuario", usuarioDTO);
			return "editarUsuario";
		}
		
	}
	
	/**
     * Elimina el usuario con el identificador indicado.
     *
     * <p>La operacion se expone mediante POST para evitar eliminaciones
     * accidentales por prefetch del navegador o rastreadores web.</p>
     *
     * @param id identificador del usuario a eliminar, recibido como parametro de formulario
     * @return redireccion a {@code /usuarios} tras la eliminacion
     */
	@PostMapping("/eliminar")
	public String eliminar(@RequestParam int id) {
		logger.info("Controller: petición POST /usuarios/eliminar - eliminando usuario con id: " + id);
		usuarioService.eliminaUsuario(id);
		logger.info("Controller: usuario con id " + id + " eliminado correctamente.");
		return "redirect:/usuarios";
	}
	
	/**
     * Muestra la pagina de perfil del usuario actualmente autenticado en sesion.
     *
     * <p>Si no existe sesion activa ({@code usuarioSesion} es {@code null}),
     * redirige a la pagina de inicio.</p>
     *
     * @param session sesion HTTP de la que se extrae el usuario autenticado
     * @param model   modelo MVC al que se aniade el atributo {@code usuario}
     * @return nombre logico de la vista {@code perfilUsuario},
     *         o redireccion a {@code /} si no hay sesion activa
     */
	@GetMapping("/perfilUsuario")
	public String perfilUsuario(HttpSession session, Model model) {
		logger.info("Controller: petición GET /usuarios/perfilUsuario.");
		Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");

		if (usuarioSesion == null) {
			logger.warn("Controller: no hay sesión activa, redirigiendo a /.");
			return "redirect:/";
		}

		model.addAttribute("usuario", usuarioSesion);
		return "perfilUsuario";
	}
	
	/**
     * Procesa el cambio de contrasenia del usuario autenticado en sesion.
     *
     * <p>Valida que {@code passwordNueva} y {@code passwordConfirmar} coincidan.
     * En caso contrario redirige al perfil con el parametro {@code error=1}.
     * Si la validacion es correcta, actualiza la contrasenia y refresca el
     * atributo de sesion {@code usuarioSesion}.</p>
     *
     * @param passwordNueva     nueva contrasenia introducida por el usuario
     * @param passwordConfirmar repeticion de la nueva contrasenia para confirmacion
     * @param session           sesion HTTP que contiene el usuario autenticado
     * @return redireccion a {@code /usuarios/perfilUsuario?success=1} si todo es correcto,
     *         a {@code /usuarios/perfilUsuario?error=1} si las contrasenias no coinciden,
     *         o a {@code /} si no hay sesion activa
     */
	@PostMapping("/actualizarPassword")
	public String actualizarPassword(@RequestParam String passwordNueva,
	                                 @RequestParam String passwordConfirmar,
	                                 HttpSession session) {
	    
		logger.info("Controller: petición POST /usuarios/actualizarPassword.");
	    Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioSesion");

	    if (usuarioSesion == null) {
	    	logger.warn("Controller: no hay sesión activa, redirigiendo a /.");
	        return "redirect:/";
	    }

	    // validar contrasenias
	    if (!passwordNueva.equals(passwordConfirmar)) {
	    	logger.warn("Controller: las contraseñas no coinciden para usuario con id: " + usuarioSesion.getId());
	        return "redirect:/usuarios/perfilUsuario?error=1";
	    }

	    // cargar usuario real
	    Usuario usuario = usuarioService.mostrarUsuarioPorId(usuarioSesion.getId());

	    // actualizar solo password
	    usuario.setPasswordUsuario(passwordNueva);

	    usuarioService.actualizaUsuario(usuario);

	    // actualizar sesión
	    session.setAttribute("usuarioSesion", usuario);
	    
	    logger.info("Controller: contraseña actualizada correctamente para usuario con id: " + usuarioSesion.getId());

	    return "redirect:/usuarios/perfilUsuario?success=1";
	}
	
}
