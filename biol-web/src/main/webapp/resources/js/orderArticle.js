orderArticle = new function()
{
	this.showOrderArticleLoadingMask = function(id)
	{
		var loadingMaskElement = getLoadingMaskElement(id);
		if (loadingMaskElement)
		{
			loadingMaskElement.style.visibility = 'visible';
		}
	};

	this.hideOrderArticleLoadingMask = function(data, id)
	{
		if (data.status == 'complete')
		{
			var loadingMaskElement = getLoadingMaskElement(id);
			if (loadingMaskElement)
			{
				loadingMaskElement.style.visibility = 'hidden';
			}
		}
	};

	var getLoadingMaskElement = function(id)
	{
		return loadingMaskElement = document.getElementById(id);
	};
};