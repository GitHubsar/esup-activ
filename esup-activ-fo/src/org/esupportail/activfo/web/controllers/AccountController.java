/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.web.controllers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.LoginAlreadyExistsException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.activfo.exceptions.AuthentificationException;
import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A visual bean for the welcome page.
 */
public class AccountController extends AbstractContextAwareController implements Serializable {


	private final Logger logger = new LoggerImpl(getClass());
	
	private  Account currentAccount;

	private boolean activ=false;
	private boolean reinit=false;
	private boolean passwChange=false;
	private boolean loginChange=false;
	
	
	private String smsAccepted;
	private String smsNonAccepted;
	
	private String accountIdKey;
	private String accountMailKey;
	private String accountMailPersoKey;
	private String accountPagerKey;
	private String accountDNKey;
	private String accountCodeKey;
	
	
	private String fieldSmsAgreementId;
	
	//liste des champs pour l'affichage des informations personnelles
	private List<BeanField> listBeanPersoInfo;
	
	//liste des attributs pour l'affichage des informations personnelles
	private String attributesInfPerso;
	
	
	//liste g�n�rique d'attributs � valider
	private List<String> attrToValidate;
	
	private String attributesStudentToValidate;
	private String attributesPersonnelToValidate;
	private String attributesOldStudentToValidate;
	
	
	
	//liste g�n�rique des champs pour la validation
	private List<BeanField> listInfoToValidate;
	
	private List<BeanField> listInfoStudentToValidate;
	private List<BeanField> listInfoPersonnelToValidate;
	private List<BeanField> listInfoOldStudentToValidate;
	
	
	
	private List<BeanField> listBeanCanal;
	
	
	//liste des champs correspondant aux procedures
	private List<BeanField> listBeanProcedure;
	
	//liste des champs correspondant aux statuts de l'utilisateur
	private List<BeanField> listBeanStatus;
	
	//champ newpassword
	private BeanField beanNewPassword;
	
	//champ code
	private BeanField beanCode;
	
	//champ login
	private BeanField beanLogin;
	
	//champ Password
	private BeanField beanPassword;
	
	//bean smsAgreement
	private BeanField beanSMSAgreement;
	
	//champ newLogin
	private BeanField beanNewLogin;
	
	
	//decriptif du compte suite � validation
	HashMap<String,String> accountDescr=new HashMap<String,String>();
	
	private String procedureReinitialisation;
	private String procedureActivation;
	private String procedurePasswordChange;
	private String procedureLoginChange;
	
	private String statusStudent;
	private String statusPersonnel;
	private String statusOldStudent;
	
	private boolean interfLogin;
	

	public boolean isInterfLogin() {
		return interfLogin;
	}

	public void setInterfLogin(boolean interfLogin) {
		this.interfLogin = interfLogin;
	}

	/**
	 * Bean constructor.
	 */
	public AccountController() {
		super();
		
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode();
	}
	
	
	/**
	 * @see org.esupportail.activ.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
	}

	/**
	 * @return true if the current user is allowed to view the page.
	 */
	public boolean isPageAuthorized() {
		return true;
	}
	
