package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseList;
import com.biol.biolbg.web.util.EJBLocator;
import com.biol.biolbg.web.util.OrdersListCredentials;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.Order;

@ManagedBean(name = "OrdersListBean")
@RequestScoped
public class OrdersListBean extends BaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date fromDate = new Date();
	private Date toDate = new Date();
	private String username = null;
	private String organisation = null;
	private OrderFacade orderFacade = EJBLocator.getInstance().lookup(OrderFacade.class);

	@Override
	public void doDeleteData(List<Integer> itemsToDelete) {
		Iterator<Integer> iter = itemsToDelete.iterator();
		while (iter.hasNext()) {
			try {
				orderFacade.removeItem(iter.next());
			} catch (Exception e) {
				addErrorMessage(e.getMessage());
			}
		}
	}

	@Override
	public Boolean canLoadDataItems() {
		if (toDate.before(fromDate)) {
			addErrorMessage(getAppBean().getMessageResourceString("endDateBeforeStartDate", null));
			return false;
		}
		return true;
	}
	
	@Override
	public void doLoadDataItems(Integer fromRow, Integer maxResults) {
		List<Order> dataItems = 
			orderFacade.getAllItems(
					fromRow, maxResults, 
					getSortByFieldName(), getSortType(), 
					fromDate, toDate, username, organisation);
		setDataItems(dataItems);
	}

	@Override
	public Long getDataItemsTotalCount() {
		return orderFacade.getAllItemsCount(fromDate, toDate, username, organisation);
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
			username = obj.getUsername();
			organisation = obj.getOrganisation();
		}
	}

	@Override
	public Object storeCustomCredentials() {
		OrdersListCredentials res = new OrdersListCredentials();
		res.setFromDate(fromDate);
		res.setToDate(toDate);
		res.setUsername(username);
		res.setOrganisation(organisation);
		return res;
	}
	
	//------GETTERS AND SETTERS---------------------------
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getToDate() {
		return toDate;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
	public Map<Integer,String> getTextStyleClass() {
		return new AbstractMap<Integer,String>() {
			@Override
			public String get(Object key) {
				Integer iKey = Integer.parseInt(key.toString());
				if (iKey == 1) {
					return "seenOrder";
				} else {
					return "unseenOrder";
				}
			}
			@Override
			public Set<java.util.Map.Entry<Integer, String>> entrySet() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
