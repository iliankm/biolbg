package com.biol.biolbg.web.util;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class Base {

	public void addErrorMessage(String errorText) {
		FacesContext.getCurrentInstance().addMessage("baseForm", new FacesMessage(
                FacesMessage.SEVERITY_ERROR, errorText, null));
	}

	public void addInfoMessage(String infoText) {
		FacesContext.getCurrentInstance().addMessage("baseForm", new FacesMessage(
                FacesMessage.SEVERITY_INFO, infoText, null));
	}

	public Boolean getHaveErrorMessages() {
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
}
