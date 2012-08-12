package com.biol.biolbg.web.managed;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.biol.biolbg.web.util.Base;
import com.biol.biolbg.web.util.EJBLocator;
import com.biol.biolbg.web.util.FileUtil;

import com.biol.biolbg.ejb.session.OrderFacade;
import com.biol.biolbg.entity.Order;
import com.biol.biolbg.entity.OrderRow;

@ManagedBean(name = "CurrentOrderBean")
@SessionScoped
public class CurrentOrderBean extends Base implements Serializable {

	private static final long serialVersionUID = 1L;
	private Order order = new Order();
	private java.util.Date fordate;
	private java.util.Date fortime;
	private HtmlDataTable orderRowsDataTable;
	private Map<Integer,String> orderedAmount = new HashMap<Integer,String>();
	private String regcode;
	private String imagesPath;
	private OrderFacade orderFacade = EJBLocator.getInstance().lookup(OrderFacade.class);
	
	@PostConstruct
	public final void postConstruct() {
		order.setRows(new ArrayList<OrderRow>());
		//get images path
		FacesContext facesContext = FacesContext.getCurrentInstance();
		imagesPath = facesContext.getExternalContext().getInitParameter("imagesPath");
		
	}
	
	public void saveData(ActionEvent event) {
		//check reg code
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		String randomRegCode = (String)session.getAttribute("RandomRegCode");
		Boolean regCodeMatches = true;
		if ((randomRegCode == null)||(regcode == null)) {
			regCodeMatches = false;
		} else {
			if (!randomRegCode.equals(regcode)) {
				regCodeMatches = false;
			}
		}	
		if (!regCodeMatches) {
			String errorText = getAppBean().getMessageResourceString("regCodeNotMatches", null);
			FacesContext.getCurrentInstance().addMessage("baseForm:regcode", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, errorText, null));	
			return;
		}
		
		//check if user logged in
		if (!getAppBean().getIsUserLoggedIn()) {
			addErrorMessage(getAppBean().getMessageResourceString("toPostOrderUserMustLogin", null));
			return;
		}
		//check if no rows
		Boolean haveRows = false;
		if (order.getRows() != null) {
			haveRows = !order.getRows().isEmpty(); 
		}
		if (!haveRows) {	
			addErrorMessage(getAppBean().getMessageResourceString("noorderrows", null));
			return;
		}	
		//set user
		order.setUser(getAppBean().getLoggedUser());
		//set for date
		if (fordate != null) {
			order.setFordate(new java.sql.Date(fordate.getTime()));
		}
		//set for time
		if (fortime != null) {
			order.setFortime(new java.sql.Time(fortime.getTime()));
		}
		//save the order
		try {
			if (order.getId() > 0) {
				orderFacade.updateItem(order);
			} else {
				orderFacade.addItem(order);
			}
			String text = getAppBean().getMessageResourceString("dataSavedOK", null);
			addInfoMessage(text);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			return;
		}
		//
		order = new Order();
		order.setRows(new ArrayList<OrderRow>());
		orderedAmount.clear();
	}
	
	public void deleteRow(ActionEvent event) {
		Iterator<OrderRow> iter = order.getRows().iterator();
		
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String sArticleId = params.get("articleId");
		
		while (iter.hasNext()) {
			OrderRow _orderrow = iter.next();
			if (String.valueOf(_orderrow.getItem().getId()).equals(sArticleId)) {
				iter.remove();
				break;
			}
		}
	}
	
	public Boolean getHaveOrderedArticles() {
		Boolean res = false;
		if (order.getRows() != null) {
			res = !order.getRows().isEmpty();
		}
		return res;
	}
	//get the last posted deliv address
	private String getLastDeliveryAddressForUser() {
		Integer loggedUserId = getAppBean().getLoggedUser().getId();
		if (loggedUserId > 0) {
			return orderFacade.getLastDeliveryAddressForUser(loggedUserId);
		} else {
			return "";
		}
	}
	
	//*****GETTERS AND SETTERS******
	public void setOrder(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		//set the last posted deliv address
		String delivAddress = order.getDeliveryAddress();
		if ((delivAddress == null)||("".equals(delivAddress))) {
			order.setDeliveryAddress(getLastDeliveryAddressForUser());
		}	
		
		return order;
	}

	public void setOrderRowsDataTable(HtmlDataTable orderRowsDataTable) {
		this.orderRowsDataTable = orderRowsDataTable;
	}

	public HtmlDataTable getOrderRowsDataTable() {
		return orderRowsDataTable;
	}
	
	public Map<Integer, String> getOrderedAmount() {
		return orderedAmount;
	}

	public void setFordate(Date fordate) {
		this.fordate = fordate;
	}

	public Date getFordate() {
		return fordate;
	}

	public void setFortime(Date fortime) {
		this.fortime = fortime;
	}

	public Date getFortime() {
		return fortime;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	public String getRegcode() {
		return regcode;
	}

	public String getRandomStringParam() {
		String res = "";
		Random random = new Random();
		Integer num1 = random.nextInt(999999);
		res = num1.toString();
		Integer num2 = random.nextInt(99999999);
		res = res.concat(num2.toString());
		return res;
	}
	
	public Map<Integer, String> getImageName() {
		Map<Integer, String> res = new AbstractMap<Integer, String>() {

			@Override
			public String get(Object key) {
				return FileUtil.imageFileName(imagesPath, key.toString());
			}
			
			@Override
			public Set<java.util.Map.Entry<Integer, String>> entrySet() {
				throw new UnsupportedOperationException();
			}
		};
			
		return res;
	}
	

}
