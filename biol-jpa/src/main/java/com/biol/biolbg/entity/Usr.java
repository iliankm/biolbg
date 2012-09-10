package com.biol.biolbg.entity;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="usr")
public class Usr extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
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
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLastlogindate() {
		return lastlogindate;
	}
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	public Date getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	public String getLastloginip() {
		return lastloginip;
	}
	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}
	public int getAdminflag() {
		return adminflag;
	}
	public void setAdminflag(int adminflag) {
		this.adminflag = adminflag;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getVersion() {
		return version;
	}

}
