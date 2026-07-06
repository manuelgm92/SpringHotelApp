package com.springhotel.dao;

import java.util.List;

import com.springhotel.entity.Usuario;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad
 * {@link Usuario}.
 *
 * <p>
 * Todas las operaciones CRUD (Create, Read, Update, Delete) sobre la tabla
 * {@code usuarios} se declaran aqui. La implementacion concreta se encuentra en
 * {@link UsuarioDAOImpl}.
 * </p>
 *
 * <p>
 * Usar una interfaz desacopla el controlador de la implementacion, facilitando
 * el mantenimiento y los tests.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @see UsuarioDAOImpl
 */
public interface UsuarioDAO {

	/**
	 * Obtiene un usuario a partir de su nombre de usuario.
	 *
	 * @param nombreUsuario el nombre de usuario a buscar
	 * @return el {@link Usuario} que coincide con el nombre indicado, o
	 *         {@code null} si no se encuentra ningun usuario
	 */
	Usuario obtenerUsuarioPorNombre(String nombreUsuario);

	/**
     * Obtiene todos los usuarios almacenados en la base de datos.
     *
     * @return una {@link List} con todas las entidades {@link Usuario};
     *         una lista vacia si no existe ningun usuario
     */
	List<Usuario> mostrarTodosLosUsuarios();

	/**
     * Obtiene un usuario a partir de su identificador unico.
     *
     * @param id el identificador unico del usuario
     * @return el {@link Usuario} con el identificador indicado.
     */
	Usuario mostrarUsuarioPorId(int id);
	
	/**
     * Persiste un nuevo usuario en la base de datos.
     *
     * @param usuario la entidad {@link Usuario} a crear;
     *                no debe ser {@code null}
     */
	void altaUsuario(Usuario usuario);
	
	/**
     * Actualiza los datos de un usuario existente en la base de datos.
     *
     * @param usuarioActualizado la entidad {@link Usuario} con los datos
     * actualizados; no debe ser {@code null} y debe tener un identificador
     *  valido
     */
	void actualizaUsuario(Usuario usuarioActualizado);
	
	/**
     * Actualiza los datos del usuario menos la contrasena
     *  de un usuario existente en la base de datos.
     * 
     * <p>
     * Esta operacion solo la podra realizar un usuario con perfil SUPERVISOR
     * </p>
     * 
     * @param usuarioActualizado la entidad {@link Usuario} con los datos
     * actualizados; no debe ser {@code null} y debe tener un identificador
     *  valido
     */
	void actualizaUsuarioMenosPassword(Usuario usuarioActualizado);
	
	/**
     * Elimina un usuario de la base de datos por su identificador unico.
     *
     * @param id el identificador unico del usuario a eliminar
     */
	void eliminaUsuario(int id);

}
