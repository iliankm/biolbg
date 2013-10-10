package com.biol.biolbg.business.boundary.facade;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.OrderDaoBean;
import com.biol.biolbg.business.control.service.OrderServiceBean;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.OrderEntity;
import com.biol.biolbg.business.entity.OrderRowEntity;
import com.biol.biolbg.business.entity.OrderStatus;
import com.biol.biolbg.business.entity.OrderStatusEntity;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.entity.UsrEntity;
import com.biol.biolbg.business.util.FindOrderCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class OrderFacadeBean implements OrderFacade
{
	@EJB
	private OrderDaoBean orderDaoBean;

	@EJB
	private OrderServiceBean orderServiceBean;

	@Override
	public OrderEntity create(Order order)
	{
		OrderEntity orderEntity = (OrderEntity)order;

		java.util.Date today = new java.util.Date();
		orderEntity.setPostdate(new java.util.Date(today.getTime()));
		orderEntity.setPosttime(new java.util.Date(today.getTime()));

		if (orderEntity.getStatus() == null)
		{
			orderEntity.setStatus(orderDaoBean.findOrderStatusById(1));
		}

		return orderDaoBean.create(orderEntity);
	}

	@Override
	public OrderEntity createLocal(Usr user)
	{
		OrderEntity orderEntity = new OrderEntity();

		orderEntity.setUser((UsrEntity)user);

		orderEntity.setStatus(orderDaoBean.findOrderStatusById(1));

		return orderEntity;
	}

	@Override
	public OrderEntity findById(Integer id)
	{
		return orderDaoBean.findById(id);
	}

	@Override
	public void deleteById(Integer id)
	{
		orderDaoBean.delete(id);
	}

	@Override
	public OrderEntity update(Order order)
	{
		return orderDaoBean.update((OrderEntity)order);
	}

	@Override
	public List<Order> findAll(int maxResultsLimit, int firstResult, SortCriteria sortCriteria)
	{
		return Collections.<Order>unmodifiableList(orderDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria));
	}

	@Override
	public Long getAllCount()
	{
		return orderDaoBean.getAllCount();
	}

	@Override
	public List<Order> findByCriteria(FindOrderCriteria findItemCriteria, int maxResultsLimit, int firstResult, SortCriteria sortCriteria)
	{
		return Collections.<Order>unmodifiableList(orderDaoBean.findByCriteria(findItemCriteria, maxResultsLimit, firstResult, sortCriteria));
	}

	@Override
	public Long getByCriteriaCount(FindOrderCriteria findOrderCriteria)
	{
		return orderDaoBean.getByCriteriaCount(findOrderCriteria);
	}

	@Override
	public List<OrderStatus> findAllOrderStatuses()
	{
		return Collections.<OrderStatus>unmodifiableList(orderDaoBean.findAllOrderStatuses());
	}

	@Override
	public OrderStatusEntity findOrderStatusById(Integer id)
	{
		return orderDaoBean.findOrderStatusById(id);
	}

	@Override
	public OrderEntity updateStatus(Integer orderId, Integer newOrderStatusId)
	{
		OrderEntity orderEntity = orderDaoBean.findById(orderId);

		OrderStatusEntity orderStatusEntity = orderDaoBean.findOrderStatusById(newOrderStatusId);

		if (orderEntity != null && orderStatusEntity != null)
		{
			orderEntity.setStatus(orderStatusEntity);

			orderEntity = orderDaoBean.update(orderEntity);

			return orderEntity;
		}

		return null;
	}

	@Override
	public OrderEntity cancelOrder(Integer orderId)
	{
		OrderEntity orderEntity = orderDaoBean.findById(orderId);

		OrderStatusEntity canceledStatusEntity = orderDaoBean.findOrderStatusById(5);

		if (orderEntity != null && canceledStatusEntity != null)
		{
			orderEntity.setStatus(canceledStatusEntity);

			orderDaoBean.update(orderEntity);

			return orderEntity;
		}

		return null;
	}

	@Override
	public String getLastDeliveryAddressForUser(Integer userId)
	{
		FindOrderCriteria findOrderCriteria = new FindOrderCriteria(null, null, userId, null, null);

		SortCriteria sortCriteria = new SortCriteria("o.id", SortCriteria.DIRECTION_DESC);

		List<OrderEntity> orders = orderDaoBean.findByCriteria(findOrderCriteria, 1, 1, sortCriteria);

		if (orders != null && orders.size() > 0)
		{
			return orders.get(1).getDeliveryAddress();
		}

		return "";
	}

	@Override
	public Order markOrderAsSeenByAdmin(Integer orderId)
	{
		OrderEntity orderEntity = orderDaoBean.findById(orderId);

		if (orderEntity != null)
		{
			orderEntity.setSeenbyadmin(1);

			orderEntity = orderDaoBean.update(orderEntity);

			return orderEntity;
		}

		return null;
	}

	@Override
	public void deleteRowByArticleIdLocal(Order order, Integer articleId)
	{
		OrderEntity orderEntity = (OrderEntity) order;

		Iterator<OrderRowEntity> iterator = orderEntity.getRowsEntities().iterator();

		while (iterator.hasNext())
		{
			OrderRowEntity orderrow = iterator.next();
			if (Integer.valueOf(orderrow.getItem().getId()).equals(articleId))
			{
				iterator.remove();
			}
		}
	}

	@Override
	public void incrementArticleAmountLocal(Order order, Integer articleId)
	{
		OrderEntity orderEntity = (OrderEntity) order;

		OrderRowEntity orderRowEntityForTheArticle = orderServiceBean.getOrderRowForArticleId(orderEntity, articleId);

		if (orderRowEntityForTheArticle == null)
		{
			orderRowEntityForTheArticle = orderServiceBean.addOrderRowLocal(orderEntity, articleId);
		}

		double am = orderRowEntityForTheArticle.getAmount().doubleValue() + 1;

		orderRowEntityForTheArticle.setAmount(am);
	}

	@Override
	public void decrementArticleAmountLocal(Order order, Integer articleId)
	{
		OrderEntity orderEntity = (OrderEntity) order;

		OrderRowEntity orderRowEntityForTheArticle = orderServiceBean.getOrderRowForArticleId(orderEntity, articleId);

		if (orderRowEntityForTheArticle != null)
		{
			double am = orderRowEntityForTheArticle.getAmount().doubleValue() - 1;

			orderRowEntityForTheArticle.setAmount(am);

			if (orderRowEntityForTheArticle.getAmount().doubleValue() <= 0)
			{
				orderServiceBean.removeOrderRowLocal(orderEntity, orderRowEntityForTheArticle);
			}
		}
	}

}
