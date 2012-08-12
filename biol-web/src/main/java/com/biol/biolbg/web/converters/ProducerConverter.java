package com.biol.biolbg.web.converters;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.biol.biolbg.web.util.EJBLocator;


import com.biol.biolbg.ejb.session.ProducerFacade;
import com.biol.biolbg.entity.Producer;

public class ProducerConverter implements Converter {
	private ProducerFacade producerFacade = EJBLocator.getInstance().lookup(ProducerFacade.class);

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if ("0".equals(arg2)) {
			return null;
		}
		Producer res = null;
		Integer id = -1;
		try {
			id = Integer.parseInt(arg2);
			res = producerFacade.findItem(id);
		} catch (Exception e) {
			throw new ConverterException(getMessage());
		}
		if (res == null) {
			throw new ConverterException(getMessage());
		} else {
			if (res.getId() <= 0) {
				throw new ConverterException(getMessage());
			}
		}
		return res;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			if (arg2 instanceof Producer) {
				Producer producer = (Producer)arg2;
				return Integer.toString(producer.getId());
			}
		}
		return "0";
	}
	
	private FacesMessage getMessage() {
        FacesMessage message = new FacesMessage();
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle =
                  ResourceBundle.getBundle("com.biol.biolbg.web.messages.resources",
                      		context.getViewRoot().getLocale());
        String str = bundle.getString("producerNotValid");
        message.setDetail(str);
        message.setSummary(str);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
		return message;
	}
	

}
