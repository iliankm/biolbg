package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.FileUtil;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

import com.biol.biolbg.ejb.session.ItemFacade;


@ManagedBean(name = "UploadImageBean")
@RequestScoped
public class UploadImageBean extends BaseEditItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{ItemImagesFilenameMapper}")
	private ItemImagesFilenameMapper itemImagesFilenameMapper;
	
	@EJB
	private ItemFacade itemFacade; //= EJBLocator.getInstance().lookup(ItemFacade.class);
	
	@Override
	public Boolean getIsViewItemOnly() {
		return true;
	}
	
	public void deleteImage(ActionEvent event) 
	{
		String imagesPath = itemImagesFilenameMapper.getImagesPath();
		
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
	public String getImageFileName() 
	{
		Integer itemId = getRealItemId();
		
		return itemImagesFilenameMapper.getSingle(itemId);
	}

	@Override
	public Boolean doSaveData() {
		return true;
	}

	@Override
	public void init() {
		// nothing to do here
	}

	public void setItemImagesFilenameMapper(ItemImagesFilenameMapper itemImagesFilenameMapper) {
		this.itemImagesFilenameMapper = itemImagesFilenameMapper;
	}

	public ItemImagesFilenameMapper getItemImagesFilenameMapper() {
		return itemImagesFilenameMapper;
	}
	
}
