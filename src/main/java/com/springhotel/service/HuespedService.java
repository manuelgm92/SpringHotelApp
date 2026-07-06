package com.springhotel.service;

import java.util.List;

import com.springhotel.dto.HuespedDTO;
import com.springhotel.entity.Huesped;
import com.springhotel.exception.ArgumentoNoValidoException;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * Interfaz que define las operaciones de negocio para la gestion de huespedes
 * del hotel.
 *
 * <p>
 * Actua como capa intermedia entre los controladores y la capa de acceso
 * a datos ({@link com.springhotel.dao.HuespedDAO}). Se encarga de declarar
 * las operaciones disponibles, cuya logica de validacion y delegacion se
 * implementa en {@link HuespedServiceImpl}.
 * </p>
 *
 * <p>
 * Usar una interfaz desacopla el controlador de la implementacion concreta,
 * facilitando el mantenimiento y los tests.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @see HuespedServiceImpl
 * @see Huesped
 */
//IA: javadoc generado con ayuda de IA(Claude)
public interface HuespedService {

    /**
     * Recupera todos los huespedes registrados en el sistema.
     *
     * @return lista con todos los {@link Huesped} existentes;
     *         lista vacia si no hay ningun registro.
     * @throws ErrorBaseDatosException si ocurre un error al acceder a la BBDD.
     */
    List<HuespedDTO> mostrarTodosLosHuespedes() throws ErrorBaseDatosException;

    /**
     * Recupera un huesped a partir de su identificador unico.
     *
     * @param id identificador unico del huesped a buscar.
     * @return el {@link Huesped} cuyo identificador coincide con el parametro.
     * @throws EntidadNoEncontradaException si no existe ningun huesped con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al acceder a la BBDD.
     */
    HuespedDTO mostrarHuespedPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException;

    /**
     * Registra un nuevo huesped en el sistema.
     * <p>
     * La implementacion valida que el objeto no sea nulo y que no tenga
     * un ID asignado previamente, ya que se trata de un nuevo registro.
     * </p>
     *
     * @param nuevoHuesped la entidad {@link Huesped} que se desea registrar;
     *                     no debe ser {@code null} ni tener un id asignado.
     * @throws ArgumentoNoValidoException si el huesped es nulo o ya tiene un id.
     * @throws ErrorBaseDatosException    si ocurre un error al guardar en la BBDD.
     */
    void altaHuesped(HuespedDTO nuevoHuesped) throws ArgumentoNoValidoException, ErrorBaseDatosException;

    /**
     * Actualiza los datos de un huesped existente en el sistema.
     * <p>
     * La implementacion valida que el objeto no sea nulo y que tenga
     * un identificador valido que corresponda a un registro existente.
     * </p>
     *
     * @param huesped la entidad {@link Huesped} con los datos modificados;
     *                no debe ser {@code null} y debe tener un id valido.
     * @throws ArgumentoNoValidoException   si el huesped es nulo o no tiene id.
     * @throws EntidadNoEncontradaException si no existe ningun huesped con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al actualizar en la BBDD.
     */
    void actualizaHuesped(HuespedDTO huespedDTO) throws ArgumentoNoValidoException, EntidadNoEncontradaException, ErrorBaseDatosException;

    /**
     * Elimina un huesped del sistema a partir de su identificador unico.
     * <p>
     * La implementacion valida que el id no sea nulo antes de
     * intentar la eliminacion.
     * </p>
     *
     * @param id identificador unico del huesped que se desea eliminar.
     * @throws ArgumentoNoValidoException   si el id es {@code null}.
     * @throws EntidadNoEncontradaException si no existe ningun huesped con ese id.
     * @throws ErrorBaseDatosException      si ocurre un error al eliminar en la BBDD.
     */
    void eliminaHuesped(Integer id) throws ArgumentoNoValidoException, EntidadNoEncontradaException, ErrorBaseDatosException;
    
    /**
     * Busca huespedes filtrando por textoBusqueda.
     *
     * @param textoBusqueda texto de busqueda para filtar por nombre y apellidos del huesped.
     * @return lista de huespedes encontrados.
     * @throws ErrorBaseDatosException si ocurre un error en la BBDD.
     */
    List<HuespedDTO> buscarHuespedes(String textoBusqueda)throws ErrorBaseDatosException;
}