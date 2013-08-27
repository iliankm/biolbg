package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.biol.biolbg.business.control.dao.OrderDaoBean;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.OrderEntity;
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

	@Override
	public OrderEntity create(final Order order)
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
	public OrderEntity findById(final Integer id)
	{
		return orderDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		orderDaoBean.delete(id);
	}

	@Override
	public OrderEntity update(final Order order)
	{
		return orderDaoBean.update((OrderEntity)order);
	}

	@Override
	public List<OrderEntity> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return orderDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria);
	}

	@Override
	public Long getAllCount()
	{
		return orderDaoBean.getAllCount();
	}

	@Override
	public List<OrderEntity> findByCriteria(final FindOrderCriteria findItemCriteria, final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return orderDaoBean.findByCriteria(findItemCriteria, maxResultsLimit, firstResult, sortCriteria);
	}

	@Override
	public Long getByCriteriaCount(final FindOrderCriteria findOrderCriteria)
	{
		return orderDaoBean.getByCriteriaCount(findOrderCriteria);
	}

	@Override
	public List<OrderStatusEntity> findAllOrderStatuses()
	{
		return orderDaoBean.findAllOrderStatuses();
	}

	@Override
	public OrderStatusEntity findOrderStatusById(final Integer id)
	{
		return orderDaoBean.findOrderStatusById(id);
	}

	@Override
	public OrderEntity updateStatus(final Integer orderId, final Integer newOrderStatusId)
	{
		OrderEntity orderEntity = orderDaoBean.findById(orderId);

		OrderStatusEntity orderStatusEntity = orderDaoBean.findOrderStatusById(newOrderStatusId);

		if (orderEntity != null && orderStatusEntity != null)
		{
			orderEntity.setStatus(orderStatusEntity);

			orderDaoBean.update(orderEntity);

			return orderEntity;
		}

		return null;
	}

	@Override
	public OrderEntity cancelOrder(final Integer orderId)
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
	public String getLastDeliveryAddressForUser(final Integer userId)
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
	public Order markOrderAsSeenByAdmin(final Integer orderId)
	{
		OrderEntity orderEntity = orderDaoBean.findById(orderId);

		if (orderEntity != null)
		{
			orderEntity.setSeenbyadmin(1);

			orderDaoBean.update(orderEntity);

			return orderEntity;
		}

		return null;
	}

}
