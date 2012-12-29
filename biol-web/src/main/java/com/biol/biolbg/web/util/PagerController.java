package com.biol.biolbg.web.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public abstract class PagerController implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Integer showRowsCount = 10;

	private Integer pageNumber = 1;

	private Integer totalPages = 1;

	public abstract void pagerAction();

	public void setShowRowsCount(Integer showRowsCount)
	{
		this.showRowsCount = showRowsCount;
	}

	public Integer getShowRowsCount()
	{
		return showRowsCount;
	}

	public void setPageNumber(Integer pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	public Integer getPageNumber()
	{
		return pageNumber;
	}

	public void setTotalPages(Integer totalPages)
	{
		this.totalPages = totalPages;
	}

	public Integer getTotalPages()
	{
		return totalPages;
	}

	public String gotoFirstPage()
	{
		pageNumber = 1;
		pagerAction();

		return "";
	}

	public String gotoLastPage()
	{
		pageNumber = totalPages;
		pagerAction();

		return "";
	}

	public String gotoPrevPage()
	{
		if (pageNumber > 1)
		{
			pageNumber--;
		}
		pagerAction();

		return "";
	}

	public String gotoNextPage()
	{
		if (pageNumber < totalPages)
		{
			pageNumber++;
		}
		pagerAction();

		return "";
	}

	public Boolean getRenderFirstLink()
	{
		if (FacesContext.getCurrentInstance().getRenderResponse())
		{
			return (pageNumber > 1);
		}
		else
		{
			return true;
		}
	}

	public Boolean getRenderLastLink()
	{
		if (FacesContext.getCurrentInstance().getRenderResponse())
		{
			return pageNumber < totalPages;
		}
		else
		{
			return true;
		}
	}

	public void itemsTotalCount(Long totalCount)
	{
		totalPages = 1;
		if ((totalCount != 0) && (showRowsCount != 0))
		{
			totalPages = (int)Math.ceil(totalCount.doubleValue() / showRowsCount.doubleValue());
		}
		if (pageNumber > totalPages)
		{
			pageNumber = totalPages;
		}
	}

	public Integer fromRow()
	{
		return ((pageNumber - 1) * showRowsCount);
	}
}
