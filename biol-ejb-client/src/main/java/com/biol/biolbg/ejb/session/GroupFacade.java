package com.biol.biolbg.ejb.session;

import java.util.List;

import javax.ejb.Local;

import com.biol.biolbg.entity.Group;

@Local
public interface GroupFacade
{
	public void addItem(Group item);

	public void removeItem(Integer id);

	public void updateItem(Group item);

	public Group findItem(Integer id);

	public Group createNewItem();

	public List<Group> getAllItems(Integer fromRow, Integer maxResults, String sortBy, String sortType);

	public Long getAllItemsCount();

	public Group getRandomGroup();
}
