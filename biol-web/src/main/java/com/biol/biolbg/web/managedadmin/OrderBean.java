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

import com.biol.biolbg.web.managed.AppBean;
import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.MessageResourcesBean;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.Order;
import com.biol.biolbg.entity.OrderStatus;

@Named("OrderBean")
@RequestScoped
public class OrderBean extends BaseEditItem implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<SelectItem> orderStatusSelectItems = new ArrayList<SelectItem>();

	private HtmlDataTable orderRowsDataTable;

	@Inject
	private AppBean appBean;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	@EJB
	private OrderFacade orderFacade;

	@Override
	public Boolean getIsViewItemOnly() {
		return true;
	}

	@Override
	public Object createNewItem() {
		return orderFacade.createNewItem();
	}

	@Override
	public Boolean doSaveData() {
		return true;
	}

	@Override
	public Object findItemById(Integer id) {
		Order res = null;

		try {

			res = orderFacade.findItem(id);

			//mark Order as seen by admin
			if ((appBean.getLoggedUser().getAdminflag()==1)&&(res.getSeenbyadmin()!=1)) {

				Order res1 = orderFacade.markOrderAsSeenByAdmin(res.getId());

				if (res1 != null) {
					res = res1;
				}

			}
		}

		catch (Exception e) {
			addErrorMessage(e.getMessage());
		}

		return res;
	}

	@Override
	public void init() {

		// load orderStatusSelectItems
		List<OrderStatus> listOrderStatus = orderFacade.getAllOrderStatus();

		if (listOrderStatus != null) {

			Iterator<OrderStatus> iter = listOrderStatus.iterator();

			while (iter.hasNext()) {

				OrderStatus orderStatus = iter.next();
				String orderStatusName = null;

				if (appBean.getAppLocale().equals("en")) {

					orderStatusName = orderStatus.getNameen();

				} else {

					orderStatusName = orderStatus.getNamebg();
				}

				orderStatusSelectItems.add(new SelectItem(orderStatus, orderStatusName));
			}
		}
	}

	public void updateStatus(ActionEvent event) {

		Order order = (Order) getItem();

		try {

			order = orderFacade.updateStatus(this.getRealItemId(),order.getStatus());
			String text = messageResourcesBean.getMessage("dataSavedOK", null);
			addInfoMessage(text);

		}
		catch (Exception e) {

			addErrorMessage(e.getMessage());

		}
	}

	//--------------GETTERS AND SETTERS-----------------------
	public List<SelectItem> getOrderStatusSelectItems() {
		return orderStatusSelectItems;
	}

	public void setOrderRowsDataTable(HtmlDataTable orderRowsDataTable) {
		this.orderRowsDataTable = orderRowsDataTable;
	}

	public HtmlDataTable getOrderRowsDataTable() {
		return orderRowsDataTable;
	}
}
