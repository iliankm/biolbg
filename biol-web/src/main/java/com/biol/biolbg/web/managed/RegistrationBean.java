package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.MessageResourcesBean;

import com.biol.biolbg.business.boundary.facade.MailMessageSenderFacade;
import com.biol.biolbg.business.entity.mail.MailMessage;
import com.biol.biolbg.business.entity.mail.UserRegisteredMailMessageBuilder;
import com.biol.biolbg.ejb.session.UsrFacade;

import com.biol.biolbg.entity.Usr;
import com.biol.biolbg.exception.ValidateRegistrationException;

@Named("RegistrationBean")
@RequestScoped
public class RegistrationBean extends Base implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String repeatPassword;

	private String fullname;

	private String organisation;

	private String email;

	private String regcode;

	@EJB
	private UsrFacade usrFacade;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	@Inject
	private UserRegisteredMailMessageBuilder userRegisteredMailMessageBuilder;

	@EJB
	private MailMessageSenderFacade mailMessageSenderService;

	public String register()
	{
		String errorText;

		Boolean haveErrors = false;
		//check reg code
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();

		String randomRegCode = (String)session.getAttribute("RandomRegCode");

		Boolean regCodeMatches = true;
		if ((randomRegCode == null)||(regcode == null))
		{
			regCodeMatches = false;
		}
		else
		{
			if (!randomRegCode.equals(regcode))
			{
				regCodeMatches = false;
			}
		}

		if (!regCodeMatches)
		{
			errorText = messageResourcesBean.getMessage("regCodeNotMatches", null);
			FacesContext.getCurrentInstance().addMessage("baseForm:regcode", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, errorText, null));
			haveErrors = true;
		}

		//check username, password, repeatpassword
		try
		{
			usrFacade.validateRegistrationInfo(username, password, repeatPassword);

			//create Usr object and set properties
			Usr usr = new Usr();
			usr.setUsername(username);
			usr.setPassword(password);
			usr.setOrganisation(organisation);
			usr.setFullname(fullname);
			usr.setEmail(email);

			usrFacade.addItem(usr);

			sendEmailWhenUserRegistered(usr);
		}
		catch (ValidateRegistrationException e)
		{
			if (e.getInvalidUsername())
			{
				errorText = messageResourcesBean.getMessage("invalidValue", null);
				FacesContext.getCurrentInstance().addMessage("baseForm:username", new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, errorText, null));
			}

			if (e.getUsernameExists())
			{
				errorText = messageResourcesBean.getMessage("usernameExists", null);
				FacesContext.getCurrentInstance().addMessage("baseForm:username", new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, errorText, null));
			}

			if (e.getInvalidPassword())
			{
				errorText = messageResourcesBean.getMessage("invalidValue", null);
				FacesContext.getCurrentInstance().addMessage("baseForm:password", new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, errorText, null));
			}

			if (e.getUsernameEqualsPassword())
			{
				errorText = messageResourcesBean.getMessage("usernameEqualsPassword", null);
				FacesContext.getCurrentInstance().addMessage("baseForm:password", new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, errorText, null));
			}

			if (e.getInvalidRepeatPassword())
			{
				errorText = messageResourcesBean.getMessage("passwordNotMatch", null);
				FacesContext.getCurrentInstance().addMessage("baseForm:repeatpassword", new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, errorText, null));
			}
			haveErrors = true;
		}
		catch (Exception e)
		{
			if (!(e instanceof ValidateRegistrationException))
			{
				addErrorMessage(e.getMessage());
			}
			haveErrors = true;
		}

		if (haveErrors)
		{
			return "";
		}

		return "success";
	}

	public void validateUsername(ActionEvent event)
	{
		//check username
		String errorText;
		Boolean haveError = false;
		try
		{
			usrFacade.validateRegistrationInfo(username, "", "");

		}
		catch (ValidateRegistrationException e)
		{
			if (e.getInvalidUsername())
			{
				errorText = messageResourcesBean.getMessage("invalidValue", null);
				FacesContext.getCurrentInstance().addMessage("baseForm:username", new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, errorText, null));
				haveError = true;
			}

			if (e.getUsernameExists())
			{
				errorText = messageResourcesBean.getMessage("usernameExists", null);
				FacesContext.getCurrentInstance().addMessage("baseForm:username", new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, errorText, null));
				haveError = true;
			}
		}
		catch (Exception e)
		{
			if (!(e instanceof ValidateRegistrationException))
			{
				haveError = true;
			}
		}

		if (!haveError)
		{
			String msg = messageResourcesBean.getMessage("validUsername", null);
			FacesContext.getCurrentInstance().addMessage("baseForm:username", new FacesMessage(
	                FacesMessage.SEVERITY_WARN, msg, null));
		}
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getRepeatPassword()
	{
		return repeatPassword;
	}

	public void setFullname(String fullname)
	{
		this.fullname = fullname;
	}

	public String getFullname()
	{
		return fullname;
	}

	public void setRepeatPassword(String repeatPassword)
	{
		this.repeatPassword = repeatPassword;
	}

	public String getOrganisation()
	{
		return organisation;
	}

	public void setOrganisation(String organisation)
	{
		this.organisation = organisation;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getRegcode()
	{
		return regcode;
	}

	public void setRegcode(String regcode)
	{
		this.regcode = regcode;
	}

	public String getRandomStringParam()
	{
		String res = "";
		Random random = new Random();
		Integer num1 = random.nextInt(999999);
		res = num1.toString();
		Integer num2 = random.nextInt(99999999);
		res = res.concat(num2.toString());

		return res;
	}

	private void sendEmailWhenUserRegistered(Usr registeredUser)
	{
		List<String> adminEmails = usrFacade.getAdminEmailAddresses();

		MailMessage mailMessage =
			userRegisteredMailMessageBuilder.
				adminEmails(adminEmails).
				date(new Date()).
				messageLocale(new Locale("bg")).
				name(registeredUser.getFullname()).
				organization(registeredUser.getOrganisation()).
				username(registeredUser.getUsername()).
				build();

		mailMessageSenderService.send(mailMessage);
	}
}
