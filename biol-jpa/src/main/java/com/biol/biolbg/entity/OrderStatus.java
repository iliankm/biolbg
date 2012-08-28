package com.biol.biolbg.entity;

import javax.persistence.*;

@Entity
@Table(name="orderstatus")
public class OrderStatus extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String namebg;
	private String nameen;
	
	public String getNamebg() {
		return namebg;
	}
	public void setNamebg(String namebg) {
		this.namebg = namebg;
	}
	public String getNameen() {
		return nameen;
	}
	public void setNameen(String nameen) {
		this.nameen = nameen;
	}
	
	

}