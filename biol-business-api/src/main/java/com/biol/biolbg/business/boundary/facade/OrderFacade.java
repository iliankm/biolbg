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
	public Order create(Order order);

	public Order createLocal(Usr user);

	public Order findById(Integer id);

	public void deleteById(Integer id);

	public Order update(Order order);

	public List<Order> findAll(int maxResultsLimit,
			int firstResult, SortCriteria sortCriteria);

	public Long getAllCount();

	public List<Order> findByCriteria(
			FindOrderCriteria findItemCriteria,
			int maxResultsLimit, final int firstResult,
			SortCriteria sortCriteria);

	public Long getByCriteriaCount(FindOrderCriteria findOrderCriteria);

	public List<OrderStatus> findAllOrderStatuses();

	public OrderStatus findOrderStatusById(Integer id);

	public Order updateStatus(Integer orderId,
			Integer newOrderStatusId);

	public Order cancelOrder(Integer orderId);

	public String getLastDeliveryAddressForUser(Integer userId);

	public Order markOrderAsSeenByAdmin(Integer orderId);

	public void deleteRowByArticleIdLocal(Order order, Integer articleId);

	public void incrementArticleAmountLocal(Order order, Integer articleId);

	public void decrementArticleAmountLocal(Order order, Integer articleId);

}