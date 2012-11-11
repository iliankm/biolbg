package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.entity.Producer;

@Local
public interface ProducerFacade {
	public void addItem(Producer item);
	public void removeItem(Integer id);
	public void updateItem(Producer item);
	public Producer findItem(Integer id);
	public Producer createNewItem();
	public List<Producer> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType);
	public Long getAllItemsCount();

}
