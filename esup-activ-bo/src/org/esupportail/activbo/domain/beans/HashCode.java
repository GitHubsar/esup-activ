package org.esupportail.activbo.domain.beans;

import java.util.HashMap;

import org.springframework.beans.factory.InitializingBean;

public class HashCode extends HashMap<String,HashMap<String,String>> implements InitializingBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HashCode(){
		
	}
	
	public HashMap<String,String> getValueWithKey(String key){
		return this.get(key);
	}
	
	public void putKeyValue(String key,HashMap<String,String> value){
		this.put(key, value);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}