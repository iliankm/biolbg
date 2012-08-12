package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.EJBLocator;

import com.biol.biolbg.ejb.session.HomeInfoFacade;
import com.biol.biolbg.entity.HomeInfo;

@ManagedBean(name = "HomeInfoListBean")
@RequestScoped
public class HomeInfoListBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private HomeInfoFacade homeInfoFacade = EJBLocator.getInstance().lookup(HomeInfoFacade.class);

	@Override
	public void init() {
		if (getSortByFieldName() == "") { 
			setSortByFieldName("o.id");
		}	
	}
	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		List<HomeInfo> dataItems = homeInfoFacade.getAllItems(fromRow, maxResults, getSortByFieldName(), getSortType());
		setDataItems(dataItems);
	}
	@Override
	public Long getDataItemsTotalCount() {
		return homeInfoFacade.getAllItemsCount();
	}
	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext()) {
			try {
				homeInfoFacade.removeItem(iter.next());
			} catch (Exception e) {
				addErrorMessage(e.getMessage());
			}
		}
	}
	@Override
	public void restoreCustomCredentials(Object customCredentials) {
		// nothing to do here
	}
	@Override
	public Object storeCustomCredentials() {
		return null;
	}
	

}
