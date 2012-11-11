package com.biol.biolbg.web.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBLocator {
	
	private static Context JNDI_CONTEXT;
	static 
	{
		try 
		{
			JNDI_CONTEXT = new InitialContext();
		} catch (NamingException e) 
		{
			BiolLogger.getLogger().log(Level.ALL, "", e);
		}
	}
	
	private static String EJB_CONTEXT = "java:comp/env/";
	
	private Map<String, Object> ejbInterfaces;
	
	//Singleton
	private EJBLocator() 
	{
		ejbInterfaces = new ConcurrentHashMap<String, Object>(); //HashMap<String, Object>();
	}
	
	private static class EJBLocatorHolder 
	{
		public static final EJBLocator instance = new EJBLocator();
	}
	
	public static EJBLocator getInstance() 
	{
		return EJBLocatorHolder.instance;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T lookup(String name) 
	{
		String facadeInterfaceClassname = EJB_CONTEXT + name;
		Object ejbInterface = ejbInterfaces.get(facadeInterfaceClassname);
		if (ejbInterface == null) 
		{
			try 
			{
				ejbInterface = JNDI_CONTEXT.lookup(facadeInterfaceClassname);
			} 
			catch (NamingException e) 
			{
				BiolLogger.getLogger().log(Level.ALL, "", e);
			}
			ejbInterfaces.put(facadeInterfaceClassname, ejbInterface);
		}
		
		return (T)ejbInterface;
	}
}
