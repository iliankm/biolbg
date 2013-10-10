package com.biol.biolbg.business.util;

import java.io.Serializable;

public class SortCriteria implements Serializable
{
	private static final long serialVersionUID = 1L;

	public final static int DIRECTION_ASC = 1;

	public final static int DIRECTION_DESC = -1;

	private String propertyName;

	private int sortDirection;

	public SortCriteria(String propertyName, int direction)
	{
		this.propertyName = propertyName;

		this.sortDirection = direction;
	}

	public SortCriteria(String propertyName, String direction)
	{
		this.propertyName = propertyName;

		if ("asc".equals(direction))
		{
			this.sortDirection = DIRECTION_ASC;
		}
		else
		{
			if ("desc".equals(direction))
			{
				this.sortDirection = DIRECTION_DESC;
			}
		}
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public int getSortDirection()
	{
		return sortDirection;
	}

	public String getSortDirectionForJPA()
	{
		if (sortDirection == SortCriteria.DIRECTION_ASC)
		{
			return "asc";
		}
		else
		{
			if (sortDirection == SortCriteria.DIRECTION_DESC)
			{
				return "desc";
			}
		}

		return "";
	}

}
