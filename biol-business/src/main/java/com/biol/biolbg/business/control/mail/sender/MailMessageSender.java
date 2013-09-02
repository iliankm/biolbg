package com.biol.biolbg.business.control.mail.sender;

import com.biol.biolbg.business.entity.mail.MailMessage;
import com.biol.biolbg.business.exception.MailMessageSenderException;

public interface MailMessageSender<E extends MailMessage>
{
	public void send(E mailMessage) throws MailMessageSenderException;
}
