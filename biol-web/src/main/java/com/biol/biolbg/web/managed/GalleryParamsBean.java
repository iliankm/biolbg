package com.biol.biolbg.web.managed;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.biol.biolbg.web.util.Base;

import com.biol.biolbg.ejb.session.GroupFacade;
import com.biol.biolbg.entity.Group;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name = "GalleryParamsBean")
@SessionScoped
public class GalleryParamsBean extends Base {
	private Group paramGroup;
	private Producer paramProducer;
	
	@EJB
	private GroupFacade groupFacade; //= EJBLocator.getInstance().lookup(ItemFacade.class);
	
	@PostConstruct
	public final void postConstruct() {
		paramProducer = null;
		paramGroup = getRandomGroup();
	}
	
	public Group getParamGroup() {
		return paramGroup;
	}
	public void setParamGroup(Group paramGroup) {
		this.paramGroup = paramGroup;
	}
	public Producer getParamProducer() {
		return paramProducer;
	}
	public void setParamProducer(Producer paramProducer) {
		this.paramProducer = paramProducer;
	}
	
	private Group getRandomGroup() {
		return groupFacade.getRandomGroup();
	}

}
