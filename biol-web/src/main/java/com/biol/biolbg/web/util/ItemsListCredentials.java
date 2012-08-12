package com.biol.biolbg.web.util;

import java.io.Serializable;

import com.biol.biolbg.entity.Group;
import com.biol.biolbg.entity.Producer;
public class ItemsListCredentials implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Group group;
	private Producer producer;
	private String name;
	
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Producer getProducer() {
		return producer;
	}
	public void setProducer(Producer producer) {
		this.producer = producer;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}
