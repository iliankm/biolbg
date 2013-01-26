package com.biol.biolbg.util.mail.message;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.biol.biolbg.util.configuration.ApplicationConfiguration;

/**
 * Builder of mail message,
 * that is sent to all administrators after new user is successfully registered.
 *
 * @author Iliyan Kamilov
 *
 */
public class UserRegisteredMailMessageBuilder implements MailMessageBuilder<MailMessage>
{
	private static final long serialVersionUID = 1L;

	private static final String USER_REGISTERED_MESSAGE_KEY = "userRegistered";

	private static final String USER_REGISTERED_SUBJECT_MESSAGE_KEY = "userRegisteredSubject";

	private Locale messageLocale;

	private String username;

	private String name;

	private String organization;

	private Date date;

	private List<String> adminEmails;

	public UserRegisteredMailMessageBuilder messageLocale(Locale messageLocale)
	{
		this.messageLocale = messageLocale;
		return this;
	}

	public UserRegisteredMailMessageBuilder username(String username)
	{
		this.username = username;
		return this;
	}

	public UserRegisteredMailMessageBuilder name(String name)
	{
		this.name = name;
		return this;
	}

	public UserRegisteredMailMessageBuilder adminEmails(List<String> adminEmails)
	{
		this.adminEmails = adminEmails;
		return this;
	}

	public UserRegisteredMailMessageBuilder organization(String organization)
	{
		this.organization = organization;
		return this;
	}

	public UserRegisteredMailMessageBuilder date(Date date)
	{
		this.date = date;
		return this;
	}

	public MailMessage build()
	{
		ResourceBundle messagesResourceBundle = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, messageLocale);

		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance();

		MailMessageImpl mailMessage = new MailMessageImpl();

		Format dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String messageString = String.format(
				messagesResourceBundle.getString(USER_REGISTERED_MESSAGE_KEY),
				"administrator",
				dateFormatter.format(date),
				this.name,
				this.organization,
				this.username,
				applicationConfiguration.getExternalURL());

		mailMessage.text = messageString;
		mailMessage.textType = "text/plain; charset=utf-8";
		mailMessage.from = applicationConfiguration.getMailFromAddress();
		mailMessage.recipientsTo = adminEmails;
		mailMessage.subject = messagesResourceBundle.getString(USER_REGISTERED_SUBJECT_MESSAGE_KEY);

		return mailMessage;
	}
}
