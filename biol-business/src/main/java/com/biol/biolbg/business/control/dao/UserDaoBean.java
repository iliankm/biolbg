package com.biol.biolbg.business.control.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.entity.UsrEntity;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
@LocalBean
public class UserDaoBean extends AbstractDaoBean<Usr>
{

	@Override
	protected Class<UsrEntity> getClazz()
	{
		return UsrEntity.class;
	}

	public List<Usr> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		String queryText = "SELECT o FROM UsrEntity o ORDER BY %s %s";
		queryText = String.format(queryText, sortCriteria.getPropertyName(), sortCriteria.getSortDirectionForJPA());

		Query query = em.createQuery(queryText);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	public Long getAllCount()
	{
		List<Long> results = this.findObjectsByNamedQuery(UsrEntity.QueryNames.GET_ALL_USERS_COUNT);

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

	public Usr findByUsername(final String username)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);

		List<UsrEntity> results = this.findObjectsByNamedQuery(UsrEntity.QueryNames.FIND_USER_BY_USERNAME, params);

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return null;
	}

	public List<Usr> findByAdminFlag(final int adminFlag)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("adminflag", Integer.valueOf(adminFlag));

		return findObjectsByNamedQuery(UsrEntity.QueryNames.FIND_BY_ADMIN_FLAG, params);
	}


}
