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
import com.biol.biolbg.business.entity.OrderRow;
import com.biol.biolbg.business.entity.OrderStatus;
import com.biol.biolbg.business.entity.Usr;
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
	public Order create(Order order)
	{
		java.util.Date today = new java.util.Date();
		order.setPostdate(new java.util.Date(today.getTime()));
		order.setPosttime(new java.util.Date(today.getTime()));

		if (order.getStatus() == null)
		{
			order.setStatus(orderDaoBean.findOrderStatusById(1));
		}

		return orderDaoBean.create(order);
	}

	@Override
	public Order createLocal(Usr user)
	{
		Order order = new OrderEntity();

		order.setUser(user);

		order.setStatus(orderDaoBean.findOrderStatusById(1));

		return order;
	}

	@Override
	public Order findById(Integer id)
	{
		return orderDaoBean.findById(id);
	}

	@Override
	public void deleteById(Integer id)
	{
		orderDaoBean.delete(id);
	}

	@Override
	public Order update(Order order)
	{
		return orderDaoBean.update(order);
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
	public OrderStatus findOrderStatusById(Integer id)
	{
		return orderDaoBean.findOrderStatusById(id);
	}

	@Override
	public Order updateStatus(Integer orderId, Integer newOrderStatusId)
	{
		Order order = orderDaoBean.findById(orderId);

		OrderStatus orderStatus = orderDaoBean.findOrderStatusById(newOrderStatusId);

		if (order != null && orderStatus != null)
		{
			order.setStatus(orderStatus);

			order = orderDaoBean.update(order);

			return order;
		}

		return null;
	}

	@Override
	public Order cancelOrder(Integer orderId)
	{
		Order order = orderDaoBean.findById(orderId);

		OrderStatus canceledStatus = orderDaoBean.findOrderStatusById(5);

		if (order != null && canceledStatus != null)
		{
			order.setStatus(canceledStatus);

			orderDaoBean.update(order);

			return order;
		}

		return null;
	}

	@Override
	public String getLastDeliveryAddressForUser(Integer userId)
	{
		FindOrderCriteria findOrderCriteria = new FindOrderCriteria(null, null, userId, null, null);

		SortCriteria sortCriteria = new SortCriteria("o.id", SortCriteria.DIRECTION_DESC);

		List<Order> orders = orderDaoBean.findByCriteria(findOrderCriteria, 1, 1, sortCriteria);

		if (orders != null && orders.size() > 0)
		{
			return orders.get(0).getDeliveryAddress();
		}

		return "";
	}

	@Override
	public Order markOrderAsSeenByAdmin(Integer orderId)
	{
		Order order = orderDaoBean.findById(orderId);

		if (order != null)
		{
			order.setSeenbyadmin(1);

			order = orderDaoBean.update(order);

			return order;
		}

		return null;
	}

	@Override
	public void deleteRowByArticleIdLocal(Order order, Integer articleId)
	{
		Iterator<OrderRow> iterator = order.getRows().iterator();

		while (iterator.hasNext())
		{
			OrderRow orderrow = iterator.next();
			if (Integer.valueOf(orderrow.getItem().getId()).equals(articleId))
			{
				order.removeRow(orderrow);
				break;
			}
		}
	}

	@Override
	public void incrementArticleAmountLocal(Order order, Integer articleId)
	{
		OrderRow orderRowEntityForTheArticle = orderServiceBean.getOrderRowForArticleId(order, articleId);

		if (orderRowEntityForTheArticle == null)
		{
			orderRowEntityForTheArticle = orderServiceBean.addOrderRowLocal(order, articleId);
		}

		double am = orderRowEntityForTheArticle.getAmount().doubleValue() + 1;

		orderRowEntityForTheArticle.setAmount(am);
	}

	@Override
	public void decrementArticleAmountLocal(Order order, Integer articleId)
	{
		OrderRow orderRowEntityForTheArticle = orderServiceBean.getOrderRowForArticleId(order, articleId);

		if (orderRowEntityForTheArticle != null)
		{
			double am = orderRowEntityForTheArticle.getAmount().doubleValue() - 1;

			orderRowEntityForTheArticle.setAmount(am);

			if (orderRowEntityForTheArticle.getAmount().doubleValue() <= 0)
			{
				orderServiceBean.removeOrderRowLocal(order, orderRowEntityForTheArticle);
			}
		}
	}

}
