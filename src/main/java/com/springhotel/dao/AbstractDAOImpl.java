package com.springhotel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.springhotel.exception.EntidadNoEncontradaException;
import com.springhotel.exception.ErrorBaseDatosException;

/**
 * Clase base abstracta para las implementaciones DAO del proyecto.
 * Centraliza las operaciones CRUD comunes sobre Hibernate, eliminando
 * la duplicación de código entre los distintos DAOImpl.
 *
 * @param <T> tipo de la entidad gestionada por este DAO
 * @author Ana Laura y Manuel
 * @version 1.0
 */
public abstract class AbstractDAOImpl<T> {

	protected final Logger logger = Logger.getLogger(getClass());
	protected final SessionFactory sessionFactory;
	private final Class<T> entityClass;

	/**
	 * Constructor que inicializa la fábrica de sesiones y el tipo de entidad.
	 *
	 * @param sessionFactory fábrica de sesiones de Hibernate inyectada por Spring
	 * @param entityClass    clase de la entidad gestionada por este DAO
	 */
	protected AbstractDAOImpl(SessionFactory sessionFactory, Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/**
	 * Obtiene la sesión actual de Hibernate asociada a la transacción en curso.
	 *
	 * @return sesión activa de Hibernate
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Ejecuta una consulta HQL de lectura y devuelve la lista de resultados.
	 *
	 * @param hql consulta HQL a ejecutar
	 * @return lista de entidades
	 * @throws ErrorBaseDatosException si Hibernate lanza una excepción
	 */
	protected List<T> findAll(String hql) {
		try {
			return getSession().createQuery(hql, entityClass).getResultList();
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException(
					"Error al obtener registros de " + entityClass.getSimpleName(), e);
		}
	}

	/**
	 * Busca una entidad por su clave primaria. Lanza excepción si no existe.
	 *
	 * @param id identificador de la entidad
	 * @return la entidad encontrada
	 * @throws EntidadNoEncontradaException si no existe registro con ese id
	 * @throws ErrorBaseDatosException      si Hibernate lanza una excepción
	 */
	protected T findById(Integer id) {
		try {
			T entity = getSession().get(entityClass, id);
			if (entity == null) {
				throw new EntidadNoEncontradaException(
						entityClass.getSimpleName() + " no encontrado con id: " + id);
			}
			return entity;
		} catch (EntidadNoEncontradaException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException(
					"Error al obtener " + entityClass.getSimpleName() + " con id: " + id, e);
		}
	}

	/**
	 * Persiste una nueva entidad en la base de datos.
	 *
	 * @param entity la entidad a guardar
	 * @throws ErrorBaseDatosException si Hibernate lanza una excepción
	 */
	protected void persistEntity(T entity) {
		try {
			getSession().persist(entity);
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException(
					"Error al guardar " + entityClass.getSimpleName(), e);
		}
	}

	/**
	 * Guarda una nueva entidad usando session.save().
	 *
	 * @param entity la entidad a guardar
	 * @throws ErrorBaseDatosException si Hibernate lanza una excepción
	 */
	protected void saveEntity(T entity) {
		try {
			getSession().save(entity);
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException(
					"Error al guardar " + entityClass.getSimpleName(), e);
		}
	}

	/**
	 * Sincroniza los cambios de una entidad existente con la base de datos.
	 *
	 * @param entity la entidad con los datos actualizados
	 * @throws ErrorBaseDatosException si Hibernate lanza una excepción
	 */
	protected void mergeEntity(T entity) {
		try {
			getSession().merge(entity);
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException(
					"Error al actualizar " + entityClass.getSimpleName(), e);
		}
	}

	/**
	 * Elimina una entidad por su clave primaria. Lanza excepción si no existe.
	 *
	 * @param id identificador de la entidad a eliminar
	 * @throws EntidadNoEncontradaException si no existe registro con ese id
	 * @throws ErrorBaseDatosException      si Hibernate lanza una excepción
	 */
	protected void deleteById(Integer id) {
		try {
			T entity = findById(id);
			getSession().delete(entity);
		} catch (EntidadNoEncontradaException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ErrorBaseDatosException(
					"Error al eliminar " + entityClass.getSimpleName() + " con id: " + id, e);
		}
	}
}
