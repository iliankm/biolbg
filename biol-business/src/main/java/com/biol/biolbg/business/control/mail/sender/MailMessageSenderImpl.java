package com.biol.biolbg.business.control.mail.sender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.biol.biolbg.business.entity.mail.MailMessage;
import com.biol.biolbg.business.exception.MailMessageSenderException;
import com.biol.biolbg.business.util.annotations.MailSender;

@MailSender
public class MailMessageSenderImpl extends MailMessageAbstractSender<MailMessage>
{
	private static final long serialVersionUID = 6776071602467893119L;

	@Override
	public void send(MailMessage mailMessage) throws MailMessageSenderException
	{
		Session session = getMailSession();

		MimeMessage mimeMessage = new MimeMessage(session);

		try
		{
			mimeMessage.setFrom(new InternetAddress(mailMessage.getFrom()));

			addRecipientsToMimeMessage(mailMessage, mimeMessage);

			mimeMessage.setSubject(mailMessage.getSubject());

			mimeMessage.setContent(mailMessage.getText(), mailMessage.getTextType());

			Transport.send(mimeMessage);
		}
		catch (AddressException e)
		{
			throw new MailMessageSenderException(e);
		}
		catch (MessagingException e)
		{
			throw new MailMessageSenderException(e);
		}
	}
}
