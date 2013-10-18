package com.biol.biolbg.business.boundary.facade;

import java.util.Collections;
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
	public Group createLocal()
	{
		return new GroupEntity();
	}

	@Override
	public Group create(final Group group)
	{
		return groupDaoBean.create(group);
	}

	@Override
	public Group findById(final Integer id)
	{
		return groupDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		groupDaoBean.delete(id);
	}

	@Override
	public Group update(final Group group)
	{
		return groupDaoBean.update(group);
	}

	@Override
	public List<Group> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return Collections.<Group>unmodifiableList(groupDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria));
	}

	@Override
	public Long getAllCount()
	{
		return groupDaoBean.getAllCount();
	}

	@Override
	public Group getRandomGroup()
	{
		return groupService.getRandomGroup();
	}

}
