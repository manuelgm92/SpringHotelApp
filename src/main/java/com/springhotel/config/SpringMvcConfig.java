package com.springhotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import com.springhotel.interceptores.AutenticacionInterceptor;
/**
 * Configuracion de la capa web de SpringHotelApp.
 *
 * <p>Esta clase sustituye al spring-mvc-servlet.xml: toda la configuracion de Spring MVC
 * en una clase Java. Aqui se configura lo relacionado con Spring MVC: como se 
 * resuelven las vistas, donde estan los recursos estaticos, que paquetes escanear, etc.</p>
 *
 * <p>Anotaciones que lleva esta clase:</p>
 * <ul>
 *   <li>{@code @Configuration} - indica a Spring que esta clase contiene
 *       definiciones de beans, como si fuera un XML de configuracion.</li>
 *   <li>{@code @EnableWebMvc} - activa el soporte completo de Spring MVC
 *       (controladores, binding, validacion, conversion de tipos...).</li>
 *   <li>{@code @ComponentScan} - le dice a Spring que busque componentes
 *       anotados ({@code @Controller}, {@code @Service}, etc.) dentro
 *       del paquete {@code com.springhotel}.</li>
 *   <li>{@code @PropertySource} - carga el archivo {@code database.properties}
 *       del classpath para poder inyectar sus valores con {@code @Value}.</li>
 * </ul>
 *
 * @author  Ana Laura y Manuel
 * @version 1.0
 * @see AppInitializer
 * @see HibernateConfig
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Configuration
@EnableWebMvc
@ComponentScan("com.springhotel")
@PropertySource("classpath:database.properties")
public class SpringMvcConfig implements WebMvcConfigurer {
	
	/**
     * Configura el ViewResolver de la aplicacion.
     *
     * <p>El ViewResolver es el componente que traduce el nombre logico de una
     * vista en la ruta real del archivo JSP. Por ejemplo, si un controlador
     * devuelve la cadena {@code "login"}, este resolver la convierte en:</p>
     *
     * <p>{@code /WEB-INF/views/login.jsp}</p>
     *
     * <p>Los JSP se guardan dentro de {@code /WEB-INF/} para que no sean
     * accesibles directamente desde el navegador, solo a traves de un controlador.</p>
     *
     * @return instancia de {@link InternalResourceViewResolver} lista para usar.
     */
	@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
	
	/**
     * Registra los manejadores de recursos estaticos de la aplicacion.
     *
     * <p>Por defecto, cuando se usa {@code @EnableWebMvc}, el DispatcherServlet
     * intercepta TODAS las peticiones, incluidas las de archivos CSS, JS e imagenes,
     * e intenta buscar un controlador para ellas, lo que provoca un error 404.</p>
     *
     * <p>Este metodo soluciona ese problema indicando a Spring que las peticiones
     * a {@code /css/**} deben servirse directamente desde la carpeta fisica
     * {@code /css/} de la aplicacion web, sin pasar por ningun controlador.</p>
     *
     * <p>Si en el futuro se añaden carpetas de JS o imagenes, habria que
     * registrar aqui sus manejadores de la misma forma:</p>
     * <ul>
     *   <li>{@code /js/**} → {@code /js/}</li>
     *   <li>{@code /img/**} → {@code /img/}</li>
     * </ul>
     *
     * @param registry objeto proporcionado por Spring donde se registran
     *                 los manejadores de recursos estaticos.
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esto mapea la URL /css/** a la carpeta física src/main/webapp/css/
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/");
    }
	/**
	 * Registra el interceptor de autenticación para todas las rutas de la
	 * aplicación.
	 *
	 * <p>
	 * Se aplica a {@code /**} para proteger todas las URLs y se excluye
	 * {@code /login} y los recursos estáticos {@code /css/**} para que el
	 * formulario de login y los estilos funcionen correctamente.
	 * </p>
	 *
	 * @param registry registro de interceptores de Spring MVC
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new AutenticacionInterceptor())
	            .addPathPatterns("/**")
	            .excludePathPatterns("/login", "/css/**");
	}
}

