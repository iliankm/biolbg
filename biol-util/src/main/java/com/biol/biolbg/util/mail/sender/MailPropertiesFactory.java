package com.biol.biolbg.util.mail.sender;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.inject.Produces;

public class MailPropertiesFactory
{
	private static final String MAIL_PROPERTIES_CONFIG_RESOURCE = "com/biol/biolbg/util/mail/mailproperties.properties";

	@Produces
	@MailProperties
	public static Properties createTLSProperties() throws IOException
	{
		Properties props = new Properties();

		props.load(MailPropertiesFactory.class.getClassLoader().getResourceAsStream(MAIL_PROPERTIES_CONFIG_RESOURCE));

		return props;
	}
}
