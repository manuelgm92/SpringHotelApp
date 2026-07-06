package com.springhotel.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springhotel.entity.Habitacion;
import com.springhotel.entity.Incidencia;
import com.springhotel.entity.Incidencia.PrioridadIncidencia;
import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * IA:Documentación JavaDoc elaborada por Ana Laura con asistencia parcial de IA (Claude).
 * Implementacion de {@link IncidenciaDAO} usando Hibernate.
 *
 * <p>
 * Utiliza JOIN FETCH en las consultas para evitar LazyInitializationException
 * en las vistas JSP.
 * </p>
 *
 * @author Ana Laura
 * @version 1.1
 * @see IncidenciaDAO
 */
@Repository
@Transactional
public class IncidenciaDAOImpl extends AbstractDAOImpl<Incidencia> implements IncidenciaDAO {

	/**
	 * Constructor con inyección de dependencias.
	 *
	 * @param sessionFactory fábrica de sesiones de Hibernate
	 */
    @Autowired
    public IncidenciaDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Incidencia.class);
    }

	/**
	 * {@inheritDoc}
	 * Utiliza JOIN FETCH para cargar la habitación asociada.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Incidencia> mostrarTodasIncidencias() throws ErrorBaseDatosException {
		return findAll("FROM Incidencia i JOIN FETCH i.habitacion ORDER BY i.fechaApertura DESC");
	}

	/**
	 * {@inheritDoc}
	 * Utiliza JOIN FETCH para cargar la habitación asociada.
	 */
	@Override
	@Transactional(readOnly = true)
	public Incidencia mostrarIncidenciaPorId(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		try {
			String hql = "FROM Incidencia i JOIN FETCH i.habitacion WHERE i.id = :id";
			Incidencia incidencia = getSession().createQuery(hql, Incidencia.class)
					.setParameter("id", id).uniqueResult();
			if (incidencia == null) {
				throw new EntidadNoEncontradaException("Incidencia no encontrada con id: " + id);
			}
			return incidencia;
		} catch (EntidadNoEncontradaException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException("Error al obtener la incidencia con id: " + id, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * Carga la habitación asociada y establece la fecha de apertura automáticamente.
	 */
	@Override
	public void altaIncidencia(Incidencia incidencia) throws ErrorBaseDatosException {
		try {
			Habitacion habitacion = getSession().get(Habitacion.class, incidencia.getHabitacion().getId());
			if (habitacion == null) {
				throw new ErrorBaseDatosException("La habitación no existe");
			}
			incidencia.setHabitacion(habitacion);
			incidencia.setFechaApertura(LocalDateTime.now());
			saveEntity(incidencia);
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException("Error al guardar la incidencia", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * Establece automáticamente la fecha de cierre cuando el estado pasa a CERRADA.
	 */
	@Override
	public void actualizaIncidencia(Incidencia incidencia)
			throws EntidadNoEncontradaException, ErrorBaseDatosException {
		try {
			String hql = "FROM Incidencia i JOIN FETCH i.habitacion WHERE i.id = :id";
			Incidencia existente = getSession().createQuery(hql, Incidencia.class)
					.setParameter("id", incidencia.getId()).uniqueResult();

			if (existente == null) {
				throw new EntidadNoEncontradaException("Incidencia no encontrada con id: " + incidencia.getId());
			}

			Habitacion habitacion = getSession().get(Habitacion.class, incidencia.getHabitacion().getId());
			if (habitacion == null) {
				throw new ErrorBaseDatosException("La habitación asociada no existe");
			}

			existente.setHabitacion(habitacion);
			existente.setDescripcion(incidencia.getDescripcion());
			existente.setPrioridad(incidencia.getPrioridad());
			existente.setTecnicoAsignado(incidencia.getTecnicoAsignado());
			existente.setObservaciones(incidencia.getObservaciones());
			existente.setEstadoIncidencia(incidencia.getEstadoIncidencia());

			if (incidencia.getEstadoIncidencia() == Incidencia.EstadoIncidencia.CERRADA
					&& existente.getFechaCierre() == null) {
				existente.setFechaCierre(LocalDateTime.now());
			}

			getSession().update(existente);

		} catch (EntidadNoEncontradaException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException("Error al actualizar la incidencia", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eliminaIncidencia(Integer id) throws EntidadNoEncontradaException, ErrorBaseDatosException {
		try {
			String hql = "FROM Incidencia i JOIN FETCH i.habitacion WHERE i.id = :id";
			Incidencia existente = getSession().createQuery(hql, Incidencia.class)
					.setParameter("id", id).uniqueResult();
			if (existente == null) {
				throw new EntidadNoEncontradaException("Incidencia no encontrada con id: " + id);
			}
			getSession().delete(existente);
		} catch (EntidadNoEncontradaException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException("Error al eliminar la incidencia con id: " + id, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Incidencia> mostrarIncidenciasPorPrioridad(PrioridadIncidencia prioridad)
			throws ErrorBaseDatosException {
		try {
			String hql = "FROM Incidencia i JOIN FETCH i.habitacion WHERE i.prioridad = :prioridad "
					+ "ORDER BY i.fechaApertura ASC";
			return getSession().createQuery(hql, Incidencia.class)
					.setParameter("prioridad", prioridad).getResultList();
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException("Error al obtener incidencias con prioridad: " + prioridad, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Incidencia> mostrarIncidenciasPorHabitacion(Integer idHabitacion) throws ErrorBaseDatosException {
		try {
			String hql = "FROM Incidencia i JOIN FETCH i.habitacion h "
					+ "WHERE h.id = :idHabitacion "
					+ "ORDER BY i.fechaApertura DESC";
			return getSession().createQuery(hql, Incidencia.class)
					.setParameter("idHabitacion", idHabitacion).getResultList();
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException("Error al obtener incidencias de habitación: " + idHabitacion, e);
		}
	}
}