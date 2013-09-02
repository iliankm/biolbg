package com.biol.biolbg.business.entity.mail;

import java.util.List;

import com.biol.biolbg.business.entity.mail.MailMessageWithAttachment;

public class MailMessageWithAttachmentImpl extends	MailMessageImpl implements MailMessageWithAttachment
{
	private static final long serialVersionUID = 6068768711749388305L;

	protected List<String> fileNames;

	public List<String> getFileNames()
	{
		return fileNames;
	}
}
