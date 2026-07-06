package com.springhotel.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springhotel.entity.Usuario;
import com.springhotel.exception.CredencialesInvalidasException;
import com.springhotel.service.UsuarioService;

/**
 * Controlador de Spring MVC encargado de gestionar las operaciones
 * relacionadas con la autenticacion y el registro de usuarios.
 * <p>
 * Maneja las peticiones HTTP correspondientes al login, logout,
 * navegacion a la pagina principal y el alta de nuevos usuarios.
 * Delega la logica de negocio en {@link UsuarioService}.
 * </p>
 * 
 * @author Manuel
 * @version 1.0
 * @see UsuarioService
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Controller
public class LoginController {
	
	/**
	 * Logger del sistema para la clase {@link LoginController}.
	 * Se utiliza para registrar eventos, flujos de datos y excepciones 
	 * en los diferentes niveles de depuración (DEBUG, INFO, WARN, ERROR).
	 */
    private static final Logger logger = Logger.getLogger(LoginController.class);
	
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
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
	
	/**
     * Muestra el jsp de inicio de sesion.
     * <p>
     * Responde a peticiones GET sobre {@code /login} y devuelve
     * la vista del formulario de login.
     * </p>
     *
     * @return el nombre de la vista {@code login}
     */
	@GetMapping("/login")
	public String mostrarLogin() {
		logger.info("Controller: petición GET /login - mostrando formulario de login.");
		return "login";
	}
	
	/**
     * Procesa el formulario de inicio de sesion.
     * <p>
     * Recibe las credenciales introducidas por el usuario y delega
     * la autenticacion en {@link UsuarioService#login(String, String)}.
     * Si las credenciales son correctas, almacena el usuario en la sesion
     * HTTP y redirige a la pagina principal. Si son incorrectas, captura
     * la excepcion lanzada por el servicio y muestra el mensaje de error
     * en el formulario.
     * </p>
     *
     * @param nombreUsuario   el nombre de usuario introducido en el formulario
     * @param passwordUsuario la contrasena introducida en el formulario
     * @param session         la sesion HTTP activa donde se almacenara el usuario autenticado
     * @param modelo          el modelo de Spring MVC para pasar atributos a la vista
     * @return la vista {@code principal} si el login es correcto,
     *         o la vista {@code login} con un mensaje de error si falla
     */
	@PostMapping("/login")
	public String procesarLogin(
			@RequestParam String nombreUsuario, 
			@RequestParam String passwordUsuario,
			HttpSession session,
			Model modelo) {
		
		logger.info("Controller: petición POST /login - procesando login de usuario: " + nombreUsuario);
		try {
            Usuario usuarioLogin = usuarioService.login(nombreUsuario, passwordUsuario);
            session.setAttribute("usuarioSesion", usuarioLogin);
            logger.info("Controller: login correcto para usuario: " + nombreUsuario);
            return "principal";
        } catch (CredencialesInvalidasException e) {
            logger.warn("Controller: credenciales inválidas para usuario: " + nombreUsuario);
            modelo.addAttribute("error", e.getMessage());
            return "login";
        }
	}
	
	/**
     * Cierra la sesion del usuario autenticado.
     * <p>
     * Invalida la sesion HTTP activa, eliminando todos los atributos
     * almacenados en ella, y redirige al formulario de login.
     * </p>
     *
     * @param session la sesion HTTP activa que se desea invalidar
     * @return una redireccion a la vista {@code /login}
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("Controller: petición GET /logout - cerrando sesión.");
        session.invalidate();
        return "redirect:/login";
    }
    
    
}
