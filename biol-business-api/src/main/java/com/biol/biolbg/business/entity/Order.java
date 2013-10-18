package com.biol.biolbg.business.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

public interface Order
{
	public int getId();

	public Date getPostdate();

	public void setPostdate(Date postdate);

	public Date getPosttime();

	public void setPosttime(Date posttime);

	public Date getFordate();

	public void setFordate(Date fordate);

	public Date getFortime();

	public void setFortime(Date fortime);

	public Usr getUser();

	public void setUser(Usr user);

	public OrderStatus getStatus();

	public void setStatus(OrderStatus status);

	public List<OrderRow> getRows();

	public void addRow(OrderRow row);

	public void removeRow(OrderRow row);

	@Transient
	public Double getTotalValue();

	public void setDeliveryAddress(String deliveryAddress);

	public String getDeliveryAddress();

	public int getVersion();

	public int getSeenbyadmin();

	public void setSeenbyadmin(int seenbyadmin);

	public void setVersion(int version);

	public void setId(int id);

}