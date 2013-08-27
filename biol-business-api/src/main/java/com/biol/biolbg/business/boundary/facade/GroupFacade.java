package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.util.SortCriteria;

@Local
public interface GroupFacade
{
	public Group createLocal();

	public Group create(Group group);

	public Group findById(Integer id);

	public void deleteById(Integer id);

	public Group update(Group group);

	public List<? extends Group> findAll(int maxResultsLimit, int firstResult,
			SortCriteria sortCriteria);

	public Long getAllCount();

	public Group getRandomGroup();

}