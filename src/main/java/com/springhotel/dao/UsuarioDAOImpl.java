package com.springhotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springhotel.entity.Usuario;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * Implementacion de la interfaz {@link UsuarioDAO} que gestiona el acceso a la
 * base de datos para la entidad {@link Usuario} mediante Hibernate.
 * <p>
 * Esta clase actua como capa de persistencia dentro de la arquitectura en capas
 * del proyecto. Todas las operaciones de consulta, insercion, actualizacion y
 * eliminacion de usuarios se realizan aqui.
 * </p>
 * <p>
 * En caso de error de base de datos se lanza {@link ErrorBaseDatosException}, y
 * si no se encuentra una entidad solicitada se lanza
 * {@link EntidadNoEncontradaException}.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @see UsuarioDAO
 * @see Usuario
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Repository
public class UsuarioDAOImpl extends AbstractDAOImpl<Usuario> implements UsuarioDAO {

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param sessionFactory fábrica de sesiones de Hibernate
	 */
    @Autowired
    public UsuarioDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Usuario.class);
    }
    /**
     * Busca y devuelve un usuario a partir de su nombre de usuario.
     * <p>
     * Ejecuta una consulta HQL filtrando por el campo {@code nombreUsuario} de la
     * entidad {@link Usuario}. Si el nombre no corresponde a ningun registro,
     * devuelve {@code null}.
     * </p>
     *
     * @param nombreUsuario el nombre de usuario con el que se realizara la
     *                      busqueda; no debe ser {@code null} ni vacio
     * @return el {@link Usuario} cuyo nombre coincide con el parametro, o
     *         {@code null} si no existe ningun usuario con ese nombre
     * @throws ErrorBaseDatosException si ocurre un error de Hibernate al ejecutar
     *                                 la consulta
     */
    @Override
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
    	logger.debug("DAO: buscando usuario por nombre: " + nombreUsuario);
        try {
            return getSession()
                    .createQuery("FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", Usuario.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .uniqueResult();
        } catch (HibernateException e) {
            logger.error("DAO: error al buscar usuario por nombre: " + e.getMessage());
            throw new ErrorBaseDatosException("Error al consultar el usuario '" + nombreUsuario + "'.", e);
        }
    }

    /**
     * Busca y devuelve un usuario a partir de su identificador unico.
     * <p>
     * Utiliza el metodo {@code session.get()} de Hibernate para localizar la
     * entidad por clave primaria. Si el identificador no corresponde a ningun
     * registro existente, se lanza {@link EntidadNoEncontradaException}.
     * </p>
     *
     * @param id el identificador unico (clave primaria) del usuario a buscar
     * @return el {@link Usuario} cuyo identificador coincide con el parametro
     * @throws EntidadNoEncontradaException si no existe ningun usuario con el
     *                                      identificador indicado
     * @throws ErrorBaseDatosException      si ocurre un error de Hibernate al
     *                                      ejecutar la consulta
     */
    @Override
    public Usuario mostrarUsuarioPorId(int id) {
    	logger.debug("DAO: buscando usuario con id: " + id);
    	return findById(id);
    }

    /**
     * Recupera y devuelve todos los usuarios almacenados en la base de datos.
     * <p>
     * Ejecuta una consulta HQL sin filtros para obtener la lista completa de
     * entidades {@link Usuario}. Si no existe ningun usuario registrado, devuelve
     * una lista vacia.
     * </p>
     *
     * @return una {@link List} con todos los {@link Usuario} existentes; lista
     *         vacia si no hay ningun registro
     * @throws ErrorBaseDatosException si ocurre un error de Hibernate al ejecutar
     *                                 la consulta
     */
    @Override
    public List<Usuario> mostrarTodosLosUsuarios() {
    	logger.debug("DAO: obteniendo todos los usuarios.");
    	return findAll("FROM Usuario");
    }

    /**
     * Persiste un nuevo usuario en la base de datos.
     * <p>
     * Utiliza el metodo {@code session.persist()} de Hibernate para insertar la
     * entidad recibida como un nuevo registro. El identificador del usuario sera
     * asignado automaticamente por la base de datos.
     * </p>
     *
     * @param nuevoUsuario la entidad {@link Usuario} que se desea crear;
     *                     no debe ser {@code null}
     * @throws ErrorBaseDatosException si ocurre un error de Hibernate al intentar
     *                                 guardar el usuario
     */
    @Override
    public void altaUsuario(Usuario nuevoUsuario) {
    	logger.debug("DAO: guardando nuevo usuario: " + nuevoUsuario.getNombreUsuario());
    	persistEntity(nuevoUsuario);
    	logger.info("DAO: usuario guardado correctamente.");
    }

    /**
     * Actualiza los datos de un usuario ya existente en la base de datos.
     * <p>
     * Recupera la entidad gestionada por Hibernate mediante {@code session.get()}
     * y actualiza sus campos directamente. Hibernate detecta los cambios
     * automaticamente al finalizar la transaccion (dirty checking), por lo que
     * no es necesario llamar a ningun metodo de actualizacion explicito.
     * </p>
     *
     * @param usuarioActualizado la entidad {@link Usuario} con los nuevos datos;
     *                           no debe ser {@code null} y debe tener un
     *                           identificador valido
     * @throws EntidadNoEncontradaException si no existe ningun usuario con el
     *                                      identificador indicado
     * @throws ErrorBaseDatosException      si ocurre un error de Hibernate al
     *                                      intentar actualizar el usuario
     */
    @Override
    public void actualizaUsuario(Usuario usuarioActualizado) {
    	logger.debug("DAO: actualizando usuario con id: " + usuarioActualizado.getId());

    	Usuario usuario = findById(usuarioActualizado.getId());

    	usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
    	usuario.setNombreCompleto(usuarioActualizado.getNombreCompleto());
    	usuario.setPasswordUsuario(usuarioActualizado.getPasswordUsuario());
    	usuario.setPerfil(usuarioActualizado.getPerfil());
    	logger.info("DAO: usuario con id " + usuarioActualizado.getId() + " actualizado correctamente.");
    }

    /**
     * Actualiza los daots de un usuario ya existente en la base de datos.
     * Menos la contrasena.
     * 
     * <p>
     * Delega la busqueda del usuario en {@link #mostrarUsuarioPorId(int)} y
     * modifica sus campos. Hibernate sincroniza el
     * cambio automaticamente al finalizar la transaccion (dirty checking).
     * </p>
     * 
     * <p>
     * Esta operacion solo la podra realizar un usuario con perfil SUPERVISOR
     * </p>
     *
     * @param usuarioActualizado la entidad {@link Usuario} que contiene el nuevo
     *                           perfil; debe tener un identificador valido
     * @throws EntidadNoEncontradaException si no existe ningun usuario con el
     *                                      identificador indicado
     * @throws ErrorBaseDatosException      si ocurre un error de Hibernate al
     *                                      intentar actualizar el usuario
     */
    @Override
    public void actualizaUsuarioMenosPassword(Usuario usuarioActualizado) {
    	logger.debug("DAO: actualizando datos (sin contrase\u00f1a) de usuario con id: " + usuarioActualizado.getId());

    	Usuario usuario = mostrarUsuarioPorId(usuarioActualizado.getId());
    	usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
    	usuario.setPerfil(usuarioActualizado.getPerfil());
    	usuario.setNombreCompleto(usuarioActualizado.getNombreCompleto());
    }

    /**
     * Elimina un usuario de la base de datos a partir de su identificador unico.
     * <p>
     * Primero verifica que el usuario existe mediante {@code session.get()}.
     * Si no se encuentra el registro, lanza {@link EntidadNoEncontradaException}
     * antes de intentar la eliminacion. Si el usuario existe, se elimina
     * mediante {@code session.remove()}.
     * </p>
     *
     * @param id el identificador unico del usuario que se desea eliminar
     * @throws EntidadNoEncontradaException si no existe ningun usuario con el
     *                                      identificador indicado
     * @throws ErrorBaseDatosException      si ocurre un error de Hibernate al
     *                                      intentar eliminar el usuario
     */
    @Override
    public void eliminaUsuario(int id) {
    	logger.debug("DAO: eliminando usuario con id: " + id);
    	deleteById(id);
    	logger.info("DAO: usuario con id " + id + " eliminado correctamente.");
    }
}