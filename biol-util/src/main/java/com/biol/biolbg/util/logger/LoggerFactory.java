package com.biol.biolbg.util.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.biol.biolbg.util.configuration.ApplicationConfiguration;
import com.biol.biolbg.util.configuration.EnvVarsResolver;

@ApplicationScoped
public class LoggerFactory
{
	private static final String PARENT_LOGGER_NAME = "com.biol";

	private static final String LOGGER_NAME = "com.biol.biolbg";

	@Inject
	private ApplicationConfiguration applicationConfiguration;

	@Inject
	private EnvVarsResolver envVarsResolver;

	@PostConstruct
	public void postConstruct()
	{
		initParentLogger();
	}

	@Produces Logger createLogger()
	{
		Logger logger = Logger.getLogger(LOGGER_NAME);
		logger.setUseParentHandlers(true);
		logger.setLevel(Level.ALL);

		return logger;
	}

	private void initParentLogger()
	{
		Logger parentLogger = Logger.getLogger(PARENT_LOGGER_NAME);

		String logPathUnresolved = applicationConfiguration.getLogPath();
		String logPath = envVarsResolver.resolve(logPathUnresolved);

		try
		{
			int limit = 1024 * 1024;
			FileHandler handler = new FileHandler(logPath, limit, 1, true);
			handler.setFormatter(new SimpleFormatter());
			parentLogger.addHandler(handler);
			parentLogger.setUseParentHandlers(false);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
