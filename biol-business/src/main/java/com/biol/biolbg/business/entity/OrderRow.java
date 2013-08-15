package com.biol.biolbg.business.entity;

import javax.persistence.*;

@Entity
@Table(name="orderrow")
public class OrderRow extends BaseEntity
{

	private static final long serialVersionUID = 5010480686115702684L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item;

	@Column(name="amount")
	private Double amount;

	@Column(name="price")
	private Double price;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	public Item getItem()
	{
		return item;
	}

	public void setItem(Item item)
	{
		this.item = item;
	}

	public Double getAmount()
	{
		return amount;
	}

	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	@Transient
	public Double getValue()
	{
		return amount*price;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		OrderRow other = (OrderRow) obj;
		if (amount == null) 
		{
			if (other.amount != null)
				return false;
			
		} else if (!amount.equals(other.amount))
			return false;
		
		if (item == null) 
		{
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		
		if (price == null) 
		{
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		
		return true;
	}
}
