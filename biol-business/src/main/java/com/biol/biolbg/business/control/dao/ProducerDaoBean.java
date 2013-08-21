package com.biol.biolbg.business.control.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.biol.biolbg.business.entity.ProducerEntity;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
@LocalBean
public class ProducerDaoBean extends AbstractDaoBean<ProducerEntity>
{

	@Override
	protected Class<ProducerEntity> getClazz()
	{
		return ProducerEntity.class;
	}

	@SuppressWarnings("unchecked")
	public List<ProducerEntity> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		String queryText = "SELECT o FROM ProducerEntity o ORDER BY %s %s";
		queryText = String.format(queryText, sortCriteria.getPropertyName(), sortCriteria.getSortDirectionForJPA());

		Query query = em.createQuery(queryText);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return query.getResultList();
	}

	public Long getAllCount()
	{
		List<Long> results = this.findObjectsByNamedQuery(ProducerEntity.QueryNames.GET_ALL_COUNT);

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

}
