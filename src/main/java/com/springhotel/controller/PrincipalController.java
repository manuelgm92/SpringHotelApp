package com.springhotel.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal de la aplicacion.
 *
 * <p>
 * Este controlador atiende las peticiones GET dirigidas a {@code "/principal"} y
 * devuelve la vista inicial del sistema. Al retornar la cadena {@code "principal"},
 * Spring MVC delega en el motor de vistas para localizar el archivo
 * {@code principal.jsp} dentro del directorio configurado (por ejemplo,
 * {@code /WEB-INF/views/}).
 * </p>
 *
 * <p>
 * Este controlador no realiza logica adicional: su unica responsabilidad es
 * mostrar la pagina principal cuando el usuario incia sesion.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Controller
public class PrincipalController {
	
	/**
	 * Logger del sistema para la clase {@link PrincipalController}.
	 * Se utiliza para registrar eventos, flujos de datos y excepciones 
	 * en los diferentes niveles de depuración (DEBUG, INFO, WARN, ERROR).
	 */
    private static final Logger logger = Logger.getLogger(PrincipalController.class);
    
	/**
     * Maneja las solicitudes GET a la ruta {@code "/principal"}.
     *
     * <p>Devuelve el nombre logico de la vista principal de la aplicacion.
     * Spring resolvera automaticamente la ubicacion del archivo JSP asociado.</p>
     *
     * @return el nombre de la vista {@code "principal"} que se mostrara al usuario.
     */
	@GetMapping("/principal")
	public String principal() {
		logger.info("Controller: petición GET /principal - cargando vista principal.");
		return "principal"; // Spring buscara principal.jsp
	}

}
