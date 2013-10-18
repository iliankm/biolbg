package com.biol.biolbg.business.control.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.ItemDaoBean;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.OrderRow;
import com.biol.biolbg.business.entity.OrderRowEntity;

@Stateless
@LocalBean
public class OrderServiceBean
{
	@EJB
	private ItemDaoBean itemDaoBean;

	public OrderRow getOrderRowForArticleId(Order order, Integer articleId)
	{
		OrderRow orderRowForTheArticle = null;

		for (OrderRow row : order.getRows())
		{
			if (row.getItem() != null && Integer.valueOf(row.getItem().getId()).equals(articleId))
			{
				orderRowForTheArticle = row;

				break;
			}
		}

		return orderRowForTheArticle;
	}

	public OrderRow addOrderRowLocal(Order order, Integer articleId)
	{
		Item item = itemDaoBean.findById(articleId);

		OrderRow orderRow = new OrderRowEntity();

		order.addRow(orderRow);

		orderRow.setItem(item);
		orderRow.setAmount(Double.valueOf(0));
		orderRow.setPrice(item.getPriceforpacking());

		return orderRow;
	}

	public void removeOrderRowLocal(Order order, OrderRow orderRow)
	{
		order.removeRow(orderRow);
	}

}
