package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.biol.biolbg.business.boundary.facade.UserFacade;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.CookiesManager;
import com.biol.biolbg.web.util.MessageResourcesBean;


@Named("AppBean")
@SessionScoped
public class AppBean extends Base implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String appLocale;

	private TimeZone timeZone = TimeZone.getDefault(); //"UTC/GMT +3";

	private String username = "";

	private String password = "";

	private Usr loggedUser;

	private Boolean isUserLoggedIn;

	@Inject
	private CookiesManager cookiesManager;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	@Inject
	private transient Logger log;

	@EJB
	private UserFacade usrFacade;

	@PostConstruct
	public void postConstruct()
	{
		//add info message to log file
		addInfoMessageToLog();

		FacesContext fc = FacesContext.getCurrentInstance();

		//initialize appLocale
		String localeFromCookie =
			cookiesManager.getCookieValue(CookiesManager.CookieNames.LOCALE_COOKIE_NAME);

		if (localeFromCookie != null && localeFromCookie != "")
		{
			appLocale = localeFromCookie;
		}
		else
		{
			//appLocale = Locale.getDefault().getLanguage();
			appLocale = fc.getApplication().getDefaultLocale().getLanguage();
		}

		loggedUser = usrFacade.createLocal();

		isUserLoggedIn = false;
	}

	private void addInfoMessageToLog()
	{
		FacesContext facesCtx = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest)facesCtx.getExternalContext().getRequest();

		String msg = "Site accessed from: ".concat(request.getRemoteAddr());

		log.info(msg);
	}

	public String setBgLocale()
	{
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		viewRoot.setLocale(new Locale("bg"));
		appLocale = "bg";
		cookiesManager.addCookie(CookiesManager.CookieNames.LOCALE_COOKIE_NAME, appLocale);

		return "";
	}

	public String setEnLocale()
	{
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		viewRoot.setLocale(new Locale("en"));
		appLocale = "en";
		cookiesManager.addCookie(CookiesManager.CookieNames.LOCALE_COOKIE_NAME, appLocale);

		return "";
	}

	public String getAppLocale()
	{
		return appLocale;
	}

	public Usr getLoggedUser()
	{
		return loggedUser;
	}

	public String login()
	{
		log.info("User attempt to login with username: '" + username + "' and password: '" + password + "'");


		//check if username & password is provided
		if (username == null || password == null || username.isEmpty() || password.isEmpty())
		{
			String errorText = messageResourcesBean.getMessage("provideUsernamePassword", null);

			FacesContext.getCurrentInstance().addMessage("headerForm:usrInput", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, errorText, null));

			return "error";
		}

		//get Usr object from database with provided username
		Usr usr = usrFacade.findByUsername(username);

		//check if Usr is with provided username and password
		if (usr != null && username != null && username.equals(usr.getUsername()) && password != null && password.equals(usr.getPassword()))
		{
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

			usrFacade.updateAfterLogin(usr.getId(), request.getRemoteAddr());

			loggedUser = usr;

			isUserLoggedIn = true;

			return "loginok";
		}
		else
		{
			String errorText = messageResourcesBean.getMessage("invalidUsernamePassword", null);
			FacesContext.getCurrentInstance().addMessage("headerForm:usrInput", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, errorText, null));

			return "error";
		}
	}

	public String logout()
	{
		loggedUser = usrFacade.createLocal();

		isUserLoggedIn = false;

		return "logout";
	}

	public Boolean getIsUserLoggedIn()
	{
		return isUserLoggedIn;
	}

	public String getAppPath()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	public Boolean getRenderLoginPanel()
	{

		return (isUserLoggedIn == false);
	}

	public Boolean getRenderLogoutPanel()
	{
		return isUserLoggedIn;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setTimeZone(TimeZone timeZone)
	{
		this.timeZone = timeZone;
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}
}
