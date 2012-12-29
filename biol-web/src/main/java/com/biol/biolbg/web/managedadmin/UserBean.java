package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.biol.biolbg.web.util.BaseEditItem;

import com.biol.biolbg.ejb.session.UsrFacade;
import com.biol.biolbg.entity.Usr;

@Named("UserBean")
@RequestScoped
public class UserBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private UsrFacade usrFacade;

	@Override
	public Object createNewItem()
	{
		return usrFacade.createNewItem();
	}

	@Override
	public Boolean doSaveData()
	{
		Boolean res = false;

		try
		{
			Usr item = (Usr)this.getItem();
			if (item.getId() > 0)
			{
				usrFacade.updateItem(item);
			}
			else
			{
				usrFacade.addItem(item);
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
		return usrFacade.findItem(id);
	}

	@Override
	public void init()
	{
	}

	public Boolean getAdminflag()
	{
		if (getItem() instanceof Usr)
		{
			Usr item = (Usr)getItem();
			return (item.getAdminflag() == 1);
		}
		else
		{
			return false;
		}
	}

	public void setAdminflag(Boolean adminflag)
	{
		if (getItem() instanceof Usr)
		{
			Usr item = (Usr)getItem();
			if (adminflag)
			{
				item.setAdminflag(1);
			}
			else
			{
				item.setAdminflag(0);
			}
		}
	}
}
