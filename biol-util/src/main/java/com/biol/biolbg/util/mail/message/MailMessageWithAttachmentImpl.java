package com.biol.biolbg.util.mail.message;

import java.util.List;

public class MailMessageWithAttachmentImpl extends	MailMessageImpl implements MailMessageWithAttachment
{
	private static final long serialVersionUID = 6068768711749388305L;

	protected List<String> fileNames;

	public List<String> getFileNames()
	{
		return fileNames;
	}
}
