package com.biol.biolbg.ejb.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.Order;
import com.biol.biolbg.entity.OrderRow;
import com.biol.biolbg.entity.OrderStatus;
import com.biol.biolbg.entity.Usr;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
public class OrderFacadeBean implements OrderFacade
{
	@PersistenceContext
	private EntityManager em;

	public void addItem(Order item)
	{
		java.util.Date today = new java.util.Date();
		item.setPostdate(new java.util.Date(today.getTime()));
		item.setPosttime(new java.util.Date(today.getTime()));

		if (item.getStatus() == null)
		{
			item.setStatus(findOrderStatus(1));
		}

		em.persist(item);
	}

	public Order findItem(Integer id)
	{
		Order res = em.find(Order.class, id);

		return res;
	}

	public Order createNewItem()
	{
		Order order = new Order();
		order.setRows(new ArrayList<OrderRow>());
		order.setStatus(new OrderStatus());
		order.setUser(new Usr());

		return order;
	}

	public void removeItem(Integer id)
	{
		Order itemOrder = em.find(Order.class, id);

		if (itemOrder != null)
		{
			em.remove(itemOrder);
		}
	}

	public void updateItem(Order item)
	{
		em.merge(item);
	}

	@SuppressWarnings("unchecked")
	public List<Order> getAllItems(Integer fromRow, Integer maxResults,
			String sortBy, String sortType, Date fromDate, Date toDate,
			String username, String organisation)
	{

		if (username != null)
		{
			username = username.trim().toUpperCase();
			username = "%".concat(username).concat("%");
		}

		if (organisation != null)
		{
			organisation = organisation.trim().toUpperCase();
			organisation = "%".concat(organisation).concat("%");
		}

		String queryText =
			"SELECT o FROM Order o " +
			"WHERE ( o.postdate >= :fromDate ) AND " +
					"( o.postdate <= :toDate ) AND " +
					"( UPPER(o.user.username) LIKE :username OR :username IS NULL ) AND " +
					"( UPPER(o.user.organisation) LIKE :organisation OR :organisation IS NULL ) " +
					"ORDER BY %s %s";

		queryText = String.format(queryText, sortBy, sortType);
		Query query = em.createQuery(queryText);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("username", username);
		query.setParameter("organisation", organisation);
		query.setFirstResult(fromRow);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}

	public Long getAllItemsCount(Date fromDate, Date toDate, String username,
			String organisation)
	{

		if (username != null)
		{
			username = username.trim().toUpperCase();
			username = "%".concat(username).concat("%");
		}

		if (organisation != null)
		{
			organisation = organisation.trim().toUpperCase();
			organisation = "%".concat(organisation).concat("%");
		}

		Query query = em.createQuery(
			"SELECT COUNT(o.id) FROM Order o " +
			"WHERE ( o.postdate >= :fromDate ) AND " +
					"( o.postdate <= :toDate ) AND " +
					"( UPPER(o.user.username) LIKE :username OR :username IS NULL ) AND " +
					"( UPPER(o.user.organisation) LIKE :organisation OR :organisation IS NULL )"
			);

		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("username", username);
		query.setParameter("organisation", organisation);

		return (Long)query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<OrderStatus> getAllOrderStatus()
	{
		Query query = em.createQuery("SELECT o FROM OrderStatus o ORDER BY o.id ASC");

		return query.getResultList();
	}

	public OrderStatus findOrderStatus(Integer id)
	{
		OrderStatus res = em.find(OrderStatus.class, id);

		return res;
	}

	public Order updateStatus(Integer orderId, OrderStatus newStatus)
	{
		Order itemOrder = em.find(Order.class, orderId);

		if (itemOrder != null)
		{
			if (itemOrder.getId() > 0)
			{
				itemOrder.setStatus(newStatus);
				em.merge(itemOrder);
			}
		}

		return itemOrder;
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrdersForUser(Integer fromRow, Integer maxResults,
			String sortBy, String sortType, Date fromDate, Date toDate,
			Integer userId)
	{
		String queryText =
			"SELECT o FROM Order o " +
			"WHERE ( o.postdate >= :fromDate ) AND " +
					"( o.postdate <= :toDate ) AND " +
					"( o.user.id = :userid ) " +
					"ORDER BY %s %s";

		queryText = String.format(queryText, sortBy, sortType);
		Query query = em.createQuery(queryText);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("userid", userId);
		query.setFirstResult(fromRow);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}

	public Long getOrdersForUserCount(Date fromDate, Date toDate, Integer userId)
	{
		String queryText =
			"SELECT COUNT(o.id) FROM Order o " +
			"WHERE ( o.postdate >= :fromDate ) AND " +
					"( o.postdate <= :toDate ) AND " +
					"( o.user.id = :userid )";

		Query query = em.createQuery(queryText);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("userid", userId);

		return (Long)query.getSingleResult();
	}

	public Order cancelOrder(Integer orderId)
	{
		Order itemOrder = em.find(Order.class, orderId);

		if (itemOrder != null)
		{
			if (itemOrder.getId() > 0)
			{
				OrderStatus cancelStatus = findOrderStatus(5);

				if (cancelStatus != null)
				{
					itemOrder.setStatus(cancelStatus);
					em.merge(itemOrder);
				}
			}
		}

		return itemOrder;
	}

	@SuppressWarnings("unchecked")
	public String getLastDeliveryAddressForUser(Integer userId)
	{
		String queryText1 =
			"SELECT MAX(o.id) FROM Order o WHERE o.user.id = :userId";
		Query query1 = em.createQuery(queryText1);
		query1.setParameter("userId", userId);
		Integer lastOrderId = (Integer)query1.getSingleResult();

		String queryText2 =
			"SELECT o.deliveryAddress FROM Order o " +
			"WHERE o.id = :lastOrderId";
		Query query2 = em.createQuery(queryText2);
		query2.setParameter("lastOrderId", lastOrderId);
		List<String> results = query2.getResultList();

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}
		else
		{
			return "";
		}
	}

	public Order markOrderAsSeenByAdmin(Integer orderId)
	{
		Order res = null;
		Order itemOrder = em.find(Order.class, orderId);

		if (itemOrder != null)
		{
			if (itemOrder.getId() > 0)
			{
				itemOrder.setSeenbyadmin(1);
				res = em.merge(itemOrder);
			}
		}

		return res;
	}
}
