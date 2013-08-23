package com.biol.biolbg.business.util;

import java.io.Serializable;
import java.sql.Date;

public class FindOrderCriteria implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Date fromDate;

	private Date toDate;

	private Integer userId;
	
	private String username;
	
	private String organization;

	public FindOrderCriteria(final Date fromDate, final Date toDate, final Integer userId, final String username, final String organization)
	{
		this.fromDate = fromDate;

		this.toDate = toDate;
		
		this.userId = userId;

		this.username = username;

		this.organization = organization;
	}

	public Date getFromDate()
	{
		return fromDate;
	}

	public Date getToDate()
	{
		return toDate;
	}
	
	public Integer getUserId()
	{
		return userId;
	}

	public String getUsername()
	{
		return username;
	}

	public String getOrganization()
	{
		return organization;
	}

}
