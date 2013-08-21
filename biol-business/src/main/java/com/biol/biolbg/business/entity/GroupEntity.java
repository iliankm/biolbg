package com.biol.biolbg.business.entity;

import javax.persistence.*;

@Entity
@Table(name="grp")
public class GroupEntity extends BaseEntity implements Group
{

	private static final long serialVersionUID = 7833652000774985122L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="version")
	@Version
	private int version;

	@Column(name="namebg")
	private String namebg;

	@Column(name="nameen")
	private String nameen;

	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public String getNamebg()
	{
		return namebg;
	}

	@Override
	public void setNamebg(String namebg)
	{
		this.namebg = namebg;
	}

	@Override
	public String getNameen()
	{
		return nameen;
	}

	@Override
	public void setNameen(String nameen)
	{
		this.nameen = nameen;
	}

	@Override
	public void setVersion(int version)
	{
		this.version = version;
	}

	@Override
	public int getVersion()
	{
		return version;
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

		GroupEntity other = (GroupEntity) obj;
		if (namebg == null)
		{
			if (other.namebg != null)
				return false;
		}
		else if (!namebg.equals(other.namebg))
			return false;

		if (nameen == null)
		{
			if (other.nameen != null)
				return false;
		}
		else if (!nameen.equals(other.nameen))
			return false;

		return true;
	}

}