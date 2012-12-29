package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.MessageResourcesBean;
import com.biol.biolbg.web.util.OrdersListCredentials;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.Order;

@Named("OrdersForUserListBean")
@RequestScoped
public class OrdersForUserListBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fromDate = new Date();

	private Date toDate = new Date();

	@Inject
	private AppBean appBean;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	@EJB
	private OrderFacade orderFacade;

	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		//nothing to do here
	}

	@Override
	public Boolean canLoadDataItems() {

		if (!getAppBean().getIsUserLoggedIn()) {

			return false;
		}

		if (toDate.before(fromDate)) {

			addErrorMessage(messageResourcesBean.getMessage("endDateBeforeStartDate", null));

			return false;
		}

		return true;
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {

		List<Order> dataItems =
			orderFacade.getOrdersForUser(
					fromRow, maxResults, getSortByFieldName(),
					getSortType(), fromDate, toDate,
					getAppBean().getLoggedUser().getId());

		setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount() {

		return orderFacade.getOrdersForUserCount(
				fromDate, toDate, getAppBean().getLoggedUser().getId());
	}

	@Override
	public void init() {

		if (getSortByFieldName() == "") {

			setSortByFieldName("o.id");
			setSortType(SORT_DESC);
		}
	}

	@Override
	public void restoreCustomCredentials(Object customCredentials) {

		if (customCredentials instanceof OrdersListCredentials) {

			OrdersListCredentials obj = (OrdersListCredentials) customCredentials;
			fromDate = obj.getFromDate();
			toDate = obj.getToDate();
		}
	}

	@Override
	public Object storeCustomCredentials() {

		OrdersListCredentials res = new OrdersListCredentials();
		res.setFromDate(fromDate);
		res.setToDate(toDate);

		return res;
	}

	//------GETTERS AND SETTERS---------------------------
	public void setFromDate(Date fromDate) {

		this.fromDate = fromDate;
	}

	public Date getFromDate()
	{
		return fromDate;
	}

	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}

	public Date getToDate()
	{
		return toDate;
	}

	public void setAppBean(AppBean appBean)
	{
		this.appBean = appBean;
	}

	public AppBean getAppBean()
	{
		return appBean;
	}

}
