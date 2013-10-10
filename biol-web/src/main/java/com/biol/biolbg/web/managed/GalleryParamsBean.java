package com.biol.biolbg.web.managed;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.GroupFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.web.util.Base;


@Named("GalleryParamsBean")
@SessionScoped
public class GalleryParamsBean extends Base implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Group paramGroup;

	private Producer paramProducer;

	@EJB
	private GroupFacade groupFacade;

	@PostConstruct
	public void postConstruct()
	{
		paramProducer = null;
		paramGroup = getRandomGroup();
	}

	public Group getParamGroup()
	{
		return paramGroup;
	}

	public void setParamGroup(Group paramGroup)
	{
		this.paramGroup = paramGroup;
	}

	public Producer getParamProducer()
	{
		return paramProducer;
	}

	public void setParamProducer(Producer paramProducer)
	{
		this.paramProducer = paramProducer;
	}

	private Group getRandomGroup()
	{
		return groupFacade.getRandomGroup();
	}
}
