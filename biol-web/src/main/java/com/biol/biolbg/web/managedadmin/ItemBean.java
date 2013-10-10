package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.ItemFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.web.managed.AppBean;
import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.BaseList;


@Named("ItemBean")
@RequestScoped
public class ItemBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private ItemFacade itemFacade;

	@Inject
	private GroupBean groupBean;

	@Inject
	private ProducerBean producerBean;

	@Inject
	private AppBean appBean;

	private List<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();

	private List<SelectItem> producersSelectItems = new ArrayList<SelectItem>();

	private Group group;

	private Producer producer;

	@Override
	public Object createNewItem()
	{
		Item res = itemFacade.createLocal();

		return res;
	}

	@Override
	public Boolean doSaveData()
	{
		Boolean res = false;
		try
		{
			Item item = (Item)getItem();

			itemFacade.assignGroupAndProducerLocal(item, group.getId(), producer.getId());

			if (item.getId() > 0)
			{
				itemFacade.update(item);
			}
			else
			{
				itemFacade.create(item);
			}
			res = true;
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}

		return res;
	}

	@Override
	public Object findItemById(Integer id)
	{
		Item item = itemFacade.findById(id);

		producer = item.getProducer();

		group = item.getGroup();

		return item;
	}

	public List<SelectItem> getGroupsSelectItems()
	{
		return groupsSelectItems;
	}

	public List<SelectItem> getProducersSelectItems()
	{
		return producersSelectItems;
	}

	@Override
	public void init()
	{

		//fill groupsSelectItems from GroupBean method
		groupsSelectItems =
			getGroupBean().groupsSelectItemList("o.id", BaseList.SORT_ASC, appBean.getAppLocale());

		//fill producersSelectItems from ProducerBean method
		producersSelectItems =
			getProducerBean().producersSelectItemList("o.id", BaseList.SORT_ASC, appBean.getAppLocale());
	}

	public Boolean getPromotion()
	{
		if (getItem() instanceof Item)
		{
			Item item = (Item)getItem();
			return (item.getPromotion() == 1);
		}
		else
		{
			return false;
		}
	}

	public void setPromotion(Boolean promotion)
	{
		if (getItem() instanceof Item)
		{
			Item item = (Item)getItem();
			if (promotion)
			{
				item.setPromotion(1);
			}
			else
			{
				item.setPromotion(0);
			}
		}
	}

	public Boolean getNewitem()
	{
		if (getItem() instanceof Item)
		{
			Item item = (Item)getItem();
			return (item.getNewitem() == 1);
		}
		else
		{
			return false;
		}
	}

	public void setNewitem(Boolean newitem)
	{
		if (getItem() instanceof Item)
		{
			Item item = (Item)getItem();
			if (newitem)
			{
				item.setNewitem(1);
			}
			else
			{
				item.setNewitem(0);
			}
		}
	}

	public Boolean getBestsell()
	{
		if (getItem() instanceof Item)
		{
			Item item = (Item)getItem();
			return (item.getBestsell() == 1);
		}
		else
		{
			return false;
		}
	}

	public void setBestsell(Boolean bestsell)
	{
		if (getItem() instanceof Item)
		{
			Item item = (Item)getItem();
			if (bestsell)
			{
				item.setBestsell(1);
			}
			else
			{
				item.setBestsell(0);
			}
		}
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

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(Group group)
	{
		this.group = group;
	}

	public Producer getProducer()
	{
		return producer;
	}

	public void setProducer(Producer producer)
	{
		this.producer = producer;
	}

}
