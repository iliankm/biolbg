package com.biol.biolbg.business.entity;

import javax.persistence.Transient;

public interface OrderRow
{
	public int getId();

	public Order getOrder();

	public void setOrder(Order order);

	public Item getItem();

	public void setItem(Item item);

	public Double getAmount();

	public void setAmount(Double amount);

	public Double getPrice();

	public void setPrice(Double price);

	@Transient
	public Double getValue();

	public void setId(int id);

}