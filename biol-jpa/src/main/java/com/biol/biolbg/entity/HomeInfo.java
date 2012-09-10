package com.biol.biolbg.entity;

import javax.persistence.*;

@Entity
@Table(name = "homeinfo")
public class HomeInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
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
	
	public String getHeadertextbg() {
		return headertextbg;
	}
	public void setHeadertextbg(String headertextbg) {
		this.headertextbg = headertextbg;
	}
	public String getInfotextbg() {
		return infotextbg;
	}
	public void setInfotextbg(String infotextbg) {
		this.infotextbg = infotextbg;
	}
	public String getHeadertexten() {
		return headertexten;
	}
	public void setHeadertexten(String headertexten) {
		this.headertexten = headertexten;
	}
	public String getInfotexten() {
		return infotexten;
	}
	public void setInfotexten(String infotexten) {
		this.infotexten = infotexten;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getVersion() {
		return version;
	}
	

}
