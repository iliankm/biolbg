package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

import com.biol.biolbg.ejb.session.ItemFacade;

import com.biol.biolbg.entity.Item;

@Named("PromotionsBean")
@RequestScoped
public class PromotionsBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer,String> itemsImages = new HashMap<Integer,String>();
	@EJB
	private ItemFacade itemFacade;

	@Inject
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		// nothing to do here
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		List<Item> dataItems = itemFacade.getPromotionItems(fromRow, maxResults);
		setDataItems(dataItems);

		//load itemsImages with file names of images
		if (getDataItems() != null) {
			itemsImages = getItemImagesFilenameMapper().getMap((List<Item>)this.getDataItems());
		}
	}

	@Override
	public Long getDataItemsTotalCount() {
		return itemFacade.getPromotionItemsCount();
	}

	@Override
	public void init() {
		getPagerController().setShowRowsCount(1);
	}

	@Override
	public void restoreCustomCredentials(Object customCredentials) {
		// nothing to do here
	}

	@Override
	public Object storeCustomCredentials() {
		return null;
	}

	public void setItemsImages(Map<Integer,String> itemsImages) {
		this.itemsImages = itemsImages;
	}

	public Map<Integer,String> getItemsImages() {
		return itemsImages;
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper) {
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper() {
		return itemImagesFilenameMapper;
	}

}
