package com.springhotel.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Manejador global de excepciones para la aplicación web.
 *
 * <p>
 * Centraliza el tratamiento de excepciones relacionadas con accesos no
 * permitidos. Cuando se captura una {@link AccesoNoPermitidoException} añade un
 * mensaje flash al {@code RedirectAttributes} y redirige al login ("/login").
 * </p>
 *
 * <p>
 * Esta clase está anotada con {@code @ControllerAdvice} para que Spring la
 * detecte y aplique a todos los controladores gestionados por el Dispatcher
 * Servlet. Mantener el manejo aquí evita duplicar la lógica de redirección y la
 * creación de mensajes de error en cada controlador.
 * </p>
 *
 *
 * @author Ana Laura y Manuel
 * @version 1.0
 * @see AccesoNoPermitidoException
 */
@ControllerAdvice
public class ManejadorAccesoNoPermitido {

	/**
	 * Maneja las excepciones de tipo {@link AccesoNoPermitidoException}.
	 *
	 * <p>
	 * Añade un atributo flash con la clave {@code "errorMensaje"} cuyo valor es el
	 * mensaje de la excepción, de modo que la vista destino pueda mostrarlo tras la
	 * redirección. Finalmente realiza una redirección a {@code /habitaciones}.
	 * </p>
	 *
	 * @param e                  excepción lanzada que indica acceso no permitido
	 * @param redirectAttributes contenedor de atributos flash para la redirección;
	 *                           nunca debe ser {@code null}
	 * @return cadena con la instrucción de redirección
	 *         {@code "redirect:/habitaciones"}
	 */
	@ExceptionHandler(AccesoNoPermitidoException.class)
	public String manejarAccesoNoPermitido(AccesoNoPermitidoException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error", e.getMessage());
		return "redirect:/login";
	}
}
