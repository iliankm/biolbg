package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.ItemFacade;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;


@Named("CheapArticlesBean")
@RequestScoped
public class CheapArticlesBean extends BaseList implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Map<Integer,String> itemsImages = new HashMap<Integer,String>();

	@EJB
	private ItemFacade itemFacade;

	@Inject
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Override
	public void doDeleteData(List<Integer> itemsToDelete)
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults)
	{
		SortCriteria sortCriteria = new SortCriteria("o.priceforpacking", SortCriteria.DIRECTION_ASC);

		List<Item> dataItems = itemFacade.findAll(maxResults, fromRow, sortCriteria);

		setDataItems(dataItems);

		//load itemsImages with file names of images
		itemsImages = itemImagesFilenameMapper.getMap((List<Item>)this.getDataItems());
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		return Long.valueOf(5);
	}

	@Override
	public void init()
	{
		getPagerController().setShowRowsCount(1);
	}

	@Override
	public void restoreCustomCredentials(Object customCredentials)
	{
	}

	@Override
	public Object storeCustomCredentials()
	{
		return null;
	}

	public void gotoPage(ActionEvent event)
	{
		Integer pageNumber = 1;

		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		String sPageNumber = params.get("pageNumber");

		try
		{
			pageNumber = Integer.parseInt(sPageNumber);
		}
		catch (Exception e)
		{
			pageNumber = 1;
		}

		getPagerController().setPageNumber(pageNumber);

		loadDataItems();
	}

	public void setItemsImages(Map<Integer,String> itemsImages)
	{
		this.itemsImages = itemsImages;
	}

	public Map<Integer,String> getItemsImages()
	{
		return itemsImages;
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper)
	{
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper()
	{
		return itemImagesFilenameMapper;
	}
}
