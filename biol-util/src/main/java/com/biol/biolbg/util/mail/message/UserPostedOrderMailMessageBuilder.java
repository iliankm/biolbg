package com.biol.biolbg.util.mail.message;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.biol.biolbg.entity.Order;
import com.biol.biolbg.entity.OrderRow;
import com.biol.biolbg.entity.Usr;
import com.biol.biolbg.util.configuration.ApplicationConfiguration;

public class UserPostedOrderMailMessageBuilder implements MailMessageBuilder<MailMessage>
{
	private static final long serialVersionUID = 1L;

	private static final String USER_POSTED_ORDER_MASSAGE_KEY = "userPostedOrder";

	private static final String USER_POSTED_ORDER_SUBJECT_MSSAGE_KEY = "userPostedOrderSubject";

	private Locale messageLocale;

	private List<Usr> adminUsers;

	private Order order;

	public UserPostedOrderMailMessageBuilder messageLocale(Locale locale)
	{
		this.messageLocale = locale;

		return this;
	}

	public UserPostedOrderMailMessageBuilder adminUsers(List<Usr> adminUsers)
	{
		this.adminUsers = adminUsers;

		return this;
	}

	public UserPostedOrderMailMessageBuilder order(Order order)
	{
		this.order = order;

		return this;
	}

	public MailMessage build()
	{
		ResourceBundle messagesResourceBundle = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, messageLocale);

		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance();

		MailMessageImpl mailMessage = new MailMessageImpl();

		Format dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		Format timeFormatter = new SimpleDateFormat("HH:mm:ss");

		String messageString = String.format(
				messagesResourceBundle.getString(USER_POSTED_ORDER_MASSAGE_KEY),
				"administrator",
				dateFormatter.format(this.order.getPostdate()) + " " + timeFormatter.format(this.order.getPosttime()),
				this.order.getUser().getFullname(),
				this.order.getUser().getOrganisation(),
				this.order.getDeliveryAddress(),
				getDeliveryDateTimeString(order, dateFormatter, timeFormatter),
				getOrderedArticlesString(order),
				applicationConfiguration.getExternalURL());

		mailMessage.from = applicationConfiguration.getMailFromAddress();
		mailMessage.recipientsTo = getAdminUsersEmails(adminUsers);
		mailMessage.subject = messagesResourceBundle.getString(USER_POSTED_ORDER_SUBJECT_MSSAGE_KEY);
		mailMessage.text = messageString;
		mailMessage.textType = "text/plain; charset=utf-8";

		return mailMessage;
	}

	private String getDeliveryDateTimeString(Order order, Format dateFormatter, Format timeFormatter)
	{
		String deliveryDateTimeString = "";

		if (order.getFordate() != null)
		{
			deliveryDateTimeString = dateFormatter.format(order.getFordate());
		}
		if (order.getFortime() != null)
		{
			deliveryDateTimeString.concat(" ").concat(timeFormatter.format(order.getFortime()));
		}

		return deliveryDateTimeString != "" ? deliveryDateTimeString : " - ";

	}

	private String getOrderedArticlesString(Order order)
	{
		String orderedArticlesString = "";

		for (OrderRow row : order.getRows())
		{
			orderedArticlesString =
			orderedArticlesString.
				concat(row.getItem().getNamebg()).
				concat(" ").
				concat(row.getAmount().toString()).
				concat(row.getItem().getPackingbg()).
				concat("; ");
		}

		return orderedArticlesString;
	}

	private List<String> getAdminUsersEmails(List<Usr> adminUsers)
	{
		List<String> adminUsersEmails = new ArrayList<String>();

		for (Usr admin : adminUsers)
		{
			adminUsersEmails.add(admin.getEmail());
		}

		return adminUsersEmails;
	}
}
