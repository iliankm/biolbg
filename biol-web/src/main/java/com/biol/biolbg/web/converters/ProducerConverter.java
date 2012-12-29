package com.biol.biolbg.web.converters;

import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import com.biol.biolbg.ejb.session.ProducerFacade;
import com.biol.biolbg.entity.Producer;

@ManagedBean(name="ProducerConverter")
@RequestScoped
public class ProducerConverter implements Converter
{
	@EJB
	private ProducerFacade producerFacade;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
	{
		if ("0".equals(arg2))
		{
			return null;
		}

		Integer id;
		try
		{
			id = Integer.parseInt(arg2);
		}
		catch (Exception e)
		{
			throw new ConverterException(getMessage());
		}

		Producer res = producerFacade.findItem(id);

		if (res == null)
		{
			throw new ConverterException(getMessage());
		}

		return res;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
	{
		if (arg2 != null)
		{
			if (arg2 instanceof Producer)
			{
				Producer producer = (Producer)arg2;
				return Integer.toString(producer.getId());
			}
		}

		return "0";
	}

	private FacesMessage getMessage()
	{
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
