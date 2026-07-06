package com.springhotel.dao;

import java.util.List;


import com.springhotel.entity.Huesped;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad
 * {@link Huesped}.
 *
 * <p>
 * Todas las operaciones CRUD (Create, Read, Update, Delete) sobre la tabla
 * {@code huespedes} se declaran aqui. La implementacion concreta se
 * encuentra en {@link HuespedDAOImpl}.
 * </p>
 *
 * <p>
 * Usar una interfaz desacopla el controlador de la implementacion, facilitando
 * el mantenimiento y los tests.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @see HuespedDAOImpl
 */
//IA: javadoc generado con ayuda de IA(Claude)
public interface HuespedDAO {

    /**
     * Devuelve todos los huespedes registrados en la base de datos.
     *
     * @return lista de huespedes; vacia si no hay ninguno.
     * @throws ErrorBaseDatosException si ocurre un error al acceder a la BBDD.
     */
    List<Huesped> mostrarTodosLosHuespedes() throws ErrorBaseDatosException;

    /**
     * Busca y devuelve un huesped por su identificador.
     *
     * @param id identificador del huesped.
     * @return el huesped encontrado.
     * @throws EntidadNoEncontradaException si no existe ningun huesped con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al acceder a la BBDD.
     */
    Huesped mostrarHuespedPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;

    /**
     * Persiste un nuevo huesped en la base de datos.
     *
     * @param huesped objeto huesped a guardar; el id debe ser {@code null}.
     * @throws ErrorBaseDatosException si ocurre un error al guardar.
     */
    void altaHuesped(Huesped huesped) throws ErrorBaseDatosException;

    /**
     * Actualiza los datos de un huesped existente.
     *
     * @param huesped objeto huesped con los datos actualizados; el id debe
     *                corresponder a un registro existente.
     * @throws EntidadNoEncontradaException si no existe el huesped a actualizar.
     * @throws ErrorBaseDatosException      si ocurre un error al actualizar.
     */
    void actualizaHuesped(Huesped huesped) throws EntidadNoEncontradaException, ErrorBaseDatosException;

    /**
     * Elimina el huesped con el identificador indicado.
     *
     * @param id identificador del huesped a eliminar.
     * @throws EntidadNoEncontradaException si no existe ningun huesped con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al eliminar.
     */
    void eliminaHuesped(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;
    
    /**
     * Busca huespedes por nombre completo (nombre + apellidos).
     * Si el parametro viene vacio o null, devuelve todos los huespedes.
     *
     * @param textoBusqueda texto a buscar.
     * @return lista de huespedes filtrados.
     * @throws ErrorBaseDatosException si ocurre un error en la BBDD.
     */
    List<Huesped> buscarHuespedes(String textoBusqueda)
            throws ErrorBaseDatosException;
}