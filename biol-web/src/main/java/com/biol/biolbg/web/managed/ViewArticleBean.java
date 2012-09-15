package com.biol.biolbg.web.managed;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.EJBLocator;
import com.biol.biolbg.web.util.FileUtil;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

import com.biol.biolbg.ejb.session.ItemFacade;

import com.biol.biolbg.entity.BaseEntity;
import com.biol.biolbg.entity.Item;

@ManagedBean(name = "ViewArticleBean")
@RequestScoped
public class ViewArticleBean extends BaseEditItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private ItemFacade itemFacade = EJBLocator.getInstance().lookup(ItemFacade.class);

	@Override
	public Boolean getIsViewItemOnly() {
		return true;
	}
	
	@Override
	public Object createNewItem() {
		return itemFacade.createNewItem();
	}

	@Override
	public Boolean doSaveData() {
		return true;
	}

	@Override
	public Object findItemById(Integer id) {
		return itemFacade.findItem(id);
	}

	@Override
	public void init() {
		// nothing to do here
	}
	
	//determine the file image for the given itemId
	public String getImageFileName() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String imagesPath = facesContext.getExternalContext().getInitParameter(ItemImagesFilenameMapper.IMAGES_PATH);
		
		BaseEntity item_ = (BaseEntity) getItem();
		String itemId = String.valueOf(item_.getId());  //getRealItemId().toString(); //getItemId().toString();
		
		String res = FileUtil.imageFileName(imagesPath, itemId);
		if (res == null) {
			res = "";
		}
		return res;
	}
	
	public String getLocalizedItemName() {
		Item item = (Item)getItem();
		if (item != null) {
			if (getAppBean() != null) {
				if (getAppBean().getAppLocale().equals("en")) {
					return item.getNameen();
				} else {
					return item.getNamebg();
				}
			} else {
				return item.getNamebg();
			}
		} else {
			return "";
		}
	}
	

}
