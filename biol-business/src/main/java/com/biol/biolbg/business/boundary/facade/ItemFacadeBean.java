package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.biol.biolbg.business.control.dao.ItemDaoBean;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.entity.ItemEntity;
import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class ItemFacadeBean implements ItemFacade
{
	@EJB
	private ItemDaoBean itemDaoBean;


	@Override
	public ItemEntity create(final Item item)
	{
		return itemDaoBean.create((ItemEntity)item);
	}

	@Override
	public ItemEntity createLocal()
	{
		ItemEntity item = new ItemEntity();
		item.setNewitem(1);

		return item;
	}

	@Override
	public ItemEntity findById(final Integer id)
	{
		return itemDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		itemDaoBean.delete(id);
	}

	@Override
	public ItemEntity update(final Item item)
	{
		return itemDaoBean.update((ItemEntity)item);
	}

	@Override
	public List<ItemEntity> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return itemDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria);
	}

	@Override
	public Long getAllCount()
	{
		return itemDaoBean.getAllCount();
	}

	@Override
	public List<ItemEntity> findByCriteria(final FindItemCriteria findItemCriteria, final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return itemDaoBean.findByCriteria(findItemCriteria, maxResultsLimit, firstResult, sortCriteria);
	}

	@Override
	public Long getByCriteriaCount(final FindItemCriteria findItemCriteria)
	{
		return itemDaoBean.getByCriteriaCount(findItemCriteria);
	}

	@Override
	public List<ItemEntity> findPromotions(final int maxResultsLimit, final int firstResult)
	{
		return itemDaoBean.findPromotions(maxResultsLimit, firstResult);
	}

	@Override
	public Long getPromotionsCount()
	{
		return itemDaoBean.getPromotionsCount();
	}

	@Override
	public List<ItemEntity> findBestSell(final int maxResultsLimit, final int firstResult)
	{
		return itemDaoBean.findBestSell(maxResultsLimit, firstResult);
	}

	@Override
	public Long getBestSellCount()
	{
		return itemDaoBean.getBestSellCount();
	}

	@Override
	public List<ItemEntity> findNew(final int maxResultsLimit, final int firstResult)
	{
		return itemDaoBean.findNew(maxResultsLimit, firstResult);
	}

	@Override
	public Long getNewCount()
	{
		return itemDaoBean.getNewCount();
	}
}
