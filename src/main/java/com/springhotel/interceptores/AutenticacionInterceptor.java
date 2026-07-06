package com.springhotel.interceptores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor de autenticación para proteger las rutas de la aplicación.
 *
 * <p>
 * Comprueba en cada petición si existe una sesión HTTP con el atributo
 * {@code "usuarioSesion"}. Si no existe, redirige al formulario de login y
 * detiene la cadena de ejecución del controlador.
 * </p>
 *
 * <p>
 * Con esto se evita que un usuario acceda directamente a URLs protegidas
 * escribiendo la dirección en el navegador.
 * </p>
 *
 * @author Ana Laura
 * @version 1.0
 */
public class AutenticacionInterceptor implements HandlerInterceptor {
	/**
     * Intercepta la petición antes de que alcance al controlador.
     *
     * @param request  la petición HTTP entrante
     * @param response la respuesta HTTP que se puede modificar antes de devolverla
     * @param handler  el handler elegido para procesar la petición
     * @return {@code true} si el usuario está autenticado y puede continuar;
     *         {@code false} si no lo está, tras redirigir al formulario de login
     * @throws java.io.IOException si ocurre un error de E/S al ejecutar la
     *                             redirección con {@code response.sendRedirect}
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// Intenta obtener la sesion existente. El false significa "no crees una sesion
		// nueva si no existe". Si no hay sesion, devuelve null.
		HttpSession session = request.getSession(false);
		
		// Comprobacion de autenticacion: se verifica que existe una sesion activa
		// y que en esa sesion hay un usuario logeado.
		if (session != null && session.getAttribute("usuarioSesion") != null) {
			return true;
		}
		
		// Redireccion a login
		response.sendRedirect(request.getContextPath() + "/login");
		return false;
	}
}
