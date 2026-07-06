package com.springhotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springhotel.entity.Huesped;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * Implementacion de la interfaz {@link HuespedDAO} que gestiona el acceso
 * a la base de datos para la entidad {@link Huesped} mediante Hibernate.
 *
 * @author Manuel
 * @version 1.1
 * @see HuespedDAO
 * @see Huesped
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Repository
public class HuespedDAOImpl extends AbstractDAOImpl<Huesped> implements HuespedDAO {

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param sessionFactory fábrica de sesiones de Hibernate
	 */
    @Autowired
    public HuespedDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Huesped.class);
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Huesped> mostrarTodosLosHuespedes() {
		logger.debug("Obteniendo todos los huéspedes.");
		return findAll("FROM Huesped");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Huesped> buscarHuespedes(String textoBusqueda) {
	    try {
	    	logger.info("Buscar Huéspedes: " + textoBusqueda);
	        String hql = "FROM Huesped h " +
	                     "WHERE (:textoBusqueda IS NULL OR " +
	                     "LOWER(CONCAT(h.nombreHuesped, ' ', h.apellidosHuesped)) " +
	                     "LIKE LOWER(:textoBusqueda))";
	    	
	        Query<Huesped> query = getSession().createQuery(hql, Huesped.class);
	        
	        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
	            query.setParameter("textoBusqueda", null);
	        } else {
	            query.setParameter("textoBusqueda", "%" + textoBusqueda.trim() + "%");
	        }

	        List<Huesped> lista = query.getResultList();
	        logger.debug("Huéspedes encontrados en búsqueda: " + lista.size());
	        return lista;

	    } catch (HibernateException e) {
	        logger.error("Error al buscar huéspedes: " + e.getMessage());
	        throw new ErrorBaseDatosException("Error al buscar huéspedes.", e);
	    }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Huesped mostrarHuespedPorId(Integer id) {
		logger.debug("Buscando huésped con id: " + id);
		return findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void altaHuesped(Huesped huesped) {
		logger.debug("Guardando nuevo huésped: " + huesped.getNombreHuesped());
		persistEntity(huesped);
		logger.info("Huésped guardado correctamente.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaHuesped(Huesped huespedActualizado) {
		logger.debug("Actualizando huésped con id: " + huespedActualizado.getId());

		Huesped h = findById(huespedActualizado.getId());

		h.setNombreHuesped(huespedActualizado.getNombreHuesped());
		h.setApellidosHuesped(huespedActualizado.getApellidosHuesped());
		h.setDniPasaporte(huespedActualizado.getDniPasaporte());
		h.setTelefonoHuesped(huespedActualizado.getTelefonoHuesped());
		h.setEmail(huespedActualizado.getEmail());
		h.setDireccion(huespedActualizado.getDireccion());
		
		try {
			// IA: problema resuelto con Claude. 
	        getSession().flush();
	        logger.info("Huésped actualizado correctamente.");
	    } catch (HibernateException e) {
	    	logger.error("Error al hacer flush al actualizar el huésped: " + e.getMessage());
	        throw new ErrorBaseDatosException("Error al actualizar el huésped en la base de datos.", e);
	    }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eliminaHuesped(Integer id) {
		logger.debug("Eliminando huésped con id: " + id);
		deleteById(id);
		logger.info("Huésped con id " + id + " eliminado correctamente.");
	}
}
