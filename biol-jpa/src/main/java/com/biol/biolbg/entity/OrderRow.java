package com.biol.biolbg.entity;

import javax.persistence.*;

@Entity
@Table(name="orderrow")
public class OrderRow extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Order order;
	private Item item;
	private Double amount;
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@ManyToOne
	@JoinColumn(name="item_id")
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getValue() {
		return amount*price;
	}

}
