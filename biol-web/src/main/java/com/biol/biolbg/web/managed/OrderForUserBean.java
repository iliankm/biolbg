package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.EJBLocator;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.Order;
import com.biol.biolbg.entity.OrderRow;
import com.biol.biolbg.entity.OrderStatus;
import com.biol.biolbg.entity.Usr;

@ManagedBean(name = "OrderForUserBean")
@RequestScoped
public class OrderForUserBean extends BaseEditItem implements Serializable{

	private static final long serialVersionUID = 1L;
	private HtmlDataTable orderRowsDataTable;
	private OrderFacade orderFacade = EJBLocator.getInstance().lookup(OrderFacade.class);
	
	@Override
	public Boolean getIsViewItemOnly() {
		return true;
	}

	@Override
	public Object createNewItem() {
		Order res = orderFacade.createNewItem();
		res.setRows(new ArrayList<OrderRow>());
		res.setUser(new Usr());
		res.setStatus(new OrderStatus());
		return res;
	}

	@Override
	public Boolean doSaveData() {
		return true;
	}

	@Override
	public Object findItemById(Integer id) {
		return orderFacade.findItem(id);
	}
	
	public Boolean getUserLoggedIn() {
		return getAppBean().getIsUserLoggedIn();
	}
	public Boolean getOrderIsForThisUser() {
		Integer iItemId = getRealItemId();
		
		Object obj = findItemById(iItemId); 
		if (obj instanceof Order) {
			Order order = (Order) obj;
			if (order.getUser() == null) {
				return false;
			}
			return order.getUser().getId() == getAppBean().getLoggedUser().getId();
		} else {
			return false;
		}
		//return order.getUser().getId() == getAppBean().getLoggedUser().getId();
	}
	
	public Boolean getRenderView() {
		return ((getUserLoggedIn())&&(getOrderIsForThisUser()));
	}

	@Override
	public void init() {
		//nothing to do here
	}

	public void cancelOrder(ActionEvent event) {
		//check if user logged in
		if (!getAppBean().getIsUserLoggedIn()) {
			addErrorMessage(getAppBean().getMessageResourceString("toCancelOrderUserMustLogin", null));
			return;
		}
		
		try {
			Order updatedOrder = orderFacade.cancelOrder(getRealItemId());
			setItem(updatedOrder);
			String text = getAppBean().getMessageResourceString("orderCanceled", null);
			addInfoMessage(text);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}
	
	//--------------GETTERS AND SETTERS-----------------------

	public void setOrderRowsDataTable(HtmlDataTable orderRowsDataTable) {
		this.orderRowsDataTable = orderRowsDataTable;
	}

	public HtmlDataTable getOrderRowsDataTable() {
		return orderRowsDataTable;
	}
}
