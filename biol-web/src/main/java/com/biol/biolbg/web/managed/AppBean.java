package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.BiolLogger;
import com.biol.biolbg.web.util.EJBLocator;
import com.biol.biolbg.web.util.ViewCredentials;

import com.biol.biolbg.ejb.session.UsrFacade;
import com.biol.biolbg.entity.Usr;

@ManagedBean(name = "AppBean")
@SessionScoped
public class AppBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String LOCALE_COOKIE_NAME = "locale";
	private String appLocale;
	private TimeZone timeZone = TimeZone.getDefault(); //"UTC/GMT +3";
	private String username = "";
	private String password = "";
	private Usr loggedUser = new Usr();
	private Boolean isUserLoggedIn = false;
	private Map<String, ViewCredentials> viewCredentials = new HashMap<String, ViewCredentials>();
	private UIInput usernameComp;
	private UIInput passwordComp;
	private UsrFacade usrFacade = EJBLocator.getInstance().lookup(UsrFacade.class);
	
	private String getLocaleFromCookie() {
		String locale = "";
		FacesContext fc = FacesContext.getCurrentInstance();
		Cookie cookie[] = ((HttpServletRequest)fc.getExternalContext().
							getRequest()).getCookies();		
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals(LOCALE_COOKIE_NAME)) {
					locale = cookie[i].getValue();
				}
			}
		}
		return locale;
	}
	private void setLocaleInCookie( String locale ) {
		Cookie LocaleCookie = new Cookie(LOCALE_COOKIE_NAME, appLocale);
		FacesContext fc = FacesContext.getCurrentInstance();
		((HttpServletResponse)fc.getExternalContext().getResponse()).
			addCookie(LocaleCookie);
	}
	private void addInfoMessageToLog() {
		FacesContext facesCtx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)facesCtx.getExternalContext().getRequest();
		String msg = "Site accessed from: ".concat(request.getRemoteAddr());
		BiolLogger.getLogger().info(msg);
	}
	public AppBean() {
		//add info message to log file
		addInfoMessageToLog();
		//initialize appLocale
		String LocaleFromCookie = getLocaleFromCookie();
		if ((LocaleFromCookie != "") && (LocaleFromCookie != null)) {
			appLocale = LocaleFromCookie;
		}
		else {
			appLocale = Locale.getDefault().getLanguage();
		}
	}
	public String setBgLocale() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		viewRoot.setLocale(new Locale("bg"));
		appLocale = "bg";
		setLocaleInCookie(appLocale);
		return "";
	}
	public String setEnLocale() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		viewRoot.setLocale(new Locale("en"));
		appLocale = "en";
		setLocaleInCookie(appLocale);
		return "";
	}
	public String getAppLocale() {
		return appLocale;
	}
	public Usr getLoggedUser() {
		return loggedUser;
	}
	public String login() {
		//get values from username and password components
		if (usernameComp.isLocalValueSet()) {
			username = usernameComp.getValue().toString();
		} else {
			username = usernameComp.getSubmittedValue().toString();
		}
		if (passwordComp.isLocalValueSet()) {
			password = passwordComp.getValue().toString();
		} else {
			password = passwordComp.getSubmittedValue().toString();
		}
		
		loggedUser = new Usr();
		isUserLoggedIn = false;
		//check if username & password is provided
		if ((username.trim().equals(""))||(password.trim().equals(""))) {
			String errorText = getMessageResourceString("provideUsernamePassword", null);
			FacesContext.getCurrentInstance().addMessage("baseForm:usrInput", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, errorText, null));
			return "error";
		}
		//get Usr object from database with provided username
		Usr usr = null;
		usr = usrFacade.findUsrByUsername(username);
		
		//check if Usr is with provided username and password 
		Boolean res = false;
		if (usr != null) {
			if (usr.getUsername().equals(username)) {
				if (usr.getPassword().equals(password)) {
					res = true;
				}
			}
		}
		if (!res) {
			String errorText = getMessageResourceString("invalidUsernamePassword", null);
			FacesContext.getCurrentInstance().addMessage("baseForm:usrInput", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, errorText, null));
			return "error";
		} else {
			//update Usr entity with ip address and lastlogin date and time
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			try {
				usrFacade.updateUsrAfterLogin(usr, request.getRemoteAddr());
			} catch (Exception e) {
				Base.addErrorMessage(e.getMessage());
				return "error";
			}
			loggedUser = usr;
			isUserLoggedIn = true;
			return "loginok";
		}
	}
	public String logout() {
		loggedUser = new Usr();
		isUserLoggedIn = false;
		return "logout";
	}
	public Boolean getIsUserLoggedIn() {
		return isUserLoggedIn;
	}
	public String getAppPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
	public String getViewId() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		return viewRoot.getViewId();
	}
	public Boolean getRenderLoginPanel() {
		//return (getViewId().equals("/home.xhtml"))&&(isUserLoggedIn == false);
		return (isUserLoggedIn == false);
	}
	public Boolean getRenderLogoutPanel() {
		return isUserLoggedIn;
	}
	public void storeCredentials(String key, String sortByFieldName, String sortType, Integer showRowsCount, 
			Integer pageNumber, Integer totalPages, Object customCredentials) {
		ViewCredentials vc = viewCredentials.get(getViewId());
		if (vc == null) {
			vc = new ViewCredentials();
		}
		vc.setSortByFieldName(sortByFieldName);
		vc.setSortType(sortType);
		vc.setShowRowsCount(showRowsCount);
		vc.setPageNumber(pageNumber);
		vc.setTotalPages(totalPages);
		vc.setCustomCredentials(customCredentials);
		this.viewCredentials.put(key, vc);
	}
	public ViewCredentials restoreCredentials(String key) {
		return viewCredentials.get(key);
	}
	public String getMessageResourceString(String key, Object params[]) {
		
		String text = null;
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = context.getViewRoot().getLocale();
		ResourceBundle bundle =	ResourceBundle.getBundle("com.biol.biolbg.web.messages.messages", locale);
		try {
			text = bundle.getString(key);
		} catch(MissingResourceException e){
			text = "?? key " + key + " not found ??";
		}
		if(params != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	public TimeZone getTimeZone() {
		return timeZone;
	}
	public void setUsernameComp(UIInput usernameComp) {
		this.usernameComp = usernameComp;
	}
	public UIInput getUsernameComp() {
		return usernameComp;
	}
	public void setPasswordComp(UIInput passwordComp) {
		this.passwordComp = passwordComp;
	}
	public UIInput getPasswordComp() {
		return passwordComp;
	}
}
