package com.biol.biolbg.business.boundary.facade;

import java.util.concurrent.Future;

import javax.ejb.Local;

import com.biol.biolbg.business.entity.mail.MailMessage;
import com.biol.biolbg.business.entity.mail.MailMessageWithAttachment;
import com.biol.biolbg.business.entity.mail.MailStatus;


@Local
public interface MailMessageSenderFacade
{
	public Future<MailStatus> send(MailMessage message);

	public Future<MailStatus> send(MailMessageWithAttachment message);
}
