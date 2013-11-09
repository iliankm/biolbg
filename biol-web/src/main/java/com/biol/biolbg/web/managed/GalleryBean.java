package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;
import com.biol.biolbg.business.boundary.facade.GroupFacade;
import com.biol.biolbg.business.boundary.facade.ItemFacade;
import com.biol.biolbg.business.boundary.facade.ProducerFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Named("GalleryBean")
@SessionScoped
public class GalleryBean extends Base implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<Item> items;

	private List<Group> groups;

	private List<Producer> producers;

	@Inject
	private GalleryParamsBean galleryParamsBean;

	@Inject
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Inject
	private AppBean appBean;

	@EJB
	private ItemFacade itemFacade;

	@EJB
	private GroupFacade groupFacade;

	@EJB
	private ProducerFacade producerFacade;

	public void preRenderView(javax.faces.event.ComponentSystemEvent event)
	{
		groups = getAllGroups();

		producers = getAllProducers();

		Integer groupId = null;
		Integer producerId = null;

		if (getGalleryParamsBean().getParamGroup() != null)
		{
			groupId = getGalleryParamsBean().getParamGroup().getId();
		}

		if (getGalleryParamsBean().getParamProducer() != null)
		{
			producerId = getGalleryParamsBean().getParamProducer().getId();
		}

		FindItemCriteria findItemCriteria = new FindItemCriteria(producerId, groupId, null);
		SortCriteria sortCriteria = new SortCriteria("o.id", SortCriteria.DIRECTION_ASC);
		items = itemFacade.findByCriteria(findItemCriteria, 1000, 0, sortCriteria);
	}

	public void setParamGroup(Group paramGroup)
	{
		getGalleryParamsBean().setParamGroup(paramGroup);
	}

	public Group getParamGroup()
	{
		return getGalleryParamsBean().getParamGroup();
	}

	public void setParamProducer(Producer paramProducer)
	{
		getGalleryParamsBean().setParamProducer(paramProducer);
	}

	public Producer getParamProducer()
	{
		return getGalleryParamsBean().getParamProducer();
	}

	public void setItems(List<Item> items)
	{
		this.items = items;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public List<Group> getGroups()
	{
		return groups;
	}

	public void setGroupsSelectItems(List<Group> groups)
	{
		this.groups = groups;
	}

	public List<Producer> getProducers()
	{
		return producers;
	}

	public void setProducers(List<Producer> producers)
	{
		this.producers = producers;
	}

	public Map<Integer, String> getImageName()
	{
		Map<Integer, String> res = new AbstractMap<Integer, String>()
		{
			@Override
			public String get(Object key)
			{
				Integer itemId;
				try
				{
					itemId = Integer.valueOf(key.toString());
				}
				catch (Exception e)
				{
					itemId = -1;
				}

				return itemImagesFilenameMapper.getSingle(itemId);
			}

			@Override
			public Set<java.util.Map.Entry<Integer, String>> entrySet()
			{
				throw new UnsupportedOperationException();
			}
		};

		return res;
	}

	private List<Group> getAllGroups()
	{
		String groupsSortByField;
		if (appBean.getAppLocale().equals("en"))
		{
			groupsSortByField = "o.nameen";
		}
		else
		{
			groupsSortByField = "o.namebg";
		}

		SortCriteria sortCriteria = new SortCriteria(groupsSortByField, SortCriteria.DIRECTION_ASC);

		return groupFacade.findAll(1000, 0, sortCriteria);
	}

	private List<Producer> getAllProducers()
	{
		String producersSortByField = null;
		if (appBean.getAppLocale().equals("en"))
		{
			producersSortByField = "o.nameen";
		}
		else
		{
			producersSortByField = "o.namebg";
		}

		SortCriteria sortCriteria = new SortCriteria(producersSortByField, SortCriteria.DIRECTION_ASC);

		return producerFacade.findAll(1000, 0, sortCriteria);
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
}
