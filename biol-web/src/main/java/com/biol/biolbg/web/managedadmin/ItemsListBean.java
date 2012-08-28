package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.EJBLocator;
import com.biol.biolbg.web.util.FileUtil;
import com.biol.biolbg.web.util.ItemsListCredentials;

import com.biol.biolbg.ejb.session.ItemFacade;
import com.biol.biolbg.entity.Group;
import com.biol.biolbg.entity.Item;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name = "ItemsListBean")
@RequestScoped
public class ItemsListBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Group group = null;
	private Producer producer = null;
	private List<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();
	private List<SelectItem> producersSelectItems = new ArrayList<SelectItem>();
	private Map<Integer,String> itemsImages = new HashMap<Integer,String>();
	private ItemFacade itemFacade = EJBLocator.getInstance().lookup(ItemFacade.class);

	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext()) {
			try {
				itemFacade.removeItem(iter.next());
			} catch (Exception e) {
				addErrorMessage(e.getMessage());
			}
		}
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		Integer groupId = 0;
		if (group != null) {
			groupId = group.getId();
		}
		Integer producerId = 0;
		if (producer != null) {
			producerId = producer.getId();
		}
		List<Item> dataItems = itemFacade.getAllItems(groupId, producerId, fromRow, maxResults, getSortByFieldName(), getSortType());
		setDataItems(dataItems);

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
		Integer groupId = 0;
		if (group != null) {
			groupId = group.getId();
		}
		Integer producerId = 0;
		if (producer != null) {
			producerId = producer.getId();
		}
		Long itemsCount = itemFacade.getAllItemsCount(groupId, producerId);
		return itemsCount;
	}

	@Override
	public void init() {
		if (getSortByFieldName() == "") { 
			setSortByFieldName("o.id");
		}
		
		//fill groupsSelectItems from GroupBean method
		groupsSelectItems = 
			GroupBean.groupsSelectItemList("o.id", SORT_ASC, getAppBean().getAppLocale());
		//fill producersSelectItems from ProducerBean method
		producersSelectItems = 
			ProducerBean.producersSelectItemList("o.id", BaseList.SORT_ASC, getAppBean().getAppLocale());
	}

	@Override
	public void restoreCustomCredentials(Object customCredentials) {
		if (customCredentials != null) {
			if (customCredentials instanceof ItemsListCredentials) {
				ItemsListCredentials itemsListCredentials = (ItemsListCredentials)customCredentials;
				group = itemsListCredentials.getGroup();
				producer = itemsListCredentials.getProducer();
			}
		}
	}

	@Override
	public Object storeCustomCredentials() {
		ItemsListCredentials itemsListCredentials = new ItemsListCredentials();
		itemsListCredentials.setGroup(group);
		itemsListCredentials.setProducer(producer);
		return itemsListCredentials;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroupsSelectItems(List<SelectItem> groupsSelectItems) {
		this.groupsSelectItems = groupsSelectItems;
	}

	public List<SelectItem> getGroupsSelectItems() {
		return groupsSelectItems;
	}

	public void setProducersSelectItems(List<SelectItem> producersSelectItems) {
		this.producersSelectItems = producersSelectItems;
	}

	public List<SelectItem> getProducersSelectItems() {
		return producersSelectItems;
	}

	public Map<Integer, String> getItemsImages() {
		return itemsImages;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Producer getProducer() {
		return producer;
	}
	
}