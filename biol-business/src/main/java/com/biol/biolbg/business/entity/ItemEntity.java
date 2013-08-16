package com.biol.biolbg.business.entity;

import javax.persistence.*;

@Entity
@Table(name="item")
public class ItemEntity extends BaseEntity
{

	private static final long serialVersionUID = 6077654513083484055L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="version")
	@Version
	private int version;

	@ManyToOne
	@JoinColumn(name="group_id")
	private GroupEntity group;

	@ManyToOne
	@JoinColumn(name="producer_id")
	private ProducerEntity producer;

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

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public GroupEntity getGroup()
	{
		return group;
	}

	public void setGroup(GroupEntity group)
	{
		this.group = group;
	}

	public ProducerEntity getProducer()
	{
		return producer;
	}

	public void setProducer(ProducerEntity producer)
	{
		this.producer = producer;
	}

	public String getNamebg()
	{
		return namebg;
	}

	public void setNamebg(String namebg)
	{
		this.namebg = namebg;
	}

	public String getNameen()
	{
		return nameen;
	}

	public void setNameen(String nameen)
	{
		this.nameen = nameen;
	}

	public String getDescriptionbg()
	{
		return descriptionbg;
	}

	public void setDescriptionbg(String descriptionbg)
	{
		this.descriptionbg = descriptionbg;
	}

	public String getDescriptionen()
	{
		return descriptionen;
	}

	public void setDescriptionen(String descriptionen)
	{
		this.descriptionen = descriptionen;
	}

	public String getPackingbg()
	{
		return packingbg;
	}

	public void setPackingbg(String packingbg)
	{
		this.packingbg = packingbg;
	}

	public String getPackingen()
	{
		return packingen;
	}

	public void setPackingen(String packingen)
	{
		this.packingen = packingen;
	}

	public String getMeasureunitbg()
	{
		return measureunitbg;
	}

	public void setMeasureunitbg(String measureunitbg)
	{
		this.measureunitbg = measureunitbg;
	}

	public String getMeasureuniten()
	{
		return measureuniten;
	}

	public void setMeasureuniten(String measureuniten)
	{
		this.measureuniten = measureuniten;
	}

	public Double getAmountinpacking()
	{
		return amountinpacking;
	}

	public void setAmountinpacking(Double amountinpacking)
	{
		this.amountinpacking = amountinpacking;
	}

	public Double getPriceforpacking()
	{
		return priceforpacking;
	}

	public void setPriceforpacking(Double priceforpacking)
	{
		this.priceforpacking = priceforpacking;
	}

	public void setPromotion(int promotion)
	{
		this.promotion = promotion;
	}

	public int getPromotion()
	{
		return promotion;
	}

	public void setNewitem(int newitem)
	{
		this.newitem = newitem;
	}

	public int getNewitem()
	{
		return newitem;
	}

	public void setBestsell(int bestsell)
	{
		this.bestsell = bestsell;
	}

	public int getBestsell()
	{
		return bestsell;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public int getVersion()
	{
		return version;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((amountinpacking == null) ? 0 : amountinpacking.hashCode());
		result = prime * result
				+ ((measureunitbg == null) ? 0 : measureunitbg.hashCode());
		result = prime * result
				+ ((measureuniten == null) ? 0 : measureuniten.hashCode());
		result = prime * result + ((namebg == null) ? 0 : namebg.hashCode());
		result = prime * result + ((nameen == null) ? 0 : nameen.hashCode());
		result = prime * result
				+ ((packingbg == null) ? 0 : packingbg.hashCode());
		result = prime * result
				+ ((packingen == null) ? 0 : packingen.hashCode());
		
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
		
		ItemEntity other = (ItemEntity) obj;
		if (amountinpacking == null) 
		{
			if (other.amountinpacking != null)
				return false;
		} else if (!amountinpacking.equals(other.amountinpacking))
			return false;
		
		if (measureunitbg == null) 
		{
			if (other.measureunitbg != null)
				return false;
		} else if (!measureunitbg.equals(other.measureunitbg))
			return false;
		
		if (measureuniten == null) 
		{
			if (other.measureuniten != null)
				return false;
		} else if (!measureuniten.equals(other.measureuniten))
			return false;
		
		if (namebg == null) 
		{
			if (other.namebg != null)
				return false;
		} else if (!namebg.equals(other.namebg))
			return false;
		
		if (nameen == null) 
		{
			if (other.nameen != null)
				return false;
		} else if (!nameen.equals(other.nameen))
			return false;
		
		if (packingbg == null) 
		{
			if (other.packingbg != null)
				return false;
		} else if (!packingbg.equals(other.packingbg))
			return false;
		
		if (packingen == null) 
		{
			if (other.packingen != null)
				return false;
		} else if (!packingen.equals(other.packingen))
			return false;
		
		return true;
	}
}
