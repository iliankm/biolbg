package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.OrderFacade;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.OrderStatus;
import com.biol.biolbg.business.entity.Usr;
import com.biol.biolbg.web.managed.AppBean;
import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.MessageResourcesBean;


@Named("OrderBean")
@RequestScoped
public class OrderBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<SelectItem> orderStatusSelectItems = new ArrayList<SelectItem>();

	private HtmlDataTable orderRowsDataTable;

	private OrderStatus orderStatus;

	@Inject
	private AppBean appBean;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	@EJB
	private OrderFacade orderFacade;

	@Override
	public Boolean getIsViewItemOnly()
	{
		return true;
	}

	@Override
	public Object createNewItem()
	{
		return orderFacade.createLocal((Usr)appBean.getLoggedUser());
	}

	@Override
	public Boolean doSaveData()
	{
		return true;
	}

	@Override
	public Object findItemById(Integer id)
	{
		try
		{
			Order order = orderFacade.findById(id);

			orderStatus = order.getStatus();

			//mark Order as seen by admin
			if (appBean.getLoggedUser().getAdminflag() == 1 && order.getSeenbyadmin() != 1)
			{
				order = orderFacade.markOrderAsSeenByAdmin(id);
			}

			return order;
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}

		return null;
	}

	@Override
	public void init()
	{
		// load orderStatusSelectItems
		List<OrderStatus> listOrderStatus = orderFacade.findAllOrderStatuses();

		if (listOrderStatus != null)
		{
			Iterator<OrderStatus> iter = listOrderStatus.iterator();

			while (iter.hasNext())
			{
				OrderStatus orderStatus = iter.next();
				String orderStatusName;

				if (appBean.getAppLocale().equals("en"))
				{
					orderStatusName = orderStatus.getNameen();
				}
				else
				{
					orderStatusName = orderStatus.getNamebg();
				}

				orderStatusSelectItems.add(new SelectItem(orderStatus, orderStatusName));
			}
		}
	}

	public void updateStatus(ActionEvent event)
	{
		try
		{
			orderFacade.updateStatus(this.getRealItemId(), Integer.valueOf(orderStatus.getId()));

			String text = messageResourcesBean.getMessage("dataSavedOK", null);

			addInfoMessage(text);
		}
		catch (Exception e)
		{
			addErrorMessage(e.getMessage());
		}
	}

	public List<SelectItem> getOrderStatusSelectItems()
	{
		return orderStatusSelectItems;
	}

	public void setOrderRowsDataTable(HtmlDataTable orderRowsDataTable)
	{
		this.orderRowsDataTable = orderRowsDataTable;
	}

	public HtmlDataTable getOrderRowsDataTable()
	{
		return orderRowsDataTable;
	}

	public OrderStatus getOrderStatus()
	{
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus)
	{
		this.orderStatus = orderStatus;
	}
}
