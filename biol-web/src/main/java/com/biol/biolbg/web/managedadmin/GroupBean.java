package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.ejb.session.GroupFacade;
import com.biol.biolbg.entity.Group;

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
				groupFacade.updateItem(item);
			}
			else
			{
				groupFacade.addItem(item);
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
		return groupFacade.findItem(id);
	}

	@Override
	public Object createNewItem()
	{
		return groupFacade.createNewItem();
	}

	public List<SelectItem> groupsSelectItemList(String sortByFieldName, String sortType, String localeName)
	{
		//GroupFacade groupFacade = EJBLocator.getInstance().lookup(GroupFacade.class);
		List<SelectItem> result = new ArrayList<SelectItem>();

		//get all groups in allGroups
		List<Group> allGroups = null;
		allGroups = groupFacade.getAllItems(0, 0, sortByFieldName, sortType);

		//iterate over allGroups and form result List with SelectItem instances
		result.add(new SelectItem(new Group(),""));

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
