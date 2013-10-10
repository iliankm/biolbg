package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.UserFacade;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.util.BaseList;

@Named("UsersListBean")
@RequestScoped
public class UsersListBean extends BaseList implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacade usrFacade;

	@Override
	public void doDeleteData(List<Integer> itemsToDelete)
	{
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext())
		{
			try
			{
				usrFacade.deleteById(iter.next());
			}
			catch (Exception e)
			{
				addErrorMessage(e.getMessage());
			}
		}
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults)
	{
		SortCriteria sortCriteria = new SortCriteria(getSortByFieldName(), getSortType());

		List<Usr> dataItems = usrFacade.findAll(maxResults, fromRow, sortCriteria);

		this.setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		return usrFacade.getAllCount();
	}

	@Override
	public void init()
	{
		if (getSortByFieldName() == "")
		{
			setSortByFieldName("o.id");
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
