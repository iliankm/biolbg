package com.biol.biolbg.business.util;

import java.io.Serializable;

public class FindItemCriteria implements Serializable
{

	private static final long serialVersionUID = -7381842480344025090L;

	private Integer producerId;

	private Integer groupId;

	private String name;
	
	public FindItemCriteria(final Integer producerId, final Integer groupId, final String name)
	{
		this.producerId = producerId;

		this.groupId = groupId;

		this.name = name;
	}

	public Integer getProducerId()
	{
		return this.producerId;
	}

	public Integer getGroupId()
	{
		return this.groupId;
	}

	public String getName()
	{
		return this.name;
	}

}
