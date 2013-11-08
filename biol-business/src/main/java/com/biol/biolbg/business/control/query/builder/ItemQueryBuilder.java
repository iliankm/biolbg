package com.biol.biolbg.business.control.query.builder;

import com.biol.biolbg.business.util.FindItemCriteria;
import com.biol.biolbg.business.util.SortCriteria;

public class ItemQueryBuilder
{
	private String queryText;

	public ItemQueryBuilder select()
	{
		queryText = "SELECT o FROM ItemEntity o";

		return this;
	}

	public ItemQueryBuilder selectForCount()
	{
		queryText = "SELECT COUNT(o.id) FROM ItemEntity o";

		return this;
	}

	public ItemQueryBuilder findItemCriteria(final FindItemCriteria findItemCriteria)
	{
		if (findItemCriteria.getGroupId() != null ||
			findItemCriteria.getProducerId() != null ||
			(findItemCriteria.getName() != null && !findItemCriteria.getName().isEmpty()))
			queryText = queryText.concat(" WHERE");

		if (findItemCriteria.getGroupId() != null)
		{
			queryText = queryText.concat(" o.group.id=").concat(findItemCriteria.getGroupId().toString());

			if (findItemCriteria.getProducerId() != null)
				queryText = queryText.concat(" AND");
		}

		if (findItemCriteria.getProducerId() != null)
		{
			queryText = queryText.concat(" o.producer.id=").concat(findItemCriteria.getProducerId().toString());

			if (findItemCriteria.getName() != null && !findItemCriteria.getName().isEmpty())
				queryText = queryText.concat(" AND");
		}

		if (findItemCriteria.getName() != null && !findItemCriteria.getName().isEmpty())
		{
			String nameArg = "'%" + findItemCriteria.getName().toUpperCase() + "%'";

			queryText = queryText.concat(String.format(" (UPPER(o.nameen) LIKE %s OR UPPER(o.namebg) LIKE %s)", nameArg, nameArg));
		}

		return this;
	}

	public ItemQueryBuilder sortCriteria(final SortCriteria sortCriteria)
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

}
