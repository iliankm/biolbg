package com.biol.biolbg.web.util.cdi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.biol.biolbg.entity.Item;
import com.biol.biolbg.web.util.FileUtil;

@ManagedBean(name="ItemImagesFilenameMapper")
@RequestScoped
public class ItemImagesFilenameMapper {
	public static final String IMAGES_PATH = "imagesPath";
	
	@ManagedProperty(value="#{EnvVarsResolver}")
	private EnvVarsResolver envVarsResolver;
	
	
	public Map<Integer, String> getMap(List<Item> items) {
		Map<Integer, String> result = new HashMap<Integer, String>();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String imagesPath = facesContext.getExternalContext().getInitParameter(IMAGES_PATH);
		imagesPath = envVarsResolver.resolve(imagesPath);
		
		for (Item item : items) {
			String imageFileName = FileUtil.imageFileName(imagesPath, Integer.toString(item.getId()));
			if (imageFileName != null) {
				result.put(item.getId(), imageFileName);
			}
			
		}
			
		return result;
	}


	public void setEnvVarsResolver(EnvVarsResolver envVarsResolver) {
		this.envVarsResolver = envVarsResolver;
	}


	public EnvVarsResolver getEnvVarsResolver() {
		return envVarsResolver;
	}

}
