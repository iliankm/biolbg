package com.biol.biolbg.util.mail.message;

import java.io.Serializable;

public interface MailMessageBuilder<E extends MailMessage> extends Serializable
{
	public static final String MESSAGES_BUNDLE_NAME = "com.biol.biolbg.util.mail.messages";

	public E build();
}
