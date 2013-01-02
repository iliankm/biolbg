package com.biol.biolbg.util.configuration;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

public class ApplicationConfigurationFactory
{
	@Produces
	@Default
	public static ApplicationConfiguration createApplicationConfiguration()
	{
		return ApplicationConfiguration.getInstance();
	}
}
