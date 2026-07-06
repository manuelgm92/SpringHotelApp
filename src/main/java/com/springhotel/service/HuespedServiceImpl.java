package com.springhotel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springhotel.dao.HuespedDAO;
import com.springhotel.dto.HuespedDTO;
import com.springhotel.entity.Huesped;
import com.springhotel.exception.ArgumentoNoValidoException;

import com.springhotel.mapper.HuespedMapper;

/**
 * Implementacion de la interfaz {@link HuespedService} que contiene
 * la logica de negocio relacionada con la gestion de huespedes del hotel.
 * <p>
 * Actua como capa intermedia entre los controladores y la capa de acceso
 * a datos ({@link HuespedDAO}). Se encarga de validar los datos de entrada
 * antes de delegar las operaciones al DAO correspondiente.
 * </p>
 * <p>
 * Todas las operaciones estan marcadas con {@link Transactional} a nivel de
 * clase, por lo que Spring gestiona automaticamente el ciclo de vida de las
 * transacciones de base de datos.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @see HuespedService
 * @see HuespedDAO
 * @see Huesped
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Service
@Transactional
public class HuespedServiceImpl implements HuespedService {
	
	/**
	 * Logger del sistema para la clase {@link HuespedServiceImpl}.
	 * Se utiliza para registrar eventos, flujos de datos y excepciones 
	 * en los diferentes niveles de depuración (DEBUG, INFO, WARN, ERROR).
	 */
    private static final Logger logger = Logger.getLogger(HuespedServiceImpl.class);
	

    /**
     * Objeto de acceso a datos para huéspedes,
     * inyectado automáticamente por Spring a través del constructor.
     */
    private final HuespedDAO huespedDAO;

    /**
     * Mapper para convertir entre entidades y DTOs de huéspedes.
     */
    private final HuespedMapper mapper;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param huespedDAO DAO de huéspedes inyectado por Spring
     * @param mapper mapper para conversión entre entidad y DTO
     */
    @Autowired
    public HuespedServiceImpl(HuespedDAO huespedDAO,
                              HuespedMapper mapper) {
        this.huespedDAO = huespedDAO;
        this.mapper = mapper;
    }
	
	/**
     * {@inheritDoc}
     * <p>
     * Delega directamente la consulta al DAO sin aplicar
     * logica de negocio adicional.
     * </p>
     */
	@Override
	public List<HuespedDTO> mostrarTodosLosHuespedes() {
		logger.debug("Service: solicitando lista de todos los huéspedes.");
		return huespedDAO.mostrarTodosLosHuespedes()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
	}
	
	/**
     * {@inheritDoc}
     * <p>
     * Delega directamente la busqueda al DAO. Si no existe un huesped
     * con el identificador indicado, el DAO lanzara
     * {@link com.springhotel.exception.EntidadNoEncontradaException}.
     * </p>
     */
	@Override
	public HuespedDTO mostrarHuespedPorId(Integer id) {
	    logger.debug("Service: solicitando huésped con id: " + id);
	    Huesped entidad = huespedDAO.mostrarHuespedPorId(id);
        return mapper.toDTO(entidad);
	}
	
	/**
     * {@inheritDoc}
     * <p>
     * Antes de persistir el huesped aplica las siguientes validaciones:
     * <ul>
     *   <li>El huesped no puede ser {@code null}.</li>
     *   <li>El huesped no debe tener un ID asignado, ya que es un nuevo registro
     *       y el ID lo genera automaticamente la base de datos.</li>
     * </ul>
     * Si alguna validacion falla se lanza {@link ArgumentoNoValidoException}.
     * </p>
     */
	@Override
	public void altaHuesped(HuespedDTO huesped) {
		
		if (huesped == null) {
			logger.warn("Service: intento de alta con huésped nulo.");
			throw new ArgumentoNoValidoException("No se puede dar de alta un huésped nulo.");
		}
		
		if (huesped.getId() != null) {
	        logger.warn("Service: intento de alta con ID ya asignado: " + huesped.getId());
			throw new ArgumentoNoValidoException("Un nuevo huésped no debe tener un ID asignado.");
		}
		logger.debug("Service: dando de alta al huésped: " + huesped.getNombreHuesped());
		Huesped entidad = mapper.toEntity(huesped);
        huespedDAO.altaHuesped(entidad);
	}
	
	/**
     * {@inheritDoc}
     * <p>
     * Antes de actualizar valida que el objeto recibido no sea {@code null}
     * y que tenga un identificador valido. Si alguna condicion no se cumple
     * se lanza {@link ArgumentoNoValidoException}.
     * </p>
     * <p>
     * Si no existe un huesped con el identificador indicado, el DAO lanzara
     * {@link com.springhotel.exception.EntidadNoEncontradaException}.
     * </p>
     */
	@Override
	public void actualizaHuesped(HuespedDTO nuevoHuesped) {
		
		if (nuevoHuesped == null || nuevoHuesped.getId() == null) {
			logger.warn("Service: intento de actualización con huésped nulo o sin ID.");
			throw new ArgumentoNoValidoException("Huésped no encontrado.");
		}
		logger.debug("Service: actualizando huésped con id: " + nuevoHuesped.getId());
		 Huesped entidad = mapper.toEntity(nuevoHuesped);
	        huespedDAO.actualizaHuesped(entidad);
	}
	
	/**
     * {@inheritDoc}
     * <p>
     * Valida que el identificador recibido no sea {@code null} antes de
     * delegar la eliminacion al DAO. Si el id es nulo se lanza
     * {@link ArgumentoNoValidoException}.
     * </p>
     * <p>
     * Si no existe un huesped con el identificador indicado, el DAO lanzara
     * {@link com.springhotel.exception.EntidadNoEncontradaException}.
     * </p>
     */
	@Override
	public void eliminaHuesped(Integer id) {
		if (id == null) {
			logger.warn("Service: intento de eliminación con ID nulo.");
			throw new ArgumentoNoValidoException("El ID no puede ser nulo.");
		}
		logger.info("Service: eliminando huésped con id: " + id);
		huespedDAO.eliminaHuesped(id);
		
	}
	
	/**
     * {@inheritDoc}
     * <p>
     * Procesa la busqueda de huespedes delegando la operacion en la capa 
     * de acceso a datos (DAO). Aplica un filtro flexible basado en una 
     * cadena de texto que contrasta contra el nombre o apellidos.
     * </p>
     *
     * @param textoBusqueda cadena de texto utilizada como criterio para filtrar 
     * los registros; puede ser null o estar vacia.
     * @return lista de objetos {@link Huesped} que coinciden con el criterio; 
     * lista vacia si no se encuentran resultados.
     * @throws ErrorBaseDatosException si ocurre una anomalía al consultar la BBDD.
     */
	@Override
	public List<HuespedDTO> buscarHuespedes(String textoBusqueda) {
		logger.debug("Service: procesando búsqueda de huéspedes: " + textoBusqueda);
	    List<Huesped> entidades = huespedDAO.buscarHuespedes(textoBusqueda);
	    return entidades.stream()
	            .map(mapper::toDTO)
	            .collect(Collectors.toList());
	}

}
