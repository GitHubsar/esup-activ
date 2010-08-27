/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.domain.beans;



import java.util.Date;

import org.esupportail.commons.utils.strings.StringUtils;
import org.springframework.beans.factory.InitializingBean;



/**
 * The class that represent net account.
 */
public class Account implements InitializingBean {
	
	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5854730800181753413L;

	private String oldPassword; 
	
	private String id;
	
    private String harpegeNumber;
    
    private String displayName;
    
	private String birthName;
	
	private String shadowLastChange;
	
	private Date birthDate;
	
	private String password;
	
	private String mail;
	
	private String initialPassword;
	
	private boolean charterAgreement=false;
	
	private String smsAgreement;

	private String code;
	
	private String emailPerso;
	
	private String pager;
	
		
	private String oneRadioValue;
	
	private String oneRadioProcedure;
	
	private String oneChoiceCanal;
	
	
	public String getOneChoiceCanal() {
		return oneChoiceCanal;
	}

	public void setOneChoiceCanal(String oneChoiceCanal) {
		this.oneChoiceCanal = oneChoiceCanal;
	}

	public String getOneRadioValue() {
		return oneRadioValue;
	}

	public void setOneRadioValue(String oneRadioValue) {
		this.oneRadioValue = oneRadioValue;
	}

	public Account() {
		super();
		
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		return id.equals(((Account) obj).getId());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User#" + hashCode() + "[id=[" + id + "], harpegeNumber=[" + harpegeNumber 
		+ "], birthName=[" + birthName + "],displayName=[" + displayName + "],   birthDate=[" + birthDate + "], shadowLastChange=[" + shadowLastChange + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = StringUtils.nullIfEmpty(id);
	}
		
	public String getBirthName() {
		return birthName;
	}

	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}

	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
		
	public String getInitialPassword() {
		return initialPassword;
	}

	public void setInitialPassword(String initialPassword) {
		this.initialPassword = initialPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esupportail.activ.domain.DomainService#changeDisplayName(org.esupportail.activ.domain.beans.Account,
	 *      java.lang.String)
	 */
	

	public String getShadowLastChange() {
		return shadowLastChange;
	}

	public void setShadowLastChange(String shadowLastChange) {
		this.shadowLastChange = shadowLastChange;
	}
	
	public boolean isActivated() {
		return (this.shadowLastChange!=null && this.shadowLastChange.length()!=0);
	}

	public boolean isCharterAgreement() {
		return charterAgreement;
	}

	public void setCharterAgreement(boolean charterAgreement) {
		this.charterAgreement = charterAgreement;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getEmailPerso() {
		return emailPerso;
	}

	public void setEmailPerso(String emailPerso) {
		this.emailPerso = emailPerso;
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOneRadioProcedure() {
		return oneRadioProcedure;
	}

	public void setOneRadioProcedure(String oneRadioProcedure) {
		this.oneRadioProcedure = oneRadioProcedure;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	

	public String getSmsAgreement() {
		return smsAgreement;
	}

	public void setSmsAgreement(String smsAgreement) {
		this.smsAgreement = smsAgreement;
	}

	
	
	

}
