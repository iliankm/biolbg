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

import com.biol.biolbg.business.boundary.facade.ItemFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.managedadmin.GroupBean;
import com.biol.biolbg.web.managedadmin.ProducerBean;
import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;


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
		SortCriteria sortCriteria = new SortCriteria(getSortByFieldName(), getSortType());

		FindItemCriteria findItemCriteria = getFindItemCriteria();

		try
		{
			setDataItems(itemFacade.findByCriteria(findItemCriteria, maxResults, fromRow, sortCriteria));
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}

		//load itemsImages with file names of images
		itemsImages = getItemImagesFilenameMapper().getMap((List<Item>)this.getDataItems());
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		Long itemsCount = Long.valueOf(0);

		FindItemCriteria findItemCriteria = getFindItemCriteria();

		try
		{
			itemsCount = itemFacade.getByCriteriaCount(findItemCriteria);
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}

		return itemsCount;
	}

	private FindItemCriteria getFindItemCriteria()
	{
		FindItemCriteria findItemCriteria;

		if (name == null || "".equals(name))
		{
			Integer producerId = getGalleryParamsBean().getParamProducer() != null ? Integer.valueOf(getGalleryParamsBean().getParamProducer().getId()) : null;

			Integer groupId = getGalleryParamsBean().getParamGroup() != null ? Integer.valueOf(getGalleryParamsBean().getParamGroup().getId()) : null;

			findItemCriteria = new FindItemCriteria(producerId, groupId, null);
		}
		else
		{
			findItemCriteria = new FindItemCriteria(null, null, name);
		}

		return findItemCriteria;
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
