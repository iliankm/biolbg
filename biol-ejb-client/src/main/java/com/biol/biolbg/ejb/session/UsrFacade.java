package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.entity.Usr;
import com.biol.biolbg.exception.ValidateRegistrationException;


@Local
public interface UsrFacade
{
	public void addItem(Usr item) ;

	public void removeItem(Integer id) ;

	public void updateItem(Usr item) ;

	public Usr findItem(Integer id);

	public Usr createNewItem();

	public List<Usr> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType);

	public Long getAllItemsCount();

	public Usr findUsrByUsername(String username);

	public void updateUsrAfterLogin(Usr usr, String ipAddress) ;

	public void validateRegistrationInfo(String username, String password, String repeatPassword) throws ValidateRegistrationException;

	public List<String> getAdminEmailAddresses();

	public List<Usr> getAllAdminUsers();
}
