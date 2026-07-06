package com.springhotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springhotel.entity.Reserva;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA
 * (Claude). Implementación de {@link ReservaDAO} usando Hibernate.
 *
 * <p>
 * Utiliza JOIN FETCH en las consultas para evitar LazyInitializationException
 * en las vistas JSP.
 * </p>
 *
 * @author Ana Laura y Manuel
 * @version 1.1
 * @see ReservaDAO
 */
@Repository
@Transactional
public class ReservaDAOImpl extends AbstractDAOImpl<Reserva> implements ReservaDAO {

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param sessionFactory fábrica de sesiones de Hibernate
	 */
    @Autowired
    public ReservaDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Reserva.class);
    }

	/**
	 * {@inheritDoc}
	 * Utiliza JOIN FETCH para cargar huésped y habitación junto con la reserva.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Reserva> mostrarTodasReservas() throws ErrorBaseDatosException {
		return findAll("SELECT r FROM Reserva r JOIN FETCH r.huesped JOIN FETCH r.habitacion ORDER BY r.checkIn");
	}

	/**
	 * {@inheritDoc}
	 * Utiliza JOIN FETCH para cargar huésped y habitación junto con la reserva.
	 */
	@Override
	@Transactional(readOnly = true)
	public Reserva mostrarReservaPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		try {
			String hql = "SELECT r FROM Reserva r JOIN FETCH r.huesped JOIN FETCH r.habitacion WHERE r.id = :id";
			Reserva r = getSession().createQuery(hql, Reserva.class).setParameter("id", id).uniqueResult();
			if (r == null) {
				throw new EntidadNoEncontradaException("Reserva no encontrada con id: " + id);
			}
			return r;
		} catch (EntidadNoEncontradaException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException("Error al obtener la reserva con id: " + id, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void altaReserva(Reserva reserva) throws ErrorBaseDatosException {
        persistEntity(reserva);
        logger.info("Reserva guardada correctamente.");
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void actualizaReserva(Reserva reserva) throws EntidadNoEncontradaException, ErrorBaseDatosException {
        logger.debug("DAO: Ejecutando Merge sobre reserva con id: " + reserva.getId());
        mergeEntity(reserva);
        logger.info("Reserva actualizada correctamente mediante Merge.");
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eliminaReserva(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		deleteById(id);
	}
}
