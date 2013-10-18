package com.biol.biolbg.business.control.service;

import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.GroupDaoBean;
import com.biol.biolbg.business.entity.Group;

@Stateless
@LocalBean
public class GroupServiceBean
{
	@EJB
	GroupDaoBean groupDaoBean;

	public Group getRandomGroup()
	{
		Group group = null;

		List<Integer> groupIds = groupDaoBean.getGroupIds();

		if (groupIds != null && groupIds.size() > 0)
		{
			Random random = new Random();
			Integer randomInteger = random.nextInt(groupIds.size()-1);
			Integer groupId = groupIds.get(randomInteger);

			group = groupDaoBean.findById(groupId);
		}

		return group;
	}

}
