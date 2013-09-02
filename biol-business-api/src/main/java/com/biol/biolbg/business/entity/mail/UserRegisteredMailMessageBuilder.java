package com.biol.biolbg.business.entity.mail;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface UserRegisteredMailMessageBuilder extends MailMessageBuilder<MailMessage> 
{

	public UserRegisteredMailMessageBuilder messageLocale(
			Locale messageLocale);

	public UserRegisteredMailMessageBuilder username(String username);

	public UserRegisteredMailMessageBuilder name(String name);

	public UserRegisteredMailMessageBuilder adminEmails(
			List<String> adminEmails);

	public UserRegisteredMailMessageBuilder organization(String organization);

	public UserRegisteredMailMessageBuilder date(Date date);

	public MailMessage build();

}