package com.biol.biolbg.business.boundary.facade;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.UserDaoBean;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.entity.UsrEntity;
import com.biol.biolbg.business.exception.ValidateRegistrationException;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class UserFacadeBean implements UserFacade
{
	@EJB
	private UserDaoBean userDaoBean;


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
		UsrEntity user = userDaoBean.findById(id);

		Calendar today = Calendar.getInstance();
		user.setLastlogindate(new Date(today.getTimeInMillis()));
		user.setLastlogintime(new Date(today.getTimeInMillis()));
		user.setLastloginip(ipAddress);

		return userDaoBean.update(user);
	}

	@Override
	public void validateRegistrationInfo(final String username, final String password, final String repeatPassword) throws ValidateRegistrationException
	{
		ValidateRegistrationException exception = new ValidateRegistrationException();
		Boolean res = true;

		//check if username contains nonvalid chars
		if (!checkUsername(username))
		{
			res = false;
			exception.setInvalidUsername(true);
		}

		//check if password contains nonvalid chars
		if (!checkPassword(password))
		{
			res = false;
			exception.setInvalidPassword(true);
		}

		//check if password equals username
		if (username.equals(password))
		{
			res = false;
			exception.setUsernameEqualsPassword(true);
		}

		//check if username already exists
		if (usernameExists(username))
		{
			res = false;
			exception.setUsernameExists(true);
		}

		//check if password differs repeatPassword
		if (!password.equals(repeatPassword))
		{
			res = false;
			exception.setInvalidRepeatPassword(true);
		}

		if (!res)
		{
			throw exception;
		}
	}


	@Override
	public Boolean checkUsername(final String username)
	{
		return Pattern.matches("[a-z]{1}[a-z0-9._]{2,30}", username);
	}

	@Override
	public Boolean checkPassword(final String password)
	{
		return Pattern.matches("[a-zA-Z0-9\\p{Punct}]{6,30}", password);
	}

	@Override
	public Boolean usernameExists(final String username)
	{
		UsrEntity user = userDaoBean.findByUsername(username);

		return (user != null && user.getUsername().equals(username));
	}

	@Override
	public List<String> getAdministratorsEmailAddresses()
	{
		List<String> result = new LinkedList<String>();

		List<UsrEntity> administrators = userDaoBean.findByAdminFlag(1);

		if (administrators != null && administrators.size() > 0)
		{
			for (UsrEntity user : administrators)
			{
				if (user.getEmail() != null && !user.getEmail().isEmpty())
					result.add(user.getEmail());
			}
		}

		return result;
	}

	@Override
	public List<UsrEntity> getAllAdministrators()
	{
		return userDaoBean.findByAdminFlag(1);
	}
}
