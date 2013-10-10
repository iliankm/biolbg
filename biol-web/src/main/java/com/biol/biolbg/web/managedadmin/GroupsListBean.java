package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.GroupFacade;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.util.BaseList;


@Named("GroupsListBean")
@RequestScoped
public class GroupsListBean extends BaseList implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private GroupFacade groupFacade;

	@Override
	public void init()
	{
		if (getSortByFieldName() == "")
		{
			setSortByFieldName("o.id");
		}
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults)
	{
		SortCriteria sortCriteria = new SortCriteria(getSortByFieldName(), getSortType());

		List<Group> dataItems = groupFacade.findAll(maxResults, fromRow, sortCriteria);

		setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		return groupFacade.getAllCount();
	}

	@Override
	public void doDeleteData(List<Integer> itemsToDelete)
	{
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext())
		{
			try
			{
				groupFacade.deleteById(iter.next());
			}
			catch (Exception e)
			{
				addErrorMessage(e.getMessage());
			}
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
}
