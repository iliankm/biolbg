package com.biol.biolbg.business.boundary.facade;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Local
public interface ItemFacade
{
	public Item create(Item item);

	public Item createLocal();

	public Item findById(Integer id);

	public void deleteById(Integer id);

	public Item update(Item item);

	public List<Item> findAll(int maxResultsLimit, int firstResult,
			SortCriteria sortCriteria);

	public Long getAllCount();

	public List<Item> findByCriteria(FindItemCriteria findItemCriteria,
			int maxResultsLimit, int firstResult, SortCriteria sortCriteria);

	public Long getByCriteriaCount(FindItemCriteria findItemCriteria);

	public List<Item> findPromotions(int maxResultsLimit, int firstResult);

	public Long getPromotionsCount();

	public List<Item> findBestSell(int maxResultsLimit, int firstResult);

	public Long getBestSellCount();

	public List<Item> findNew(int maxResultsLimit, int firstResult);

	public Long getNewCount();

	public void assignGroupAndProducerLocal(Item item, Integer groupId, Integer producerId);

}