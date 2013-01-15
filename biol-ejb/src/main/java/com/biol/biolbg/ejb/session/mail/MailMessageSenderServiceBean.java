package com.biol.biolbg.ejb.session.mail;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import com.biol.biolbg.util.mail.message.MailMessage;
import com.biol.biolbg.util.mail.message.MailMessageWithAttachment;
import com.biol.biolbg.util.mail.sender.MailMessageSender;
import com.biol.biolbg.util.mail.sender.MailMessageSenderException;
import com.biol.biolbg.util.mail.sender.MailSender;
import com.biol.biolbg.util.mail.sender.MailSenderWithAttachment;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MailMessageSenderServiceBean implements MailMessageSenderService
{
	@Inject
	@MailSender
	private MailMessageSender<MailMessage> mailMessageSender;

	@Inject
	@MailSenderWithAttachment
	private MailMessageSender<MailMessageWithAttachment> mailMessageWithAttachmentSender;

	@Inject
	private Logger logger;

	@Override
	@Asynchronous
	public Future<MailStatus> send(MailMessage message)
	{
		try
		{
			mailMessageSender.send(message);

			return new AsyncResult<MailStatus>(MailStatus.SENT);
		}
		catch (MailMessageSenderException e)
		{
			logger.log(Level.ALL, "", e);

			return new AsyncResult<MailStatus>(MailStatus.FAILED);
		}
	}

	@Override
	@Asynchronous
	public Future<MailStatus> send(MailMessageWithAttachment message)
	{
		try
		{
			mailMessageWithAttachmentSender.send(message);

			return new AsyncResult<MailStatus>(MailStatus.SENT);
		}
		catch (MailMessageSenderException e)
		{
			logger.log(Level.ALL, "", e);

			return new AsyncResult<MailStatus>(MailStatus.FAILED);
		}
	}
}
