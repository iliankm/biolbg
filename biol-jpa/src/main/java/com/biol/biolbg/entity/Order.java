package com.biol.biolbg.entity;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.Date;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.*;

@Entity
@Table(name="orderm")
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private int version;
	private Date postdate;
	private Date posttime;
	private Date fordate;
	private Date fortime;
	private Usr user;
	private OrderStatus status;
	private String deliveryAddress;
	private int seenbyadmin;
	private Collection<OrderRow> rows;
	
	@Temporal(DATE)
	public Date getPostdate() {
		return postdate;
	}
	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}
	@Temporal(TIME)
	public Date getPosttime() {
		return posttime;
	}
	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}
	@Temporal(DATE)
	public Date getFordate() {
		return fordate;
	}
	public void setFordate(Date fordate) {
		this.fordate = fordate;
	}
	@Temporal(TIME)
	public Date getFortime() {
		return fortime;
	}
	public void setFortime(Date fortime) {
		this.fortime = fortime;
	}
	@ManyToOne
	@JoinColumn(name="usr_id")
	public Usr getUser() {
		return user;
	}
	public void setUser(Usr user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="orderstatus_id")
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	@OneToMany(cascade={CascadeType.ALL},
				fetch=FetchType.EAGER,
				mappedBy="order")
	public Collection<OrderRow> getRows() {
		return rows;
	}
	public void setRows(Collection<OrderRow> rows) {
		this.rows = rows;
	}
	public Double getTotalValue() {
		Double res = 0.0;
		Iterator<OrderRow> iter = rows.iterator();
		while (iter.hasNext()) {
			OrderRow orderrow = iter.next();
			res = res + orderrow.getValue();
		}
		return res;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Version
	public int getVersion() {
		return version;
	}
	public void setSeenbyadmin(int seenbyadmin) {
		this.seenbyadmin = seenbyadmin;
	}
	public int getSeenbyadmin() {
		return seenbyadmin;
	}
	

}
