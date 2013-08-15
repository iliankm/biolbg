package com.biol.biolbg.business.entity;

import javax.persistence.*;

@Entity
@Table(name="orderstatus")
public class OrderStatus extends BaseEntity
{

	private static final long serialVersionUID = 7162974088599900550L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="namebg")
	private String namebg;

	@Column(name="nameen")
	private String nameen;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namebg == null) ? 0 : namebg.hashCode());
		result = prime * result + ((nameen == null) ? 0 : nameen.hashCode());
		
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
		
		OrderStatus other = (OrderStatus) obj;
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
		
		return true;
	}
}
