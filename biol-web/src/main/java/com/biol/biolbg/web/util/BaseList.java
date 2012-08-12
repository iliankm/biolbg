package com.biol.biolbg.web.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.biol.biolbg.entity.BaseEntity;

public abstract class BaseList extends Base {

	public static final String SORT_ASC = "asc";
	public static final String SORT_DESC = "desc";
	public static final String SORT_BY_PARAM = "sortby";
	public static final String ID_PARAM = "id";
	
	private List<?> dataItems = null;
	private PagerController pagerController = new PagerController() {
		private static final long serialVersionUID = 1L;

		@Override
		public void pagerAction() {
			loadDataItems();
			
		}
		
	};
	private String sortByFieldName = "";
	private String sortType = SORT_ASC;
	
	//------ABSTRACT METHODS------------------------
	public abstract void init();
	public abstract void doLoadDataItems(Integer fromRow, Integer maxResults);
	public abstract Long getDataItemsTotalCount();
	public abstract void restoreCustomCredentials(Object customCredentials);
	public abstract Object storeCustomCredentials();
	public abstract void doDeleteData(List<Integer> itemsToDelete);
	
	//------METHODS FOR OVERRIDE--------------------
	public Boolean canLoadDataItems() {
		return true;
	}
	
	//------FINAL METHODS---------------------------
	@PostConstruct
	public final void postConstruct() {
		restoreCredentials();
		init();
		loadDataItems();
	}
	
	public final void loadDataItems() {
		if (! canLoadDataItems()) {
			pagerController.itemsTotalCount(Long.valueOf(0));			
			return;
		}
		Long totalCount = getDataItemsTotalCount();
		pagerController.itemsTotalCount(totalCount);
		Integer fromRow = pagerController.fromRow();
		Integer maxResults = pagerController.getShowRowsCount();
		doLoadDataItems(fromRow, maxResults);
		storeCredentials();
	}
	
	public final void storeCredentials() {
		if (getAppBean() != null) {
			getAppBean().storeCredentials(this.getClass().getName(), 
					sortByFieldName, sortType, 
					pagerController.getShowRowsCount(), 
					pagerController.getPageNumber(), 
					pagerController.getTotalPages(), 
					storeCustomCredentials());
		}
	}
	public final void restoreCredentials() {
		if (getAppBean() != null) {
			ViewCredentials vc = getAppBean().restoreCredentials(this.getClass().getName());
			if (vc != null) {
				sortByFieldName = vc.getSortByFieldName();
				sortType = vc.getSortType();
				pagerController.setPageNumber(vc.getPageNumber());
				pagerController.setShowRowsCount(vc.getShowRowsCount());
				if (vc.getCustomCredentials() != null) {
					restoreCustomCredentials(vc.getCustomCredentials());
				}
			}
		}	
	}
	
	public final String refreshData() {
		loadDataItems();
		return "";
	}
	
	public final String deleteData() {
		List<Integer> itemsToDelete = new ArrayList<Integer>();
		Iterator<?> iter = dataItems.iterator();
		while (iter.hasNext()) {
			Object item = iter.next();
			if (item instanceof BaseEntity) {
				if (((BaseEntity)item).getChecked()) {
					itemsToDelete.add(((BaseEntity)item).getId());
				}
			}
		}
		if (itemsToDelete.isEmpty()) {
			addErrorMessage(getAppBean().getMessageResourceString("selectItemsToDelete", null));
		} else {
			doDeleteData(itemsToDelete);
			loadDataItems();
		}
		return "";
	}
	
	public final String addData() {
		return "add";
	}
	
	public final void sortData(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String sortByFieldName = params.get(SORT_BY_PARAM);
		if (sortByFieldName != "") {
			if (this.sortByFieldName.equals(sortByFieldName)) {
				if (sortType.equals(SORT_ASC)) {
					sortType = SORT_DESC;
				} else {
					sortType = SORT_ASC;
				}
			} else {
				sortType = SORT_ASC;
			}
			this.sortByFieldName = sortByFieldName;
			loadDataItems();
		}
	}
	
	public final Integer getDataItemsCount() {
		Integer res = 0;
		if (dataItems != null) {
			res = Integer.valueOf(dataItems.size());
		}
		return res;
	}
	
	
	//---------GETTERS AND SETTERS------------------
	public final List<?> getDataItems() {
		return dataItems;
	}
	public final void setDataItems(List<?> dataItems) {
		this.dataItems = dataItems;
	}
	public final String getSortByFieldName() {
		return sortByFieldName;
	}
	public final void setSortByFieldName(String sortByFieldName) {
		this.sortByFieldName = sortByFieldName;
	}
	public final String getSortType() {
		return sortType;
	}
	public final void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public final String getFinalSortAsc() {
		return SORT_ASC;
	}
	public final String getFinalSortDesc() {
		return SORT_DESC;
	}
	public final String getFinalSortByParam() {
		return SORT_BY_PARAM;
	}
	public final String getFinalIdParam() {
		return ID_PARAM;
	}
	public final PagerController getPagerController() {
		return pagerController;
	}

}
