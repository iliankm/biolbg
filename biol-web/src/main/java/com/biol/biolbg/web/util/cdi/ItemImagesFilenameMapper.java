package com.biol.biolbg.web.util.cdi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.context.FacesContext;

import com.biol.biolbg.entity.Item;
import com.biol.biolbg.web.util.FileUtil;

@ManagedBean(name="ItemImagesFilenameMapper")
@NoneScoped
public class ItemImagesFilenameMapper {
	public static final String IMAGES_PATH = "imagesPath";
	
	@ManagedProperty(value="#{EnvVarsResolver}")
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
