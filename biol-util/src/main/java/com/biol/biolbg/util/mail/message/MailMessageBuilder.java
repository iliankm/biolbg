package com.biol.biolbg.util.mail.message;

public interface MailMessageBuilder<E extends MailMessage>
{
	public static final String MESSAGES_BUNDLE_NAME = "com.biol.biolbg.util.mail.messages";

	public E build();
}
