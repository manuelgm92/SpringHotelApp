package com.springhotel.service;

import java.util.List;

import com.springhotel.dto.UsuarioDTO;
import com.springhotel.entity.Usuario;

/**
 * Interfaz de la capa de servicio para la gestion de entidades {@link Usuario}.
 * <p>
 * Define el contrato de las operaciones de negocio relacionadas con los usuarios
 * del hotel. Actua como intermediaria entre la capa de presentacion (controladores)
 * y la capa de acceso a datos ({@link com.springhotel.dao.UsuarioDAO}).
 * </p>
 * <p>
 * Las implementaciones de esta interfaz son responsables de aplicar
 * la logica de negocio antes de delegar las operaciones al DAO correspondiente.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @see com.springhotel.dao.UsuarioDAO
 * @see Usuario
 */
public interface UsuarioService {
	
	/**
     * Autentica a un usuario comprobando su nombre de usuario y contrasena.
     *
     * @param nombreUsuario   el nombre de usuario introducido en el login
     * @param passwordUsuario la contrasena introducida en el login
     * @return el {@link Usuario} autenticado si las credenciales son correctas
     * @throws CredencialesInvalidasException si los campos estan vacios o las
     *                                        credenciales no son correctas
     */
	Usuario login(String nombreUsuario, String passwordUsuario);
	
	/**
     * Recupera todos los usuarios registrados en el sistema.
     *
     * @return una {@link List} con todos los {@link Usuario} existentes;
     *         lista vacia si no hay ningun registro
     */
    List<Usuario> mostrarTodosLosUsuarios();
    List<UsuarioDTO> mostrarTodosLosUsuariosDTO();
    
    /**
     * Recupera un usuario a partir de su identificador unico.
     *
     * @param id el identificador unico del usuario a buscar
     * @return el {@link Usuario} cuyo identificador coincide con el parametro
     * @throws EntidadNoEncontradaException si no existe ningun usuario con ese id
     */
    Usuario mostrarUsuarioPorId(int id);
    UsuarioDTO mostrarUsuarioDTOPorId(int id);
    
    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario la entidad {@link Usuario} que se desea registrar;
     *                no debe ser {@code null}
     * @throws ArgumentoNoValidoException si el nombre de usuario es nulo, vacio
     *                                    o ya existe un usuario con ese nombre
     */
    void altaUsuario(Usuario usuario);
    void altaUsuario(UsuarioDTO nuevoUsuario);
    
    /**
     * Actualiza los datos de un usuario existente en el sistema.
     *
     * @param usuarioActualizado la entidad {@link Usuario} con los datos
     *                           modificados; no debe ser {@code null} y debe
     *                           tener un identificador valido
     * @throws ArgumentoNoValidoException   si el nombre de usuario es nulo o vacio
     * @throws EntidadNoEncontradaException si no existe ningun usuario con ese id
     */
    void actualizaUsuario(Usuario usuarioActualizado);
    void actualizaUsuario(UsuarioDTO usuarioActualizado);
    
    /**
     * Actualiza unicamente el perfil de un usuario existente en el sistema.
     *
     * <p>
     * Esta operacion solo la podra realizar un usuario con perfil SUPERVISOR
     * </p>
     *
     * @param usuarioActualizado la entidad {@link Usuario} con el nuevo perfil;
     *                           no debe ser {@code null} y debe tener un
     *                           identificador valido
     * @throws EntidadNoEncontradaException si no existe ningun usuario con ese id
     */
    void actualizaUsuarioMenosPassword(Usuario usuarioActualizado);
    void actualizaUsuarioMenosPassword(UsuarioDTO usuarioActualizado);
    
    /**
     * Elimina un usuario del sistema a partir de su identificador unico.
     *
     * @param id el identificador unico del usuario que se desea eliminar
     * @throws EntidadNoEncontradaException si no existe ningun usuario con ese id
     */
    void eliminaUsuario(int id);
}
