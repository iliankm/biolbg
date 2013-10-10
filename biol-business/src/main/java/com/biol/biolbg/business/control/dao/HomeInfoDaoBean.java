package com.biol.biolbg.business.control.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.biol.biolbg.business.entity.HomeInfoEntity;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
@LocalBean
public class HomeInfoDaoBean extends AbstractDaoBean<HomeInfoEntity>
{

	@Override
	protected Class<HomeInfoEntity> getClazz()
	{
		return HomeInfoEntity.class;
	}

	public List<HomeInfoEntity> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		String queryText = "SELECT o FROM HomeInfoEntity o ORDER BY %s %s";
		queryText = String.format(queryText, sortCriteria.getPropertyName(), sortCriteria.getSortDirectionForJPA());

		Query query = em.createQuery(queryText);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	public Long getAllCount()
	{
		List<Long> results = this.findObjectsByNamedQuery(HomeInfoEntity.QueryNames.GET_ALL_COUNT);

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

}
