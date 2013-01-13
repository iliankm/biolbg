package com.biol.biolbg.util.configuration;

import java.util.ResourceBundle;

import javax.enterprise.inject.Alternative;

@Alternative
public class ApplicationConfiguration
{

	private static final String CONFIG_BUNDLE_NAME = "com.biol.biolbg.util.configuration.config";

	private static final String APP_CONTEXT_KEY = "appContext";

	private static final String APP_WEB_CONTEXT_KEY = "appWebContext";

	private static final String APP_DATASOURCE_KEY = "appDatasource";

	private static final String IMAGES_PATH_KEY = "imagesPath";

	private static final String LOG_PATH_KEY = "logPath";

	private static final String MAIL_FROM_ADDRESS = "mailFromAddress";

	private static final String MAIL_ACCOUNT_USERNAME = "mailAccountUsername";

	private static final String MAIL_ACCOUNT_PASSWORD = "mailAccountPassword";

	private static ApplicationConfiguration instance;

	private ResourceBundle configBundle;

	private String imagesPath;

	private String logPath;

	private EnvVarsResolver envVarsResolver;

	protected ApplicationConfiguration()
	{
		envVarsResolver = new EnvVarsResolver();

		configBundle = ResourceBundle.getBundle(CONFIG_BUNDLE_NAME);
	}

	public static ApplicationConfiguration getInstance()
	{
		if (instance == null)
		{
			instance = new ApplicationConfiguration();
		}

		return instance;
	}

	public String getApplicationContext()
	{
		return configBundle.getString(APP_CONTEXT_KEY);

	}

	public String getApplicationWebContext()
	{
		return configBundle.getString(APP_WEB_CONTEXT_KEY);
	}

	public String getApplicationDatasource()
	{
		return configBundle.getString(APP_DATASOURCE_KEY);
	}

	public String getImagesPath()
	{
		if (imagesPath == null)
		{
			String imagesPathUnresolved = configBundle.getString(IMAGES_PATH_KEY);
			imagesPath = envVarsResolver.resolve(imagesPathUnresolved);
		}

		return imagesPath;
	}

	public String getLogPath()
	{
		if (logPath == null)
		{
			String logPathUnresolved = configBundle.getString(LOG_PATH_KEY);
			logPath = envVarsResolver.resolve(logPathUnresolved);
		}

		return logPath;
	}

	public String getMailFromAddress()
	{
		return configBundle.getString(MAIL_FROM_ADDRESS);
	}

	public String getMailAccountUsername()
	{
		return configBundle.getString(MAIL_ACCOUNT_USERNAME);
	}

	public String getMailAccountPassword()
	{
		return configBundle.getString(MAIL_ACCOUNT_PASSWORD);
	}
}
