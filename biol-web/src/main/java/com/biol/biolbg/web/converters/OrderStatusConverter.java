package com.biol.biolbg.web.converters;

import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.OrderStatus;

public class OrderStatusConverter implements Converter {
	@EJB
	private OrderFacade orderFacade; //= EJBLocator.getInstance().lookup(OrderFacade.class);

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if ("0".equals(arg2)) {
			return null;
		}
		OrderStatus res = null;
		Integer id = -1;
		try {
			id = Integer.parseInt(arg2);
			res = orderFacade.findOrderStatus(id);
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
			if (arg2 instanceof OrderStatus) {
				OrderStatus orderStatus = (OrderStatus)arg2;
				return Integer.toString(orderStatus.getId());
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
        String str = bundle.getString("orderStatusNotValid");
        message.setDetail(str);
        message.setSummary(str);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
		return message;
	}

}
