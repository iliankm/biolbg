package com.biol.biolbg.entity;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="orderm")
public class Order extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@Column(name="version")
	@Version
	private int version;

	@Column(name="postdate")
	@Temporal(DATE)
	private Date postdate;

	@Column(name="posttime")
	@Temporal(TIME)
	private Date posttime;

	@Column(name="fordate")
	@Temporal(DATE)
	private Date fordate;

	@Column(name="fortime")
	@Temporal(TIME)
	private Date fortime;

	@ManyToOne
	@JoinColumn(name="usr_id")
	private Usr user;

	@ManyToOne
	@JoinColumn(name="orderstatus_id")
	private OrderStatus status;

	@Column(name="deliveryAddress")
	private String deliveryAddress;

	@Column(name="seenbyadmin")
	private int seenbyadmin;

	@OneToMany(cascade={CascadeType.ALL},
			fetch=FetchType.EAGER,
			mappedBy="order")
	private List<OrderRow> rows;

	public Date getPostdate()
	{
		return postdate;
	}

	public void setPostdate(Date postdate)
	{
		this.postdate = postdate;
	}

	public Date getPosttime()
	{
		return posttime;
	}

	public void setPosttime(Date posttime)
	{
		this.posttime = posttime;
	}

	public Date getFordate()
	{
		return fordate;
	}

	public void setFordate(Date fordate)
	{
		this.fordate = fordate;
	}

	public Date getFortime()
	{
		return fortime;
	}

	public void setFortime(Date fortime)
	{
		this.fortime = fortime;
	}

	public Usr getUser()
	{
		return user;
	}

	public void setUser(Usr user)
	{
		this.user = user;
	}

	public OrderStatus getStatus()
	{
		return status;
	}

	public void setStatus(OrderStatus status)
	{
		this.status = status;
	}

	public List<OrderRow> getRows()
	{
		return rows;
	}

	public void setRows(List<OrderRow> rows)
	{
		this.rows = rows;
	}

	@Transient
	public Double getTotalValue()
	{
		Double res = 0.0;
		Iterator<OrderRow> iter = rows.iterator();

		while (iter.hasNext())
		{
			OrderRow orderrow = iter.next();
			res = res + orderrow.getValue();
		}

		return res;
	}

	public void setDeliveryAddress(String deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryAddress()
	{
		return deliveryAddress;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public int getVersion()
	{
		return version;
	}

	public void setSeenbyadmin(int seenbyadmin)
	{
		this.seenbyadmin = seenbyadmin;
	}

	public int getSeenbyadmin()
	{
		return seenbyadmin;
	}
}
