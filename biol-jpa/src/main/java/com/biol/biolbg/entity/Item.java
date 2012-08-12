package com.biol.biolbg.entity;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private int version;
	private Group group;
	private Producer producer;
	private String namebg;
	private String nameen;
	private String descriptionbg;
	private String descriptionen;
	private String packingbg;
	private String packingen;
	private String measureunitbg;
	private String measureuniten;
	private Double amountinpacking;
	private Double priceforpacking;
	private int promotion;
	private int newitem;
	private int bestsell;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	@ManyToOne
	@JoinColumn(name="producer_id")
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
	@Version
	public int getVersion() {
		return version;
	}

}
