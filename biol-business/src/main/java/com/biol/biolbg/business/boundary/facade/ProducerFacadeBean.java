package com.biol.biolbg.business.boundary.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.ProducerDaoBean;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.entity.ProducerEntity;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class ProducerFacadeBean implements ProducerFacade
{

	@EJB
	private ProducerDaoBean producerDaoBean;

	@Override
	public ProducerEntity createLocal()
	{
		return new ProducerEntity();
	}

	@Override
	public ProducerEntity create(final Producer producer)
	{
		return producerDaoBean.create((ProducerEntity)producer);
	}

	@Override
	public ProducerEntity findById(final Integer id)
	{
		return producerDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		producerDaoBean.delete(id);
	}

	@Override
	public ProducerEntity update(final Producer producer)
	{
		return producerDaoBean.update((ProducerEntity)producer);
	}

	@Override
	public List<Producer> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return Collections.<Producer>unmodifiableList(producerDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria));
	}

	@Override
	public Long getAllCount()
	{
		return producerDaoBean.getAllCount();
	}

}
