package com.biol.biolbg.util.mail.sender;

public class MailMessageSenderException extends Exception
{
	private static final long serialVersionUID = 8912180428169645445L;

	public MailMessageSenderException()
	{
		super();
	}

	public MailMessageSenderException(String message)
	{
		super(message);
	}

	public MailMessageSenderException(Throwable t)
	{
		super(t);
	}

	public MailMessageSenderException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
