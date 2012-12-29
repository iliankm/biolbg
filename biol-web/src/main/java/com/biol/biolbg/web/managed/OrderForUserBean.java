package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.MessageResourcesBean;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.Order;
import com.biol.biolbg.entity.OrderRow;
import com.biol.biolbg.entity.OrderStatus;
import com.biol.biolbg.entity.Usr;

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
		Order res = orderFacade.createNewItem();
		res.setRows(new ArrayList<OrderRow>());
		res.setUser(new Usr());
		res.setStatus(new OrderStatus());

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
		return orderFacade.findItem(id);
	}

	public Boolean getUserLoggedIn()
	{
		return appBean.getIsUserLoggedIn();
	}

	public Boolean getOrderIsForThisUser()
	{
		Integer iItemId = getRealItemId();

		Object obj = findItemById(iItemId);

		if (obj instanceof Order)
		{
			Order order = (Order) obj;
			if (order.getUser() == null)
			{
				return false;
			}

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
