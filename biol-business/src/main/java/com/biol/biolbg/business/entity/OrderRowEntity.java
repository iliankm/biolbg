package com.biol.biolbg.business.entity;

import javax.persistence.*;

@Entity
@Table(name="orderrow")
public class OrderRowEntity extends BaseEntity implements OrderRow
{

	private static final long serialVersionUID = 5010480686115702684L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="order_id")
	private OrderEntity order;

	@ManyToOne
	@JoinColumn(name="item_id")
	private ItemEntity item;

	@Column(name="amount")
	private Double amount;

	@Column(name="price")
	private Double price;

	@Override
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public OrderEntity getOrder()
	{
		return order;
	}

	public void setOrder(OrderEntity order)
	{
		this.order = order;
	}

	@Override
	public ItemEntity getItem()
	{
		return item;
	}

	public void setItem(ItemEntity item)
	{
		this.item = item;
	}

	@Override
	public Double getAmount()
	{
		return amount;
	}

	@Override
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	@Override
	public Double getPrice()
	{
		return price;
	}

	@Override
	public void setPrice(Double price)
	{
		this.price = price;
	}

	@Override
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

		OrderRowEntity other = (OrderRowEntity) obj;
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
