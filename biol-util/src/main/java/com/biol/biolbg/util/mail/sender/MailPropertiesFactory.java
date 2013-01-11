package com.biol.biolbg.util.mail.sender;

import java.util.Properties;

import javax.enterprise.inject.Produces;

public class MailPropertiesFactory
{
	@Produces
	@TLSProperties
	public static Properties createTLSProperties()
	{
		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		return props;
	}

	@Produces
	@SSLProperties
	public static Properties createSSLProperties()
	{
		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		return props;
	}
}
