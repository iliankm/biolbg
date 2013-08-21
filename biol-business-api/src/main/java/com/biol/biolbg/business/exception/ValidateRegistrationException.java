package com.biol.biolbg.business.exception;

public class ValidateRegistrationException extends Exception
{
	private static final long serialVersionUID = 1L;

	private Boolean invalidUsername = false;

	private Boolean usernameExists = false;

	private Boolean invalidPassword = false;

	private Boolean invalidRepeatPassword = false;

	private Boolean usernameEqualsPassword = false;

	public Boolean getInvalidUsername()
	{
		return invalidUsername;
	}

	public void setInvalidUsername(Boolean invalidUsername)
	{
		this.invalidUsername = invalidUsername;
	}

	public Boolean getUsernameExists()
	{
		return usernameExists;
	}

	public void setUsernameExists(Boolean usernameExists)
	{
		this.usernameExists = usernameExists;
	}

	public Boolean getInvalidPassword()
	{
		return invalidPassword;
	}

	public void setInvalidPassword(Boolean invalidPassword)
	{
		this.invalidPassword = invalidPassword;
	}

	public Boolean getInvalidRepeatPassword()
	{
		return invalidRepeatPassword;
	}

	public void setInvalidRepeatPassword(Boolean invalidRepeatPassword)
	{
		this.invalidRepeatPassword = invalidRepeatPassword;
	}

	public void setUsernameEqualsPassword(Boolean usernameEqualsPassword)
	{
		this.usernameEqualsPassword = usernameEqualsPassword;
	}

	public Boolean getUsernameEqualsPassword()
	{
		return usernameEqualsPassword;
	}
}
