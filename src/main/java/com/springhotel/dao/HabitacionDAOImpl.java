package com.springhotel.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springhotel.entity.Habitacion;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Implementacion de {@link HabitacionDao} usando Hibernate.
 *
 * <p>
 * Esta clase accede directamente a la tabla {@code habitaciones} a traves de la
 * {@link SessionFactory} inyectada por Spring. Cada operacion abre una sesion
 * de Hibernate (gestionada automaticamente con {@code @Transactional}) y
 * ejecuta la consulta o modificacion correspondiente.
 * </p>
 *
 * @author Ana Laura
 * @version 1.1
 * @see HabitacionDAO
 */
@Repository
@Transactional
public class HabitacionDAOImpl extends AbstractDAOImpl<Habitacion> implements HabitacionDAO {

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param sessionFactory fábrica de sesiones de Hibernate
	 */
    @Autowired
    public HabitacionDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Habitacion.class);
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Habitacion> mostrarTodasHabitaciones() throws ErrorBaseDatosException {
		return findAll("FROM Habitacion ORDER BY numeroHabitacion");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Habitacion mostrarHabitacionPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		return findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void altaHabitacion(Habitacion habitacion) throws ErrorBaseDatosException {
		saveEntity(habitacion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaHabitacion(Habitacion habitacion)
			throws EntidadNoEncontradaException, ErrorBaseDatosException {
		findById(habitacion.getId());
		mergeEntity(habitacion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eliminaHabitacion(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		deleteById(id);
	}
}
