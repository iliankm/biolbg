package com.biol.biolbg.business.control.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.ItemDaoBean;
import com.biol.biolbg.business.entity.ItemEntity;
import com.biol.biolbg.business.entity.OrderEntity;
import com.biol.biolbg.business.entity.OrderRowEntity;

@Stateless
@LocalBean
public class OrderServiceBean
{
	@EJB
	private ItemDaoBean itemDaoBean;

	public OrderRowEntity getOrderRowForArticleId(OrderEntity orderEntity, Integer articleId)
	{
		OrderRowEntity orderRowEntityForTheArticle = null;

		for (OrderRowEntity row : orderEntity.getRowsEntities())
		{
			if (row.getItem() != null && Integer.valueOf(row.getItem().getId()).equals(articleId))
			{
				orderRowEntityForTheArticle = row;

				break;
			}
		}

		return orderRowEntityForTheArticle;
	}

	public OrderRowEntity addOrderRowLocal(OrderEntity orderEntity, Integer articleId)
	{
		ItemEntity itemEntity = itemDaoBean.findById(articleId);

		OrderRowEntity orderRowEntity = new OrderRowEntity();

		orderEntity.getRowsEntities().add(orderRowEntity);

		orderRowEntity.setOrder(orderEntity);
		orderRowEntity.setItem(itemEntity);
		orderRowEntity.setAmount(Double.valueOf(0));
		orderRowEntity.setPrice(itemEntity.getPriceforpacking());

		return orderRowEntity;
	}

	public void removeOrderRowLocal(OrderEntity orderEntity, OrderRowEntity orderRowEntity)
	{
		orderEntity.getRowsEntities().remove(orderRowEntity);
	}

}
