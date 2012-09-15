package com.biol.biolbg.web.util.cdi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="EnvVarsResolver")
@RequestScoped
public class EnvVarsResolver {
	
	public String resolve(String input)
	{
	    if (null == input)
	    {
	        return null;
	    }
	    // match ${ENV_VAR_NAME} or $ENV_VAR_NAME
	    Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
	    Matcher m = p.matcher(input); // get a matcher object
	    StringBuffer sb = new StringBuffer();
	    while(m.find()){
	        String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
	        String envVarValue = System.getenv(envVarName);
	        m.appendReplacement(sb, null == envVarValue ? "" : envVarValue);
	    }
	    m.appendTail(sb);
	    return sb.toString();
	}

}
