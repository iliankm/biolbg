package com.biol.biolbg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="producer")
public class Producer extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@Column(name="version")
	@Version
	private int version;

	@Column(name="namebg")
	private String namebg;

	@Column(name="nameen")
	private String nameen;

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

	public void setVersion(int version)
	{
		this.version = version;
	}
	public int getVersion()
	{
		return version;
	}
}
