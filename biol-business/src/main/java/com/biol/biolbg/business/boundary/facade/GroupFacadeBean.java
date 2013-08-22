package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.GroupDaoBean;
import com.biol.biolbg.business.control.service.GroupServiceBean;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.GroupEntity;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class GroupFacadeBean implements GroupFacade
{

	@EJB
	private GroupDaoBean groupDaoBean;
	
	@EJB
	private GroupServiceBean groupService;
	

	@Override
	public GroupEntity createLocal()
	{
		return new GroupEntity();
	}

	@Override
	public GroupEntity create(final Group group)
	{
		return groupDaoBean.create((GroupEntity)group);
	}

	@Override
	public GroupEntity findById(final Integer id)
	{
		return groupDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		groupDaoBean.delete(id);
	}

	@Override
	public GroupEntity update(final Group group)
	{
		return groupDaoBean.update((GroupEntity)group);
	}

	@Override
	public List<GroupEntity> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return groupDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria);
	}

	@Override
	public Long getAllCount()
	{
		return groupDaoBean.getAllCount();
	}
	
	@Override
	public GroupEntity getRandomGroup()
	{
		return groupService.getRandomGroup();
	}

}
