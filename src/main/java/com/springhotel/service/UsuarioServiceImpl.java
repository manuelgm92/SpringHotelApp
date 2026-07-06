package com.springhotel.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springhotel.dao.UsuarioDAO;
import com.springhotel.dto.UsuarioDTO;
import com.springhotel.entity.Usuario;
import com.springhotel.exception.ArgumentoNoValidoException;
import com.springhotel.exception.CredencialesInvalidasException;
import com.springhotel.mapper.UsuarioMapper;
import com.springhotel.utils.BCryptUtil;

/**
 * Implementacion de la interfaz {@link UsuarioService} que contiene la logica
 * de negocio relacionada con la gestion de usuarios del hotel.
 * <p>
 * Actua como capa intermedia entre los controladores y la capa de acceso a
 * datos ({@link UsuarioDAO}). Se encarga de validar los datos de entrada antes
 * de delegar las operaciones al DAO correspondiente.
 * </p>
 * <p>
 * Todas las operaciones estan marcadas con {@link Transactional} a nivel de
 * clase, por lo que Spring gestiona automaticamente el ciclo de vida de las
 * transacciones de base de datos.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * 
 * @see UsuarioService
 * @see UsuarioDAO
 * @see Usuario
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	/**
	 * Logger del sistema para la clase {@link UsuarioServiceImpl}. Se utiliza para
	 * registrar eventos, flujos de datos y excepciones en los diferentes niveles de
	 * depuración (DEBUG, INFO, WARN, ERROR).
	 */
	private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class);

	/**
	 * Objeto de acceso a datos para usuarios, inyectado automáticamente por Spring
	 * a través del constructor.
	 */
	private final UsuarioDAO usuarioDAO;

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param usuarioDAO DAO de usuarios inyectado por Spring
	 */
	@Autowired
	public UsuarioServiceImpl(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	/**
	 * Autentica a un usuario comprobando su nombre de usuario y contrasena.
	 * <p>
	 * Primero valida que ninguno de los dos campos sea nulo o este vacio. A
	 * continuacion busca al usuario en la base de datos por su nombre, recupera el
	 * hash almacenado y verifica la contrasena mediante
	 * {@link com.springhotel.utils.BCryptUtil#verificarPassword}. Si no existe o la
	 * contrasena no coincide, lanza una excepcion de credenciales invalidas.
	 * </p>
	 *
	 * @param nombreUsuario   el nombre de usuario introducido en el login; no debe
	 *                        ser {@code null} ni vacio
	 * @param passwordUsuario la contrasena introducida en el login; no debe ser
	 *                        {@code null} ni vacia
	 * @return el {@link Usuario} autenticado si las credenciales son correctas
	 * @throws CredencialesInvalidasException si alguno de los campos esta vacio o
	 *                                        las credenciales no son correctas
	 */
	@Override
	public Usuario login(String nombreUsuario, String passwordUsuario) {

		logger.debug("Service: intentando login para usuario: " + nombreUsuario);

		if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || passwordUsuario == null
				|| passwordUsuario.trim().isEmpty()) {

			logger.warn("Service: usuario o contraseña vacíos.");
			throw new CredencialesInvalidasException("El usuario y la contraseña son obligatorios.");
		}

		Usuario usuarioLogin = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario.trim());

		if (usuarioLogin == null || !usuarioLogin.getNombreUsuario().equals(nombreUsuario.trim())
				|| !BCryptUtil.verificarPassword(passwordUsuario, usuarioLogin.getPasswordUsuario())) {
			logger.warn("Service: credenciales inválidas para usuario: " + nombreUsuario);
			throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
		}

		logger.info("Service: login correcto para usuario: " + nombreUsuario);

		return usuarioLogin;
	}

	/**
	 * Recupera todos los usuarios registrados en el sistema.
	 * <p>
	 * Delega directamente la consulta al DAO sin aplicar logica de negocio
	 * adicional.
	 * </p>
	 *
	 * @return una {@link List} con todos los {@link Usuario} existentes; lista
	 *         vacia si no hay ningun registro
	 */
	@Override
	public List<Usuario> mostrarTodosLosUsuarios() {
		logger.debug("Service: obteniendo lista de todos los usuarios.");
		return usuarioDAO.mostrarTodosLosUsuarios();
	}

	@Override
	public List<UsuarioDTO> mostrarTodosLosUsuariosDTO() {
		logger.debug("Service: obteniendo lista DTO de todos los usuarios.");
		return UsuarioMapper.toDTOList(usuarioDAO.mostrarTodosLosUsuarios());
	}

	/**
	 * Recupera un usuario a partir de su identificador unico.
	 * <p>
	 * Delega directamente la busqueda al DAO. Si no existe un usuario con el
	 * identificador indicado, el DAO lanzara
	 * {@link com.springhotel.exception.EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id el identificador unico del usuario a buscar
	 * @return el {@link Usuario} cuyo identificador coincide con el parametro
	 */
	@Override
	public Usuario mostrarUsuarioPorId(int id) {
		logger.debug("Service: buscando usuario con id: " + id);
		return usuarioDAO.mostrarUsuarioPorId(id);
	}

	@Override
	public UsuarioDTO mostrarUsuarioDTOPorId(int id) {
		logger.debug("Service: buscando usuario DTO con id: " + id);
		return UsuarioMapper.toDTO(usuarioDAO.mostrarUsuarioPorId(id));
	}

	/**
	 * Registra un nuevo usuario en el sistema.
	 * <p>
	 * Antes de persistir el usuario aplica las siguientes validaciones:
	 * <ul>
	 * <li>El nombre de usuario no puede ser nulo ni vacio.</li>
	 * <li>No puede existir ya un usuario con el mismo nombre en la base de
	 * datos.</li>
	 * </ul>
	 * Si las validaciones son correctas, la contrasena se cifra con BCrypt antes de
	 * persistir el usuario. Si alguna validacion falla, se lanza
	 * {@link ArgumentoNoValidoException}.
	 * </p>
	 *
	 * @param nuevoUsuario la entidad {@link Usuario} que se desea registrar; no
	 *                     debe ser {@code null}
	 * @throws ArgumentoNoValidoException si el nombre de usuario es nulo, vacio o
	 *                                    ya existe un usuario con ese nombre
	 */
	@Override
	public void altaUsuario(Usuario nuevoUsuario) {

		logger.debug("Service: dando de alta usuario: " + nuevoUsuario.getNombreUsuario());

		if (nuevoUsuario.getNombreUsuario() == null || nuevoUsuario.getNombreUsuario().trim().isEmpty()) {
			logger.warn("Service: nombre de usuario vacío en alta.");
			throw new ArgumentoNoValidoException("El nombre de usuario es obligatorio.");
		}

		if (usuarioDAO.obtenerUsuarioPorNombre(nuevoUsuario.getNombreUsuario().trim()) != null) {
			logger.warn("Service: ya existe un usuario con el nombre: " + nuevoUsuario.getNombreUsuario());
			throw new ArgumentoNoValidoException(
					"Ya existe un usuario con el nombre '" + nuevoUsuario.getNombreUsuario() + "'.");
		}

		// Hashear contraseña antes de persistir
		nuevoUsuario.setPasswordUsuario(BCryptUtil.hashPassword(nuevoUsuario.getPasswordUsuario()));

		usuarioDAO.altaUsuario(nuevoUsuario);
		logger.info("Service: usuario dado de alta correctamente.");
	}

	@Override
	public void altaUsuario(UsuarioDTO nuevoUsuarioDTO) {
		if (nuevoUsuarioDTO == null) {
			logger.warn("Service: intento de alta DTO con usuario nulo.");
			throw new ArgumentoNoValidoException("El usuario no puede ser nulo.");
		}
		Usuario usuario = UsuarioMapper.toEntity(nuevoUsuarioDTO);
		altaUsuario(usuario);
	}

	/**
	 * Actualiza los datos de un usuario existente en el sistema.
	 * <p>
	 * Verifica que el nuevo nombre de usuario no este siendo utilizado por otro
	 * registro diferente al que se esta editando. Si la validacion es correcta, la
	 * contrasena se cifra con BCrypt antes de delegar la actualizacion al DAO.
	 * </p>
	 *
	 * @param usuarioActualizado la entidad {@link Usuario} con los datos
	 *                           modificados; no debe ser {@code null} y debe tener
	 *                           un identificador valido
	 */
	@Override
	public void actualizaUsuario(Usuario usuarioActualizado) {

		logger.debug("Service: actualizando usuario con id: " + usuarioActualizado.getId());
		Usuario usuarioAEditar = usuarioDAO.obtenerUsuarioPorNombre(usuarioActualizado.getNombreUsuario().trim());

		if (usuarioAEditar != null && usuarioAEditar.getId() != usuarioActualizado.getId()) {
			logger.warn("Service: nombre duplicado al actualizar usuario: " + usuarioActualizado.getNombreUsuario());
			throw new ArgumentoNoValidoException(
					"Ya existe un usuario con el nombre '" + usuarioActualizado.getNombreUsuario() + "'.");
		}

		// Hashear la nueva contraseña antes de actualizar
		usuarioActualizado.setPasswordUsuario(BCryptUtil.hashPassword(usuarioActualizado.getPasswordUsuario()));

		usuarioDAO.actualizaUsuario(usuarioActualizado);
		logger.info("Service: usuario actualizado correctamente.");

	}

	@Override
	public void actualizaUsuario(UsuarioDTO usuarioActualizadoDTO) {
		if (usuarioActualizadoDTO == null) {
			logger.warn("Service: intento de actualización DTO con usuario nulo.");
			throw new ArgumentoNoValidoException("El usuario no puede ser nulo.");
		}
		Usuario usuarioActualizado = UsuarioMapper.toEntity(usuarioActualizadoDTO);
		actualizaUsuario(usuarioActualizado);
	}

	/**
	 * Actualiza los datos de un usuario existente (excepto la contraseña) en el
	 * sistema.
	 * 
	 * <p>
	 * La contrasenia unicamente puede ser modificada por su usuario.
	 * </p>
	 * 
	 * <p>
	 * Antes de realizar la actualizacion, verifica que el nuevo nombre de usuario
	 * no este siendo utilizado por otro registro diferente al que se esta editando.
	 * </p>
	 * <p>
	 * Esta operacion esta restringida a usuarios con perfil SUPERVISOR.
	 * </p>
	 *
	 * @param usuarioActualizado la entidad {@link Usuario} con los datos
	 *                           modificados; debe incluir un ID valido y el nombre
	 *                           de usuario.
	 * @throws ArgumentoNoValidoException si el nombre de usuario ya existe en otro
	 *                                    registro.
	 * @see UsuarioDAO#actualizaUsuarioMenosPassword(Usuario)
	 */
	@Override
	public void actualizaUsuarioMenosPassword(Usuario usuarioActualizado) {

		logger.debug("Service: actualizando datos (sin contraseña) de usuario con id: " + usuarioActualizado.getId());
		Usuario usuarioAEditar = usuarioDAO.obtenerUsuarioPorNombre(usuarioActualizado.getNombreUsuario().trim());

		if (usuarioAEditar != null && usuarioAEditar.getId() != usuarioActualizado.getId()) {
			logger.warn("Service: nombre duplicado al actualizar usuario: " + usuarioActualizado.getNombreUsuario());
			throw new ArgumentoNoValidoException(
					"Ya existe un usuario con el nombre '" + usuarioActualizado.getNombreUsuario() + "'.");
		}
		usuarioDAO.actualizaUsuarioMenosPassword(usuarioActualizado);
		logger.info("Service: datos de usuario actualizados correctamente.");

	}

	@Override
	public void actualizaUsuarioMenosPassword(UsuarioDTO usuarioActualizadoDTO) {
		if (usuarioActualizadoDTO == null) {
			logger.warn("Service: intento de actualización DTO sin contraseña con usuario nulo.");
			throw new ArgumentoNoValidoException("El usuario no puede ser nulo.");
		}
		Usuario usuarioActualizado = UsuarioMapper.toEntity(usuarioActualizadoDTO);
		actualizaUsuarioMenosPassword(usuarioActualizado);
	}

	/**
	 * Elimina un usuario del sistema a partir de su identificador unico.
	 * <p>
	 * Delega directamente la eliminacion al DAO. Si no existe un usuario con el
	 * identificador indicado, el DAO lanzara
	 * {@link com.springhotel.exception.EntidadNoEncontradaException}.
	 * </p>
	 *
	 * @param id el identificador unico del usuario que se desea eliminar
	 */
	@Override
	public void eliminaUsuario(int id) {
		logger.debug("Service: eliminando usuario con id: " + id);
		usuarioDAO.eliminaUsuario(id);
		logger.info("Service: usuario con id " + id + " eliminado correctamente.");
	}

}
