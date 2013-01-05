package com.biol.biolbg.web.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
public class CookiesManager implements Serializable
{
	private static final long serialVersionUID = 1L;

	public interface CookieNames
	{
		public static final String LOCALE_COOKIE_NAME = "locale";
	}

	public Cookie getCookie(String cookieName)
	{
		Map<String, Cookie> cookiesMap = getCookiesMap();

		return cookiesMap.get(cookieName);
	}

	public String getCookieValue(String cookieName)
	{
		Map<String, Cookie> cookiesMap = getCookiesMap();

		Cookie cookie = cookiesMap.get(cookieName);

		if (cookie != null)
		{
			return cookiesMap.get(cookieName).getValue();
		}
		else
		{
			return "";
		}
	}

	public void addCookie(String cookieName, String cookieValue)
	{
		FacesContext fc = FacesContext.getCurrentInstance();

		Cookie cookie = new Cookie(cookieName, cookieValue);

		((HttpServletResponse)fc.getExternalContext().getResponse()).addCookie(cookie);
	}

	private Map<String, Cookie> getCookiesMap()
	{
		Map<String, Cookie> cookiesMap = new HashMap<String, Cookie>();

		FacesContext fc = FacesContext.getCurrentInstance();
		Cookie cookies[] = ((HttpServletRequest)fc.getExternalContext().getRequest()).getCookies();

		if (cookies != null)
		{
			for (int i = 0; i < cookies.length; i++)
			{
				Cookie cookie = cookies[i];
				cookiesMap.put(cookie.getName(), cookie);
			}
		}

		return cookiesMap;
	}
}
