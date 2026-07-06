package com.springhotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.log4j.Logger;


/**
 * Controlador principal de la aplicación encargado de gestionar la ruta raíz.
 *
 * <p>Este controlador atiende las peticiones GET dirigidas a {@code "/"} y
 * devuelve la vista inicial del sistema. Al retornar la cadena {@code "index"},
 * Spring MVC delega en el motor de vistas para localizar el archivo
 * {@code index.jsp} dentro del directorio configurado (por ejemplo,
 * {@code /WEB-INF/views/}).</p>
 *
 * <p>Este controlador no realiza lógica adicional: su única responsabilidad es
 * mostrar la página de inicio cuando el usuario accede a la raíz del sitio.</p>
 *
 * @author Ana Laura y Manuel
 * @version 1.0
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Controller
public class HomeController {
	
	/**
	 * Logger del sistema para la clase {@link HomeController}.
	 * Se utiliza para registrar eventos, flujos de datos y excepciones 
	 * en los diferentes niveles de depuración (DEBUG, INFO, WARN, ERROR).
	 */
    private static final Logger logger = Logger.getLogger(HomeController.class);
	
	 /**
     * Maneja las solicitudes GET a la ruta raíz {@code "/"}.
     *
     * <p>Devuelve el nombre lógico de la vista inicial de la aplicación.
     * Spring resolverá automáticamente la ubicación del archivo JSP asociado.</p>
     *
     * @return el nombre de la vista {@code "index"} que se mostrará al usuario.
     */
    @GetMapping("/")
    public String index() {
        logger.info("Controller: petición GET / - cargando vista index.");
        return "index"; // Spring buscará index.jsp
    }
}
