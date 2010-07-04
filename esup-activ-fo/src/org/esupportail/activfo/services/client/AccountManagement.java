package org.esupportail.activfo.services.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.esupportail.commons.services.ldap.LdapException;



public interface AccountManagement {
	
	
	public HashMap<String,String> validateAccount(String number,String birthName,Date birthDate,List<String>attrPersoInfo) throws LdapException;

	public boolean setPassword(final String currentPassword,String id,String code)throws LdapException;

	//public void updateDisplayName(String displayName,String id, String code);
	
	//public void setMailPerso(String id,String mailPerso);
	
	//public int validateCode(String id,String code);
}
