package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

import com.biol.biolbg.ejb.session.ItemFacade;

import com.biol.biolbg.entity.Item;

@ManagedBean(name="CheapArticlesBean")
@RequestScoped
public class CheapArticlesBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer,String> itemsImages = new HashMap<Integer,String>();
	
	@EJB
	private ItemFacade itemFacade; //= EJBLocator.getInstance().lookup(ItemFacade.class);
	
	@ManagedProperty(value="#{ItemImagesFilenameMapper}")
	private ItemImagesFilenameMapper itemImagesFilenameMapper; 

	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		// nothing to do here
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		List<Item> dataItems = itemFacade.getCheapestItems(fromRow, maxResults);
		setDataItems(dataItems);

		//load itemsImages with file names of images
		if (getDataItems() != null) {
			itemsImages = itemImagesFilenameMapper.getMap((List<Item>)this.getDataItems());
		}
	}

	@Override
	public Long getDataItemsTotalCount() {
		return Long.valueOf(5);
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
	
	public void gotoPage(ActionEvent event) {
		Integer pageNumber = 1;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String sPageNumber = params.get("pageNumber");
		
		try {
			pageNumber = Integer.parseInt(sPageNumber);
		} catch (Exception e) {
			pageNumber = 1;
		}
		
		getPagerController().setPageNumber(pageNumber);
		loadDataItems();
		
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
