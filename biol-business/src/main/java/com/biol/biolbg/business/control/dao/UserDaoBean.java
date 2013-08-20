package com.biol.biolbg.business.control.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.biol.biolbg.business.entity.UsrEntity;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
@LocalBean
public class UserDaoBean extends AbstractDaoBean<UsrEntity>
{

	@Override
	protected Class<UsrEntity> getClazz() 
	{
		return UsrEntity.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<UsrEntity> findAllUsers(final int maxResultsLimit, final int firstResult, SortCriteria sortCriteria)
	{
		String queryText = "SELECT o FROM UsrEntity o ORDER BY %s %s";
		queryText = String.format(queryText, sortCriteria.getPropertyName(), sortCriteria.getSortDirectionForJPA());
		
		Query query = em.createQuery(queryText);
		
		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return query.getResultList();
	}
	
	public Long getAllUsersCount()
	{
		List<Long> results = this.findObjectsByNamedQuery(UsrEntity.QueryNames.GET_ALL_USERS_COUNT);
		
		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}
		
		return Long.valueOf(0);
	}
	
	public UsrEntity findUserByUsername(final String username)
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
	
	public List<UsrEntity> findUsersByAdminFlag(final int adminFlag)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("adminflag", Integer.valueOf(adminFlag));
		
		return findObjectsByNamedQuery(UsrEntity.QueryNames.FIND_BY_ADMIN_FLAG, params);
	}
	

}
