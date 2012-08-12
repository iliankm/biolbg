package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import com.biol.biolbg.web.managedadmin.GroupBean;
import com.biol.biolbg.web.managedadmin.ProducerBean;
import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.EJBLocator;
import com.biol.biolbg.web.util.FileUtil;


import com.biol.biolbg.ejb.session.ItemFacade;

import com.biol.biolbg.entity.Group;
import com.biol.biolbg.entity.Item;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name = "ArticlesBean")
@RequestScoped
public class ArticlesBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{GalleryParamsBean}")
	private GalleryParamsBean galleryParamsBean;
	private String name = "";
	private List<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();
	private List<SelectItem> producersSelectItems = new ArrayList<SelectItem>();
	private Map<Integer,String> itemsImages = new HashMap<Integer,String>();
	ItemFacade itemFacade = EJBLocator.getInstance().lookup(ItemFacade.class);
	
	//-----------------CONSTRUCTOR-------------------------------------
	public ArticlesBean() {
		getPagerController().setShowRowsCount(4);
	}

	//-----------------OVERRIDEN METHODS-------------------------------
	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		// nothing to do here
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		try {
			if ((name == null)||("".equals(name))) {
				setDataItems(getItemsByGroupAndProducer(fromRow, maxResults));
			} else {
				setDataItems(getItemsByName(fromRow, maxResults));
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}

		//load itemsImages with file names of images
		if (getDataItems() != null) {
			itemsImages.clear();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String imagesPath = facesContext.getExternalContext().getInitParameter("imagesPath");
			
			Iterator<?> iter = getDataItems().iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof Item) {
					Item item = (Item) obj;
					String imageFileName = FileUtil.imageFileName(imagesPath, Integer.toString(item.getId()));
					if (imageFileName != null) {
						itemsImages.put(item.getId(), imageFileName);
					}
				}
			}
		}
	}

	@Override
	public Long getDataItemsTotalCount() {
		Long itemsCount = Long.valueOf(0);
		try {
			if ((name == null)||(name == "")) {
				itemsCount = getAllItemsCount();
			} else {
				itemsCount = getItemsByNameCount();
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
		return itemsCount;
	}

	@Override
	public void init() {
		if (getSortByFieldName() == "") { 	
			if (this.getAppBean().getAppLocale().equals("bg")) {
				setSortByFieldName("o.namebg");
			} else {
				if (this.getAppBean().getAppLocale().equals("en")) {
					setSortByFieldName("o.nameen");
				}
			}
		}
		
		//fill groupsSelectItems from GroupBean method
		String groupsSortByField = null;
		String producersSortByField = null;
		if (this.getAppBean().getAppLocale().equals("en")) {
			groupsSortByField = "o.nameen";
			producersSortByField = "o.nameen";
		} else {
			groupsSortByField = "o.namebg";
			producersSortByField = "o.namebg";
		}
		try {
			groupsSelectItems = 
				GroupBean.groupsSelectItemList(groupsSortByField, SORT_ASC, getAppBean().getAppLocale());
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
		//fill producersSelectItems from ProducerBean method
		
		try {
			producersSelectItems = 
				ProducerBean.producersSelectItemList(producersSortByField, BaseList.SORT_ASC, getAppBean().getAppLocale());
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}

	@Override
	public void restoreCustomCredentials(Object customCredentials) {
	}

	@Override
	public Object storeCustomCredentials() {
		return null;
	}
	
	public void findItemsByName(ActionEvent event) {
		name = name.trim();
		getGalleryParamsBean().setParamGroup(null);
		getGalleryParamsBean().setParamProducer(null);
		loadDataItems();
		
		//if (name.trim() == "") {
		//	addErrorMessage(getAppBean().getMessageResourceString("enterNameForSearch", null));
		//} else {
		//	group = null;
		//	producer = null;
		//	loadDataItems();
		//}
	}
	
	//---------------PRIVATE METHODS--------------------------
	private List<Item> getItemsByGroupAndProducer(Integer fromRow, Integer maxResults) {
		Group group = getGalleryParamsBean().getParamGroup();
		Producer producer = getGalleryParamsBean().getParamProducer();
		Integer groupId = 0;
		if (group != null) {
			groupId = group.getId();
		}
		Integer producerId = 0;
		if (producer != null) {
			producerId = producer.getId();
		}
		return itemFacade.getAllItems(groupId, producerId, fromRow, maxResults, getSortByFieldName(), getSortType());
	}
	
	private List<Item> getItemsByName(Integer fromRow, Integer maxResults) {
		return itemFacade.getItemsByName(name, fromRow, maxResults, getSortByFieldName(), getSortType());
	}
	
	private Long getItemsByNameCount() {
		return itemFacade.getItemsByNameCount(name);
	}

	private Long getAllItemsCount() throws NamingException {
		Group group = getGalleryParamsBean().getParamGroup();
		Producer producer = getGalleryParamsBean().getParamProducer();
		
		Integer groupId = 0;
		if (group != null) {
			groupId = group.getId();
		}
		Integer producerId = 0;
		if (producer != null) {
			producerId = producer.getId();
		}
		return itemFacade.getAllItemsCount(groupId, producerId);
	}
	
	//------------------GETTERS AND SETTERS--------------------------- 
	public Group getGroup() {
		return getGalleryParamsBean().getParamGroup();
	}

	public void setGroup(Group group) {
		getGalleryParamsBean().setParamGroup(group);
	}

	public Producer getProducer() {
		return getGalleryParamsBean().getParamProducer();
	}

	public void setProducer(Producer producer) {
		getGalleryParamsBean().setParamProducer(producer);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<SelectItem> getGroupsSelectItems() {
		return groupsSelectItems;
	}

	public void setGroupsSelectItems(List<SelectItem> groupsSelectItems) {
		this.groupsSelectItems = groupsSelectItems;
	}

	public List<SelectItem> getProducersSelectItems() {
		return producersSelectItems;
	}

	public void setProducersSelectItems(List<SelectItem> producersSelectItems) {
		this.producersSelectItems = producersSelectItems;
	}

	public Map<Integer, String> getItemsImages() {
		return itemsImages;
	}

	public void setItemsImages(Map<Integer, String> itemsImages) {
		this.itemsImages = itemsImages;
	}

	public void setGalleryParamsBean(GalleryParamsBean galleryParamsBean) {
		this.galleryParamsBean = galleryParamsBean;
	}

	public GalleryParamsBean getGalleryParamsBean() {
		return galleryParamsBean;
	}
	

}
