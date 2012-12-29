package com.biol.biolbg.ejb.session;

import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biol.biolbg.ejb.session.GroupFacade;
import com.biol.biolbg.entity.Group;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
public class GroupFacadeBean implements GroupFacade
{
	@PersistenceContext
	private EntityManager em;

	public void addItem(Group item)
	{
		em.persist(item);
	}

	public Group findItem(Integer id)
	{
		Group res = em.find(Group.class, id);
		return res;
	}

	public Group createNewItem()
	{
		return new Group();
	}

	public void removeItem(Integer id)
	{
		Group itemGroup = em.find(Group.class, id);

		if (itemGroup != null)
		{
			em.remove(itemGroup);
		}
	}

	public void updateItem(Group item)
	{
		em.merge(item);
	}

	@SuppressWarnings("unchecked")
	public List<Group> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType)
	{
		String queryText = "SELECT o FROM Group o ORDER BY %s %s";

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
		Query query = em.createQuery("SELECT COUNT(o.id) FROM Group o");

		return (Long)query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public Group getRandomGroup()
	{
		Group res = null;
		Query query = em.createQuery("SELECT o.group.id FROM Item o GROUP BY o.group.id");
		List<Integer> groupIds = query.getResultList();

		if (groupIds.size() > 0)
		{
			Random random = new Random();
			Integer randomInteger = random.nextInt(groupIds.size()-1);
			Integer groupId = groupIds.get(randomInteger);
			res = em.find(Group.class, groupId);
		}

		return res;
	}
}
