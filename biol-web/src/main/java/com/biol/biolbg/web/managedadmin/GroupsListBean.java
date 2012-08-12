package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.EJBLocator;

import com.biol.biolbg.ejb.session.GroupFacade;
import com.biol.biolbg.entity.Group;

@ManagedBean(name = "GroupsListBean")
@RequestScoped
public class GroupsListBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private GroupFacade groupFacade = EJBLocator.getInstance().lookup(GroupFacade.class);

	@Override
	public void init() {
		if (getSortByFieldName() == "") { 
			setSortByFieldName("o.id");
		}	
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		List<Group> dataItems = groupFacade.getAllItems(fromRow, maxResults, getSortByFieldName(), getSortType());
		setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount() {
		return groupFacade.getAllItemsCount();
	}

	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext()) {
			try {
				groupFacade.removeItem(iter.next());
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
