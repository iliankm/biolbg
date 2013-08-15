package com.biol.biolbg.business.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@MappedSuperclass
public abstract class BaseEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private Boolean checked;

	public Boolean getChecked() 
	{
		return checked;
	}

	public void setChecked(Boolean checked) 
	{
		this.checked = checked;
	}
	
	@Override
	public String toString()
	{
		try
		{
		     return (new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE) {
                 @Override
                 protected boolean accept(Field f) {
		        	 //filter jpa objects and collections, as that will cause errors with lazy loading or hurt performance
		             return !Collection.class.isAssignableFrom(f.getType()) &&
		            		!List.class.isAssignableFrom(f.getType()) &&
		     				!BaseEntity.class.isAssignableFrom(f.getType())
		     				&& acceptToStringField(f);
		         }
		     }).toString();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();

			return super.toString();
		}
	}
	
	
	/**
	 * Override this method if you have specific fields in the entity which are not needed in toString result.
	 * 
	 * @param f the entity Field that is checked
	 * @return true if the field is to be included in toString result
	 */
	protected boolean acceptToStringField(Field f)
	{
		return true;
	}
	
}
