package com.biol.biolbg.business.control.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.biol.biolbg.business.control.query.builder.OrderQueryBuilder;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.OrderEntity;
import com.biol.biolbg.business.entity.OrderStatus;
import com.biol.biolbg.business.entity.OrderStatusEntity;
import com.biol.biolbg.business.util.FindOrderCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
@LocalBean
public class OrderDaoBean extends AbstractDaoBean<Order>
{
	@Inject
	private OrderQueryBuilder orderQueryBuilder;

	@Override
	protected Class<OrderEntity> getClazz()
	{
		return OrderEntity.class;
	}

	public List<Order> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		String queryText = orderQueryBuilder.select().sortCriteria(sortCriteria).build();

		Query query = em.createQuery(queryText);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	public Long getAllCount()
	{
		String queryText = orderQueryBuilder.selectForCount().build();

		Query query = em.createQuery(queryText);

		List<Long> results = query.getResultList();

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

	public List<Order> findByCriteria(final FindOrderCriteria findOrderCriteria, final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		String queryText = orderQueryBuilder.select().findOrderCriteria(findOrderCriteria).sortCriteria(sortCriteria).build();

		Query query = em.createQuery(queryText);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	public Long getByCriteriaCount(final FindOrderCriteria findOrderCriteria)
	{
		String queryText = orderQueryBuilder.selectForCount().findOrderCriteria(findOrderCriteria).build();

		Query query = em.createQuery(queryText);

		List<Long> results = query.getResultList();

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

	public List<OrderStatus> findAllOrderStatuses()
	{
		return this.findObjectsByNamedQuery(OrderStatusEntity.QueryNames.FIND_ALL);
	}

	public OrderStatus findOrderStatusById(final Integer id)
	{
		return em.find(OrderStatusEntity.class, id);
	}

}
