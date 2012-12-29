package com.biol.biolbg.web.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBLocator
{

	private Context context;

	private Map<String, Object> ejbInterfaces;

	//Singleton
	private EJBLocator()
	{
		try
		{
			context = new InitialContext();
		}
		catch (NamingException e)
		{
			BiolLogger.getLogger().log(Level.ALL, "", e);
		}
		ejbInterfaces = new ConcurrentHashMap<String, Object>();
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
	public <T> T lookup(Class<T> facadeInterfaceClass)
	{
		String facadeInterfaceClassname = facadeInterfaceClass.getName();
		Object ejbInterface = ejbInterfaces.get(facadeInterfaceClassname);
		if (ejbInterface == null)
		{
			try
			{
				ejbInterface = context.lookup(facadeInterfaceClassname);
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
