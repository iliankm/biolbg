package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biol.biolbg.ejb.session.ProducerFacade;
import com.biol.biolbg.entity.Producer;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
public class ProducerFacadeBean implements ProducerFacade
{
	@PersistenceContext
	private EntityManager em;

	public void addItem(Producer item)
	{
		em.persist(item);
	}

	public Producer createNewItem()
	{
		return new Producer();
	}

	public Producer findItem(Integer id)
	{
		Producer res = em.find(Producer.class, id);

		return res;
	}

	@SuppressWarnings("unchecked")
	public List<Producer> getAllItems(Integer fromRow, Integer maxResults,
			String sortBy, String sortType)
	{
		String queryText = "SELECT o FROM Producer o ORDER BY %s %s";
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
		Query query = em.createQuery("SELECT COUNT(o.id) FROM Producer o");

		return (Long)query.getSingleResult();
	}

	public void removeItem(Integer id)
	{
		Producer itemProducer = em.find(Producer.class, id);

		if (itemProducer != null)
		{
			em.remove(itemProducer);
		}
	}

	public void updateItem(Producer item)
	{
		em.merge(item);
	}
}
