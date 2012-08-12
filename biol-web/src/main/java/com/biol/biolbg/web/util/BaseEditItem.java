package com.biol.biolbg.web.util;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;

public abstract class BaseEditItem extends Base {
	
	public static final String ID_PARAM = "id";
	
	private Integer itemId = -1;  //binded to view param only
	private UIInput itemIdComp;
	private Object item = null;
	
	
	//------ABSTRACT METHODS------------------------
	public abstract void init();
	public abstract Boolean doSaveData();
	public abstract Object createNewItem();
	public abstract Object findItemById(Integer id);
	
	//------FINAL METHODS---------------------------
	@PostConstruct
	public final void postConstruct() {
		init();
		item = createNewItem();
	}
	
	public final void preRenderView(javax.faces.event.ComponentSystemEvent event) {
		if (itemId > 0) {
			item = findItemById(itemId);
		} else {
			if (getIsViewItemOnly()) {
				item = findItemById(getRealItemId());
			}
		}
		if (item == null) {
			item = createNewItem();
			addErrorMessage(getAppBean().getMessageResourceString("invalidParamOrRecordDeleted", null));
		}
	}
	
	public Boolean getIsViewItemOnly() {
		return false;
	}
	
	public final String saveData() {
		if (doSaveData()) {
			String text = getAppBean().getMessageResourceString("dataSavedOK", null);
			addInfoMessage(text);
			item = createNewItem();
			return "OK";
		} else {
			return "Error";
		}
	}
	
	public final String cancelData() {
		return "cancel";
	}
	
	public final Integer getRealItemId() {
		Integer iItemId = -1;
		if (getItemIdComp() != null) {
			//Object submittedValue = null;
			//if (getItemIdComp().isLocalValueSet()) {
			//	submittedValue = getItemIdComp().getValue();
			//} else {
			//	submittedValue = getItemIdComp().getSubmittedValue();
			//}
			Object submittedValue = getItemIdComp().getSubmittedValue();
			if (submittedValue == null) {
				submittedValue = getItemIdComp().getValue();
			}
			if (submittedValue != null) {
				String sItemId = submittedValue.toString();
				try {
					iItemId = Integer.valueOf(sItemId);
				} catch (Exception e) {
					iItemId = -1;
				}
			}	
		}	
		return iItemId;
	}
	
	
	//---------GETTERS AND SETTERS------------------
	public final Object getItem() {
		return item;
	}

	public final void setItem(Object item) {
		this.item = item;
	}
	
	public final void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public final Integer getItemId() {
		return itemId;
	}
	
	public void setItemIdComp(UIInput itemIdComp) {
		this.itemIdComp = itemIdComp;
	}
	public UIInput getItemIdComp() {
		return itemIdComp;
	}
	public final String getFinalIdParam() {
		return ID_PARAM;
	}

}
