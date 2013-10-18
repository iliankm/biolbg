package com.biol.biolbg.business.boundary.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.HomeInfoDaoBean;
import com.biol.biolbg.business.entity.HomeInfo;
import com.biol.biolbg.business.entity.HomeInfoEntity;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class HomeInfoFacadeBean implements HomeInfoFacade
{

	@EJB
	private HomeInfoDaoBean homeInfoDaoBean;

	@Override
	public HomeInfo createLocal()
	{
		return new HomeInfoEntity();
	}

	@Override
	public HomeInfo create(final HomeInfo homeInfo)
	{
		return homeInfoDaoBean.create(homeInfo);
	}

	@Override
	public HomeInfo findById(final Integer id)
	{
		return homeInfoDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		homeInfoDaoBean.delete(id);
	}

	@Override
	public HomeInfo update(final HomeInfo homeInfo)
	{
		return homeInfoDaoBean.update(homeInfo);
	}

	@Override
	public List<HomeInfo> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return Collections.<HomeInfo>unmodifiableList(homeInfoDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria));
	}

	@Override
	public Long getAllCount()
	{
		return homeInfoDaoBean.getAllCount();
	}

}
