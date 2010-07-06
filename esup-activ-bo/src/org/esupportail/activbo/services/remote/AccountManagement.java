package org.esupportail.activbo.services.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.esupportail.commons.services.ldap.LdapException;



public interface AccountManagement {
		
	public HashMap<String,String> validateAccount(String number,String birthName,Date birthDate,List<String>attrPersoInfo) throws LdapException;

	public boolean setPassword(String id,String code,final String currentPassword)throws LdapException;
		
	public boolean updateInfoPerso(HashMap<String,String> infoPerso);
	
	//public void updateDisplayName(String displayName,String id,String code);


	//public void setMailPerso(String id,String mailPerso);
	
	/*public int validateCode(String id,String code);*/
	
	/*verification du code mentionné par l'utilisateur*/
	/*public boolean verifyCode(String id,String code);
	
	public void updateMailPerso(String mailPerso);
	
	public void updatePhoneNumber(String phoneNumber);
	
	public putPassword(String password,String id,String newPassword);
	
	*
	*/
}
