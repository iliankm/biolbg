package com.biol.biolbg.business.entity.mail;

import java.io.Serializable;


public interface MailMessageBuilder<E extends MailMessage> extends Serializable
{
	public static final String MESSAGES_BUNDLE_NAME = "com.biol.biolbg.util.mail.messages";

	public E build();
}
