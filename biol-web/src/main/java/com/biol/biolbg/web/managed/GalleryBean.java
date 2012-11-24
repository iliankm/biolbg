package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

import com.biol.biolbg.ejb.session.GroupFacade;
import com.biol.biolbg.ejb.session.ItemFacade;
import com.biol.biolbg.ejb.session.ProducerFacade;

import com.biol.biolbg.entity.Group;
import com.biol.biolbg.entity.Item;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name = "GalleryBean")
@ViewScoped
public class GalleryBean extends Base implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{GalleryParamsBean}")
	private GalleryParamsBean galleryParamsBean;
	
	@ManagedProperty(value="#{ItemImagesFilenameMapper}")
	private ItemImagesFilenameMapper itemImagesFilenameMapper;
	
	private List<Item> items;
	private List<Group> groups;
	private List<Producer> producers;
	
	@EJB
	private ItemFacade itemFacade; //= EJBLocator.getInstance().lookup(ItemFacade.class);
	
	@EJB
	private GroupFacade groupFacade; //= EJBLocator.getInstance().lookup(GroupFacade.class);
	
	@EJB
	private ProducerFacade producerFacade; //= EJBLocator.getInstance().lookup(ProducerFacade.class);
	
	public void preRenderView(javax.faces.event.ComponentSystemEvent event) {
		if (items == null)
			items = new ArrayList<Item>();
		items.clear();

		groups = getAllGroups();
		producers = getAllProducers();
		
		Integer groupId = 0;
		Integer producerId = 0;
		if (getGalleryParamsBean().getParamGroup() != null) {
			groupId = getGalleryParamsBean().getParamGroup().getId();
		} 
		if (getGalleryParamsBean().getParamProducer() != null) {
			producerId = getGalleryParamsBean().getParamProducer().getId();
		}
		if (groupId == 0 && producerId == 0) {
			items = new ArrayList<Item>();
		}
		else {
			items = itemFacade.getAllItems(groupId, producerId, 0, 1000, "o.id", BaseList.SORT_ASC);
		}	
	}
	
	public void setParamGroup(Group paramGroup) {
		getGalleryParamsBean().setParamGroup(paramGroup);
	}
	public Group getParamGroup() {
		return getGalleryParamsBean().getParamGroup();
	}
	public void setParamProducer(Producer paramProducer) {
		getGalleryParamsBean().setParamProducer(paramProducer);
	}
	public Producer getParamProducer() {
		return getGalleryParamsBean().getParamProducer();
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public List<Item> getItems() {
		return items;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroupsSelectItems(List<Group> groups) {
		this.groups = groups;
	}
	public List<Producer> getProducers() {
		return producers;
	}
	public void setProducers(List<Producer> producers) {
		this.producers = producers;
	}
	public Map<Integer, String> getImageName() 
	{
		Map<Integer, String> res = new AbstractMap<Integer, String>() 
		{

			@Override
			public String get(Object key) 
			{
				Integer itemId;
				try
				{
					itemId = Integer.valueOf(key.toString());
				}
				catch (Exception e)
				{
					itemId = -1;
				}
				return itemImagesFilenameMapper.getSingle(itemId);
			}
			
			@Override
			public Set<java.util.Map.Entry<Integer, String>> entrySet() 
			{
				throw new UnsupportedOperationException();
			}
		};
			
		return res;
	}
	
	//************PRIVATE METHODS************
	private List<Group> getAllGroups() {
		String groupsSortByField;
		if (this.getAppBean().getAppLocale().equals("en")) {
			groupsSortByField = "o.nameen";
		} else { 
			groupsSortByField = "o.namebg";
		}
		return groupFacade.getAllItems(0, 0, groupsSortByField, BaseList.SORT_ASC);
	}

	private List<Producer> getAllProducers() {
		String producersSortByField = null;
		if (this.getAppBean().getAppLocale().equals("en")) {
			producersSortByField = "o.nameen";
		} else {
			producersSortByField = "o.namebg";
		}
		return producerFacade.getAllItems(0, 0, producersSortByField, BaseList.SORT_ASC);
	}

	public void setGalleryParamsBean(GalleryParamsBean galleryParamsBean) {
		this.galleryParamsBean = galleryParamsBean;
	}

	public GalleryParamsBean getGalleryParamsBean() {
		return galleryParamsBean;
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper) {
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper() {
		return itemImagesFilenameMapper;
	}
	
}
