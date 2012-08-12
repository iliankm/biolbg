package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.EJBLocator;

import com.biol.biolbg.ejb.session.HomeInfoFacade;
import com.biol.biolbg.entity.HomeInfo;

@ManagedBean(name = "HomeInfoBean")
@RequestScoped
public class HomeInfoBean extends BaseEditItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private HomeInfoFacade homeInfoFacade = EJBLocator.getInstance().lookup(HomeInfoFacade.class);

	@Override
	public Object createNewItem() {
		return homeInfoFacade.createNewItem();
	}

	@Override
	public Boolean doSaveData() {
		Boolean res = false;
		try {
			HomeInfo item = (HomeInfo)getItem();
			if (item.getId() > 0) {
				homeInfoFacade.updateItem(item);
			} else {
				homeInfoFacade.addItem(item);
			}
			res = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
		return res;
	}

	@Override
	public Object findItemById(Integer id) {
		return homeInfoFacade.findItem(id);
	}

	@Override
	public void init() {
		// nothing to do here
	}

}
