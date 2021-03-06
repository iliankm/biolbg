package com.biol.biolbg.business.boundary.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.biol.biolbg.business.control.dao.GroupDaoBean;
import com.biol.biolbg.business.control.dao.ItemDaoBean;
import com.biol.biolbg.business.control.dao.ProducerDaoBean;
import com.biol.biolbg.business.entity.Group;
import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.business.entity.ItemEntity;
import com.biol.biolbg.business.entity.Producer;
import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;

@Stateless
public class ItemFacadeBean implements ItemFacade
{
	@EJB
	private ItemDaoBean itemDaoBean;

	@EJB
	private GroupDaoBean groupDaoBean;

	@EJB
	private ProducerDaoBean producerDaoBean;


	@Override
	public Item create(final Item item)
	{
		return itemDaoBean.create(item);
	}

	@Override
	public Item createLocal()
	{
		ItemEntity item = new ItemEntity();
		item.setNewitem(1);

		return item;
	}

	@Override
	public Item findById(final Integer id)
	{
		return itemDaoBean.findById(id);
	}

	@Override
	public void deleteById(final Integer id)
	{
		itemDaoBean.delete(id);
	}

	@Override
	public Item update(final Item item)
	{
		return itemDaoBean.update(item);
	}

	@Override
	public List<Item> findAll(final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return Collections.<Item>unmodifiableList(itemDaoBean.findAll(maxResultsLimit, firstResult, sortCriteria));
	}

	@Override
	public Long getAllCount()
	{
		return itemDaoBean.getAllCount();
	}

	@Override
	public List<Item> findByCriteria(final FindItemCriteria findItemCriteria, final int maxResultsLimit, final int firstResult, final SortCriteria sortCriteria)
	{
		return Collections.<Item>unmodifiableList(itemDaoBean.findByCriteria(findItemCriteria, maxResultsLimit, firstResult, sortCriteria));
	}

	@Override
	public Long getByCriteriaCount(final FindItemCriteria findItemCriteria)
	{
		return itemDaoBean.getByCriteriaCount(findItemCriteria);
	}

	@Override
	public List<Item> findPromotions(final int maxResultsLimit, final int firstResult)
	{
		return Collections.<Item>unmodifiableList(itemDaoBean.findPromotions(maxResultsLimit, firstResult));
	}

	@Override
	public Long getPromotionsCount()
	{
		return itemDaoBean.getPromotionsCount();
	}

	@Override
	public List<Item> findBestSell(final int maxResultsLimit, final int firstResult)
	{
		return Collections.<Item>unmodifiableList(itemDaoBean.findBestSell(maxResultsLimit, firstResult));
	}

	@Override
	public Long getBestSellCount()
	{
		return itemDaoBean.getBestSellCount();
	}

	@Override
	public List<Item> findNew(final int maxResultsLimit, final int firstResult)
	{
		return Collections.<Item>unmodifiableList(itemDaoBean.findNew(maxResultsLimit, firstResult));
	}

	@Override
	public Long getNewCount()
	{
		return itemDaoBean.getNewCount();
	}

	@Override
	public void assignGroupAndProducerLocal(Item item, Integer groupId,
			Integer producerId)
	{
		Group group = groupId != null ? groupDaoBean.findById(groupId) : null;

		Producer producer = producerId != null ? producerDaoBean.findById(producerId) : null;

		item.setGroup(group);

		item.setProducer(producer);
	}
}