	public String enter() {
		
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		
		if (currentAccount.getOneRadioProcedure().equals(procedureReinitialisation)){
			reinit=true;
			passwChange=false;
			activ=false;
			loginChange=false;
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedurePasswordChange)){
			passwChange=true;
			reinit=false;
			activ=false;
			loginChange=false;
			return "gotoAuthentification";
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedureLoginChange)){
			passwChange=false;
			reinit=false;
			activ=false;
			loginChange=true;
			return "gotoAuthentification";
		}
		else{
			activ=true;
			reinit=false;
			passwChange=false;
			loginChange=false;
		}
		
		
		if(currentAccount.getOneRadioValue().equals(statusStudent)){
			this.listInfoToValidate=listInfoStudentToValidate;
			attrToValidate=Arrays.asList(attributesStudentToValidate.split(","));
		}
		else if (currentAccount.getOneRadioValue().equals(this.statusPersonnel)){
			this.listInfoToValidate=listInfoPersonnelToValidate;
			attrToValidate=Arrays.asList(attributesPersonnelToValidate.split(","));
		}
		else{
			this.listInfoToValidate=listInfoOldStudentToValidate;
			attrToValidate=Arrays.asList(attributesOldStudentToValidate.split(","));
		}
		return "goToInfoToValidate";
	}
	
		
	public String pushValid() {
		try {
			
			HashMap<String,String> hashInfToValidate;
			hashInfToValidate=this.getMap(listInfoToValidate, attrToValidate);

			logger.info("La validation concerne les donn�es suivantes: "+hashInfToValidate.toString());

			//Attributs concernant les informations personnelles que l'on souhaite afficher
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			accountDescr=this.getDomainService().validateAccount(hashInfToValidate,attrPersoInfo);

			if (accountDescr!=null) {
				
				logger.info("Identification valide");
				
				currentAccount.setId(accountDescr.get(accountIdKey));
				currentAccount.setMail(accountDescr.get(accountMailKey));
				currentAccount.setCode(accountDescr.get(accountCodeKey));
				currentAccount.setEmailPerso(accountDescr.get(accountMailPersoKey));
				currentAccount.setPager(accountDescr.get(accountPagerKey));
				currentAccount.setSmsAgreement(accountDescr.get(fieldSmsAgreementId));
		
				if (currentAccount.getCode()!=null) {
					if (reinit){
						logger.info("Reinitialisation impossible, compte non activ�");
						this.addErrorMessage(null, "IDENTIFICATION.REINITIALISATION.MESSAGE.ACCOUNT.NONACTIVATED");

					}else if(activ){
						logger.info("Construction de la liste des informations personnelles du compte");
						this.buildListPersoInfo(attrPersoInfo);
						this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						return "gotoPersonalInfo";
					}
				}
				else {
					if (reinit){
						logger.info("Construction de la liste des informations personnelles du compte");
						this.buildListPersoInfo(attrPersoInfo);
									
						if (currentAccount.getPager()!=null &&  currentAccount.getEmailPerso()!=null && currentAccount.getSmsAgreement().equals(smsAccepted)){
							this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
							return "gotoChoice"; 	
						}
						
						else if (currentAccount.getPager()==null &&  currentAccount.getEmailPerso()!=null){
							
							currentAccount.setOneChoiceCanal(accountMailPersoKey);
							if (this.getDomainService().getCode(currentAccount.getId(),accountMailPersoKey)){
								addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
								logger.info("Code envoy�");
								return "gotoPushCode";
							}
							else{
								logger.info("Erreur lors de l'envoi du code");
								addErrorMessage(null, "CODE.ERROR.SENDING");
							}
						}
						
						else if (currentAccount.getPager()!=null && currentAccount.getSmsAgreement().equals(smsAccepted)&&  currentAccount.getEmailPerso()==null){
							
							currentAccount.setOneChoiceCanal(accountPagerKey);
							
							if (this.getDomainService().getCode(currentAccount.getId(), accountPagerKey)){
								this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
								logger.info("Erreur lors de l'envoi du code");
								return "gotoPushCode";
							}
							else{
								logger.info("Erreur lors de l'envoi du code");
								addErrorMessage(null, "CODE.ERROR.SENDING");
							}
						}
						
						else{//si les deux sont null
							//Vous n'avez encore jamais renseign� un email perso ou votre numero de portable. Il est impossbile donc de vous envoyer un code de reinitialisation de mot de passe
							addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
							addErrorMessage(null, "IDENTIFICATION.MESSAGE.NONECANAL");
						}
					}
					else if(activ){
						logger.info("Compte d�ja activ�");
						addErrorMessage(null, "IDENTIFICATION.ACTIVATION.MESSAGE.ALREADYACTIVATEDACCOUNT");
					}
				}
			}
			else {
				logger.info("Identifation utilisateur non valide");
				addErrorMessage(null, "IDENTIFICATION.MESSAGE.INVALIDACCOUNT");
			}
		}
		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
		}
		
		return null;
	}
	
	
	public String pushChangeInfoPerso() {
			
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			try{
				logger.info("Mise � jour des informations personnelles");
				
				HashMap<String,String> hashBeanPersoInfo=new HashMap<String,String>();
				Iterator it=listBeanPersoInfo.iterator();
				int i=0;
				while(it.hasNext()){
					BeanField beanPersoInfo=(BeanField)it.next();
					if (beanPersoInfo.getId().equals(fieldSmsAgreementId)){
						if (beanPersoInfo.isValue2()){
							hashBeanPersoInfo.put(attrPersoInfo.get(i), smsAccepted);
						}
						else
							hashBeanPersoInfo.put(attrPersoInfo.get(i), smsNonAccepted);
					}
					else{
						hashBeanPersoInfo.put(attrPersoInfo.get(i), beanPersoInfo.getValue());
					}
						
					i++;
				}
				
								
				if (this.getDomainService().updatePersonalInformations(currentAccount.getId(),currentAccount.getCode(),hashBeanPersoInfo)){
					logger.info("Informations personnelles envoy�es au BO pour mise � jour: "+hashBeanPersoInfo.toString());
					
					this.addInfoMessage(null, "PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL");
					
					if (activ){
						if (isInterfLogin())
							return "gotoLogin";
						else
							return "gotoCharterAgreement";
					}
					else{
						if (isInterfLogin())
							return "gotoLogin";
						else
							return "gotoAccountEnabled";
					}
				}	
			}
			catch (LdapProblemException e) {
				logger.error(e.getMessage());
				addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
			}catch (UserPermissionException e) {
				logger.error(e.getMessage());
				addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
			}
			
			return null;
			
	}
	public String pushAuthentificate(){
		try{
			currentAccount.setId(beanLogin.getValue());
			currentAccount.setOldPassword(beanPassword.getValue());
			//Attributs concernant les informations personnelles que l'on souhaite afficher
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			accountDescr=this.getDomainService().authentificateUser(currentAccount.getId(), currentAccount.getOldPassword(),attrPersoInfo);
			if (accountDescr!=null){
				logger.info("Authentification r�usssie");
				
				currentAccount.setId(accountDescr.get(accountIdKey));
				currentAccount.setMail(accountDescr.get(accountMailKey));
				currentAccount.setCode(accountDescr.get(accountCodeKey));
				currentAccount.setEmailPerso(accountDescr.get(accountMailPersoKey));
				currentAccount.setPager(accountDescr.get(accountPagerKey));
				currentAccount.setSmsAgreement(accountDescr.get(fieldSmsAgreementId));
				
				if (currentAccount.getCode()!=null) {
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildListPersoInfo(attrPersoInfo);
					
					this.addInfoMessage(null, "AUTHENTIFICATION.MESSAGE.VALID");
					
					if (passwChange){
						return "gotoPasswordChange";
					}
					else if (loginChange){
						return "gotoLoginChange";
					}
				}
				else{
					logger.info("Reinitialisation impossible, compte non activ�");
					this.addErrorMessage(null, "AUTHENTIFICATION.MESSAGE.ACCOUNT.NONACTIVATED");
				}
			
			}
			else {
				addErrorMessage(null, "AUTHENTIFICATION.MESSAGE.INVALID");
			}
			
		}catch(AuthentificationException e){
			logger.error(e.getMessage());
			addErrorMessage(null, "AUTHENTIFICATION.MESSAGE.INVALID");
		
		}catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		}
		
		
		return null;
	}
	
	public String pushLogin(){
		try {
			
			if (this.getDomainService().changeLogin(currentAccount.getId(), currentAccount.getCode(), beanNewLogin.getValue())){
				beanNewLogin.setValue("");
				logger.info("Changement de login r�ussi");
				this.addInfoMessage(null, "LOGIN.MESSAGE.CHANGE.SUCCESSFULL");
				
				if (loginChange)
					return "gotoPersonalInfo";
				else if (activ) 
					return "gotoCharter";
				else
					return "gotoAccountEnabled";
			}
			
			else {
				logger.info("Changement de login non effectu�");
				addErrorMessage(null, "LOGIN.MESSAGE.INVALIDLOGIN");
			}
			
		}

		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		}
		catch (LoginAlreadyExistsException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LOGIN.MESSAGE.PROBLEM");
		}
	
		catch (UserPermissionException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
		
		}catch (KerberosException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "KERBEROS.MESSAGE.PROBLEM");
		}

		return null;

	}
	
	public String pushChoice(){
		try{
			
			if (this.getDomainService().getCode(currentAccount.getId(),currentAccount.getOneChoiceCanal())){
				logger.info("Code envoy� par le FO sur le canal choisi de l'utilisateur");
				return "gotoPushCode";
			}
			else{
				logger.info("Code non envoy� par le FO");
				addErrorMessage(null, "CODE.ERROR.SENDING");
			}
		
		}catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		}
		
		return null;
	}
	
	
	public String pushVerifyCode() {
		
		currentAccount.setCode(beanCode.getValue());
		if (this.getDomainService().validateCode(currentAccount.getId(), currentAccount.getCode())){
			this.addInfoMessage(null, "CODE.MESSAGE.CODESUCCESSFULL");
			beanCode.setValue("");
			return "gotoPasswordChange";
		}
		
		addErrorMessage(null, "CODE.MESSAGE.CODENOTVALIDE");
		return null;

	}
	
	/**
	 * JSF callback.
	 * @return A String. gotoPasswordChange
	 */
	public String pushCharterAgreement() {
	
			if (currentAccount.isCharterAgreement()){
				logger.info("Charte accept�e");
				this.addInfoMessage(null, "CHARTER.MESSAGE.AGREE.SUCCESSFULL");
				return "gotoPasswordChange";
			}
			
			logger.info("Charte non accept�e");
			this.addErrorMessage(null, "CHARTER.MESSAGE.AGREE.UNSUCCESSFULL");
			return null;
	}
	
	/**
	 * JSF callback.
	 * @return A String.
	 */
	public String pushChangePassword() {
		try {
			if (this.getDomainService().setPassword(currentAccount.getId(),currentAccount.getCode(),beanNewPassword.getValue())){
				logger.info("Changement de mot de passe r�ussi");
				this.addInfoMessage(null, "PASSWORD.MESSAGE.CHANGE.SUCCESSFULL");
				beanNewPassword.setValue("");
				if (!activ){
				return "gotoPersonalInfo";
				}
				else{
					return "gotoAccountEnabled";
				}
			}
			else {
				logger.info("Changement mot de passe non effectu�");
				addErrorMessage(null, "PASSWORD.MESSAGE.INVALIDLOGIN");
			}
		}

		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		
		}catch (UserPermissionException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
		
		}catch (KerberosException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "KERBEROS.MESSAGE.PROBLEM");
		}

		return null;
	}
	
	private void buildListPersoInfo(List<String>attrPersoInfo){
		for(int i=0;i<attrPersoInfo.size();i++){
			if (attrPersoInfo.get(i).equals(accountDNKey)){
				currentAccount.setDisplayName(accountDescr.get(attrPersoInfo.get(i)));
			}
			listBeanPersoInfo.get(i).setValue(accountDescr.get(attrPersoInfo.get(i)));
			if (attrPersoInfo.get(i).equals(fieldSmsAgreementId)){
				if (accountDescr.get(attrPersoInfo.get(i)).equals(smsAccepted)){
					listBeanPersoInfo.get(i).setValue2(true);
				}
				else{
					listBeanPersoInfo.get(i).setValue2(false);
				}
			}
		}
	}

	public Account getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}
	
	public String getAccountIdKey() {
		return accountIdKey;
	}

	public void setAccountIdKey(String accountIdKey) {
		this.accountIdKey = accountIdKey;
	}

	public String getAccountMailKey() {
		return accountMailKey;
	}

	public void setAccountMailKey(String accountMailKey) {
		this.accountMailKey = accountMailKey;
	}

	public String getAccountDNKey() {
		return accountDNKey;
	}

	public void setAccountDNKey(String accountDNKey) {
		this.accountDNKey = accountDNKey;
	}

	public List<BeanField> getListBeanPersoInfo() {
		return listBeanPersoInfo;
	}

	public void setListBeanPersoInfo(List<BeanField> listBeanPersoInfo) {
		this.listBeanPersoInfo = listBeanPersoInfo;
	}

	public String getAccountCodeKey() {
		return accountCodeKey;
	}

	public void setAccountCodeKey(String accountCodeKey) {
		this.accountCodeKey = accountCodeKey;
	}

	public boolean isReinit() {
		return reinit;
	}

	public void setReinit(boolean reinit) {
		this.reinit = reinit;
	}
	
	

	public String getAttributesInfPerso() {
		return attributesInfPerso;
	}

	public void setAttributesInfPerso(String attributesInfPerso) {
		this.attributesInfPerso = attributesInfPerso;
	}
	
	public BeanField getBeanCode() {
		return beanCode;
	}

	public void setBeanCode(BeanField beanCode) {
		this.beanCode = beanCode;
	}
	
	public boolean isPasswChange() {
		return passwChange;
	}

	public void setPasswChange(boolean passwChange) {
		this.passwChange = passwChange;
	}

	public List<BeanField> getListBeanProcedure() {
		return listBeanProcedure;
	}

	public void setListBeanProcedure(List<BeanField> listBeanProcedure) {
		this.listBeanProcedure = listBeanProcedure;
	}

	
	public List<BeanField> getListBeanStatus() {
		return listBeanStatus;
	}

	public void setListBeanStatus(List<BeanField> listBeanStatus) {
		this.listBeanStatus = listBeanStatus;
	}

	public String getAttributesOldStudentToValidate() {
		return attributesOldStudentToValidate;
	}

	public void setAttributesOldStudentToValidate(
			String attributesOldStudentToValidate) {
		this.attributesOldStudentToValidate = attributesOldStudentToValidate;
	}

	public List<BeanField> getListInfoStudentToValidate() {
		return listInfoStudentToValidate;
	}

	public void setListInfoStudentToValidate(
			List<BeanField> listInfoStudentToValidate) {
		this.listInfoStudentToValidate = listInfoStudentToValidate;
	}

	public List<BeanField> getListInfoPersonnelToValidate() {
		return listInfoPersonnelToValidate;
	}

	public void setListInfoPersonnelToValidate(
			List<BeanField> listInfoPersonnelToValidate) {
		this.listInfoPersonnelToValidate = listInfoPersonnelToValidate;
	}

	public List<BeanField> getListInfoOldStudentToValidate() {
		return listInfoOldStudentToValidate;
	}

	public void setListInfoOldStudentToValidate(
			List<BeanField> listInfoOldStudentToValidate) {
		this.listInfoOldStudentToValidate = listInfoOldStudentToValidate;
	}

	private HashMap<String,String> getMap(List<BeanField> listeInfoToValidate,List<String>attrToValidate){
		
		HashMap<String,String> hashInfToValidate=new HashMap<String,String>();
		Iterator it=listeInfoToValidate.iterator();
		int j=0;
		while(it.hasNext()){
			BeanField beanInfoToValidate=(BeanField)it.next();
			hashInfToValidate.put(attrToValidate.get(j), beanInfoToValidate.getValue());
			beanInfoToValidate.setValue("");//security reason
			j++;
		}
		return hashInfToValidate;
		
	}

	public String getAttributesStudentToValidate() {
		return attributesStudentToValidate;
	}

	public void setAttributesStudentToValidate(String attributesStudentToValidate) {
		this.attributesStudentToValidate = attributesStudentToValidate;
	}

	public String getAttributesPersonnelToValidate() {
		return attributesPersonnelToValidate;
	}

	public void setAttributesPersonnelToValidate(
			String attributesPersonnelToValidate) {
		this.attributesPersonnelToValidate = attributesPersonnelToValidate;
	}

	public List<BeanField> getListInfoToValidate() {
		return listInfoToValidate;
	}

	public void setListInfoToValidate(List<BeanField> listInfoToValidate) {
		this.listInfoToValidate = listInfoToValidate;
	}

	public List<String> getAttrToValidate() {
		return attrToValidate;
	}

	public void setAttrToValidate(List<String> attrToValidate) {
		this.attrToValidate = attrToValidate;
	}

	public String getProcedureReinitialisation() {
		return procedureReinitialisation;
	}

	public void setProcedureReinitialisation(String procedureReinitialisation) {
		this.procedureReinitialisation = procedureReinitialisation;
	}

	public String getProcedureActivation() {
		return procedureActivation;
	}

	public void setProcedureActivation(String procedureActivation) {
		this.procedureActivation = procedureActivation;
	}

	public String getProcedurePasswordChange() {
		return procedurePasswordChange;
	}

	public void setProcedurePasswordChange(String procedurePasswordChange) {
		this.procedurePasswordChange = procedurePasswordChange;
	}

	public String getStatusStudent() {
		return statusStudent;
	}

	public void setStatusStudent(String statusStudent) {
		this.statusStudent = statusStudent;
	}

	public String getStatusPersonnel() {
		return statusPersonnel;
	}

	public void setStatusPersonnel(String statusPersonnel) {
		this.statusPersonnel = statusPersonnel;
	}

	public String getStatusOldStudent() {
		return statusOldStudent;
	}

	public void setStatusOldStudent(String statusOldStudent) {
		this.statusOldStudent = statusOldStudent;
	}

	public boolean isActiv() {
		return activ;
	}

	public void setActiv(boolean activ) {
		this.activ = activ;
	}

	public String getAccountMailPersoKey() {
		return accountMailPersoKey;
	}

	public void setAccountMailPersoKey(String accountMailPersoKey) {
		this.accountMailPersoKey = accountMailPersoKey;
	}

	public BeanField getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanField beanLogin) {
		this.beanLogin = beanLogin;
	}

	public String getAccountPagerKey() {
		return accountPagerKey;
	}

	public void setAccountPagerKey(String accountPagerKey) {
		this.accountPagerKey = accountPagerKey;
	}

	public List<BeanField> getListBeanCanal() {
		return listBeanCanal;
	}

	public void setListBeanCanal(List<BeanField> listBeanCanal) {
		this.listBeanCanal = listBeanCanal;
	}

	public BeanField getBeanSMSAgreement() {
		return beanSMSAgreement;
	}

	public void setBeanSMSAgreement(BeanField beanSMSAgreement) {
		this.beanSMSAgreement = beanSMSAgreement;
	}

	public String getFieldSmsAgreementId() {
		return fieldSmsAgreementId;
	}

	public void setFieldSmsAgreementId(String fieldSmsAgreementId) {
		this.fieldSmsAgreementId = fieldSmsAgreementId;
	}

	public String getSmsAccepted() {
		return smsAccepted;
	}

	public void setSmsAccepted(String smsAccepted) {
		this.smsAccepted = smsAccepted;
	}

	public String getSmsNonAccepted() {
		return smsNonAccepted;
	}

	public void setSmsNonAccepted(String smsNonAccepted) {
		this.smsNonAccepted = smsNonAccepted;
	}

	public boolean isLoginChange() {
		return loginChange;
	}

	public void setLoginChange(boolean loginChange) {
		this.loginChange = loginChange;
	}

	public String getProcedureLoginChange() {
		return procedureLoginChange;
	}

	public void setProcedureLoginChange(String procedureLoginChange) {
		this.procedureLoginChange = procedureLoginChange;
	}

	public BeanField getBeanNewLogin() {
		return beanNewLogin;
	}

	public void setBeanNewLogin(BeanField beanNewLogin) {
		this.beanNewLogin = beanNewLogin;
	}
	
	public BeanField getBeanNewPassword() {
		return beanNewPassword;
	}

	public void setBeanNewPassword(BeanField beanNewPassword) {
		this.beanNewPassword = beanNewPassword;
	}

	public BeanField getBeanPassword() {
		return beanPassword;
	}

	public void setBeanPassword(BeanField beanPassword) {
		this.beanPassword = beanPassword;
	}
	
}
