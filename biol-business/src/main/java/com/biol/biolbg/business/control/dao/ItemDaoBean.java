package com.biol.biolbg.business.control.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.biol.biolbg.business.control.query.builder.ItemQueryBuilder;
import com.biol.biolbg.business.entity.ItemEntity;
import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
@LocalBean
public class ItemDaoBean extends AbstractDaoBean<ItemEntity>
{
	@Inject
	private ItemQueryBuilder itemQueryBuilder;

	@Override
	protected Class<ItemEntity> getClazz()
	{
		return ItemEntity.class;
	}

	public List<ItemEntity> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		String queryText = itemQueryBuilder.select().sortCriteria(sortCriteria).build();

		Query query = em.createQuery(queryText);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	public Long getAllCount()
	{
		String queryText = itemQueryBuilder.selectForCount().build();

		Query query = em.createQuery(queryText);

		List<Long> results = query.getResultList();

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

	public List<ItemEntity> findByCriteria(final FindItemCriteria findItemCriteria, final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		String queryText = itemQueryBuilder.select().findItemCriteria(findItemCriteria).sortCriteria(sortCriteria).build();

		Query query = em.createQuery(queryText);

		setResultsLimitToQuery(query, maxResultsLimit, firstResult);

		return getResultList(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	public Long getByCriteriaCount(final FindItemCriteria findItemCriteria)
	{
		String queryText = itemQueryBuilder.selectForCount().findItemCriteria(findItemCriteria).build();

		Query query = em.createQuery(queryText);

		List<Long> results = query.getResultList();

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

	public List<ItemEntity> findPromotions(final int maxResultsLimit, final int firstResult)
	{
		return findObjectsByNamedQuery(ItemEntity.QueryNames.FIND_PROMOTION_ITEMS, maxResultsLimit, firstResult);
	}

	public Long getPromotionsCount()
	{
		List<Long> results = findObjectsByNamedQuery(ItemEntity.QueryNames.GET_PROMOTION_ITEMS_COUNT);

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

	public List<ItemEntity> findBestSell(final int maxResultsLimit, final int firstResult)
	{
		return findObjectsByNamedQuery(ItemEntity.QueryNames.FIND_BEST_SELL, maxResultsLimit, firstResult);
	}

	public Long getBestSellCount()
	{
		List<Long> results = findObjectsByNamedQuery(ItemEntity.QueryNames.GET_BEST_SELL_COUNT);

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

	public List<ItemEntity> findNew(final int maxResultsLimit, final int firstResult)
	{
		return findObjectsByNamedQuery(ItemEntity.QueryNames.FIND_NEW, maxResultsLimit, firstResult);
	}

	public Long getNewCount()
	{
		List<Long> results = findObjectsByNamedQuery(ItemEntity.QueryNames.GET_NEW_COUNT);

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return Long.valueOf(0);
	}

}
