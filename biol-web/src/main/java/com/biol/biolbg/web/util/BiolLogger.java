package com.biol.biolbg.web.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.faces.context.FacesContext;

import com.biol.biolbg.web.util.cdi.EnvVarsResolver;

public class BiolLogger {
	
	private BiolLogger() {
	}
	

	static 
	{
		Logger parentLogger = Logger.getLogger("com.biol");
		
		FacesContext facesCtx = FacesContext.getCurrentInstance();
		EnvVarsResolver envVarsResolver = new EnvVarsResolver();
		String logPathUnresolved = facesCtx.getExternalContext().getInitParameter("logPath");
		String logPath = envVarsResolver.resolve(logPathUnresolved);
		try 
		{
			int limit = 1024 * 1024;
			FileHandler handler = new FileHandler(logPath, limit, 1, true);
			handler.setFormatter(new SimpleFormatter());
			parentLogger.addHandler(handler);
			parentLogger.setUseParentHandlers(false);
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static Logger logger = Logger.getLogger("com.biol.biolbg");
	
	static
	{
		logger.setUseParentHandlers(true);
		logger.setLevel(Level.ALL);
	}
	
	public static Logger getLogger() {
		return logger;
	}
	

}
