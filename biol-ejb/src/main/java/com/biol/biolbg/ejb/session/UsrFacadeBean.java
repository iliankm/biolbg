package com.biol.biolbg.ejb.session;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biol.biolbg.ejb.session.UsrFacade;
import com.biol.biolbg.entity.Usr;
import com.biol.biolbg.exception.ValidateRegistrationException;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
public class UsrFacadeBean implements UsrFacade
{
	@PersistenceContext
	private EntityManager em;

	public void addItem(Usr item)
	{
		em.persist(item);
	}

	public Usr findItem(Integer id)
	{
		Usr res = em.find(Usr.class, id);

		return res;
	}

	public Usr createNewItem()
	{
		return new Usr();
	}

	public void removeItem(Integer id)
	{
		Usr itemUsr = em.find(Usr.class, id);

		if (itemUsr != null)
		{
			em.remove(itemUsr);
		}
	}

	public void updateItem(Usr item)
	{
		em.merge(item);
	}

	@SuppressWarnings("unchecked")
	public List<Usr> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType)
	{
		String queryText = "SELECT o FROM Usr o ORDER BY %s %s";
		queryText = String.format(queryText, sortBy, sortType);
		Query query = em.createQuery(queryText);
		query.setFirstResult(fromRow);

		if (maxResults > 0)
		{
			query.setMaxResults(maxResults);
		}

		return query.getResultList();
	}

	public Long getAllItemsCount()
	{
		Query query = em.createQuery("SELECT COUNT(o.id) FROM Usr o");

		return (Long)query.getSingleResult();
	}

	public Usr findUsrByUsername(String username)
	{
		Query query = em.createQuery("SELECT o FROM Usr o WHERE (o.username=:username)");
		query.setParameter("username", username);
		Usr res = null;

		try
		{
			res = (Usr) query.getSingleResult();
		}
		catch (NoResultException e)
		{
			res = null;
		}

		return res;
	}

	public void updateUsrAfterLogin(Usr usr, String ipAddress)
	{
		Calendar today = Calendar.getInstance();
		usr.setLastlogindate(new Date(today.getTimeInMillis()));
		usr.setLastlogintime(new Date(today.getTimeInMillis()));
		usr.setLastloginip(ipAddress);
		em.merge(usr);
	}

	public void validateRegistrationInfo(String username, String password,
			String repeatPassword) throws ValidateRegistrationException
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


	private Boolean checkUsername(String username)
	{
		return Pattern.matches("[a-z]{1}[a-z0-9._]{2,30}", username);
	}

	private Boolean checkPassword(String password)
	{
		return Pattern.matches("[a-zA-Z0-9\\p{Punct}]{6,30}", password);
	}

	private Boolean usernameExists(String username)
	{
		Usr usr = findUsrByUsername(username);
		return (usr != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAdminEmailAddresses()
	{
		Query query = em.createQuery("SELECT o.email FROM Usr o WHERE o.adminflag=1");

		return query.getResultList();
	}
}
