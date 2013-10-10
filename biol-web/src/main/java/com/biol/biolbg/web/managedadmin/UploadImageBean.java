package com.biol.biolbg.web.managedadmin;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.boundary.facade.ItemFacade;
import com.biol.biolbg.util.configuration.ApplicationConfiguration;
import com.biol.biolbg.web.util.BaseEditItem;
import com.biol.biolbg.web.util.FileUtil;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

@Named("UploadImageBean")
@RequestScoped
public class UploadImageBean extends BaseEditItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	ApplicationConfiguration applicationConfiguration;

	@Inject
	ItemImagesFilenameMapper itemImagesFilenameMapper;

	@EJB
	private ItemFacade itemFacade;

	@Override
	public Boolean getIsViewItemOnly()
	{
		return true;
	}

	public void deleteImage(ActionEvent event)
	{
		String imagesPath = applicationConfiguration.getImagesPath();

		FileUtil.deleteImageFilesForItem(imagesPath, getRealItemId(), "");
	}

	@Override
	public Object createNewItem()
	{
		return itemFacade.createLocal();
	}

	@Override
	public Object findItemById(Integer id)
	{
		return itemFacade.findById(id);
	}

	//determine the file image for the given itemId
	public String getImageFileName()
	{
		Integer itemId = getRealItemId();

		return itemImagesFilenameMapper.getSingle(itemId);
	}

	@Override
	public Boolean doSaveData()
	{
		return true;
	}

	@Override
	public void init()
	{
	}
}
