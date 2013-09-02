package com.biol.biolbg.business.control.mail.sender;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.biol.biolbg.business.entity.mail.MailMessageWithAttachment;
import com.biol.biolbg.business.exception.MailMessageSenderException;
import com.biol.biolbg.business.util.annotations.MailSenderWithAttachment;

@MailSenderWithAttachment
public class MailMessageWithAttachmentSenderImpl extends MailMessageAbstractSender<MailMessageWithAttachment>
{
	private static final long serialVersionUID = 5302154752751685274L;

	@Override
	public void send(MailMessageWithAttachment mailMessage) throws MailMessageSenderException
	{
		Session session = getMailSession();

		MimeMessage mimeMessage = new MimeMessage(session);

		try
		{
			mimeMessage.setFrom(new InternetAddress(mailMessage.getFrom()));

			addRecipientsToMimeMessage(mailMessage, mimeMessage);

			mimeMessage.setSubject(mailMessage.getSubject());

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(mailMessage.getText(), mailMessage.getTextType());

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			addAttachmentsToMultipart(mailMessage, multipart);

			mimeMessage.setContent(multipart);

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

	private void addAttachmentsToMultipart(MailMessageWithAttachment mailMessage, Multipart multipart) throws MessagingException
	{
		if (mailMessage.getFileNames() != null)
		{
			for (String filename : mailMessage.getFileNames())
			{
				BodyPart messageBodyPart = new MimeBodyPart();

				DataSource source = new FileDataSource(filename);

				messageBodyPart.setDataHandler(new DataHandler(source));

				messageBodyPart.setFileName(filename);

				multipart.addBodyPart(messageBodyPart);
			}
		}
	}
}
