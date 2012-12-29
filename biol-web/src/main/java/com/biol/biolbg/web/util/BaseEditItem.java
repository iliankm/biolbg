package com.biol.biolbg.web.util;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.inject.Inject;

public abstract class BaseEditItem extends Base
{
	public static final String ID_PARAM = "id";

	private Integer itemId = -1;  //binded to view param only

	private UIInput itemIdComp;

	private Object item = null;

	@Inject
	private MessageResourcesBean messageResourcesBean;

	public abstract void init();

	public abstract Boolean doSaveData();

	public abstract Object createNewItem();

	public abstract Object findItemById(Integer id);

	@PostConstruct
	public void postConstruct()
	{
		init();
		item = createNewItem();
	}

	public void preRenderView(javax.faces.event.ComponentSystemEvent event)
	{
		if (itemId > 0)
		{
			item = findItemById(itemId);
		}
		else
		{
			if (getIsViewItemOnly())
			{
				item = findItemById(getRealItemId());
			}
		}
		if (item == null)
		{
			item = createNewItem();
			addErrorMessage(messageResourcesBean.getMessage("invalidParamOrRecordDeleted", null));
		}
	}

	public Boolean getIsViewItemOnly()
	{
		return false;
	}

	public String saveData()
	{
		if (doSaveData())
		{
			String text = messageResourcesBean.getMessage("dataSavedOK", null);
			addInfoMessage(text);
			item = createNewItem();
			return "OK";
		}
		else
		{
			return "Error";
		}
	}

	public String cancelData()
	{
		return "cancel";
	}

	public Integer getRealItemId()
	{
		Integer iItemId = -1;
		if (getItemIdComp() != null)
		{
			Object submittedValue = getItemIdComp().getSubmittedValue();
			if (submittedValue == null)
			{
				submittedValue = getItemIdComp().getValue();
			}
			if (submittedValue != null)
			{
				String sItemId = submittedValue.toString();
				try
				{
					iItemId = Integer.valueOf(sItemId);
				}
				catch (Exception e)
				{
					iItemId = -1;
				}
			}
		}

		return iItemId;
	}


	public Object getItem()
	{
		return item;
	}

	public void setItem(Object item)
	{
		this.item = item;
	}

	public void setItemId(Integer itemId)
	{
		this.itemId = itemId;
	}

	public Integer getItemId()
	{
		return itemId;
	}

	public void setItemIdComp(UIInput itemIdComp)
	{
		this.itemIdComp = itemIdComp;
	}

	public UIInput getItemIdComp()
	{
		return itemIdComp;
	}

	public String getFinalIdParam()
	{
		return ID_PARAM;
	}
}
