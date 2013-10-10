package com.biol.biolbg.web.managed;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.OrderFacade;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.MessageResourcesBean;


@Named("OrderForUserBean")
@RequestScoped
public class OrderForUserBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	private HtmlDataTable orderRowsDataTable;

	@EJB
	private OrderFacade orderFacade;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	@Inject
	private AppBean appBean;

	@Override
	public Boolean getIsViewItemOnly()
	{
		return true;
	}

	@Override
	public Object createNewItem()
	{
		Order res = orderFacade.createLocal((Usr)appBean.getLoggedUser());

		return res;
	}

	@Override
	public Boolean doSaveData()
	{
		return true;
	}

	@Override
	public Object findItemById(Integer id)
	{
		return orderFacade.findById(id);
	}

	public Boolean getUserLoggedIn()
	{
		return appBean.getIsUserLoggedIn();
	}

	public Boolean getOrderIsForThisUser()
	{
		Integer itemId = getRealItemId();

		Order order = (Order)findItemById(itemId);

		if (order != null && order.getUser() != null)
		{
			return order.getUser().getId() == appBean.getLoggedUser().getId();
		}
		else
		{
			return false;
		}
	}

	public Boolean getRenderView()
	{
		return ((getUserLoggedIn())&&(getOrderIsForThisUser()));
	}

	@Override
	public void init()
	{
	}

	public void cancelOrder(ActionEvent event)
	{
		//check if user logged in
		if (!appBean.getIsUserLoggedIn())
		{
			addErrorMessage(messageResourcesBean.getMessage("toCancelOrderUserMustLogin", null));
			return;
		}

		try
		{
			Order updatedOrder = orderFacade.cancelOrder(getRealItemId());
			setItem(updatedOrder);
			String text = messageResourcesBean.getMessage("orderCanceled", null);
			addInfoMessage(text);

		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}
	}

	public void setOrderRowsDataTable(HtmlDataTable orderRowsDataTable)
	{
		this.orderRowsDataTable = orderRowsDataTable;
	}

	public HtmlDataTable getOrderRowsDataTable()
	{
		return orderRowsDataTable;
	}
}
