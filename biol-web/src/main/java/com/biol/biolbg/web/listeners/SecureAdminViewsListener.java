package com.biol.biolbg.web.listeners;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import com.biol.biolbg.web.managed.AppBean;


public class SecureAdminViewsListener implements PhaseListener
{
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent arg0)
	{
        FacesContext facesContext = arg0.getFacesContext();
        ExternalContext externalContext = facesContext.getExternalContext();

        //if the accessed view is from /admin/ folder
        if (facesContext.getViewRoot().getViewId().contains("/admin/"))
        {
        	boolean doRedirect = true;

        	AppBean appBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{AppBean}", AppBean.class);

        	if (appBean != null && appBean.getIsUserLoggedIn() && appBean.getLoggedUser().getAdminflag() == 1)
        	{
       			doRedirect = false;
        	}

	        if (doRedirect)
	        {
	        	try
		        {
		        	String appPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
					externalContext.redirect(appPath.concat("/home.jsf"));
				}
		        catch (IOException e)
		        {
					throw new FacesException("Cannot redirect due to IO exception.", e);
				}
	        }
        }
	}

	@Override
	public void beforePhase(PhaseEvent arg0)
	{
	}

	@Override
	public PhaseId getPhaseId()
	{
		return PhaseId.RESTORE_VIEW;
	}
}
