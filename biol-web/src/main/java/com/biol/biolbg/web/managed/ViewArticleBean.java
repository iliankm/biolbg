package com.biol.biolbg.web.managed;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.ItemFacade;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;


@Named("ViewArticleBean")
@RequestScoped
public class ViewArticleBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private ItemFacade itemFacade;

	@Inject
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Inject
	private AppBean appBean;

	@Override
	public Boolean getIsViewItemOnly()
	{
		return true;
	}

	@Override
	public Object createNewItem()
	{
		return itemFacade.createLocal();
	}

	@Override
	public Boolean doSaveData()
	{
		return true;
	}

	@Override
	public Object findItemById(Integer id)
	{
		return itemFacade.findById(id);
	}

	@Override
	public void init()
	{
	}

	//determine the file image for the given itemId
	public String getImageFileName()
	{
		Item item_ = (Item) getItem();

		return itemImagesFilenameMapper.getSingle(item_);
	}

	public String getLocalizedItemName()
	{
		Item item = (Item)getItem();

		if (item != null)
		{
			if (appBean.getAppLocale().equals("en"))
			{
				return item.getNameen();
			}
			else
			{
				return item.getNamebg();
			}
		}
		else
		{
			return "";
		}
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
