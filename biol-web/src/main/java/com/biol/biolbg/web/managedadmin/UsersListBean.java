package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.biol.biolbg.web.util.BaseList;

import com.biol.biolbg.ejb.session.UsrFacade;
import com.biol.biolbg.entity.Usr;

@Named("UsersListBean")
@RequestScoped
public class UsersListBean extends BaseList implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private UsrFacade usrFacade;

	@Override
	public void doDeleteData(List<Integer> itemsToDelete)
	{
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext())
		{
			try
			{
				usrFacade.removeItem(iter.next());
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
		List<Usr> dataItems = usrFacade.getAllItems(fromRow, maxResults, getSortByFieldName(), getSortType());
		this.setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		return usrFacade.getAllItemsCount();
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
