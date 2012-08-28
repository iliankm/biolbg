package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.EJBLocator;

import com.biol.biolbg.ejb.session.ProducerFacade;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name = "ProducerBean")
@RequestScoped
public class ProducerBean extends BaseEditItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private ProducerFacade producerFacade = EJBLocator.getInstance().lookup(ProducerFacade.class);

	@Override
	public Object createNewItem() {
		return producerFacade.createNewItem();
	}

	@Override
	public Boolean doSaveData() {
		Boolean res = false;
		try {
			Producer item = (Producer)getItem();
			if (item.getId() > 0) {
				producerFacade.updateItem(item);
			} else {
				producerFacade.addItem(item);
			}
			res = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
		return res;
	}

	@Override
	public Object findItemById(Integer id) {
		return producerFacade.findItem(id);
	}

	@Override
	public void init() {
		// nothing to do here
	}

	public static List<SelectItem> producersSelectItemList(String sortByFieldName, String sortType, String localeName) {
		ProducerFacade producerFacade = EJBLocator.getInstance().lookup(ProducerFacade.class);
		List<SelectItem> result = new ArrayList<SelectItem>();

		//get all producers in allProducers
		List<Producer> allProducers = null;
		allProducers = producerFacade.getAllItems(0, 0, sortByFieldName, sortType);
		//iterate over allProducers and form result List with SelectItem instances
		result.add(new SelectItem(new Producer(),""));
		Iterator<Producer> iter = allProducers.iterator();
		while (iter.hasNext()) {
			Producer producer = iter.next();
			String producerName = producer.getNameen();
			if (localeName.equals("bg")) {
				producerName = producer.getNamebg();
			}
			result.add(new SelectItem(producer,producerName));
		}
		return result;
	}
	
}