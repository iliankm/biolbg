package com.biol.biolbg.ejb.session.mail;

import java.util.concurrent.Future;

import javax.ejb.Local;

import com.biol.biolbg.util.mail.message.MailMessage;
import com.biol.biolbg.util.mail.message.MailMessageWithAttachment;

@Local
public interface MailMessageSenderService
{
	public Future<MailStatus> send(MailMessage message);

	public Future<MailStatus> send(MailMessageWithAttachment message);
}
