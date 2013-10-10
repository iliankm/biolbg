package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.GroupFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.util.BaseEditItem;


@Named("GroupBean")
@RequestScoped
public class GroupBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private GroupFacade groupFacade;

	@Override
	public Boolean doSaveData()
	{
		Boolean res = false;
		try
		{
			Group item = (Group)getItem();
			if (item.getId() > 0)
			{
				groupFacade.update(item);
			}
			else
			{
				groupFacade.create(item);
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
		return groupFacade.findById(id);
	}

	@Override
	public Object createNewItem()
	{
		return groupFacade.createLocal();
	}

	public List<SelectItem> groupsSelectItemList(String sortByFieldName, String sortType, String localeName)
	{
		//GroupFacade groupFacade = EJBLocator.getInstance().lookup(GroupFacade.class);
		List<SelectItem> result = new ArrayList<SelectItem>();

		//get all groups in allGroups
		SortCriteria sortCriteria = new SortCriteria(sortByFieldName, sortType);
		List<Group> allGroups = groupFacade.findAll(1000, 0, sortCriteria);

		//iterate over allGroups and form result List with SelectItem instances
		result.add(new SelectItem(groupFacade.createLocal(), ""));

		Iterator<Group> iter = allGroups.iterator();
		while (iter.hasNext())
		{
			Group group = iter.next();
			String groupName = group.getNameen();

			if (localeName.equals("bg"))
			{
				groupName = group.getNamebg();
			}

			result.add(new SelectItem(group,groupName));
		}

		return result;
	}

	@Override
	public void init()
	{
	}
}
