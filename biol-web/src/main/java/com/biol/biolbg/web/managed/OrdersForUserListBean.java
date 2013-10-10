package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.OrderFacade;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.util.FindOrderCriteria;
import com.biol.biolbg.business.util.SortCriteria;
import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.MessageResourcesBean;
import com.biol.biolbg.web.util.OrdersListCredentials;


@Named("OrdersForUserListBean")
@RequestScoped
public class OrdersForUserListBean extends BaseList implements Serializable
{
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
	public void doDeleteData(List<Integer> itemsToDelete)
	{
	}

	@Override
	public Boolean canLoadDataItems()
	{
		if (!getAppBean().getIsUserLoggedIn())
		{
			return false;
		}

		if (toDate.before(fromDate))
		{
			addErrorMessage(messageResourcesBean.getMessage("endDateBeforeStartDate", null));
			return false;
		}

		return true;
	}

	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults)
	{
		FindOrderCriteria findOrderCriteria = new FindOrderCriteria(fromDate, toDate, Integer.valueOf(getAppBean().getLoggedUser().getId()), null, null);

		SortCriteria sortCriteria = new SortCriteria(getSortByFieldName(), getSortType());

		List<Order> dataItems =
			orderFacade.findByCriteria(findOrderCriteria, maxResults, fromRow, sortCriteria);

		setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount()
	{
		FindOrderCriteria findOrderCriteria = new FindOrderCriteria(fromDate, toDate, Integer.valueOf(getAppBean().getLoggedUser().getId()), null, null);

		return orderFacade.getByCriteriaCount(findOrderCriteria);

	}

	@Override
	public void init()
	{
		if (getSortByFieldName() == "")
		{
			setSortByFieldName("o.id");
			setSortType(SORT_DESC);
		}
	}

	@Override
	public void restoreCustomCredentials(Object customCredentials)
	{
		if (customCredentials instanceof OrdersListCredentials)
		{
			OrdersListCredentials obj = (OrdersListCredentials) customCredentials;
			fromDate = obj.getFromDate();
			toDate = obj.getToDate();
		}
	}

	@Override
	public Object storeCustomCredentials()
	{
		OrdersListCredentials res = new OrdersListCredentials();
		res.setFromDate(fromDate);
		res.setToDate(toDate);

		return res;
	}

	public void setFromDate(Date fromDate)
	{
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
