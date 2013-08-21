package com.biol.biolbg.business.entity;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="usr")
@NamedQueries({
	@NamedQuery(name=UsrEntity.QueryNames.GET_ALL_USERS_COUNT,
		query="SELECT COUNT(o.id) FROM UsrEntity o"),

	@NamedQuery(name=UsrEntity.QueryNames.FIND_USER_BY_USERNAME,
		query="SELECT o FROM UsrEntity o WHERE (o.username=:username)"),

	@NamedQuery(name=UsrEntity.QueryNames.FIND_BY_ADMIN_FLAG,
		query="SELECT o FROM UsrEntity o WHERE o.adminflag=:adminflag")
})
public class UsrEntity extends BaseEntity implements Usr
{

	private static final long serialVersionUID = 5352163629701855656L;

	public interface QueryNames
	{
		public static final String GET_ALL_USERS_COUNT = "UsrEntity.getAllUsersCount";
		public static final String FIND_USER_BY_USERNAME = "UsrEntity.findUserByUsername";
		public static final String FIND_BY_ADMIN_FLAG = "UsrEntity.findByAdminFlag";
	}

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

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public void setUsername(String username)
	{
		this.username = username;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String getFullname()
	{
		return fullname;
	}

	@Override
	public void setFullname(String fullname)
	{
		this.fullname = fullname;
	}

	@Override
	public String getOrganisation()
	{
		return organisation;
	}

	@Override
	public void setOrganisation(String organisation)
	{
		this.organisation = organisation;
	}

	@Override
	public String getEmail()
	{
		return email;
	}

	@Override
	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public Date getLastlogindate()
	{
		return lastlogindate;
	}

	@Override
	public void setLastlogindate(Date lastlogindate)
	{
		this.lastlogindate = lastlogindate;
	}

	@Override
	public Date getLastlogintime()
	{
		return lastlogintime;
	}

	@Override
	public void setLastlogintime(Date lastlogintime)
	{
		this.lastlogintime = lastlogintime;
	}

	@Override
	public String getLastloginip()
	{
		return lastloginip;
	}

	@Override
	public void setLastloginip(String lastloginip)
	{
		this.lastloginip = lastloginip;
	}

	@Override
	public int getAdminflag()
	{
		return adminflag;
	}

	@Override
	public void setAdminflag(int adminflag)
	{
		this.adminflag = adminflag;
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
