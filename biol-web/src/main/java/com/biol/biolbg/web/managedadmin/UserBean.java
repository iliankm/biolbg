package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.UserFacade;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.web.util.BaseEditItem;


@Named("UserBean")
@RequestScoped
public class UserBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacade usrFacade;

	@Override
	public Object createNewItem()
	{
		return usrFacade.createLocal();
	}

	@Override
	public Boolean doSaveData()
	{
		Boolean res = false;

		try
		{
			Usr user = (Usr)this.getItem();

			if (user.getId() > 0)
			{
				usrFacade.update(user);
			}
			else
			{
				usrFacade.create(user);
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
		return usrFacade.findById(id);
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
