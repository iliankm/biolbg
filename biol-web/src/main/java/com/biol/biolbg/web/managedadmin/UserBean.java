package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.EJBLocator;

import com.biol.biolbg.ejb.session.UsrFacade;
import com.biol.biolbg.entity.Usr;

@ManagedBean(name = "UserBean")
@RequestScoped
public class UserBean extends BaseEditItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private UsrFacade usrFacade = EJBLocator.getInstance().lookup(UsrFacade.class);

	@Override
	public Object createNewItem() {
		return usrFacade.createNewItem();
	}

	@Override
	public Boolean doSaveData() {
		Boolean res = false;
		try {
			Usr item = (Usr)this.getItem();
			if (item.getId() > 0) {
				usrFacade.updateItem(item);
			} else {
				usrFacade.addItem(item);
			}
			res = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
		return res;
	}

	@Override
	public Object findItemById(Integer id) {
		return usrFacade.findItem(id);
	}

	@Override
	public void init() {
		// nothing to do here
	}

	public Boolean getAdminflag() {
		if (getItem() instanceof Usr) {
			Usr item = (Usr)getItem(); 
			return (item.getAdminflag() == 1); 
		} else {
			return false;
		}
	}
	
	public void setAdminflag(Boolean adminflag) {
		if (getItem() instanceof Usr) {
			Usr item = (Usr)getItem();
			if (adminflag) {
				item.setAdminflag(1);
			} else {
				item.setAdminflag(0);
			}
		}
	}
	
}
