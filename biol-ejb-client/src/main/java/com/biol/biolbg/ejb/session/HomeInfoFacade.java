package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Remote;

import com.biol.biolbg.entity.HomeInfo;

@Remote
public interface HomeInfoFacade {
	public void addItem(HomeInfo item);
	public void removeItem(Integer id);
	public void updateItem(HomeInfo item);
	public HomeInfo findItem(Integer id);
	public HomeInfo createNewItem();
	public List<HomeInfo> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType);
	public Long getAllItemsCount();
	

}
