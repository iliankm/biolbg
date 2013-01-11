package com.biol.biolbg.util.mail.sender;

import com.biol.biolbg.util.mail.message.MailMessage;

public interface MailMessageSender<E extends MailMessage>
{
	public void send(E mailMessage) throws MailMessageSenderException;
}
