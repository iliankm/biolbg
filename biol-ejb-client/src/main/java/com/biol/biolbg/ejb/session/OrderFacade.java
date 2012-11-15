package com.biol.biolbg.ejb.session;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.entity.Order;
import com.biol.biolbg.entity.OrderStatus;

@Local
public interface OrderFacade {
	public void addItem(Order item);
	public void removeItem(Integer id);
	public void updateItem(Order item);
	public Order findItem(Integer id);
	public Order createNewItem();
	public List<Order> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType, Date fromDate, Date toDate, String username, String organisation);
	public Long getAllItemsCount(Date fromDate, Date toDate, String username, String organisation);
	public List<OrderStatus> getAllOrderStatus();
	public OrderStatus findOrderStatus(Integer id);
	public Order updateStatus(Integer orderId, OrderStatus newStatus);
	public List<Order> getOrdersForUser(Integer fromRow, Integer maxResults, String sortBy, String sortType, Date fromDate, Date toDate, Integer userId);
	public Long getOrdersForUserCount(Date fromDate, Date toDate, Integer userId);
	public Order cancelOrder(Integer orderId);
	public String getLastDeliveryAddressForUser(Integer userId);
	public Order markOrderAsSeenByAdmin(Integer orderId);
}

