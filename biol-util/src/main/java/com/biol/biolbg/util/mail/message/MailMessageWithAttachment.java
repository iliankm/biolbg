package com.biol.biolbg.util.mail.message;

import java.util.List;

public interface MailMessageWithAttachment extends MailMessage
{
	public List<String> getFileNames();
}
