package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.EJBLocator;
import com.biol.biolbg.web.util.FileUtil;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

import com.biol.biolbg.ejb.session.ItemFacade;


@ManagedBean(name = "UploadImageBean")
@RequestScoped
public class UploadImageBean extends BaseEditItem implements Serializable{

	private static final long serialVersionUID = 1L;
	private ItemFacade itemFacade = EJBLocator.getInstance().lookup(ItemFacade.class);
	
	@Override
	public Boolean getIsViewItemOnly() {
		return true;
	}
	
	public void deleteImage(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String imagesPath = facesContext.getExternalContext().getInitParameter(ItemImagesFilenameMapper.IMAGES_PATH);
		
		String itemId = getRealItemId().toString();
		
		FileUtil.deleteImageFilesForItem(imagesPath, itemId, "");
		
	}

	@Override
	public Object createNewItem() {
		return itemFacade.createNewItem();
	}

	@Override
	public Object findItemById(Integer id) {
		return itemFacade.findItem(id);
	}
	
	//determine the file image for the given itemId
	public String getImageFileName() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String imagesPath = facesContext.getExternalContext().getInitParameter(ItemImagesFilenameMapper.IMAGES_PATH);
		
		String itemId = getRealItemId().toString();
		
		String res = FileUtil.imageFileName(imagesPath, itemId);
		if (res == null) {
			res = "";
		}
		return res;
	}

	@Override
	public Boolean doSaveData() {
		return true;
	}

	@Override
	public void init() {
		// nothing to do here
	}
	
}
