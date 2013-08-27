package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.OrderStatus;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.util.FindOrderCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Local
public interface OrderFacade
{
	public Order create(final Order order);

	public Order createLocal(Usr user);

	public Order findById(final Integer id);

	public void deleteById(final Integer id);

	public Order update(final Order order);

	public List<? extends Order> findAll(final int maxResultsLimit,
			final int firstResult, final SortCriteria sortCriteria);

	public Long getAllCount();

	public List<? extends Order> findByCriteria(
			final FindOrderCriteria findItemCriteria,
			final int maxResultsLimit, final int firstResult,
			final SortCriteria sortCriteria);

	public Long getByCriteriaCount(final FindOrderCriteria findOrderCriteria);

	public List<? extends OrderStatus> findAllOrderStatuses();

	public OrderStatus findOrderStatusById(final Integer id);

	public Order updateStatus(final Integer orderId,
			final Integer newOrderStatusId);

	public Order cancelOrder(final Integer orderId);

	public String getLastDeliveryAddressForUser(final Integer userId);

	public Order markOrderAsSeenByAdmin(final Integer orderId);

}