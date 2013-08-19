package com.biol.biolbg.business.entity;

import java.util.Date;

public interface Usr
{
	public int getId();

	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	public String getFullname();

	public void setFullname(String fullname);

	public String getOrganisation();

	public void setOrganisation(String organisation);

	public String getEmail();

	public void setEmail(String email);

	public Date getLastlogindate();

	public void setLastlogindate(Date lastlogindate);

	public Date getLastlogintime();

	public void setLastlogintime(Date lastlogintime);

	public String getLastloginip();

	public void setLastloginip(String lastloginip);

	public int getAdminflag();

	public void setAdminflag(int adminflag);

	public int getVersion();

}