package com.biol.biolbg.web.util.cdi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.biol.biolbg.business.entity.Item;
import com.biol.biolbg.util.configuration.ApplicationConfiguration;
import com.biol.biolbg.web.util.FileUtil;

@Named
@ApplicationScoped
public class ItemImagesFilenameMapper implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String IMAGES_PATH = "imagesPath";

	@Inject
	ApplicationConfiguration applicationConfiguration;

	public Map<Integer, String> getMap(List<Item> items)
	{
		Map<Integer, String> result = new HashMap<Integer, String>();

		if (items != null)
		{
			for (Item item : items)
			{
				String imageFileName = getSingle(item);

				if (imageFileName != null)
				{
					result.put(item.getId(), imageFileName);
				}
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
		String imagesPath = applicationConfiguration.getImagesPath();

		return FileUtil.imageFileName(imagesPath, itemId);
	}
}
