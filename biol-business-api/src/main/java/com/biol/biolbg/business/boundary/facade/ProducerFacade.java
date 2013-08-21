package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.util.SortCriteria;

@Local
public interface ProducerFacade
{

	public Producer createLocal();

	public Producer create(Producer producer);

	public Producer findById(Integer id);

	public void deleteById(Integer id);

	public Producer update(Producer producer);

	public List<? extends Producer> findAll(int maxResultsLimit, int firstResult,
			SortCriteria sortCriteria);

	public Long getAllCount();

}