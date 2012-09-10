package com.biol.biolbg.entity;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="version")
	@Version
	private int version;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name="producer_id")
	private Producer producer;
	
	@Column(name="namebg")
	private String namebg;
	
	@Column(name="nameen")
	private String nameen;
	
	@Column(name="descriptionbg")
	private String descriptionbg;
	
	@Column(name="descriptionen")
	private String descriptionen;
	
	@Column(name="packingbg")
	private String packingbg;
	
	@Column(name="packingen")
	private String packingen;
	
	@Column(name="measureunitbg")
	private String measureunitbg;
	
	@Column(name="measureuniten")
	private String measureuniten;
	
	@Column(name="amountinpacking")
	private Double amountinpacking;
	
	@Column(name="priceforpacking")
	private Double priceforpacking;
	
	@Column(name="promotion")
	private int promotion;
	
	@Column(name="newitem")
	private int newitem;
	
	@Column(name="bestsell")
	private int bestsell;
	
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Producer getProducer() {
		return producer;
	}
	public void setProducer(Producer producer) {
		this.producer = producer;
	}
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
	public String getDescriptionbg() {
		return descriptionbg;
	}
	public void setDescriptionbg(String descriptionbg) {
		this.descriptionbg = descriptionbg;
	}
	public String getDescriptionen() {
		return descriptionen;
	}
	public void setDescriptionen(String descriptionen) {
		this.descriptionen = descriptionen;
	}
	public String getPackingbg() {
		return packingbg;
	}
	public void setPackingbg(String packingbg) {
		this.packingbg = packingbg;
	}
	public String getPackingen() {
		return packingen;
	}
	public void setPackingen(String packingen) {
		this.packingen = packingen;
	}
	public String getMeasureunitbg() {
		return measureunitbg;
	}
	public void setMeasureunitbg(String measureunitbg) {
		this.measureunitbg = measureunitbg;
	}
	public String getMeasureuniten() {
		return measureuniten;
	}
	public void setMeasureuniten(String measureuniten) {
		this.measureuniten = measureuniten;
	}
	public Double getAmountinpacking() {
		return amountinpacking;
	}
	public void setAmountinpacking(Double amountinpacking) {
		this.amountinpacking = amountinpacking;
	}
	public Double getPriceforpacking() {
		return priceforpacking;
	}
	public void setPriceforpacking(Double priceforpacking) {
		this.priceforpacking = priceforpacking;
	}
	public void setPromotion(int promotion) {
		this.promotion = promotion;
	}
	public int getPromotion() {
		return promotion;
	}
	public void setNewitem(int newitem) {
		this.newitem = newitem;
	}
	public int getNewitem() {
		return newitem;
	}
	public void setBestsell(int bestsell) {
		this.bestsell = bestsell;
	}
	public int getBestsell() {
		return bestsell;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getVersion() {
		return version;
	}

}
