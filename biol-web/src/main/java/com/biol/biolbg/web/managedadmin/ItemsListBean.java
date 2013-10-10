package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.ItemFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.managed.AppBean;
import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.ItemsListCredentials;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;


@Named("ItemsListBean")
@RequestScoped
public class ItemsListBean extends BaseList implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Group group = null;

	private Producer producer = null;

	private List<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();

	private List<SelectItem> producersSelectItems = new ArrayList<SelectItem>();

	private Map<Integer,String> itemsImages = new HashMap<Integer,String>();

	@EJB
	private ItemFacade itemFacade;

	@Inject
	private GroupBean groupBean;

	@Inject
	private ProducerBean producerBean;

	@Inject
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Inject
	private AppBean appBean;

	@Override
	public void doDeleteData(List<Integer> itemsToDelete)
	{
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext())
		{
			try
			{
				itemFacade.deleteById(iter.next());
			}
			catch (Exception e)
			{
				addErrorMessage(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults)
	{
		SortCriteria sortCriteria = new SortCriteria(getSortByFieldName(), getSortType());

		List<Item> dataItems = itemFacade.findByCriteria(getFindItemCriteria(), maxResults, fromRow, sortCriteria);

		setDataItems(dataItems);

		//load itemsImages with file names of images
		itemsImages = getItemImagesFilenameMapper().getMap((List<Item>)this.getDataItems());
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		return itemFacade.getByCriteriaCount(getFindItemCriteria());
	}

	private FindItemCriteria getFindItemCriteria()
	{
		Integer groupId = group != null ? Integer.valueOf(group.getId()) : null;

		Integer producerId = producer != null ? Integer.valueOf(producer.getId()) : null;

		return new FindItemCriteria(producerId, groupId, null);
	}

	@Override
	public void init()
	{
		if (getSortByFieldName() == "")
		{
			setSortByFieldName("o.id");
		}

		//fill groupsSelectItems from GroupBean method
		groupsSelectItems =
			getGroupBean().groupsSelectItemList("o.id", SORT_ASC, appBean.getAppLocale());

		//fill producersSelectItems from ProducerBean method
		producersSelectItems =
			getProducerBean().producersSelectItemList("o.id", BaseList.SORT_ASC, appBean.getAppLocale());
	}

	@Override
	public void restoreCustomCredentials(Object customCredentials)
	{
		if (customCredentials != null)
		{
			if (customCredentials instanceof ItemsListCredentials)
			{
				ItemsListCredentials itemsListCredentials = (ItemsListCredentials)customCredentials;
				group = itemsListCredentials.getGroup();
				producer = itemsListCredentials.getProducer();
			}
		}
	}

	@Override
	public Object storeCustomCredentials()
	{
		ItemsListCredentials itemsListCredentials = new ItemsListCredentials();
		itemsListCredentials.setGroup(group);
		itemsListCredentials.setProducer(producer);

		return itemsListCredentials;
	}

	public void setGroup(Group group)
	{
		this.group = group;
	}

	public Group getGroup()
	{
		return group;
	}

	public void setGroupsSelectItems(List<SelectItem> groupsSelectItems)
	{
		this.groupsSelectItems = groupsSelectItems;
	}

	public List<SelectItem> getGroupsSelectItems()
	{
		return groupsSelectItems;
	}

	public void setProducersSelectItems(List<SelectItem> producersSelectItems)
	{
		this.producersSelectItems = producersSelectItems;
	}

	public List<SelectItem> getProducersSelectItems()
	{
		return producersSelectItems;
	}

	public Map<Integer, String> getItemsImages()
	{
		return itemsImages;
	}

	public void setProducer(Producer producer)
	{
		this.producer = producer;
	}

	public Producer getProducer()
	{
		return producer;
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper)
	{
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper()
	{
		return itemImagesFilenameMapper;
	}

	public void setGroupBean(GroupBean groupBean)
	{
		this.groupBean = groupBean;
	}

	public GroupBean getGroupBean()
	{
		return groupBean;
	}

	public void setProducerBean(ProducerBean producerBean)
	{
		this.producerBean = producerBean;
	}

	public ProducerBean getProducerBean()
	{
		return producerBean;
	}
}
