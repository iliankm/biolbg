package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.GroupFacade;
import com.biol.biolbg.business.boundary.facade.HomeInfoFacade;
import com.biol.biolbg.business.boundary.facade.ProducerFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.HomeInfo;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.util.Base;


@Named("HomeBean")
@RequestScoped
public class HomeBean extends Base implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<Group> groups;

	private List<Producer> producers;

	private List<HomeInfo> homeinfos;

	@Inject
	private ArticlesBean articlesBean;

	@Inject
	private AppBean appBean;

	@EJB
	private GroupFacade groupFacade;

	@EJB
	private	ProducerFacade producerFacade;

	@EJB
	private HomeInfoFacade homeInfoFacade;

	@PostConstruct
	public void postConstruct()
	{
		String sortByField;
		if (appBean.getAppLocale().equals("en"))
		{
			sortByField = "o.nameen";
		}
		else
		{
			sortByField = "o.namebg";
		}
		SortCriteria sortCriteria = new SortCriteria(sortByField, SortCriteria.DIRECTION_ASC);

		groups = groupFacade.findAll(1000, 0, sortCriteria);

		producers = producerFacade.findAll(1000, 0, sortCriteria);

		SortCriteria sortCriteriaHomeInfo = new SortCriteria("o.id", SortCriteria.DIRECTION_ASC);
		homeinfos = homeInfoFacade.findAll(1000, 0, sortCriteriaHomeInfo);
	}

	public void setGroups(List<Group> groups)
	{
		this.groups = groups;
	}

	public List<Group> getGroups()
	{
		return groups;
	}

	public void setProducers(List<Producer> producers)
	{
		this.producers = producers;
	}

	public List<Producer> getProducers()
	{
		return producers;
	}

	public ArticlesBean getArticlesBean()
	{
		return articlesBean;
	}

	public void setHomeinfos(List<HomeInfo> homeinfos)
	{
		this.homeinfos = homeinfos;
	}

	public List<HomeInfo> getHomeinfos()
	{
		return homeinfos;
	}

	public void setArticlesBean(ArticlesBean articlesBean)
	{
		this.articlesBean = articlesBean;
	}
}
