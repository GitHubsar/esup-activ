package org.esupportail.activbo.services.kerberos;

import org.springframework.beans.factory.InitializingBean;


/**
 * @author aanli
 *
 */
public class KRBAdminTest implements KRBAdmin,InitializingBean {
	
	
	/** 
	 * @see org.esupportail.activbo.services.kerberos.KRBAdmin#add(String, String)
	 */
	public void add(String principal,String passwd)throws KRBPrincipalAlreadyExistsException{
		throw new KRBPrincipalAlreadyExistsException("");
		//return ADDED; 
	}
	
	
	/** 
	 * @see org.esupportail.activbo.services.kerberos.KRBAdmin#del(String)
	 */
	public void del(final String principal) {
		//return DELETED; 
	} 
	
	/** 
	 * @see org.esupportail.activbo.services.kerberos.KRBAdmin#changePasswd(String, String)
	 */
	public void changePasswd(String principal,String passwd)throws KRBException,KRBIllegalArgumentException{
		//return CHANGED;
	}
	
	
	/** 
	 * @see org.esupportail.activbo.services.kerberos.KRBAdmin#changePasswd(String, String, String)
	 */
	public void changePasswd(String principal, String oldPasswd, String newPasswd){
		//return CHANGED;
	}
			
	/** 
	 * @see org.esupportail.activbo.services.kerberos.KRBAdmin#exists(String)
	 */
	public boolean exists(String principal) {
		return true;
	}


	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}	

}
