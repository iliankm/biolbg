package com.biol.biolbg.web.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MessageResourcesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String getMessage(String key, Object params[]) {

		String text = null;

		try {
			text = getBundle().getString(key);
		}
		catch (MissingResourceException e) {
			text = "?? key " + key + " not found ??";
		}

		if(params != null) {
			MessageFormat mf = new MessageFormat(text, getLocale());
			text = mf.format(params, new StringBuffer(), null).toString();
		}

		return text;
	}

	public ResourceBundle getBundle() {

		ResourceBundle bundle =	ResourceBundle.getBundle("com.biol.biolbg.web.messages.messages", getLocale());

		return bundle;
	}

	private Locale getLocale() {

		FacesContext context = FacesContext.getCurrentInstance();

		Locale locale = context.getViewRoot().getLocale();

		return locale;

	}

}
