package com.biol.biolbg.web.util;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.biol.biolbg.web.managed.AppBean;


public abstract class Base {
	private AppBean appBean;
	

	public static final void addErrorMessage(String errorText) {
		FacesContext.getCurrentInstance().addMessage("baseForm", new FacesMessage(
                FacesMessage.SEVERITY_ERROR, errorText, null));
	}
	public static final void addInfoMessage(String infoText) {
		FacesContext.getCurrentInstance().addMessage("baseForm", new FacesMessage(
                FacesMessage.SEVERITY_INFO, infoText, null));
	}
	
	public final Boolean getHaveErrorMessages() {
		Boolean res = false;
		Iterator<FacesMessage> iter = FacesContext.getCurrentInstance().getMessages();
		while (iter.hasNext()) {
			FacesMessage facesMessage = (FacesMessage)iter.next();
			if (facesMessage.getSeverity().equals(FacesMessage.SEVERITY_ERROR)) {
				res = true;
			}
		}
		return res;
	}
	public final AppBean getAppBean() {
		if (appBean == null) {
			appBean = (AppBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("AppBean");
		}
		return appBean;
	}

}
