package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.HomeInfoFacade;
import com.biol.biolbg.business.entity.HomeInfo;
import com.biol.biolbg.web.util.BaseEditItem;


@Named("HomeInfoBean")
@RequestScoped
public class HomeInfoBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private HomeInfoFacade homeInfoFacade;

	@Override
	public Object createNewItem()
	{
		return homeInfoFacade.createLocal();
	}

	@Override
	public Boolean doSaveData()
	{
		Boolean res = false;

		try
		{
			HomeInfo item = (HomeInfo)getItem();
			if (item.getId() > 0)
			{
				homeInfoFacade.update(item);
			}
			else
			{
				homeInfoFacade.create(item);
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
		return homeInfoFacade.findById(id);
	}

	@Override
	public void init()
	{
	}
}
