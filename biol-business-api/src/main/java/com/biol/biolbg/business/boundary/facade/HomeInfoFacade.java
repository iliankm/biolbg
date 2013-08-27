package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.business.entity.HomeInfo;
import com.biol.biolbg.business.util.SortCriteria;

@Local
public interface HomeInfoFacade
{
	public HomeInfo createLocal();

	public HomeInfo create(HomeInfo homeInfo);

	public HomeInfo findById(Integer id);

	public void deleteById(Integer id);

	public HomeInfo update(HomeInfo homeInfo);

	public List<? extends HomeInfo> findAll(int maxResultsLimit, int firstResult,
			SortCriteria sortCriteria);

	public Long getAllCount();

}