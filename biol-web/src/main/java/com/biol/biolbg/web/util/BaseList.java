package com.biol.biolbg.web.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import com.biol.biolbg.business.entity.BaseEntity;


public abstract class BaseList extends Base
{
	public static final String SORT_ASC = "asc";

	public static final String SORT_DESC = "desc";

	public static final String SORT_BY_PARAM = "sortby";

	public static final String ID_PARAM = "id";

	private List<?> dataItems = null;

	private PagerController pagerController = new PagerController()
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void pagerAction()
		{
			loadDataItems();
		}
	};

	private String sortByFieldName = "";

	private String sortType = SORT_ASC;

	@Inject
	private CredentialsBean credentialsBean;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	public abstract void init();

	public abstract void doLoadDataItems(Integer fromRow, Integer maxResults);

	public abstract Long getDataItemsTotalCount();

	public abstract void restoreCustomCredentials(Object customCredentials);

	public abstract Object storeCustomCredentials();

	public abstract void doDeleteData(List<Integer> itemsToDelete);

	public Boolean canLoadDataItems()
	{
		return true;
	}

	@PostConstruct
	public void postConstruct()
	{
		restoreCredentials();
		init();
		loadDataItems();
	}

	public void loadDataItems()
	{
		if (! canLoadDataItems())
		{
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

	public void storeCredentials()
	{
		credentialsBean.storeCredentials(this.getClass().getName(),
					sortByFieldName, sortType,
					pagerController.getShowRowsCount(),
					pagerController.getPageNumber(),
					pagerController.getTotalPages(),
					storeCustomCredentials());
	}

	public void restoreCredentials()
	{
		ViewCredentials vc = credentialsBean.restoreCredentials(this.getClass().getName());

		if (vc != null)
		{
			sortByFieldName = vc.getSortByFieldName();
			sortType = vc.getSortType();
			pagerController.setPageNumber(vc.getPageNumber());
			pagerController.setShowRowsCount(vc.getShowRowsCount());

			if (vc.getCustomCredentials() != null)
			{
				restoreCustomCredentials(vc.getCustomCredentials());
			}
		}
	}

	public String refreshData()
	{
		loadDataItems();
		return "";
	}

	public String deleteData()
	{
		List<Integer> itemsToDelete = new ArrayList<Integer>();

		Iterator<?> iter = dataItems.iterator();

		while (iter.hasNext())
		{
			Object item = iter.next();
			if (item instanceof BaseEntity)
			{
				if (((BaseEntity)item).getChecked())
				{
					itemsToDelete.add(((BaseEntity)item).getId());
				}
			}
		}

		if (itemsToDelete.isEmpty())
		{
			addErrorMessage(messageResourcesBean.getMessage("selectItemsToDelete", null));
		}
		else
		{
			doDeleteData(itemsToDelete);
			loadDataItems();
		}

		return "";
	}

	public String addData()
	{
		return "add";
	}

	public void sortData(ActionEvent event)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String sortByFieldName = params.get(SORT_BY_PARAM);
		if (sortByFieldName != "")
		{
			if (this.sortByFieldName.equals(sortByFieldName))
			{
				if (sortType.equals(SORT_ASC))
				{
					sortType = SORT_DESC;
				}
				else
				{
					sortType = SORT_ASC;
				}
			}
			else
			{
				sortType = SORT_ASC;
			}
			this.sortByFieldName = sortByFieldName;
			loadDataItems();
		}
	}

	public Integer getDataItemsCount()
	{
		Integer res = 0;
		if (dataItems != null)
		{
			res = Integer.valueOf(dataItems.size());
		}
		return res;
	}


	public List<?> getDataItems()
	{
		return dataItems;
	}

	public void setDataItems(List<?> dataItems)
	{
		this.dataItems = dataItems;
	}

	public String getSortByFieldName()
	{
		return sortByFieldName;
	}

	public void setSortByFieldName(String sortByFieldName)
	{
		this.sortByFieldName = sortByFieldName;
	}

	public String getSortType()
	{
		return sortType;
	}

	public void setSortType(String sortType)
	{
		this.sortType = sortType;
	}

	public String getFinalSortAsc()
	{
		return SORT_ASC;
	}

	public String getFinalSortDesc()
	{
		return SORT_DESC;
	}

	public String getFinalSortByParam()
	{
		return SORT_BY_PARAM;
	}

	public String getFinalIdParam()
	{
		return ID_PARAM;
	}

	public PagerController getPagerController()
	{
		return pagerController;
	}
}
