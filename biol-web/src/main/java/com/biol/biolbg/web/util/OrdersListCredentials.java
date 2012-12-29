package com.biol.biolbg.web.util;

import java.io.Serializable;
import java.util.Date;

public class OrdersListCredentials implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Date fromDate = null;

	private Date toDate = null;

	private String username = null;

	private String organisation = null;

	public Date getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}

	public Date getToDate()
	{
		return toDate;
	}

	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getOrganisation()
	{
		return organisation;
	}

	public void setOrganisation(String organisation)
	{
		this.organisation = organisation;
	}
}
