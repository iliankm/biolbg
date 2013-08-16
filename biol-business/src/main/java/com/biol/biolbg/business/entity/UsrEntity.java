package com.biol.biolbg.business.entity;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="usr")
public class UsrEntity extends BaseEntity
{
	
	private static final long serialVersionUID = 5352163629701855656L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="version")
	@Version
	private int version;

	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="fullname")
	private String fullname;

	@Column(name="organisation")
	private String organisation;

	@Column(name="email")
	private String email;

	@Column(name="lastlogindate")
	@Temporal(DATE)
	private Date lastlogindate;

	@Column(name="lastlogintime")
	@Temporal(TIME)
	private Date lastlogintime;

	@Column(name="lastloginip")
	private String lastloginip;

	@Column(name="adminflag")
	private int adminflag;

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFullname()
	{
		return fullname;
	}

	public void setFullname(String fullname)
	{
		this.fullname = fullname;
	}

	public String getOrganisation()
	{
		return organisation;
	}

	public void setOrganisation(String organisation)
	{
		this.organisation = organisation;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getLastlogindate()
	{
		return lastlogindate;
	}

	public void setLastlogindate(Date lastlogindate)
	{
		this.lastlogindate = lastlogindate;
	}

	public Date getLastlogintime()
	{
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime)
	{
		this.lastlogintime = lastlogintime;
	}

	public String getLastloginip()
	{
		return lastloginip;
	}

	public void setLastloginip(String lastloginip)
	{
		this.lastloginip = lastloginip;
	}

	public int getAdminflag()
	{
		return adminflag;
	}

	public void setAdminflag(int adminflag)
	{
		this.adminflag = adminflag;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result
				+ ((organisation == null) ? 0 : organisation.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		
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
		
		UsrEntity other = (UsrEntity) obj;
		if (email == null) 
		{
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		
		if (fullname == null) 
		{
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		
		if (organisation == null) 
		{
			if (other.organisation != null)
				return false;
		} else if (!organisation.equals(other.organisation))
			return false;
		
		if (username == null) 
		{
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		
		return true;
	}
}
