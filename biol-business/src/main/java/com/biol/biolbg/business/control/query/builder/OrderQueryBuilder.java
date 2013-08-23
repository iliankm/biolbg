package com.biol.biolbg.business.control.query.builder;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.biol.biolbg.business.util.FindOrderCriteria;
import com.biol.biolbg.business.util.SortCriteria;

public class OrderQueryBuilder
{
	private String queryText;

	public OrderQueryBuilder select()
	{
		queryText = "SELECT o FROM OrderEntity o";

		return this;
	}

	public OrderQueryBuilder selectForCount()
	{
		queryText = "SELECT COUNT(o.id) FROM OrderEntity o";

		return this;
	}

	public OrderQueryBuilder findOrderCriteria(final FindOrderCriteria findOrderCriteria)
	{
		queryText = queryText.concat(" WHERE");

		if (findOrderCriteria.getFromDate() != null)
		{
			queryText = queryText.concat(" o.postdate>=").concat(dateToLiteral((findOrderCriteria.getFromDate())));

			if (findOrderCriteria.getToDate() != null)
				queryText = queryText.concat(" AND");
		}

		if (findOrderCriteria.getToDate() != null)
		{
			queryText = queryText.concat(" o.postdate<=").concat(dateToLiteral((findOrderCriteria.getToDate())));

			if (findOrderCriteria.getUserId() != null)
				queryText = queryText.concat(" AND");
		}

		if (findOrderCriteria.getUserId() != null)
		{
			queryText = queryText.concat(" o.user.id=").concat(findOrderCriteria.getUserId().toString());

			if (findOrderCriteria.getUsername() != null && !findOrderCriteria.getUsername().isEmpty())
				queryText = queryText.concat(" AND");
		}

		if (findOrderCriteria.getUsername() != null && !findOrderCriteria.getUsername().isEmpty())
		{
			queryText = queryText.concat(" UPPER(o.user.username) LIKE ").concat("%" + findOrderCriteria.getUsername().toUpperCase() + "%");

			if (findOrderCriteria.getOrganization() != null && !findOrderCriteria.getOrganization().isEmpty())
				queryText = queryText.concat(" AND");
		}

		if (findOrderCriteria.getOrganization() != null && !findOrderCriteria.getOrganization().isEmpty())
		{
			queryText = queryText.concat(" UPPER(o.user.organisation) LIKE ").concat("%" + findOrderCriteria.getOrganization().toUpperCase() + "%");
		}

		return this;
	}

	public OrderQueryBuilder sortCriteria(final SortCriteria sortCriteria)
	{
		queryText = queryText.concat(" ORDER BY %s %s");

		queryText = String.format(queryText, sortCriteria.getPropertyName(), sortCriteria.getSortDirectionForJPA());

		return this;
	}

	public String build()
	{
		String result = new String(queryText);

		queryText = null;

		return result;
	}

	private String dateToLiteral(Date date)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return String.format("{d '%s'}", simpleDateFormat.format(date));
	}

}
