package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Remote;

import com.biol.biolbg.entity.Item;


@Remote
public interface ItemFacade {
	public void addItem(Item item);
	public void removeItem(Integer id);
	public void updateItem(Item item);
	public Item findItem(Integer id);
	public Item createNewItem();
	public List<Item> getAllItems(Integer groupId, Integer producerId, Integer fromRow, Integer maxResults, String sortBy, String sortType);
	public Long getAllItemsCount(Integer groupId, Integer producerId);
	public List<Item> getItemsByName(String name, Integer fromRow, Integer maxResults, String sortBy, String sortType);
	public Long getItemsByNameCount(String name);
	public List<Item> getPromotionItems(Integer fromRow, Integer maxResults);
	public Long getPromotionItemsCount();
	public List<Item> getCheapestItems(Integer fromRow, Integer maxResults);
	public List<Item> getNewItems(Integer fromRow, Integer maxResults);
	public Long getNewItemsCount();
	public List<Item> getBestSellItems(Integer fromRow, Integer maxResults);
	public Long getBestSellItemsCount();
}
