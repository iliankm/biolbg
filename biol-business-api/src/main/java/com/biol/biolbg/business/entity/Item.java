package com.biol.biolbg.business.entity;

public interface Item
{
	public int getId();

	public Group getGroup();

	public Producer getProducer();

	public String getNamebg();

	public void setNamebg(String namebg);

	public String getNameen();

	public void setNameen(String nameen);

	public String getDescriptionbg();

	public void setDescriptionbg(String descriptionbg);

	public String getDescriptionen();

	public void setDescriptionen(String descriptionen);

	public String getPackingbg();

	public void setPackingbg(String packingbg);

	public String getPackingen();

	public void setPackingen(String packingen);

	public String getMeasureunitbg();

	public void setMeasureunitbg(String measureunitbg);

	public String getMeasureuniten();

	public void setMeasureuniten(String measureuniten);

	public Double getAmountinpacking();

	public void setAmountinpacking(Double amountinpacking);

	public Double getPriceforpacking();

	public void setPriceforpacking(Double priceforpacking);

	public void setPromotion(int promotion);

	public int getPromotion();

	public void setNewitem(int newitem);

	public int getNewitem();

	public void setBestsell(int bestsell);

	public int getBestsell();

	public int getVersion();

}