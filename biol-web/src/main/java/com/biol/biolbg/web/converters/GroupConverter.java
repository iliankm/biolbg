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

import com.biol.biolbg.business.boundary.facade.GroupFacade;
import com.biol.biolbg.business.entity.Group;


@ManagedBean(name="GroupConverter")
@RequestScoped
public class GroupConverter implements Converter
{
	@EJB
	private GroupFacade groupFacade;

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

		Group res = groupFacade.findById(id);

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
			if (arg2 instanceof Group)
			{
				Group group = (Group)arg2;
				return Integer.toString(group.getId());
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
        String str = bundle.getString("groupNotValid");
        message.setDetail(str);
        message.setSummary(str);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);

        return message;
	}
}
