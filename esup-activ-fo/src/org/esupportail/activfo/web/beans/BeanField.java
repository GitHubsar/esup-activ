package org.esupportail.activfo.web.beans;

import javax.faces.convert.Converter;

import org.esupportail.activfo.web.validators.Validator;


public interface BeanField<T> {
	
	
	public String getKey();
	
	public void setKey(String key);
	
	public T getValue();
	
	public void setValue(T value);
	
	public String getFieldType();
	
	public void setFieldType(String fieldType);
	
	public String getHelp();
	
	public void setHelp(String help);
	
	public Validator getValidator();
	
	public void setValidator(Validator validator);
	
	public Converter getConverter();
	
	public void setConverter(Converter converter);
	
	public boolean isRequired();
	
	public void setRequired(boolean required);
	
	public String getTypeBean();
	public void setTypeBean(String typeBean);
	
	public boolean isDisabled();
	public void setDisabled(boolean disabled);
	
}
