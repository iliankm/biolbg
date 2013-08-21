package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.UserDaoBean;
import com.biol.biolbg.business.control.service.UserService;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.entity.UsrEntity;
import com.biol.biolbg.business.exception.ValidateRegistrationException;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class UserFacadeBean implements UserFacade
{
	@EJB
	private UserDaoBean userDaoBean;

	@EJB
	private UserService userService;


	@Override
	public UsrEntity createLocal()
	{
		return new UsrEntity();
	}

	@Override
	public UsrEntity create(final Usr user)
	{
		return userDaoBean.create((UsrEntity)user);
	}

	@Override
	public UsrEntity findById(final Integer id)
	{
		return userDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		userDaoBean.delete(id);
	}

	@Override
	public UsrEntity update(final Usr user)
	{
		return userDaoBean.update((UsrEntity)user);
	}

	@Override
	public List<UsrEntity> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return userDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria);
	}

	@Override
	public Long getAllCount()
	{
		return userDaoBean.getAllCount();
	}

	@Override
	public UsrEntity findByUsername(final String username)
	{
		return userDaoBean.findByUsername(username);
	}

	@Override
	public UsrEntity updateAfterLogin(final int id, final String ipAddress)
	{
		return userService.updateAfterLogin(id, ipAddress);
	}

	@Override
	public void validateRegistrationInfo(final String username, final String password, final String repeatPassword) throws ValidateRegistrationException
	{
		userService.validateRegistrationInfo(username, password, repeatPassword);
	}

	@Override
	public List<String> getAdministratorsEmailAddresses()
	{
		return userService.getAdministratorsEmailAddresses();
	}

	@Override
	public List<UsrEntity> getAllAdministrators()
	{
		return userDaoBean.findByAdminFlag(1);
	}
}
