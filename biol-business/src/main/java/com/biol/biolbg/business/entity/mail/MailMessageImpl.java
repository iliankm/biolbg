package com.biol.biolbg.business.entity.mail;

import java.util.List;

import com.biol.biolbg.business.entity.mail.MailMessage;

public class MailMessageImpl implements MailMessage
{
	private static final long serialVersionUID = -1770895425170959096L;

	protected String from;

	protected List<String> recipientsTo;

	protected List<String> recipientsCC;

	protected List<String> recipientsBCC;

	protected String subject;

	protected String text;

	protected String textType;

	public String getFrom()
	{
		return from;
	}

	public List<String> getRecipientsTo()
	{
		return recipientsTo;
	}

	public List<String> getRecipientsCC()
	{
		return recipientsCC;
	}

	public List<String> getRecipientsBCC()
	{
		return recipientsBCC;
	}

	public String getSubject()
	{
		return subject;
	}

	public String getText()
	{
		return text;
	}

	public String getTextType()
	{
		return textType;
	}

}
