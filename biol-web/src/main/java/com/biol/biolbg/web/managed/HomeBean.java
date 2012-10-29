package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.BaseList;

import com.biol.biolbg.ejb.session.GroupFacade;
import com.biol.biolbg.ejb.session.HomeInfoFacade;
import com.biol.biolbg.ejb.session.ProducerFacade;
import com.biol.biolbg.entity.Group;
import com.biol.biolbg.entity.HomeInfo;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name = "HomeBean")
@RequestScoped
public class HomeBean extends Base implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{ArticlesBean}")
	private ArticlesBean articlesBean; //= (ArticlesBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ArticlesBean");
	
	@EJB
	private GroupFacade groupFacade; //= EJBLocator.getInstance().lookup(GroupFacade.class);
	
	@EJB
	private	ProducerFacade producerFacade; //= EJBLocator.getInstance().lookup(ProducerFacade.class);
	
	@EJB
	private HomeInfoFacade homeInfoFacade; //=	EJBLocator.getInstance().lookup(HomeInfoFacade.class);
	
	private List<Group> groups = null;
	private List<Producer> producers = null;
	private List<HomeInfo> homeinfos = null;

	@PostConstruct
	public final void postConstruct() {
		String groupsSortByField = null;
		String producersSortByField = null;
		if (this.getAppBean().getAppLocale().equals("en")) {
			groupsSortByField = "o.nameen";
			producersSortByField = "o.nameen";
		} else {
			groupsSortByField = "o.namebg";
			producersSortByField = "o.namebg";
			
		}
		//fill groups
		groups = groupFacade.getAllItems(0, 0, groupsSortByField, BaseList.SORT_ASC);
		//fill producers
		producers = producerFacade.getAllItems(0, 0, producersSortByField, BaseList.SORT_ASC);
		//fill home info
		homeinfos = homeInfoFacade.getAllItems(0, 0, "o.id", BaseList.SORT_ASC);
		
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setProducers(List<Producer> producers) {
		this.producers = producers;
	}

	public List<Producer> getProducers() {
		return producers;
	}

	public ArticlesBean getArticlesBean() {
		return articlesBean;
	}

	public void setHomeinfos(List<HomeInfo> homeinfos) {
		this.homeinfos = homeinfos;
	}

	public List<HomeInfo> getHomeinfos() {
		return homeinfos;
	}

	public void setArticlesBean(ArticlesBean articlesBean) {
		this.articlesBean = articlesBean;
	}



}
