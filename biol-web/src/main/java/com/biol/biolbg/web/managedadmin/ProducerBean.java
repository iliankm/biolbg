package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.ProducerFacade;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.util.BaseEditItem;


@Named("ProducerBean")
@RequestScoped
public class ProducerBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private ProducerFacade producerFacade;

	@Override
	public Object createNewItem()
	{
		return producerFacade.createLocal();
	}

	@Override
	public Boolean doSaveData()
	{
		Boolean res = false;

		try
		{
			Producer producer = (Producer)getItem();

			if (producer.getId() > 0)
			{
				producerFacade.update(producer);
			}
			else
			{
				producerFacade.create(producer);
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
		return producerFacade.findById(id);
	}

	@Override
	public void init()
	{
	}

	public List<SelectItem> producersSelectItemList(String sortByFieldName, String sortType, String localeName)
	{
		List<SelectItem> result = new ArrayList<SelectItem>();

		SortCriteria sortCriteria = new SortCriteria(sortByFieldName, sortType);

		List<Producer> allProducers = producerFacade.findAll(0, 0, sortCriteria);

		result.add(new SelectItem(producerFacade.createLocal(), ""));

		for (Producer producer : allProducers)
		{
			String producerName = localeName != null && localeName.equals("en") ? producer.getNameen() : producer.getNamebg();

			result.add(new SelectItem(producer, producerName));
		}

		return result;
	}
}
