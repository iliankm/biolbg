package com.biol.biolbg.web.util;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.biol.biolbg.web.managed.CurrentOrderBean;

import com.biol.biolbg.ejb.session.ItemFacade;
import com.biol.biolbg.entity.Item;
import com.biol.biolbg.entity.OrderRow;

@ManagedBean(name = "OrderArticleController")
@RequestScoped
public class OrderArticleController extends Base implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{CurrentOrderBean}")
	private CurrentOrderBean currentOrderBean;
	private ItemFacade itemFacade = EJBLocator.getInstance().lookup(ItemFacade.class);
	
	//increments the amount of ordered article
	//if not found - create it
	public void incArticle(ActionEvent event) {
		Integer attribArticleId = getArticleId();

		OrderRow orderrow = null;
		Iterator<OrderRow> iter = getCurrentOrderBean().getOrder().getRows().iterator();
		while (iter.hasNext()) {
			OrderRow _orderrow = iter.next();
			if ((orderrow == null)&&(_orderrow.getItem().getId() == attribArticleId.intValue())) {
				orderrow = _orderrow;
			}
		}
		if (orderrow == null) {
			Item item = getItemById(attribArticleId);
			if (item == null) {
				return;
			}
			OrderRow newOrderRow = new OrderRow();
			newOrderRow.setOrder(getCurrentOrderBean().getOrder());
			newOrderRow.setAmount(1.00);
			newOrderRow.setItem(item);
			newOrderRow.setPrice(item.getPriceforpacking());
			getCurrentOrderBean().getOrder().getRows().add(newOrderRow);
		} else {
			double am = orderrow.getAmount().doubleValue() + 1;
			orderrow.setAmount(am);
		}
	}
	//decrement the amount of ordered article
	//if amount is 1 then delete it form the list
	public void decArticle(ActionEvent event) {
		Integer attribArticleId = getArticleId();
		Iterator<OrderRow> iter = getCurrentOrderBean().getOrder().getRows().iterator();
		while (iter.hasNext()) {
			OrderRow _orderrow = iter.next();
			if (_orderrow.getItem().getId() == attribArticleId.intValue()) {
				double am = _orderrow.getAmount().doubleValue() - 1;
				if (am <= 0) {
					iter.remove();
				} else {
					_orderrow.setAmount(am);
				}
				break;
			}
		}
	}
	
	public Map<String, String> getOrderedAmount() {
		Map<String, String> resMap = new AbstractMap<String, String>() {
            @Override
            public String get(Object key) {
                return getOrderedAmount(key.toString());
            }
            @Override
            public Set<java.util.Map.Entry<String, String>> entrySet() {
                throw new UnsupportedOperationException();
            }
        };
        return resMap;
	}     

	private String getOrderedAmount(String articleId) {
		if (getCurrentOrderBean() == null) {
			return "null";
		}
		if (getCurrentOrderBean().getOrder() == null) {
			return "null1";
		}
		if (getCurrentOrderBean().getOrder().getRows() == null) {
			return "null2";
		}
		
		Integer iArticleId = getArticleId();
		if (iArticleId <= 0) {
			try {
				iArticleId = Integer.parseInt(articleId);
			} catch (Exception e) {
				iArticleId = -1;
			}
		}	
			
		Iterator<OrderRow> iter = getCurrentOrderBean().getOrder().getRows().iterator();
		String res = "";
		while (iter.hasNext()) {
			OrderRow orderRow = iter.next();
			if (orderRow.getItem().getId() == iArticleId.intValue()) {
				res = orderRow.getAmount().toString();
				res = res.concat(" ");
				if (this.getAppBean().getAppLocale().equals("en")) {
					res = res.concat(orderRow.getItem().getPackingen());
				} else {
					if (this.getAppBean().getAppLocale().equals("bg")) {
						res = res.concat(orderRow.getItem().getPackingbg());
					}
				}
				break;
			}
		}
		return res;
	}
	
	private Integer getArticleId() {
		Integer attribArticleId = -1;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String sId = params.get("articleId");
		
		try {
			attribArticleId = Integer.parseInt(sId);
		} catch (Exception e) {
			attribArticleId = -1;
		}
		return attribArticleId;
	}

	private Item getItemById(Integer itemId) {
		return itemFacade.findItem(itemId);
	}
	public void setCurrentOrderBean(CurrentOrderBean currentOrderBean) {
		this.currentOrderBean = currentOrderBean;
	}
	public CurrentOrderBean getCurrentOrderBean() {
		return currentOrderBean;
	}
	
}
