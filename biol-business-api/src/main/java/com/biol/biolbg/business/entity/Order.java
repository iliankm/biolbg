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

	public OrderStatus getStatus();

	public List<? extends OrderRow> getRows();

	@Transient
	public Double getTotalValue();

	public void setDeliveryAddress(String deliveryAddress);

	public String getDeliveryAddress();

	public int getVersion();

	public int getSeenbyadmin();

}