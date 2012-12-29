package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biol.biolbg.ejb.session.HomeInfoFacade;
import com.biol.biolbg.entity.HomeInfo;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
public class HomeInfoFacadeBean implements HomeInfoFacade
{
	@PersistenceContext
	private EntityManager em;

	public void addItem(HomeInfo item)
	{
		em.persist(item);
	}

	public HomeInfo findItem(Integer id)
	{
		HomeInfo res = em.find(HomeInfo.class, id);

		return res;
	}

	public HomeInfo createNewItem()
	{
		return new HomeInfo();
	}

	public void removeItem(Integer id)
	{
		HomeInfo itemHomeInfo = em.find(HomeInfo.class, id);

		if (itemHomeInfo != null)
		{
			em.remove(itemHomeInfo);
		}
	}

	public void updateItem(HomeInfo item)
	{
		em.merge(item);
	}

	@SuppressWarnings("unchecked")
	public List<HomeInfo> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType)
	{
		String queryText = "SELECT o FROM HomeInfo o ORDER BY %s %s";
		queryText = String.format(queryText, sortBy, sortType);
		Query query = em.createQuery(queryText);
		query.setFirstResult(fromRow);

		if (maxResults > 0)
		{
			query.setMaxResults(maxResults);
		}

		return query.getResultList();
	}

	public Long getAllItemsCount()
	{
		Query query = em.createQuery("SELECT COUNT(o.id) FROM HomeInfo o");
		return (Long)query.getSingleResult();
	}
}
