package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.EJBLocator;

import com.biol.biolbg.ejb.session.ProducerFacade;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name = "ProducersListBean")
@RequestScoped
public class ProducersListBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private ProducerFacade producerFacade = EJBLocator.getInstance().lookup(ProducerFacade.class);

	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext()) {
			try {
				producerFacade.removeItem(iter.next());
			} catch (Exception e) {
				addErrorMessage(e.getMessage());
			}
		}
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		List<Producer> dataItems = producerFacade.getAllItems(fromRow, maxResults, getSortByFieldName(), getSortType());
		setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount() {
		return producerFacade.getAllItemsCount();
	}

	@Override
	public void init() {
		if (getSortByFieldName() == "") { 
			setSortByFieldName("o.id");
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
