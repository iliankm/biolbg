package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

import com.biol.biolbg.web.managedadmin.GroupBean;
import com.biol.biolbg.web.managedadmin.ProducerBean;
import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;


import com.biol.biolbg.ejb.session.ItemFacade;

import com.biol.biolbg.entity.Group;
import com.biol.biolbg.entity.Item;
import com.biol.biolbg.entity.Producer;

@Named("ArticlesBean")
@RequestScoped
public class ArticlesBean extends BaseList implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private GalleryParamsBean galleryParamsBean;

	@Inject
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Inject
	private ProducerBean producerBean;

	@Inject
	private GroupBean groupBean;

	@Inject
	private	AppBean appBean;

	@EJB
	ItemFacade itemFacade;

	private String name = "";

	private List<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();

	private List<SelectItem> producersSelectItems = new ArrayList<SelectItem>();

	private Map<Integer,String> itemsImages = new HashMap<Integer,String>();

	public ArticlesBean()
	{
		getPagerController().setShowRowsCount(4);
	}

	@Override
	public void doDeleteData(List<Integer> itemsToDelete)
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults)
	{
		try
		{
			if ((name == null)||("".equals(name)))
			{
				setDataItems(getItemsByGroupAndProducer(fromRow, maxResults));
			}
			else
			{
				setDataItems(getItemsByName(fromRow, maxResults));
			}

		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}

		//load itemsImages with file names of images
		if (getDataItems() != null)
		{
			itemsImages = getItemImagesFilenameMapper().getMap((List<Item>)this.getDataItems());
		}
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		Long itemsCount = Long.valueOf(0);
		try
		{
			if ((name == null)||(name == ""))
			{
				itemsCount = getAllItemsCount();
			}
			else
			{
				itemsCount = getItemsByNameCount();
			}

		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}

		return itemsCount;
	}

	@Override
	public void init()
	{
		if (getSortByFieldName() == "")
		{
			if (appBean.getAppLocale().equals("bg"))
			{
				setSortByFieldName("o.namebg");
			}
			else
			{
				if (appBean.getAppLocale().equals("en"))
				{
					setSortByFieldName("o.nameen");
				}
			}
		}

		//fill groupsSelectItems from GroupBean method
		String groupsSortByField = null;
		String producersSortByField = null;

		if (appBean.getAppLocale().equals("en"))
		{
			groupsSortByField = "o.nameen";
			producersSortByField = "o.nameen";
		}
		else
		{
			groupsSortByField = "o.namebg";
			producersSortByField = "o.namebg";
		}

		try
		{
			groupsSelectItems =
				getGroupBean().groupsSelectItemList(groupsSortByField, SORT_ASC, appBean.getAppLocale());
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}

		//fill producersSelectItems from ProducerBean method
		try
		{
			producersSelectItems =
				getProducerBean().producersSelectItemList(producersSortByField, BaseList.SORT_ASC, appBean.getAppLocale());
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}
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

	public void findItemsByName(ActionEvent event)
	{
		name = name.trim();
		getGalleryParamsBean().setParamGroup(null);
		getGalleryParamsBean().setParamProducer(null);
		loadDataItems();
	}

	private List<Item> getItemsByGroupAndProducer(Integer fromRow, Integer maxResults)
	{
		Group group = getGalleryParamsBean().getParamGroup();

		Producer producer = getGalleryParamsBean().getParamProducer();

		Integer groupId = 0;
		if (group != null)
		{
			groupId = group.getId();
		}

		Integer producerId = 0;
		if (producer != null)
		{
			producerId = producer.getId();
		}

		return itemFacade.getAllItems(groupId, producerId, fromRow, maxResults, getSortByFieldName(), getSortType());
	}

	private List<Item> getItemsByName(Integer fromRow, Integer maxResults)
	{
		return itemFacade.getItemsByName(name, fromRow, maxResults, getSortByFieldName(), getSortType());
	}

	private Long getItemsByNameCount()
	{
		return itemFacade.getItemsByNameCount(name);
	}

	private Long getAllItemsCount() throws NamingException
	{
		Group group = getGalleryParamsBean().getParamGroup();

		Producer producer = getGalleryParamsBean().getParamProducer();

		Integer groupId = 0;
		if (group != null)
		{
			groupId = group.getId();
		}

		Integer producerId = 0;
		if (producer != null)
		{
			producerId = producer.getId();
		}

		return itemFacade.getAllItemsCount(groupId, producerId);
	}

	public Group getGroup()
	{
		return getGalleryParamsBean().getParamGroup();
	}

	public void setGroup(Group group)
	{
		getGalleryParamsBean().setParamGroup(group);
	}

	public Producer getProducer()
	{
		return getGalleryParamsBean().getParamProducer();
	}

	public void setProducer(Producer producer)
	{
		getGalleryParamsBean().setParamProducer(producer);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public List<SelectItem> getGroupsSelectItems()
	{
		return groupsSelectItems;
	}

	public void setGroupsSelectItems(List<SelectItem> groupsSelectItems)
	{
		this.groupsSelectItems = groupsSelectItems;
	}

	public List<SelectItem> getProducersSelectItems()
	{
		return producersSelectItems;
	}

	public void setProducersSelectItems(List<SelectItem> producersSelectItems)
	{
		this.producersSelectItems = producersSelectItems;
	}

	public Map<Integer, String> getItemsImages()
	{
		return itemsImages;
	}

	public void setItemsImages(Map<Integer, String> itemsImages)
	{
		this.itemsImages = itemsImages;
	}

	public void setGalleryParamsBean(GalleryParamsBean galleryParamsBean)
	{
		this.galleryParamsBean = galleryParamsBean;
	}

	public GalleryParamsBean getGalleryParamsBean()
	{
		return galleryParamsBean;
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper)
	{
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper()
	{
		return itemImagesFilenameMapper;
	}

	public void setProducerBean(ProducerBean producerBean)
	{
		this.producerBean = producerBean;
	}

	public ProducerBean getProducerBean()
	{
		return producerBean;
	}

	public void setGroupBean(GroupBean groupBean)
	{
		this.groupBean = groupBean;
	}

	public GroupBean getGroupBean()
	{
		return groupBean;
	}
}
