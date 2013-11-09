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

		boolean hasConditions = false;

		if (findOrderCriteria.getFromDate() != null)
		{
			if (hasConditions)
				queryText = concatAnd(queryText);

			queryText = queryText.concat(" o.postdate>=").concat(dateToLiteral((findOrderCriteria.getFromDate())));

			hasConditions = true;
		}

		if (findOrderCriteria.getToDate() != null)
		{
			if (hasConditions)
				queryText = concatAnd(queryText);

			queryText = queryText.concat(" o.postdate<=").concat(dateToLiteral((findOrderCriteria.getToDate())));

			hasConditions = true;
		}

		if (findOrderCriteria.getUserId() != null)
		{
			if (hasConditions)
				queryText = concatAnd(queryText);

			queryText = queryText.concat(" o.user.id=").concat(findOrderCriteria.getUserId().toString());

			hasConditions = true;
		}

		if (findOrderCriteria.getUsername() != null && !findOrderCriteria.getUsername().isEmpty())
		{
			if (hasConditions)
				queryText = concatAnd(queryText);

			queryText = queryText.concat(" UPPER(o.user.username) LIKE ").concat("'%" + findOrderCriteria.getUsername().toUpperCase() + "%'");

			hasConditions = true;
		}

		if (findOrderCriteria.getOrganization() != null && !findOrderCriteria.getOrganization().isEmpty())
		{
			if (hasConditions)
				queryText = concatAnd(queryText);

			queryText = queryText.concat(" UPPER(o.user.organisation) LIKE ").concat("'%" + findOrderCriteria.getOrganization().toUpperCase() + "%'");

			hasConditions = true;
		}

		return this;
	}

	public OrderQueryBuilder sortCriteria(final SortCriteria sortCriteria)
	{
		String orderBy = String.format(" ORDER BY %s %s", sortCriteria.getPropertyName(), sortCriteria.getSortDirectionForJPA());

		queryText = queryText.concat(orderBy);

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

	private String concatAnd(String queryText)
	{
		return queryText.concat(" AND");
	}

}
