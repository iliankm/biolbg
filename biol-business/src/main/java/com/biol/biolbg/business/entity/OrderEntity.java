package com.biol.biolbg.business.entity;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="orderm")
public class OrderEntity extends BaseEntity implements Order
{

	private static final long serialVersionUID = 5786594537574474297L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

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
	private UsrEntity user;

	@ManyToOne
	@JoinColumn(name="orderstatus_id")
	private OrderStatusEntity status;

	@Column(name="deliveryAddress")
	private String deliveryAddress;

	@Column(name="seenbyadmin")
	private int seenbyadmin;

	@OneToMany(cascade={CascadeType.ALL},
			fetch=FetchType.EAGER,
			mappedBy="order")
	private List<OrderRowEntity> rows;

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
	public Date getPostdate()
	{
		return postdate;
	}

	@Override
	public void setPostdate(Date postdate)
	{
		this.postdate = postdate;
	}

	@Override
	public Date getPosttime()
	{
		return posttime;
	}

	@Override
	public void setPosttime(Date posttime)
	{
		this.posttime = posttime;
	}

	@Override
	public Date getFordate()
	{
		return fordate;
	}

	@Override
	public void setFordate(Date fordate)
	{
		this.fordate = fordate;
	}

	@Override
	public Date getFortime()
	{
		return fortime;
	}

	@Override
	public void setFortime(Date fortime)
	{
		this.fortime = fortime;
	}

	@Override
	public UsrEntity getUser()
	{
		return user;
	}

	public void setUser(UsrEntity user)
	{
		this.user = user;
	}

	@Override
	public OrderStatusEntity getStatus()
	{
		return status;
	}

	public void setStatus(OrderStatusEntity status)
	{
		this.status = status;
	}

	@Override
	public List<? extends OrderRow> getRows()
	{
		return Collections.unmodifiableList(getRowsEntities());
	}

	public List<OrderRowEntity> getRowsEntities()
	{
		return rows == null ? new LinkedList<OrderRowEntity>() : rows;
	}

	@Override
	@Transient
	public Double getTotalValue()
	{
		Double res = 0.0;
		Iterator<OrderRowEntity> iter = rows.iterator();

		while (iter.hasNext())
		{
			OrderRow orderrow = iter.next();
			res = res + orderrow.getValue();
		}

		return res;
	}

	@Override
	public void setDeliveryAddress(String deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}

	@Override
	public String getDeliveryAddress()
	{
		return deliveryAddress;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	@Override
	public int getVersion()
	{
		return version;
	}

	public void setSeenbyadmin(int seenbyadmin)
	{
		this.seenbyadmin = seenbyadmin;
	}

	@Override
	public int getSeenbyadmin()
	{
		return seenbyadmin;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deliveryAddress == null) ? 0 : deliveryAddress.hashCode());
		result = prime * result + ((fordate == null) ? 0 : fordate.hashCode());
		result = prime * result + ((fortime == null) ? 0 : fortime.hashCode());
		result = prime * result
				+ ((postdate == null) ? 0 : postdate.hashCode());
		result = prime * result
				+ ((posttime == null) ? 0 : posttime.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());

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

		OrderEntity other = (OrderEntity) obj;
		if (deliveryAddress == null)
		{
			if (other.deliveryAddress != null)
				return false;
		} else if (!deliveryAddress.equals(other.deliveryAddress))
			return false;

		if (fordate == null)
		{
			if (other.fordate != null)
				return false;
		} else if (!fordate.equals(other.fordate))
			return false;

		if (fortime == null)
		{
			if (other.fortime != null)
				return false;
		} else if (!fortime.equals(other.fortime))
			return false;

		if (postdate == null)
		{
			if (other.postdate != null)
				return false;
		} else if (!postdate.equals(other.postdate))
			return false;

		if (posttime == null)
		{
			if (other.posttime != null)
				return false;
		} else if (!posttime.equals(other.posttime))
			return false;

		if (user == null)
		{
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;

		return true;
	}
}
