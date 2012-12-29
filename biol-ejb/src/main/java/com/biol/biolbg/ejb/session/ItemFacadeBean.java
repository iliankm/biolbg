package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biol.biolbg.ejb.session.ItemFacade;
import com.biol.biolbg.entity.Item;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
public class ItemFacadeBean implements ItemFacade
{
	@PersistenceContext
	private EntityManager em;

	public void addItem(Item item)
	{
		em.persist(item);
	}

	public Item findItem(Integer id)
	{
		Item res = em.find(Item.class, id);
		return res;
	}

	public Item createNewItem()
	{
		Item item = new Item();
		item.setNewitem(1);

		return item;
	}

	public void removeItem(Integer id)
	{
		Item itemItem = em.find(Item.class, id);

		if (itemItem != null)
		{
			em.remove(itemItem);
		}
	}

	public void updateItem(Item item)
	{
		em.merge(item);
	}

	@SuppressWarnings("unchecked")
	public List<Item> getAllItems(Integer groupId, Integer producerId, Integer fromRow, Integer maxResults, String sortBy, String sortType)
	{
		String queryText = "SELECT o FROM Item o WHERE ( o.group.id=:groupId OR :groupId=0 ) AND ( o.producer.id=:producerId OR :producerId=0 ) ORDER BY %s %s";
		queryText = String.format(queryText, sortBy, sortType);
		Query query = em.createQuery(queryText);
		query.setParameter("groupId", groupId);
		query.setParameter("producerId", producerId);
		query.setFirstResult(fromRow);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}

	public Long getAllItemsCount(Integer groupId, Integer producerId)
	{
		Query query = em.createQuery("SELECT COUNT(o.id) FROM Item o WHERE ( o.group.id=:groupId OR :groupId=0 ) AND ( o.producer.id=:producerId OR :producerId=0 )");
		query.setParameter("groupId", groupId);
		query.setParameter("producerId", producerId);

		return (Long)query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getPromotionItems(Integer fromRow, Integer maxResults)
	{
		String queryText = "SELECT o FROM Item o WHERE o.promotion=1 ORDER BY o.id asc";
		Query query = em.createQuery(queryText);
		query.setFirstResult(fromRow);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}

	public Long getPromotionItemsCount() {
		Query query = em.createQuery("SELECT COUNT(o.id) FROM Item o WHERE o.promotion=1");
		return (Long)query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItemsByName(String name, Integer fromRow,
			Integer maxResults, String sortBy, String sortType)
	{
		if (name != null)
		{
			name = name.trim().toUpperCase();
			name = "%".concat(name).concat("%");
		}

		String queryText =
			"SELECT o FROM Item o " +
			"WHERE ( UPPER(o.nameen) LIKE :name OR UPPER(o.namebg) LIKE :name )" +
					"ORDER BY %s %s";

		queryText = String.format(queryText, sortBy, sortType);
		Query query = em.createQuery(queryText);
		query.setParameter("name", name);
		query.setFirstResult(fromRow);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}

	public Long getItemsByNameCount(String name)
	{
		if (name != null)
		{
			name = name.trim().toUpperCase();
			name = "%".concat(name).concat("%");
		}

		String queryText =
			"SELECT COUNT(o.id) FROM Item o " +
			"WHERE ( UPPER(o.nameen) LIKE :name OR UPPER(o.namebg) LIKE :name )";

		Query query = em.createQuery(queryText);
		query.setParameter("name", name);

		return (Long)query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getCheapestItems(Integer fromRow, Integer maxResults)
	{
		String queryText =
			"SELECT o FROM Item o " +
			"ORDER BY o.priceforpacking ASC";
		Query query = em.createQuery(queryText);
		query.setMaxResults(maxResults);
		query.setFirstResult(fromRow);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getBestSellItems(Integer fromRow, Integer maxResults)
	{
		String queryText =
			"SELECT o FROM Item o " +
			"WHERE o.bestsell=1 " +
			"ORDER BY o.id ASC";
		Query query = em.createQuery(queryText);
		query.setMaxResults(maxResults);
		query.setFirstResult(fromRow);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getNewItems(Integer fromRow, Integer maxResults)
	{
		String queryText =
			"SELECT o FROM Item o " +
			"WHERE o.newitem=1 " +
			"ORDER BY o.id ASC";
		Query query = em.createQuery(queryText);
		query.setMaxResults(maxResults);
		query.setFirstResult(fromRow);

		return query.getResultList();
	}

	public Long getBestSellItemsCount()
	{
		String queryText =
			"SELECT COUNT(o.id) FROM Item o " +
			"WHERE o.bestsell=1";
		Query query = em.createQuery(queryText);

		return (Long)query.getSingleResult();
	}

	public Long getNewItemsCount()
	{
		String queryText =
			"SELECT COUNT(o.id) FROM Item o " +
			"WHERE o.newitem=1";
		Query query = em.createQuery(queryText);

		return (Long)query.getSingleResult();
	}
}
