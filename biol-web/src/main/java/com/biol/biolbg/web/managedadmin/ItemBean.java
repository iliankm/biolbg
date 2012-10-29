package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.BaseList;

import com.biol.biolbg.ejb.session.ItemFacade;
import com.biol.biolbg.entity.Item;

@ManagedBean(name = "ItemBean")
@RequestScoped
public class ItemBean extends BaseEditItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();
	private List<SelectItem> producersSelectItems = new ArrayList<SelectItem>();
	@EJB
	private ItemFacade itemFacade; //= EJBLocator.getInstance().lookup(ItemFacade.class);
	
	@ManagedProperty(value="#{GroupBean}")
	private GroupBean groupBean;
	
	@ManagedProperty(value="#{ProducerBean}")
	private ProducerBean producerBean;

	@Override
	public Object createNewItem() {
		Item res = itemFacade.createNewItem();
		//if there is instance of Item - set the same Group and Producer in res
		if (getItem() != null) {
			if (getItem() instanceof Item) {
				Item item = (Item)getItem();
				res.setGroup(item.getGroup());
				res.setProducer(item.getProducer());
			}
		}
		return res;
	}

	@Override
	public Boolean doSaveData() {
		Boolean res = false;
		try {
			Item item = (Item)getItem();
			if (item.getId() > 0) {
				itemFacade.updateItem(item);
			} else {
				itemFacade.addItem(item);
			}
			res = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
		return res;
	}

	@Override
	public Object findItemById(Integer id) {
		return itemFacade.findItem(id);
	}

	public List<SelectItem> getGroupsSelectItems() {
		return groupsSelectItems;
	}
	
	public List<SelectItem> getProducersSelectItems() {
		return producersSelectItems;
	}

	@Override
	public void init() {
		//fill groupsSelectItems from GroupBean method
		groupsSelectItems = 
			groupBean.groupsSelectItemList("o.id", BaseList.SORT_ASC, getAppBean().getAppLocale());
		//fill producersSelectItems from ProducerBean method
		producersSelectItems = 
			producerBean.producersSelectItemList("o.id", BaseList.SORT_ASC, getAppBean().getAppLocale());
		
	}
	
	public Boolean getPromotion() {
		if (getItem() instanceof Item) {
			Item item = (Item)getItem(); 
			return (item.getPromotion() == 1); 
		} else {
			return false;
		}
	}
	
	public void setPromotion(Boolean promotion) {
		if (getItem() instanceof Item) {
			Item item = (Item)getItem();
			if (promotion) {
				item.setPromotion(1);
			} else {
				item.setPromotion(0);
			}
		}
		
	}
	public Boolean getNewitem() {
		if (getItem() instanceof Item) {
			Item item = (Item)getItem(); 
			return (item.getNewitem() == 1); 
		} else {
			return false;
		}
	}
	
	public void setNewitem(Boolean newitem) {
		if (getItem() instanceof Item) {
			Item item = (Item)getItem();
			if (newitem) {
				item.setNewitem(1);
			} else {
				item.setNewitem(0);
			}
		}
		
	}
	public Boolean getBestsell() {
		if (getItem() instanceof Item) {
			Item item = (Item)getItem(); 
			return (item.getBestsell() == 1); 
		} else {
			return false;
		}
	}
	
	public void setBestsell(Boolean bestsell) {
		if (getItem() instanceof Item) {
			Item item = (Item)getItem();
			if (bestsell) {
				item.setBestsell(1);
			} else {
				item.setBestsell(0);
			}
		}
		
	}
	
	
}
