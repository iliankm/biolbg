package com.biol.biolbg.business.entity.mail;

import java.util.List;
import java.util.Locale;

import com.biol.biolbg.business.entity.Order;
import com.biol.biolbg.business.entity.Usr;

public interface UserPostedOrderMailMessageBuilder extends MailMessageBuilder<MailMessage>
{

	public UserPostedOrderMailMessageBuilder messageLocale(Locale locale);

	public UserPostedOrderMailMessageBuilder adminUsers(List<Usr> adminUsers);

	public UserPostedOrderMailMessageBuilder order(Order order);

	public MailMessage build();

}