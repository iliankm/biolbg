package com.biol.biolbg.business.entity;

import javax.persistence.*;

@Entity
@Table(name = "homeinfo")
public class HomeInfoEntity extends BaseEntity implements HomeInfo
{

	private static final long serialVersionUID = -8713362248865289063L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="version")
	@Version
	private int version;

	@Column(name="headertextbg")
	private String headertextbg;

	@Column(name="infotextbg")
	private String infotextbg;

	@Column(name="headertexten")
	private String headertexten;

	@Column(name="infotexten")
	private String infotexten;

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
	public String getHeadertextbg()
	{
		return headertextbg;
	}

	@Override
	public void setHeadertextbg(String headertextbg)
	{
		this.headertextbg = headertextbg;
	}

	@Override
	public String getInfotextbg()
	{
		return infotextbg;
	}

	@Override
	public void setInfotextbg(String infotextbg)
	{
		this.infotextbg = infotextbg;
	}

	@Override
	public String getHeadertexten()
	{
		return headertexten;
	}

	@Override
	public void setHeadertexten(String headertexten)
	{
		this.headertexten = headertexten;
	}

	@Override
	public String getInfotexten()
	{
		return infotexten;
	}

	@Override
	public void setInfotexten(String infotexten)
	{
		this.infotexten = infotexten;
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((headertextbg == null) ? 0 : headertextbg.hashCode());
		result = prime * result
				+ ((headertexten == null) ? 0 : headertexten.hashCode());
		result = prime * result
				+ ((infotextbg == null) ? 0 : infotextbg.hashCode());
		result = prime * result
				+ ((infotexten == null) ? 0 : infotexten.hashCode());

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

		HomeInfoEntity other = (HomeInfoEntity) obj;
		if (headertextbg == null)
		{
			if (other.headertextbg != null)
				return false;
		}
		else if (!headertextbg.equals(other.headertextbg))
			return false;

		if (headertexten == null)
		{
			if (other.headertexten != null)
				return false;
		}
		else if (!headertexten.equals(other.headertexten))
			return false;

		if (infotextbg == null)
		{
			if (other.infotextbg != null)
				return false;
		}
		else if (!infotextbg.equals(other.infotextbg))
			return false;

		if (infotexten == null)
		{
			if (other.infotexten != null)
				return false;
		}
		else if (!infotexten.equals(other.infotexten))
			return false;

		return true;
	}
}
