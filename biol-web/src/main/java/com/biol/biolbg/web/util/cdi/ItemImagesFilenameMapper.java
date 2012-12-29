package com.biol.biolbg.web.util.cdi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.entity.Item;
import com.biol.biolbg.web.util.FileUtil;

@Named("ItemImagesFilenameMapper")
public class ItemImagesFilenameMapper implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String IMAGES_PATH = "imagesPath";

	@Inject
	private EnvVarsResolver envVarsResolver;

	private String imagesPath;

	@PostConstruct
	public void postConstruct()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String imagesPathUnresolved = facesContext.getExternalContext().getInitParameter(IMAGES_PATH);
		imagesPath = envVarsResolver.resolve(imagesPathUnresolved);
	}

	public Map<Integer, String> getMap(List<Item> items)
	{
		Map<Integer, String> result = new HashMap<Integer, String>();

		for (Item item : items)
		{
			String imageFileName = getSingle(item);

			if (imageFileName != null)
			{
				result.put(item.getId(), imageFileName);
			}
		}

		return result;
	}

	public String getSingle(Item item)
	{
		return getSingle(Integer.valueOf(item.getId()));
	}

	public String getSingle(Integer itemId)
	{
		return FileUtil.imageFileName(imagesPath, itemId);
	}

	public void setEnvVarsResolver(EnvVarsResolver envVarsResolver)
	{
		this.envVarsResolver = envVarsResolver;
	}


	public EnvVarsResolver getEnvVarsResolver()
	{
		return envVarsResolver;
	}


	public String getImagesPath()
	{
		return imagesPath;
	}

}
