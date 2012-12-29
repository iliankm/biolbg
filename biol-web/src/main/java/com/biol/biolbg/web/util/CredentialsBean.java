package com.biol.biolbg.web.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class CredentialsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, ViewCredentials> viewCredentials;

	@PostConstruct
	public void postConstruct() {

		viewCredentials = new HashMap<String, ViewCredentials>();

	}

	public void storeCredentials(String key, String sortByFieldName,
								String sortType, Integer showRowsCount,
								Integer pageNumber, Integer totalPages, Object customCredentials) {

		ViewCredentials vc = viewCredentials.get(getViewId());

		if (vc == null) {
			vc = new ViewCredentials();
		}

		vc.setSortByFieldName(sortByFieldName);
		vc.setSortType(sortType);
		vc.setShowRowsCount(showRowsCount);
		vc.setPageNumber(pageNumber);
		vc.setTotalPages(totalPages);
		vc.setCustomCredentials(customCredentials);

		this.viewCredentials.put(key, vc);

	}

	public ViewCredentials restoreCredentials(String key) {

		return viewCredentials.get(key);

	}

	private String getViewId() {

		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

		return viewRoot.getViewId();

	}

}
