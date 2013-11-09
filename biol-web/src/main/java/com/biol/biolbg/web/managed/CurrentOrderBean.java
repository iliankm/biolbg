package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.MessageResourcesBean;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;
import com.biol.biolbg.business.boundary.facade.MailMessageSenderFacade;
import com.biol.biolbg.business.boundary.facade.OrderFacade;
import com.biol.biolbg.business.boundary.facade.UserFacade;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.business.entity.mail.MailMessage;
import com.biol.biolbg.business.entity.mail.UserPostedOrderMailMessageBuilder;

@Named("CurrentOrderBean")
@SessionScoped
public class CurrentOrderBean extends Base implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Order order;

	private java.util.Date fordate;

	private java.util.Date fortime;

	private HtmlDataTable orderRowsDataTable;

	private Map<Integer,String> orderedAmount;

	private String regcode;

	@EJB
	private OrderFacade orderFacade;

	@EJB
	private UserFacade userFacade;

	@EJB
	private MailMessageSenderFacade mailMessageSenderService;

	@Inject
	private ItemImagesFilenameMapper itemImagesFilenameMapper;

	@Inject
	private AppBean appBean;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	@Inject
	private UserPostedOrderMailMessageBuilder userPostedOrderMailMessageBuilder;

	@PostConstruct
	public void postConstruct()
	{
		order = orderFacade.createLocal(appBean.getLoggedUser());

		order.setDeliveryAddress(getLastDeliveryAddressForUser());

		orderedAmount = new HashMap<Integer,String>();
	}

	public void saveData(ActionEvent event)
	{
		//check reg code
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();

		String randomRegCode = (String)session.getAttribute("RandomRegCode");
		Boolean regCodeMatches = true;

		if ((randomRegCode == null)||(regcode == null))
		{
			regCodeMatches = false;
		}
		else
		{
			if (!randomRegCode.equals(regcode))
			{
				regCodeMatches = false;
			}
		}

		if (!regCodeMatches)
		{
			String errorText = messageResourcesBean.getMessage("regCodeNotMatches", null);
			FacesContext.getCurrentInstance().addMessage("baseForm:regcode", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, errorText, null));

			return;
		}

		//check if user logged in
		if (!appBean.getIsUserLoggedIn())
		{
			addErrorMessage(messageResourcesBean.getMessage("toPostOrderUserMustLogin", null));
			return;
		}

		//check if no rows
		Boolean haveRows = false;
		if (order.getRows() != null)
		{
			haveRows = !order.getRows().isEmpty();
		}

		if (!haveRows)
		{
			addErrorMessage(messageResourcesBean.getMessage("noorderrows", null));
			return;
		}

		//set for date
		if (fordate != null)
		{
			order.setFordate(fordate);
		}

		//set for time
		if (fortime != null)
		{
			order.setFortime(fortime);
		}

		order.setUser(appBean.getLoggedUser());

		//save the order
		try
		{
			if (order.getId() > 0)
			{
				orderFacade.update(order);
			}
			else
			{
				orderFacade.create(order);
			}

			sendEmailToAdminsWhenOrderIsSaved(order);

			String text = messageResourcesBean.getMessage("dataSavedOK", null);
			addInfoMessage(text);
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
			return;
		}

		order = orderFacade.createLocal(appBean.getLoggedUser());

		order.setDeliveryAddress(getLastDeliveryAddressForUser());

		orderedAmount.clear();
	}

	public void deleteRow(ActionEvent event)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		Integer articleId = Integer.valueOf(params.get("articleId"));

		orderFacade.deleteRowByArticleIdLocal(order, articleId);
	}

	public Boolean getHaveOrderedArticles()
	{
		Boolean res = false;
		if (order.getRows() != null)
		{
			res = !order.getRows().isEmpty();
		}

		return res;
	}

	//get the last posted deliv address
	private String getLastDeliveryAddressForUser()
	{
		if (appBean.getLoggedUser() != null && appBean.getLoggedUser().getId() > 0)
		{
			return orderFacade.getLastDeliveryAddressForUser(Integer.valueOf(appBean.getLoggedUser().getId()));
		}
		else
		{
			return "";
		}
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrderRowsDataTable(HtmlDataTable orderRowsDataTable)
	{
		this.orderRowsDataTable = orderRowsDataTable;
	}

	public HtmlDataTable getOrderRowsDataTable()
	{
		return orderRowsDataTable;
	}

	public Map<Integer, String> getOrderedAmount()
	{
		return orderedAmount;
	}

	public void setFordate(Date fordate)
	{
		this.fordate = fordate;
	}

	public Date getFordate()
	{
		return fordate;
	}

	public void setFortime(Date fortime)
	{
		this.fortime = fortime;
	}

	public Date getFortime()
	{
		return fortime;
	}

	public void setRegcode(String regcode)
	{
		this.regcode = regcode;
	}

	public String getRegcode()
	{
		return regcode;
	}

	public String getRandomStringParam()
	{
		String res = "";
		Random random = new Random();
		Integer num1 = random.nextInt(999999);
		res = num1.toString();
		Integer num2 = random.nextInt(99999999);
		res = res.concat(num2.toString());

		return res;
	}

	public Map<Integer, String> getImageName()
	{
		Map<Integer, String> res = new AbstractMap<Integer, String>()
		{
			@Override
			public String get(Object key)
			{
				Integer itemId;
				try
				{
					itemId = Integer.valueOf(key.toString());
				}
				catch (Exception e)
				{
					itemId = -1;
				}

				return itemImagesFilenameMapper.getSingle(itemId);
			}

			@Override
			public Set<java.util.Map.Entry<Integer, String>> entrySet()
			{
				throw new UnsupportedOperationException();
			}
		};

		return res;
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper)
	{
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper()
	{
		return itemImagesFilenameMapper;
	}

	private void sendEmailToAdminsWhenOrderIsSaved(Order order)
	{
		List<Usr> adminUsers = userFacade.getAllAdministrators();

		MailMessage mailMessage =
			userPostedOrderMailMessageBuilder.
				adminUsers(adminUsers).
				messageLocale(new Locale("bg")).
				order((com.biol.biolbg.business.entity.Order)order).
				build();

		mailMessageSenderService.send(mailMessage);
	}
}
