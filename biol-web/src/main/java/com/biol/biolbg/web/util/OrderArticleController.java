package com.biol.biolbg.web.util;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.OrderFacade;
import com.biol.biolbg.business.entity.OrderRow;
import com.biol.biolbg.web.managed.AppBean;
import com.biol.biolbg.web.managed.CurrentOrderBean;

@Named("OrderArticleController")
@RequestScoped
public class OrderArticleController extends Base implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private CurrentOrderBean currentOrderBean;

	@Inject
	private AppBean appBean;

	@EJB
	private OrderFacade orderFacade;

	//increments the amount of ordered article
	//if not found - create it
	public String incArticle()
	{
		Integer attribArticleId = getArticleIdParameter();

		orderFacade.incrementArticleAmountLocal(currentOrderBean.getOrder(), attribArticleId);

		return "";
	}

	//decrement the amount of ordered article
	//if amount is 1 then delete it form the list
	public String decArticle()
	{
		Integer attribArticleId = getArticleIdParameter();

		orderFacade.decrementArticleAmountLocal(currentOrderBean.getOrder(), attribArticleId);

		return "";
	}

	public Map<String, String> getOrderedAmount()
	{
		Map<String, String> resMap = new AbstractMap<String, String>()
		{
            @Override
            public String get(Object key)
            {
                return getOrderedAmountAsString(Integer.valueOf(key.toString()));
            }

            @Override
            public Set<java.util.Map.Entry<String, String>> entrySet()
            {
                throw new UnsupportedOperationException();
            }
        };

        return resMap;
	}

	private String getOrderedAmountAsString(Integer articleId)
	{
		Iterator<OrderRow> iter = currentOrderBean.getOrder().getRows().iterator();

		String res = "";

		while (iter.hasNext())
		{
			OrderRow orderRow = iter.next();

			if (orderRow.getItem().getId() == articleId.intValue())
			{
				res = orderRow.getAmount().toString();

				if (appBean.getAppLocale().equals("en"))
				{
					res = res.concat(" ").concat(orderRow.getItem().getPackingen());
				}
				else
				{
					if (appBean.getAppLocale().equals("bg"))
					{
						res = res.concat(" ").concat(orderRow.getItem().getPackingbg());
					}
				}
				break;
			}
		}

		return res;
	}

	private Integer getArticleIdParameter()
	{
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		Integer attribArticleId;

		try
		{
			attribArticleId = Integer.valueOf(params.get("articleId"));
		}
		catch (Exception e)
		{
			attribArticleId = -1;
		}

		return attribArticleId;
	}

}
