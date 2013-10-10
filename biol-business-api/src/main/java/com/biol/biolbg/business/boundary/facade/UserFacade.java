package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.exception.ValidateRegistrationException;
import com.biol.biolbg.business.util.SortCriteria;

@Local
public interface UserFacade
{
	public Usr createLocal();

	public Usr create(Usr user);

	public Usr findById(Integer id);

	public void deleteById(Integer id);

	public Usr update(Usr user);

	public List<Usr> findAll(int maxResultsLimit, int firstResult,
			SortCriteria sortCriteria);

	public Long getAllCount();

	public Usr findByUsername(String username);

	public Usr updateAfterLogin(int id, String ipAddress);

	public void validateRegistrationInfo(String username, String password,
			String repeatPassword) throws ValidateRegistrationException;

	public List<String> getAdministratorsEmailAddresses();

	public List<Usr> getAllAdministrators();

}