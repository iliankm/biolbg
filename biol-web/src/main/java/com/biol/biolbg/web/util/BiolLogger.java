package com.biol.biolbg.web.util;

//import java.io.IOException;
//import java.util.logging.FileHandler;
import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;

//import javax.faces.context.FacesContext;

public class BiolLogger {
	
	private BiolLogger() {
	}
	
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
/*	static {
		FacesContext facesCtx = FacesContext.getCurrentInstance();
		String logPath = 
			facesCtx.getExternalContext().getInitParameter("logPath");
		try {
			int limit = 1024 * 1024;
			FileHandler handler = new FileHandler(logPath, limit, 1, true);
			handler.setFormatter(new SimpleFormatter());
			logger.addHandler(handler);
			logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/	
	public static Logger getLogger() {
		return logger;
	}
	

}
