package com.springhotel.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.springhotel.entity.Usuario;
import com.springhotel.exception.AccesoNoPermitidoException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Servicio encargado de gestionar la autorización y comprobación de permisos
 * basados en la información almacenada en la sesión del usuario autenticado.
 *
 * <p>
 * Este servicio trabaja directamente con el atributo de sesión
 * {@code "usuarioSesion"}, que contiene un objeto {@link Usuario} completo. A
 * partir de dicho objeto se extraen el nombre de usuario y el perfil para
 * realizar validaciones de acceso.
 * </p>
 *
 * <p>
 * Todos los métodos son null-safe: si la sesión es {@code null} o no contiene
 * un usuario válido, se considera que no hay autenticación.
 * </p>
 *
 * @author Ana Laura
 * @version 1.1
 */
//IA: Servicio generado con ayuda de IA(Copilot) para centralizar lógica de autorización y evitar duplicación en controladores.
@Service
public class AutorizacionService {

	/** Nombre del atributo de sesión donde se almacena el objeto Usuario. */
	private static final String ATTR_USUARIO_SESION = "usuarioSesion";

	/**
	 * Enum que representa los perfiles válidos dentro de la aplicación. Deben
	 * coincidir con los valores almacenados en la base de datos.
	 */
	public enum Perfil {
		RECEPCIONISTA, SUPERVISOR
	}

	/**
	 * Comprueba si el usuario autenticado tiene perfil {@code RECEPCIONISTA}.
	 *
	 * @param session la sesión HTTP actual; puede ser {@code null}
	 * @return {@code true} si el usuario tiene perfil RECEPCIONISTA, {@code false}
	 *         si no hay sesión o el perfil no coincide
	 */
	public boolean esRecepcionista(HttpSession session) {
		Usuario u = obtenerUsuarioSesion(session);
		return u != null && Perfil.RECEPCIONISTA.name().equalsIgnoreCase(u.getPerfil());
	}

	/**
	 * Comprueba si el usuario autenticado tiene perfil {@code SUPERVISOR}.
	 *
	 * @param session la sesión HTTP actual; puede ser {@code null}
	 * @return {@code true} si el usuario tiene perfil SUPERVISOR, {@code false} si
	 *         no hay sesión o el perfil no coincide
	 */
	public boolean esSupervisor(HttpSession session) {
		Usuario u = obtenerUsuarioSesion(session);
		return u != null && Perfil.SUPERVISOR.name().equalsIgnoreCase(u.getPerfil());
	}

	/**
	 * Requiere que el usuario autenticado tenga perfil {@code RECEPCIONISTA}.
	 *
	 * @param session la sesión HTTP actual; puede ser {@code null}
	 * @throws AccesoNoPermitidoException si el usuario no está autenticado o no
	 *                                    tiene el perfil requerido
	 */
	public void requiereRecepcionista(HttpSession session) throws AccesoNoPermitidoException {
		if (!esRecepcionista(session)) {
			throw new AccesoNoPermitidoException(
					"Acción no permitida. Solo usuarios con perfil RECEPCIONISTA pueden realizar esta operación.");
		}
	}

	/**
	 * Requiere que exista un usuario autenticado en la sesión.
	 *
	 * @param session la sesión HTTP actual; puede ser {@code null}
	 * @throws AccesoNoPermitidoException si no existe un usuario autenticado
	 */
	public void requiereAutenticacion(HttpSession session) throws AccesoNoPermitidoException {
		if (obtenerUsuarioSesion(session) == null) {
			throw new AccesoNoPermitidoException("Debe iniciar sesión para acceder a esta funcionalidad.");
		}
	}

	/**
	 * Obtiene el objeto {@link Usuario} almacenado en la sesión.
	 *
	 * @param session la sesión HTTP actual; puede ser {@code null}
	 * @return el objeto Usuario autenticado, o {@code null} si no existe
	 */
	private Usuario obtenerUsuarioSesion(HttpSession session) {
		if (session == null) {
			return null;
		}
		Object obj = session.getAttribute(ATTR_USUARIO_SESION);
		return (obj instanceof Usuario) ? (Usuario) obj : null;
	}

	/**
	 * Obtiene el nombre de usuario almacenado en la sesión.
	 *
	 * @param session la sesión HTTP actual; puede ser {@code null}
	 * @return el nombre de usuario, o {@code null} si no hay sesión o usuario
	 */
	public String obtenerUsuario(HttpSession session) {
		Usuario u = obtenerUsuarioSesion(session);
		return u != null ? u.getNombreUsuario() : null;
	}
}
