package com.biol.biolbg.business.control.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Abstract DAO Bean implementation
 *
 * @param <T>
 */
public abstract class AbstractDaoBean<T>
{
	@PersistenceContext
	protected EntityManager em;

	abstract protected Class<T> getClazz();

	public T findById(final Integer id)
	{
		return em.find(getClazz(), id);
	}

	public T update(final T entity)
	{
		return em.merge(entity);
	}

	public void delete(final Integer id)
	{
		em.remove(em.getReference(getClazz(), id));
	}

	public void delete(final T entity)
	{
		em.remove(entity);
	}

	public T create(final T entity)
	{
		em.persist(entity);

		return entity;
	}

	protected <O> List<O> findObjectsByNamedQuery(final String namedQueryName)
	{
		return getResultList(em.createNamedQuery(namedQueryName).getResultList());
	}

	protected <O> List<O> findObjectsByNamedQuery(final String namedQueryName,
			final Map<String, Object> parameters)
	{
		Query query = em.createNamedQuery(namedQueryName);

		addParametersToQuery(query, parameters);

		return getResultList(query.getResultList());
	}

	protected <O> List<O> findObjectsByNamedQuery(final String namedQueryName,
			final int maxResultsLimit, final int firstResult)
	{
		Query query = em.createNamedQuery(namedQueryName);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	protected <O> List<O> findObjectsByNamedQuery(final String namedQueryName,
			final Map<String, Object> parameters, final int maxResultsLimit,
			final int firstResult)
	{
		Query query = em.createNamedQuery(namedQueryName);

		addParametersToQuery(query, parameters);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	protected void addParametersToQuery(Query query,
			final Map<String, Object> parameters)
	{
		if (parameters != null && !parameters.isEmpty())
		{
			for (Entry<String, Object> entry : parameters.entrySet())
			{
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	protected void setResultsLimitToQuery(Query query, final int maxResultsLimit,
			final int firstResult)
	{
		if (maxResultsLimit > 0)
		{
			query.setMaxResults(maxResultsLimit);
		}

		if (firstResult > 0)
		{
			query.setFirstResult(firstResult);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <O> List<O> getResultList(final List inputList)
	{
		return inputList != null ? inputList : Collections.<O>emptyList();
	}

}
