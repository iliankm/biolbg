package com.biol.biolbg.util.mail.sender;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.biol.biolbg.util.configuration.ApplicationConfiguration;
import com.biol.biolbg.util.mail.message.MailMessage;

public abstract class MailMessageAbstractSender<E extends MailMessage> implements MailMessageSender<E>, Serializable
{
	private static final long serialVersionUID = -4859573384243224754L;

	@Inject
	@MailProperties
	private Properties mailProperties;

	@Inject
	private ApplicationConfiguration applicationConfiguration;

	public abstract void send(E mailMessage) throws MailMessageSenderException;

	protected Session getMailSession()
	{
		Session session = Session.getInstance(mailProperties,
							  new javax.mail.Authenticator()
							  {
								protected PasswordAuthentication getPasswordAuthentication()
								{
									return new PasswordAuthentication(applicationConfiguration.getMailAccountUsername(),
																		applicationConfiguration.getMailAccountPassword());
								}
							  });

		return session;
	}

	protected void addRecipientsToMimeMessage(E mailMessage, MimeMessage mimeMessage) throws AddressException, MessagingException
	{
		List<String> recipientsTo = mailMessage.getRecipientsTo();
		if (recipientsTo != null)
		{
			for (String recipientTo : recipientsTo)
			{
				mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientTo));
			}
		}

		List<String> recipientsCC = mailMessage.getRecipientsCC();
		if (recipientsCC != null)
		{
			for (String recipientCC : recipientsCC)
			{
				mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(recipientCC));
			}
		}

		List<String> recipientsBCC = mailMessage.getRecipientsBCC();
		if (recipientsBCC != null)
		{
			for (String recipientBCC : recipientsBCC)
			{
				mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipientBCC));
			}
		}
	}
}
