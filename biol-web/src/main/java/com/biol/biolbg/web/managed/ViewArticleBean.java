package com.biol.biolbg.web.managed;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

import com.biol.biolbg.ejb.session.ItemFacade;

import com.biol.biolbg.entity.Item;

@ManagedBean(name = "ViewArticleBean")
@RequestScoped
public class ViewArticleBean extends BaseEditItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ItemFacade itemFacade; //= EJBLocator.getInstance().lookup(ItemFacade.class);
	
	@ManagedProperty(value="#{ItemImagesFilenameMapper}")
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Override
	public Boolean getIsViewItemOnly() {
		return true;
	}
	
	@Override
	public Object createNewItem() {
		return itemFacade.createNewItem();
	}

	@Override
	public Boolean doSaveData() {
		return true;
	}

	@Override
	public Object findItemById(Integer id) {
		return itemFacade.findItem(id);
	}

	@Override
	public void init() {
		// nothing to do here
	}
	
	//determine the file image for the given itemId
	public String getImageFileName() {
		Item item_ = (Item) getItem();
		
		return itemImagesFilenameMapper.getSingle(item_);
	}
	
	public String getLocalizedItemName() {
		Item item = (Item)getItem();
		if (item != null) {
			if (getAppBean() != null) {
				if (getAppBean().getAppLocale().equals("en")) {
					return item.getNameen();
				} else {
					return item.getNamebg();
				}
			} else {
				return item.getNamebg();
			}
		} else {
			return "";
		}
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper) {
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper() {
		return itemImagesFilenameMapper;
	}
	

}
