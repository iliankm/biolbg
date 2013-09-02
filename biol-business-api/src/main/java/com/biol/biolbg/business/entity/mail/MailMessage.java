package com.biol.biolbg.business.entity.mail;

import java.io.Serializable;
import java.util.List;

public interface MailMessage extends Serializable
{
	public String getFrom();

	public List<String> getRecipientsTo();

	public List<String> getRecipientsCC();

	public List<String> getRecipientsBCC();

	public String getSubject();

	public String getText();

	public String getTextType();
}
