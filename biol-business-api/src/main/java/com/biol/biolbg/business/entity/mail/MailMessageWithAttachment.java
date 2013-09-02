package com.biol.biolbg.business.entity.mail;

import java.util.List;

public interface MailMessageWithAttachment extends MailMessage
{
	public List<String> getFileNames();
}
