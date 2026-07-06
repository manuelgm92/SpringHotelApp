package com.springhotel.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Inicializador principal de la aplicacion SpringHotelApp.
 *
 * <p>
 * Esta clase reemplaza el archivo {@code web.xml} tradicional y configura el
 * {@link org.springframework.web.servlet.DispatcherServlet} de forma automatica
 * </p>
 *
 * <p>
 * Al extender {@link AbstractAnnotationConfigDispatcherServletInitializer},
 * Spring registra automaticamente el {@code DispatcherServlet} y el
 * {@code ContextLoaderListener} en el contenedor de servlets durante el
 * arranque de la aplicacion.
 * </p>
 *
 * <p>
 * Al arrancar Tomcat, detecta automaticamente esta clase y la ejecuta para
 * poner en marcha toda la aplicacion.
 * </p>
 * 
 * <p>
 * Estructura de contextos configurada:
 * </p>
 * <ul>
 * <li><b>Root Application Context:</b> gestionado por {@link HibernateConfig},
 * contiene los beans de infraestructura (base de datos, Hibernate, logica de
 * negocio).</li>
 * <li><b>Servlet Application Context:</b> gestionado por
 * {@link SpringMvcConfig}, contiene los beans propios de la capa web
 * (controladores, vistas, lo que ve el usuario).</li>
 * </ul>
 *
 * @author Ana Laura y Manuel
 * @version 1.0
 * @see AbstractAnnotationConfigDispatcherServletInitializer
 * @see HibernateConfig
 * @see SpringMvcConfig
 */
//IA: javadoc generado con ayuda de IA(Claude)
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Devuelve las clases de configuracion que forman el <em>Root Application
	 * Context</em>.
	 *
	 * <p>
	 * El contexto raiz es compartido por toda la aplicacion y es el lugar donde van
	 * los beans que necesita toda la app:
	 * </p>
	 * 
	 * <p>
	 * Aqui se indican las clases @Configuration que definen los beans de negocio:
	 * servicios, repositorios, acceso a datos, seguridad, etc.
	 * </p>
	 *
	 * @return array con {@link HibernateConfig} como la clase de configuracion
	 *         raiz.
	 * 
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { HibernateConfig.class };
	}

	/**
	 * Le dice a Spring que clases configuran la parte web de la aplicacion.
	 *
	 * <p>
	 * Aqui se indica la clase @Configuration que contiene lo relacionado con
	 * Spring MVC: los controladores ({@code @Controller}), el ViewResolver, los
	 * interceptores, etc.
	 * </p>
	 *
	 * <p>
	 * Es el equivalente a lo que antes se ponia en el XML indicado en
	 * contextConfigLocation del web.xml:
	 * </p>
	 * <ul>
	 * <li><param-value>/WEB-INF/spring-mvc-servlet.xml</param-value></li>
	 * </ul>
	 *
	 * @return array con {@link SpringMvcConfig} como las clases de configuracion
	 *         del contexto web
	 * 
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { SpringMvcConfig.class };
	}

	/**
	 * Indica que URLs debe interceptar el DispatcherServlet de Spring.
	 *
	 * <p>
	 * El {@code "/"} significa "todas las peticiones". Cuando el navegador pida por
	 * ejemplo, {@code /login}, Spring sera quien responda.
	 * </p>
	 *
	 * <p>
	 * Equivale a la etiqueta url-pattern del web.xml. Con "/" el DispatcherServlet
	 * captura TODAS las peticiones (excepto las que van a otros servlets
	 * registrados explicitamente).
	 * </p>
	 *
	 * @return array con los patrones de URL asignados al DispatcherServlet
	 * 
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	/**
     * Registra el filtro de codificacion UTF-8 para todas las peticiones HTTP.
     *
     * <p>Sin este filtro, los caracteres especiales del espanol (tildes, enies, etc.)
     * se corrompen al llegar al servidor a traves de formularios POST, produciendo
     * cadenas como {@code SofÃ­a} en lugar de {@code Sofia}.</p>
     *
     * <p>{@code forceEncoding} se establece a {@code true} para forzar UTF-8
     * incluso si el navegador envia una codificacion diferente en la cabecera
     * {@code Content-Type}.</p>
     *
     * <p>Este filtro se aplica antes que cualquier otro, garantizando que Spring MVC
     * lea correctamente el cuerpo de todas las peticiones.</p>
     *
     * @return array con el {@link CharacterEncodingFilter} configurado en UTF-8
     */
    @Override
    
    //IA: con ayuda de la IA (Claude)
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return new Filter[] { filter };
    }
	
	
}
