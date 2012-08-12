package com.biol.biolbg.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	protected int id;
	protected Boolean checked;
	
	
	public void setId(int id) {
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	@Transient
	public Boolean getChecked() {
		return checked;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity baseent = (BaseEntity) obj;

		return (this.id == baseent.id);	}

}
